package com.tinkerpop.pipes.sideeffect;

import com.tinkerpop.pipes.AbstractPipe;
import com.tinkerpop.pipes.Pipe;
import com.tinkerpop.pipes.PipeFunction;
import com.tinkerpop.pipes.util.iterators.ExpandableMultiIterator;
import com.tinkerpop.pipes.util.iterators.SingleIterator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GroupByReducePipe<S, K, V, V2> extends AbstractPipe<S, S> implements SideEffectPipe<S, Map<K, V2>> {
    
    protected S processNextStart() {
        final S s = this.starts.next();
        final V value = this.getValue(s);

        ExpandableMultiIterator<V> values = (ExpandableMultiIterator<V>) this.byMap.get(key);
        if (null == values) {
            values = new ExpandableMultiIterator<V>();
            this.addValue(value, values);
            this.byMap.put(key, values);
        } else {
            this.addValue(value, values);
        }
        return s;
    }
}
