import collections

import pytest

from cfme.utils import log
from cfme.utils.appliance import get_or_create_current_appliance

def pytest_sessionfinish(session, exitstatus):
    c = collections.Counter()
    for test in test_tracking:
        c[_test_status(test)] += 1
    # Prepend a total to the summary list
    results = ['total: {}'.format(sum(c.values()))] + [
        '{}: {}'.format(k, v) for k, v in c.items()]
    # Then join it with commas
    summary = ', '.join(results)
    logger().info(log.format_marker('Finished test run', mark='='))
    logger().info(log.format_marker(str(summary), mark='='))
