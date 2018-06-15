from xbmcswift2 import Plugin
from xbmcswift2 import actions
import requests
import os
import urllib2
import time
import datetime

def create_item(data):
    video = data.get('video')

    item = {
      
        'info': {
            'country': [country['label'] for country in video.get('productionCountries')],
           
        },
       }
        
    return item
