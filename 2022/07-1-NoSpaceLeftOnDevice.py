import os


# if  the size == -1 then the element is a directory otherwise it is a file
# The size of a directory gets determined and fixed upon the first call of get_size()
class element:
    def __init__(self, name, size, elements):
        self.name = name
        self.size = size
        self.elements = elements

    def get_size(self):
        if self.size != -1:
            return self.size
        else:
            self.size = sum([e.get_size() for e in self.elements.values()])
            return self.size

    def get_name(self):
        return self.name

    def get_element(self, name):
        return self.elements[name]

    def cd(self, path):
        if path == []:
            return self
        else:
            return self.get_element(path[0]).cd(path[1:])

    def add_element(self, name, size):
        new_element = element(name, size, {})
        self.elements[name] = new_element


def get_input():
    inputfile = "./input/i" + os.path.basename(__file__)[0:2] + ".txt"

    output = []
    with open(inputfile, "r") as f:
        output = [c for c in f.read().split('\n') if c != ""]

    root = element("/", -1, {})
    folders = [root]

    cur_path = []
    cur_el = root

    for i in range(1, len(output)):
        cur_c = output[i].split()
        if "$" == cur_c[0]:
            if "cd" == cur_c[1]:
                if cur_c[2] == "..":
                    cur_path.pop()
                else:
                    cur_path.append(cur_c[2])
            else:
                cur_el = root.cd(cur_path)
        else:
            if cur_c[0] == "dir":
                cur_el.add_element(cur_c[1], -1)
                folders.append(cur_el.get_element(cur_c[1]))
            else:
                cur_el.add_element(cur_c[1], int(cur_c[0]))
    return root, folders


if __name__ == "__main__":
    root, folders = get_input()

    total = 0
    for i in folders:
        size = i.get_size()
        if size <= 100000:
            total += size
    print(total)
