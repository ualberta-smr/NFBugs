import json
import subprocess
from optparse import make_option
import yaml

from django.conf import settings
from django.core.management.base import BaseCommand
from django.utils import timezone

from salmon.apps.monitor import models, utils


def handle(self, *args, **options):
        config = self.load_salmon_checks()
        if options['fake']:
            self.stdout.write("Printing checks [fake mode] ...")
        else:
            self.stdout.write("Running checks...")

        self.active_checks = []
        for target, functions in config.items():
            for func_name, func_opts in functions.items():
                cmd = utils.build_command(target, func_name)
                if options['fake']:
                    self.stdout.write("target: %s -- cmd: %s" % (target, cmd))
                    self.stdout.write("    func_name: %s" % func_name)
                    self.stdout.write("    func_opts: %s" % func_opts)

                else:
                    self._run_cmd(target, func_name, func_opts, cmd)
        if not options['fake']:
            self.cleanup()

 def cleanup(self):
        """
        Flag inactive checks and remove old results from database.
        """
        inactive_checks = models.Check.objects.exclude(
            pk__in=self.active_checks)
        self.stdout.write("{} checks deactivated".format(
            inactive_checks.count()))
        inactive_checks.update(active=False)

        self.stdout.write("Removing old results...")
        now = datetime.datetime.now()
        expiration_date = now - datetime.timedelta(
            minutes=settings.EXPIRE_RESULTS)
        models.Results.objects.filter(timestamp__lt=expiration_date).delete()
