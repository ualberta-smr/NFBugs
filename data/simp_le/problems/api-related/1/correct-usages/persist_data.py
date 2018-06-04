import abc
import argparse
import collections
import contextlib
import datetime
import doctest
import hashlib
import errno
import logging
import os
import re
import shlex
import shutil
import subprocess
import sys
import tempfile
import time
import traceback
import unittest

import six
from six.moves import zip  # pylint: disable=redefined-builtin

from cryptography.hazmat.backends import default_backend
from cryptography.hazmat.primitives import serialization
from cryptography.hazmat.primitives.asymmetric import rsa

import mock
import OpenSSL
import pytz
import requests

from acme import client as acme_client
from acme import crypto_util
from acme import challenges
from acme import errors as acme_errors
from acme import jose
from acme import messages

def persist_data(args, existing_data, new_data):
    """Persist data on disk.
    Uses all selected plugins to save certificate data to disk.
    """
    for plugin_name in args.ioplugins:
        plugin = IOPlugin.registered[plugin_name]
        if any(persisted and existing != new
               for persisted, existing, new in
               zip(plugin.persisted(), existing_data, new_data)):
            plugin.save(new_data)
