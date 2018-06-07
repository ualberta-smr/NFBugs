import sys, operator
import numpy
from scipy import sparse
import scipy.sparse
from theano.printing import Print

from .. import gof
from .. import tensor
from .. import compile
from .. import scalar

 def c_code(self, node, name, (a_val, a_ind, a_ptr, a_nrows, b), (z,), sub):
        """
        C-implementation of the dot product of the sparse matrix A and matrix B.
        @param a_val: non-zero values of the sparse matrix
        @param a_ind: column indices of the non-null values (.indices of a scipy.csc_matrix)
        @param a_ptr: a_ptr indicates col indices for col. i are in the range a_ptr[i]:a_ptr[i+1]
        @param n_rows: number of rows of sparse matrix
        @param b: dense matrix to perform dot product with, as in dot(a,b)
        @param z: return value
        @param sub: TODO, not too sure, something to do with weave probably
        """

        if node.inputs[0].type.dtype in ('complex64', 'complex128'):
            raise NotImplementedError('Complex types are not supported for a_val')
        if node.inputs[4].type.dtype in ('complex64', 'complex128'):
            raise NotImplementedError('Complex types are not supported for b')

        typenum_z = node.outputs[0].type.dtype_specs()[-1] # retrieve dtype number
        typenum_a_val = node.inputs[0].type.dtype_specs()[-1] # retrieve dtype number
        typenum_b = node.inputs[4].type.dtype_specs()[-1] # retrieve dtype number

        rval = """
        if (%(a_val)s->nd != 1) {PyErr_SetString(PyExc_NotImplementedError, "rank(a_val) != 1"); %(fail)s;}
        if (%(a_ind)s->nd != 1) {PyErr_SetString(PyExc_NotImplementedError, "rank(a_ind) != 1"); %(fail)s;}
        if (%(a_ptr)s->nd != 1) {PyErr_SetString(PyExc_NotImplementedError, "rank(a_ptr) != 1"); %(fail)s;}
        if (%(a_nrows)s->nd != 0) {PyErr_SetString(PyExc_NotImplementedError, "rank(nrows) != 0"); %(fail)s;}
        if (%(b)s->nd != 2) {PyErr_SetString(PyExc_NotImplementedError, "rank(b) != 2"); %(fail)s;}
        if (%(a_val)s->descr->type_num != %(typenum_a_val)s) {
        PyErr_SetString(PyExc_NotImplementedError, "Invalid type for a_val"); %(fail)s;}
        
        if (%(b)s->descr->type_num != %(typenum_b)s) {
        PyErr_SetString(PyExc_NotImplementedError, "Invalid type for b"); %(fail)s;}
        if (%(a_ind)s->descr->type_num != PyArray_INT32) {
        PyErr_SetString(PyExc_NotImplementedError, "a_ind dtype not INT32"); %(fail)s;}
        if (%(a_ptr)s->descr->type_num != PyArray_INT32)
        {PyErr_SetString(PyExc_NotImplementedError, "a_ptr dtype not INT32"); %(fail)s;}
        if (%(a_nrows)s->descr->type_num != PyArray_INT32)
        {PyErr_SetString(PyExc_NotImplementedError, "a_nrows dtype not INT32"); %(fail)s;}
        if (%(a_val)s->dimensions[0] != %(a_ind)s->dimensions[0])
        {PyErr_SetString(PyExc_NotImplementedError, "a_val and a_ind have different lengths"); %(fail)s;}
        if (%(a_ptr)s->dimensions[0] != %(b)s->dimensions[0]+1)
        {PyErr_SetString(PyExc_NotImplementedError, "a's number of columns doesn't match b's rows"); %(fail)s;}
        if ((!%(z)s)
            || (%(z)s->dimensions[0] != ((npy_int32 *)%(a_nrows)s->data)[0])
            || (%(z)s->dimensions[1] != %(b)s->dimensions[1])
            )
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
            // strides tell you how many bytes to skip to go to next column/row entry
            npy_intp Szm = %(z)s->strides[0] / %(z)s->descr->elsize;
            npy_intp Szn = %(z)s->strides[1] / %(z)s->descr->elsize;
            //npy_intp Sbm = %(b)s->strides[0] / %(b)s->descr->elsize;
            npy_intp Sbn = %(b)s->strides[1] / %(b)s->descr->elsize;
            npy_intp Sval = %(a_val)s->strides[0] / %(a_val)s->descr->elsize;
            npy_intp Sind = %(a_ind)s->strides[0] / %(a_ind)s->descr->elsize;
            npy_intp Sptr = %(a_ptr)s->strides[0] / %(a_ptr)s->descr->elsize;
            // pointers to access actual data in the arrays passed as params.
            dtype_%(z)s*     __restrict__ Dz   = (dtype_%(z)s*)%(z)s->data;
            const dtype_%(a_val)s* __restrict__ Dval = (dtype_%(a_val)s*)%(a_val)s->data;
            const npy_int32 * __restrict__ Dind = (npy_int32*)%(a_ind)s->data;
            const npy_int32 * __restrict__ Dptr = (npy_int32*)%(a_ptr)s->data;
            //npy_intp nnz = %(a_ind)s->dimensions[0];
            //clear the output array
            memset(Dz, 0, M*N*sizeof(dtype_%(z)s));
 
            //iterate over the sparse array, making the most of an entry wherever we find it.
            //
            // Normal matrix matrix multiply: A MxK, B KxN =>  Z = AB
            // for m
            //   for n
            //     for k
            //        z[m,n] += a[m,k] * b[k,n]
            // Here instead: Z = 
            // for k
            //   for m (sparse)
            //     for n
            //        z[m,n] += a[m,k] * b[k,n]
            // loop over inner dimension
            for (npy_int32 k = 0; k < K; ++k)
            {
                // get pointer to k-th row of dense matrix
                const dtype_%(b)s* __restrict__ bk = (dtype_%(b)s*)(%(b)s->data + %(b)s->strides[0] * k);
                
                // loop over sparse column indices through index pointer array 
                // (amounts to looping over rows M of sparse matrix)
                for (npy_int32 m_idx = Dptr[k * Sptr]; m_idx < Dptr[(k+1) * Sptr]; ++m_idx)
                {
                    npy_int32 m = Dind[m_idx * Sind]; // row index of non-null value for column K
                    const dtype_%(a_val)s Amk = Dval[m_idx * Sval]; // actual value at that location
    
                    // pointer to m-th row of the output matrix Z
                    dtype_%(z)s* __restrict__ zm = (dtype_%(z)s*)(%(z)s->data + %(z)s->strides[0] * m);
                    //RESOLVE: a.shape[0] equals z.shape[0], why is this not an equality constraint?
                    if (m >= %(z)s->dimensions[0]) 
                    {PyErr_SetString(PyExc_NotImplementedError, "illegal row index in a"); %(fail)s;}
                    // loop over final dimension (cols of dense matrix) and perform dot product
                    for(npy_int32 n = 0; n < N; ++n)
                    {
                        zm[n*Szn] += Amk * bk[n*Sbn];
                    }
                }
            }
        }
        """% dict(locals(), **sub)

        return rval
