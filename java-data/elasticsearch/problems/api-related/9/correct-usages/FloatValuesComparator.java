package org.elasticsearch.index.fielddata.fieldcomparator;

import org.elasticsearch.index.fielddata.IndexNumericFieldData;

import java.io.IOException;

public final class FloatValuesComparator extends DoubleValuesComparatorBase<Float> {

  private final float[] values;

   @Override
    public int compare(int slot1, int slot2) {
        final float v1 = values[slot1];
        final float v2 = values[slot2];
        return Float.compare(v1, v2);
    }

}
