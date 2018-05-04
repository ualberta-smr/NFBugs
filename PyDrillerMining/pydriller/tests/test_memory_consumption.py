# Copyright 2018 Davide Spadini
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

import os
import psutil
import json
import requests
import sys
if 'TRAVIS' in os.environ:
    import logging
    logging.basicConfig(format='%(asctime)s - %(levelname)s - %(message)s', level=logging.INFO)
    webhook_url = os.environ['WEBHOOK_URL']
from pydriller.repository_mining import RepositoryMining
from datetime import datetime


def test_memory():
    if 'TRAVIS' not in os.environ:
        return

    diff_with_nothing, all_commits_with_nothing = mine(0)
    diff_with_files, all_commits_with_files = mine(1)
    diff_with_everything, all_commits_with_everything = mine(2)

    logs_and_post_on_slack(diff_with_nothing, all_commits_with_nothing, diff_with_files,
                           all_commits_with_files, diff_with_everything, all_commits_with_everything)


def logs_and_post_on_slack(diff_with_nothing, all_commits_with_nothing, diff_with_files,
                           all_commits_with_files, diff_with_everything, all_commits_with_everything):

    text = "*PYTHON V{}.{}*\n" \
           "*Max memory (MB)*\n" \
           "With nothing: {}, with files: {}, with everything: {} \n" \
           "*Min memory (MB)*\n" \
           "With nothing: {}, with files: {}, with everything: {} \n" \
           "*Time*\n" \
           "With nothing: {}:{}:{}, with files: {}:{}:{}, with everything: {}:{}:{} \n" \
           "*Total number of commits*: {}\n" \
           "*Commits per second:*\n" \
            "With nothing: {}, with files: {}, with everything: {}"

    slack_data = {
        'text': text.format(
                sys.version_info[0], sys.version_info[1],
                max(all_commits_with_nothing), max(all_commits_with_files), max(all_commits_with_everything),
                min(all_commits_with_nothing), min(all_commits_with_files), min(all_commits_with_everything),
                diff_with_nothing.seconds // 3600, (diff_with_nothing.seconds % 3600) // 60, diff_with_nothing.seconds % 60,
                diff_with_files.seconds // 3600, (diff_with_files.seconds % 3600) // 60, diff_with_files.seconds % 60,
                diff_with_everything.seconds // 3600, (diff_with_everything.seconds % 3600) // 60, diff_with_everything.seconds % 60,
                len(all_commits_with_nothing),
                len(all_commits_with_nothing) / diff_with_nothing.seconds,
                len(all_commits_with_files) / diff_with_files.seconds,
                len(all_commits_with_everything) / diff_with_everything.seconds
        )}
    requests.post(
        webhook_url, data=json.dumps(slack_data),
        headers={'Content-Type': 'application/json'}
    )


def mine(_type):
    p = psutil.Process(os.getpid())
    dt1 = datetime(2015, 1, 1)
    dt2 = datetime(2016, 1, 1)
    all_commits = []

    start = datetime.now()
    for commit in RepositoryMining('test-repos/hadoop',
                                   since=dt1,
                                   to=dt2).traverse_commits():
        memory = p.memory_info()[0] / (2 ** 20)
        all_commits.append(memory)

        h = commit.author.name

        if _type == 0:
            continue

        for mod in commit.modifications:
            a = mod.old_path

            if _type == 2:
                dd = mod.diff
    end = datetime.now()

    diff = end - start

    return diff, all_commits