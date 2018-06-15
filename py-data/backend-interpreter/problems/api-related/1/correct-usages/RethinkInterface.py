from multiprocessing import Queue
from os import environ
from queue import Empty
from sys import exit as sysexit
from time import sleep, time

import rethinkdb

class RethinkInterface:

  def _send_output(self, output_data):
        
        try:
            output_job = rethinkdb.db("Brain").table("Jobs").get(
                output_data[0]
            ).run(self.rethink_connection)
        except rethinkdb.ReqlDriverError as ex:
            self.logger.send(["dbprocess",
                "".join(("Could not access Jobs Table: ", str(ex))),
                30,
                time()
            ])
        #if the job has an entry add the output to the output table
        if output_job != None:
            output_entry = {
                "OutputJob": output_job,
                "Content": output_data[1]
            }
            try:
                rethinkdb.db("Brain").table("Outputs").insert(
                    output_entry,
                    conflict="replace"
                ).run(self.rethink_connection)
            except rethinkdb.ReqlDriverError as ex:
                self.logger.send(["dbprocess",
                    "".join(("Could not write output to database", str(ex))),
                    30,
                    time()
                ])
        else:
            self.logger.send(["dbprocess",
                "".join(("There is no job with an id of ", output_data[0])),
                30,
                time()
            ])
