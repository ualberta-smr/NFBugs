package org.elasticsearch.index.fielddata.fieldcomparator;

import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.search.FieldComparator;
import org.elasticsearch.index.fielddata.DoubleValues;
import org.elasticsearch.index.fielddata.IndexNumericFieldData;

import java.io.IOException;

abstract class DoubleValuesComparatorBase<T extends Number> extends NumberComparatorBase<T> {
  
  static final int compare(double left, double right) {
        return Double.compare(left, right);
    }

}
