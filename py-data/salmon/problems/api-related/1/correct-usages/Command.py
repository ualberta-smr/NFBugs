import json
import subprocess
from optparse import make_option
import yaml

from django.conf import settings
from django.core.management.base import BaseCommand
from django.utils import timezone

from salmon.apps.monitor import models, utils

class Command(BaseCommand):

    def pattern(self):
        now = datetime.datetime.now()
        expiration_date = now - datetime.timedelta(
            minutes=settings.EXPIRE_RESULTS)
        models.Results.objects.filter(timestamp__lt=expiration_date).delete()
