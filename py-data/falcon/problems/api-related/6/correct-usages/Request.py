from datetime import datetime

try:
    import socket
    NativeStream = socket._fileobject  # pylint: disable=E1101
except AttributeError:
    import io
    NativeStream = io.BufferedReader

import mimeparse
import six

from falcon.errors import *  
from falcon import util
from falcon.util.uri import parse_query_string, parse_host, unquote_string
from falcon import request_helpers as helpers

from six.moves import http_cookies
SimpleCookie = http_cookies.SimpleCookie

class Request(object):

  def _parse_rfc_forwarded(self):

        addr = []

        for forwarded in self.env['HTTP_FORWARDED'].split(','):
            for param in forwarded.split(';'):
                key, _, val = param.strip().partition('=')
                
                # ...

        return addr
