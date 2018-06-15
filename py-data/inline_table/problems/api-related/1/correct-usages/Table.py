import collections
import copy
import re

from docutils.parsers.rst.tableparser \
    import SimpleTableParser as DocutilsSimpleTableParser
from docutils.parsers.rst.tableparser \
    import GridTableParser as DocutilsGridTableParser
from docutils.parsers.rst.tableparser \
    import TableMarkupError as DocutilsTableMarkupError
from docutils.statemachine import StringList

 class Table:
  
  def select_all(self, **condition):
        row_generator = self.__select(condition, raise_error=False)
        return [row for row in row_generator]
