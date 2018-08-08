import datetime
import decimal
import hashlib
import logging
import operator
import re
import sys
import threading
import uuid
from bisect import bisect_left
from bisect import bisect_right
from collections import deque
from collections import namedtuple
import sqlite3
try:
    from collections import OrderedDict
except ImportError:
    OrderedDict = dict
from copy import deepcopy
from functools import wraps
from inspect import isclass

class SqliteDatabase(Database):

  def _connect(self, database, **kwargs):
        conn = sqlite3.connect(database, **kwargs)
        try:
            # ...
        except:
            conn.close()
            raise
        return conn
