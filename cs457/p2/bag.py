class Bag:
    def __init__(self, index, size):
        self.size = size
        self.index = index
        self.space = size
        self.items = set()

    def pack(self, item):
        item.bag = self.index
        self.space -= item.size
        self.items.add(item)

    def unpack(self, item):
        item.bag = None
        self.space += item.size
        self.items.discard(item)

    def __str__(self):
        retval = ""
        for item in self.items:
            retval += "{} ".format(item.name)
        return retval
