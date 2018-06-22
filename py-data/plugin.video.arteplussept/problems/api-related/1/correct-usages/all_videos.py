from xbmcswift2 import Plugin
from xbmcswift2 import actions
import requests
import os
import urllib2
import time
import datetime

@plugin.route('/all', name='all')
def all_videos():
    plugin.set_content('tvshows')
    items = [create_item(program) for program in get_last7days() if program.get('video') is not None]
    return plugin.finish(items)
