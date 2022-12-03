import os


def get_template():
    with open("template.py", "r") as f:
        return f.read()
    
def create_file(name, content):
    with open(name, "w") as f:
        return f.write(content)


if __name__ == "__main__":
    template = get_template()
    start_value = 4

    for i in range(start_value, 26):
        create_file("{:02d}-1.py".format(i), template)
        create_file("{:02d}-2.py".format(i), template)