package com.tinkerpop.pipes.serial.sideeffect;


import com.tinkerpop.pipes.serial.Pipe;
import com.tinkerpop.pipes.serial.Pipeline;
import com.tinkerpop.pipes.serial.filter.CollectionFilterPipe;
import com.tinkerpop.pipes.serial.filter.ComparisonFilterPipe;
import com.tinkerpop.pipes.serial.filter.ObjectFilterPipe;
import junit.framework.TestCase;

import java.util.*;

public class AggregatorPipeTest extends TestCase {
        public void pattern() {
                List<String> list = Arrays.asList("marko", "antonio", "rodriguez", "was", "here", ".");
                AggregatorPipe<String> pipe1 = new AggregatorPipe<String>(new ArrayList<String>());
                Pipe pipe2 = new CollectionFilterPipe<String>(pipe1.getSideEffect(), ComparisonFilterPipe.Filter.EQUALS);
                Pipeline<String, String> pipeline = new Pipeline<String, String>(Arrays.asList(pipe1, pipe2));

            }
}
