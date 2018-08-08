import logging
import time
import requests
import json
import urllib3
try:
    from urllib import urlencode
except ImportError:
    from urllib.parse import urlencode

from .exceptions import TransportError, HTTP_EXCEPTIONS, ConnectionError

class Urllib3HttpConnection(Connection):
    def pattern(self, method, url, params=None, body=None, host='localhost', port=9200, **kwargs):
        # ...
        
        self.pool = urllib3.HTTPConnectionPool(host, port=port)
        url = self.url_prefix + url
        if params:
            url = '%s?%s' % (url, urlencode(params or {}))
        
        response = self.pool.urlopen(method, url, body)
        

  
