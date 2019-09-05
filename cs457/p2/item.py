class Item(object):
    def __init__(self, name, itemid, size, bags):
        self.name = name
        self.index = itemid
        self.size = int(size)
        self.bag = None
        self.valid_bags = set()
        for bag in bags:
            self.valid_bags.add(bag)
        self.valid_bag_count = len(self.valid_bags)
        self.arc_stack = []
        self.conflicts = []
        self.num_conflicts = 0

    def check_arc(self, bag):
        if self.bag != None:
            return True
        if bag in self.valid_bags and self.valid_bag_count == 1:
            return False
        else:
            return True

    def prune_arc(self, bag):
        if bag in self.valid_bags:
            self.arc_stack.append(bag)
            self.valid_bags.discard(bag)
            self.valid_bag_count -= 1
        else:
            self.arc_stack.append(None)

    def restore_arc(self):
        bag = self.arc_stack.pop()
        if bag != None:
            self.valid_bags.add(bag)
            self.valid_bag_count += 1


