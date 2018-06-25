from io import BytesIO
from json import loads
from time import strptime, strftime
from subprocess import call
from os import makedirs
from os.path import expanduser, split
from urllib.request import urlopen

from PIL import Image

from utils import get_desktop_environment
from multiprocessing import Pool, cpu_count
from itertools import product

def download_chunk(args):
    x, y, latest = args
    url_format = "http://himawari8.nict.go.jp/img/D531106/{}d/{}/{}_{}_{}.png"

    with urlopen(url_format.format(level, width, strftime("%Y/%m/%d/%H%M%S", latest), x, y)) as tile_w:
        tiledata = tile_w.read()

    return (x, y,tiledata)
    
def main():
    p = Pool(cpu_count() * level)
    res = p.map(download_chunk, product(range(level), range(level), (latest,)))
