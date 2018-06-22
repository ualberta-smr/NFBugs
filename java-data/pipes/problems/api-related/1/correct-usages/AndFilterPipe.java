package com.tinkerpop.pipes.serial.filter;

import com.tinkerpop.pipes.serial.AbstractPipe;
import com.tinkerpop.pipes.serial.SingleIterator;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class AndFilterPipe<S> extends AbstractPipe<S, S> implements FilterPipe<S> {
        
        public S processNextStart() {
                while (this.starts.hasNext()) {
                    S s = this.starts.next();
                    boolean and = true;
                    for (FilterPipe<S> pipe : this.pipes) {
                        pipe.setStarts(new SingleIterator<S>(s));
                        if (!pipe.hasNext()) {
                            and = false;
                            break;
                        } else {
                            pipe.clear();
                        }
                    }
                    if (and)
                        return s;
                }
            }
}
