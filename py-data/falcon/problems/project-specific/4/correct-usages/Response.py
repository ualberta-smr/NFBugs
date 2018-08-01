import six

from falcon.response_helpers import header_property, format_range
from falcon.util import dt_to_http, uri

class Response(object):

  def set_headers(self, headers):
     
    content_location = header_property('Content-Location', 'Sets the Content-Location header.', uri.encode)
    
    # ...
