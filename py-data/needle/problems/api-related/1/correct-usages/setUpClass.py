from __future__ import absolute_import

import os
import sys

if sys.version_info > (2, 7):
    from unittest import TestCase
else:
    from unittest2 import TestCase

from PIL import Image

from needle.diff import ImageDiff
from needle.driver import NeedleWebDriver


    @classmethod
    def setUpClass(cls):
        cls.driver = cls.get_web_driver()
        super(NeedleTestCase, cls).setUpClass()

    @classmethod
    def tearDownClass(cls):
        super(NeedleTestCase, cls).tearDownClass()
        cls.driver.quit()

    @classmethod
    def get_web_driver(cls):
        return NeedleWebDriver(cls.driver_command_executor,
                               cls.driver_desired_capabilities,
                               cls.driver_browser_profile)
