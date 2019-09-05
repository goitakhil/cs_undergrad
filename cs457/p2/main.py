#!/usr/bin/env python3
import argparse
import random
from random import randint
from item import Item
from bag import Bag

class Main:
    def __init__(self):
        self.expanded = 0
        self.arc_enabled = True

    def main(self):
        parser = argparse.ArgumentParser(description='Grocery Bag solver')
        parser.add_argument('filename', nargs=1)
        parser.add_argument('--local', help="Use local search", action="store_true")
        parser.add_argument('--slow', help="Disable arc consistency", action="store_true")
        args = parser.parse_args()
        infile = open(args.filename[0], 'r')
        self.num_bags = int(infile.readline().strip())
        self.bag_size = int(infile.readline().strip())
        self.bags = []
        for i in range(self.num_bags):
            self.bags.append(Bag(i, self.bag_size))
        tmp_items = {}
        self.items = []
        line = infile.readline().strip()
        totalsize = 0
        self.itemcount = 0
        item_names = {}
        while line != '':
            fields = line.split()
            fields.insert(1,self.itemcount)
            item_names[fields[0]] = self.itemcount
            tmp_items[fields[0]] = fields[1:]
            line = infile.readline().strip()
            self.itemcount += 1
        for name, data in tmp_items.items():
            self.items.append(Item(name, data[0], data[1], self.bags))
            totalsize += int(data[1])
            if (totalsize > self.bag_size * self.num_bags) or int(data[1]) > self.bag_size:
                print("failure")
                return
        for name, data in tmp_items.items():
            if len(data) > 2 and data[2] == '+':
                self.items[int(data[0])].conflicts = set()
                for item in self.items:
                    self.items[int(data[0])].conflicts.add(item)
                self.items[int(data[0])].conflicts.discard(self.items[int(data[0])])
                if len(data) > 3:
                    for key in data[3:]:
                        self.items[int(data[0])].conflicts.discard(item_names[key])
            else:
                self.items[int(data[0])].conflicts = set()
                if len(data) > 3:
                    for key in data[3:]:
                        self.items[int(data[0])].conflicts.add(self.items[item_names[key]])
        for item in self.items:
            for conflicting_item in item.conflicts:
                conflicting_item.conflicts.add(item)
            item.num_conflicts = len(item.conflicts)

        self.todo = [x for x in range(self.itemcount)]
        self.todo = sorted(self.todo, key=(lambda index: self.items[index].num_conflicts + self.items[index].size))
        if args.local:
            self.local_search()
        else:
            if args.slow:
                self.arc_enabled = False
            self.recurse()
    
        print("failure")
        exit(1)
    
    def recurse(self):
        if len(self.todo) == 0:
            print("success")
            for bag in self.bags:
                print(bag)
            exit(0)
        item = self.items[self.todo.pop()]
        tried_empty = False
        for bag in item.valid_bags:
            if len(bag.items) > 0 and bag.space - item.size >= 0:
                forward_arc = True
                if self.arc_enabled:
                    for conflict in item.conflicts:
                        if not conflict.check_arc(bag):
                            forward_arc = False
                            break
                if forward_arc:
                    bag.pack(item)
                    for conflict in item.conflicts:
                        conflict.prune_arc(bag)
                    self.recurse()
                    bag.unpack(item)
                    for conflict in item.conflicts:
                        conflict.restore_arc()
            elif not tried_empty and len(bag.items) == 0:
                tried_empty = True
                forward_arc = True
                if self.arc_enabled:
                    for conflict in item.conflicts:
                        if not conflict.check_arc(bag):
                            forward_arc = False
                            break
                if forward_arc:
                    bag.pack(item)
                    for conflict in item.conflicts:
                        conflict.prune_arc(bag)
                    self.recurse()
                    bag.unpack(item)
                    for conflict in item.conflicts:
                        conflict.restore_arc()
        self.todo.append(item.index)

    def local_search(self):
        for item in self.items:
            index = randint(0, self.num_bags-1)
            self.bags[index].pack(item)
        score = self.local_check()
        while score > 0:
            item = self.items[randint(0, self.itemcount-1)]
            while self.local_score(self.bags[item.bag]) == 0:
                item = self.items[randint(0, self.itemcount-1)]
            trade = None
            curbag = self.bags[item.bag]
            best = None
            best_score = 9999999999999999999999999999999999999
            curbag.unpack(item)
            for bag in self.bags:
                if bag == curbag:
                    continue
                bag.pack(item)
                this_score = self.local_check()
                if this_score < best_score:
                    best_score = this_score
                    best = bag
                    trade = None
                for other in bag.items:
                    bag.unpack(other)
                    curbag.pack(other)
                    this_score = self.local_check()
                    if this_score < best_score:
                        best_score = this_score
                        best = bag
                        trade = other
                    curbag.unpack(other)
                    bag.pack(other)
                bag.unpack(item)

            best.pack(item)
            if trade != None:
                best.unpack(trade)
                curbag.pack(trade)
            score = self.local_check()
        print("success")
        for bag in self.bags:
            print(bag)
        exit(0)

    def local_score(self, bag):
        confs = 0
        for item in bag.items:
            confs += len(item.conflicts.intersection(bag.items))
        for bag in self.bags:
            if bag.space < 0:
                confs += -(bag.space)
        return confs
    
    def local_check(self):
        confs = 0
        for item in self.items:
            confs += len(item.conflicts.intersection(self.bags[item.bag].items))
        for bag in self.bags:
            if bag.space < 0:
                confs += -(bag.space)
        return confs


if __name__ == '__main__':
    runmain = Main()
    runmain.main()
