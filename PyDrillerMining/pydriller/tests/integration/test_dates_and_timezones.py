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

import logging
logging.basicConfig(format='%(asctime)s - %(levelname)s - %(message)s', level=logging.INFO)

from pydriller.repository_mining import RepositoryMining

from datetime import datetime, timezone, timedelta


def test_one_timezone():
    lc = list(RepositoryMining('test-repos/git-2/', single='29e929fbc5dc6a2e9c620069b24e2a143af4285f').traverse_commits())

    to_zone = timezone(timedelta(hours=2))
    dt = datetime(2016, 4, 4, 13, 21, 25, tzinfo=to_zone)

    assert dt == lc[0].author_date


def test_between_dates_reversed():
    lc = list(
        RepositoryMining('test-repos/git-4/', single='375de7a8275ecdc0b28dc8de2568f47241f443e9').traverse_commits())

    to_zone = timezone(timedelta(hours=-4))
    dt = datetime(2016, 10, 8, 17, 57, 49, tzinfo=to_zone)

    assert dt == lc[0].author_date
