from datetime import datetime

try:
    import socket
    NativeStream = socket._fileobject
except AttributeError:  
    import io
    NativeStream = io.BufferedReader

import mimeparse
import six

from falcon.exceptions import HTTPBadRequest
from falcon import util
from falcon import request_helpers as helpers

class Request(object):

    @property
    def headers(self):
        return dict([(k.replace('_', '-'), v) for k, v in self._headers.items()])
