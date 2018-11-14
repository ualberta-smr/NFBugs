from __future__ import absolute_import

from contextlib import contextmanager
import os
import sys

if sys.version_info > (2, 7):
    from unittest import TestCase
else:
    from unittest2 import TestCase

from PIL import Image

from needle.diff import ImageDiff
from needle.driver import NeedleWebDriver, NeedleWebElement


class NeedleTestCase(TestCase):
    def assertScreenshot(self, element_or_selector, filename, threshold=0.1):
        with self.compareScreenshot(element_or_selector, filename, threshold=threshold):
            pass
