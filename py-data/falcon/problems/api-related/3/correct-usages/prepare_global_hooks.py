import inspect
import re
from functools import wraps

from falcon import responders, HTTP_METHODS
import falcon.status_codes as status

def prepare_global_hooks(hooks):
    if hooks is not None:
        for action in hooks:
            if not callable(action):
                raise TypeError('One or more hooks are not callable')

    return hooks
