import argparse
import random


parser = argparse.ArgumentParser()

parser.add_argument('--number', help="Number of random line you want", required=True, type=int)
parser.add_argument('--file', help="File to which you want to create / append the lines", required=True, type=str)
parser.add_argument('--noappend', help="Add this parameter to erase the file, instead of append the lines to", action='store_true')

args = parser.parse_args()

mode = 'w' if args.noappend else 'a'
file = open(args.file, mode)

base_line = "INSERT INTO public.\"character\"({}) VALUES\n"


def generateNewName(length):
    return "\'{}\'".format(''.join([chr(random.randint(65, 90)) for i in range(length)]))


def generateLine():
    data_line = [
        ('stamina', str(80)),
        ('name', generateNewName(16)),
        ('level', str(100)),
        ('health', str(80)),
        ('mana', str(80)),
        ('password', '\'$2a$10$PYyJXwPKBOzic3JIHCVd5uNIS5.DplEyWtkDPnhseetf0Sv8LovAC\''),
        ('mount_id', str(random.randint(1, 10))),
        ('class_id', str(random.randint(1, 12))),
        ('isadmin', 'true' if random.randint(0, 1) else 'false'),
    ]
    return data_line


file.write(base_line.format(', '.join(map(lambda x: x[0], generateLine()))))

lines = []

HAS_FAILED = False

try:
    from tqdm import tqdm
except Exception as e:
    HAS_FAILED = True

for i in range(args.number) if HAS_FAILED else tqdm(range(args.number)):
    data = generateLine()
    lines.append(', '.join(map(lambda x: x[1], data)))


file.write('({});'.format('),\n('.join(lines)))

file.close()
print('Writed {} lines to file {}'.format(args.number, args.file))
