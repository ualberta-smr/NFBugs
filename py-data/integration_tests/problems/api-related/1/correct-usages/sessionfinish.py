import collections

import pytest

from cfme.utils import log
from cfme.utils.appliance import get_or_create_current_appliance

def pytest_sessionfinish(session, exitstatus):
    c = collections.Counter()
    results = ['total: {}'.format(sum(c.values()))] + [
        '{}: {}'.format(k, v) for k, v in c.items()]
