import sys, operator
import numpy
from scipy import sparse
import scipy.sparse
from theano.printing import Print

from .. import gof
from .. import tensor
from .. import compile
from .. import scalar

class StructuredDotCSC(gof.Op):

   def c_code(self, node, name, (a_val, a_ind, a_ptr, a_nrows, b), (z,), sub):
      
      typenum_z = node.outputs[0].type.dtype_specs()[-1] # retrieve dtype number
      
        {
            if (%(z)s) Py_DECREF(%(z)s);
            npy_intp dims[] = {0,0};
            dims[0] = ((npy_int32 *)%(a_nrows)s->data)[0];
            dims[1] = %(b)s->dimensions[1];
            %(z)s = (PyArrayObject*) PyArray_SimpleNew(2, dims, %(typenum_z)s);
        }
        {
            // sparse array has size MxK, dense KxN, output MxN
            npy_intp M = %(z)s->dimensions[0];
            npy_intp N = %(z)s->dimensions[1];
            npy_intp K = %(b)s->dimensions[0];
           
            dtype_%(z)s*     __restrict__ Dz   = (dtype_%(z)s*)%(z)s->data;
            
           //clear the output array
            memset(Dz, 0, M*N*sizeof(dtype_%(z)s));
 
        }
  
