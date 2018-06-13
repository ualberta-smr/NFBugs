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
        """assert-style variant of compareScreenshot context manager
        compareScreenshot() can be considerably more efficient for recording baselines by avoiding the need
        to load pages before checking whether we're actually going to save them. This function allows you
        to continue using normal unittest-style assertions if you don't need the efficiency benefits
        """

        with self.compareScreenshot(element_or_selector, filename, threshold=threshold):
            pass

    @contextmanager
    def compareScreenshot(self, element_or_selector, filename, threshold=0.1):
        """
        Assert that a screenshot of an element is the same as a screenshot on disk,
        within a given threshold.
        :param element_or_selector:
            Either a CSS selector as a string or a
            :py:class:`~needle.driver.NeedleWebElement` object that represents
            the element to capture.
        :param name:
            A name for the screenshot, which will be appended with ``.png``.
        :param threshold:
            The threshold for triggering a test failure.
        """

        if not isinstance(filename, basestring):
            raise NotImplementedError

        baseline_file = os.path.join(self.baseline_directory, '%s.png' % filename)
        output_file = os.path.join(self.output_directory, '%s.png' % filename)

        if self.capture and os.path.exists(baseline_file):
            self.skipTest('Not capturing %s, image already exists. If you '
                          'want to capture this element again, delete %s'
                          % (filename, baseline_file))

        yield

        if not isinstance(element_or_selector, NeedleWebElement):
            element = self.driver.find_element_by_css_selector(element_or_selector)
        else:
            element = element_or_selector

        if self.capture:
            element.get_screenshot().save(baseline_file)
            return
        else:
            screenshot = element.get_screenshot()
            screenshot.save(output_file)

            baseline_image = Image.open(baseline_file)

            diff = ImageDiff(screenshot, baseline_image)
            distance = abs(diff.get_distance())
            if distance > threshold:
                raise AssertionError("The saved screenshot for '%s' did not match "
                                     "the screenshot captured (by a distance of %.2f)"
                                     % (filename, distance))
