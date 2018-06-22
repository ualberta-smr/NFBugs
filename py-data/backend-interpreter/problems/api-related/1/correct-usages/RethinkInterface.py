from multiprocessing import Queue
from os import environ
from queue import Empty
from sys import exit as sysexit
from time import sleep, time

import rethinkdb

class RethinkInterface:

  def pattern(self, output_data):
        
        # ...
        
        self.logger.send(["dbprocess",
                "".join(("There is no job with an id of ", output_data[0])),
                30,
                time()
            ])
