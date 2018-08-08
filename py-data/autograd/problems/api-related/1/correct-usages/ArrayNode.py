from __future__ import absolute_import
import numpy as np

from autograd.core import (Node, FloatNode, primitive, cast,
                           differentiable_ops, nondifferentiable_ops, getval)
from . import numpy_wrapper as anp
from .use_gpu_numpy import use_gpu_numpy

class ArrayNode(Node):

  @primitive
  def primitive_sum_arrays(*arrays):
      new_array = type(new_array_node(arrays[0], [])).zeros_like(arrays[0]) 
      for array in arrays:
          if isinstance(array, SparseArray):
                  new_array[array.idx] += array.val
           
