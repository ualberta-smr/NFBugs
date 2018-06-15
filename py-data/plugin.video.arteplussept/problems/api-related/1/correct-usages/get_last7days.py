from xbmcswift2 import Plugin
from xbmcswift2 import actions
import requests
import os
import urllib2
import time
import datetime

def flatten(l):
  return [item for sublist in l for item in sublist]
  
def get_last7days():
    return flatten([get_day(date) for (date, _) in get_dates()])
