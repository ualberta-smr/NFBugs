import hilbert_curve as hc
from PIL import Image
import math
from time import sleep
import alsaaudio
import numpy as np
from threading import Thread, Lock

def main():

    while True:
        img = Image.open(input_file).convert("L")
        pixels = img.load()
        output = [
                pixels[hc.d2xy(math.log(x * y, 2), i)]
                for i in range(x*x)
                ]
