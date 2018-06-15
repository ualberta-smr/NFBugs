import numpy as np
import pandas as pd
import time
from sklearn.feature_extraction.text import TfidfVectorizer
import string
import warnings
warnings.filterwarnings('ignore')
from contextlib import contextmanager
import mysql.connector
from sqlalchemy import create_engine
import pygsheets
from tqdm import tqdm
import yaml
import os
import binascii
import random
from numba import jit

class TextSim(object): 
 
 def create_shingles(self, data):
        text_list = list(data[self.text_column])
        shingles_all = [self._create_shingle(text.split(" ")) for text in tqdm(text_list)]
        maxshingleID = max([max(sublist) for sublist in shingles_all])
        return shingles_all, maxshingleID
