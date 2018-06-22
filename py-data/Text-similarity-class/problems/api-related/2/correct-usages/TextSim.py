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

   def _create_shingle(self, doc):
        shinglesInDoc = set()
        
        for index in range(0, len(doc) - (self.shinglesize - 1)):
            shingle = " ".join(doc[index:(index + self.shinglesize)]) 
            
            # ... 
            
        return shinglesInDoc
