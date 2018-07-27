package org.elasticsearch.search.facet.termsstats.strings;

import org.elasticsearch.common.collect.ImmutableList;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.common.thread.ThreadLocals;
import org.elasticsearch.common.trove.ExtTHashMap;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentBuilderString;
import org.elasticsearch.search.facet.Facet;
import org.elasticsearch.search.facet.termsstats.InternalTermsStatsFacet;

import java.io.IOException;
import java.util.*;

public class InternalTermsStatsStringFacet extends InternalTermsStatsFacet {

  @Override public Facet reduce(String name, List<Facet> facets) {
     
          ExtTHashMap<String, StringEntry> map = aggregateCache.get().get();
          
          List<StringEntry> ordered = new ArrayList<StringEntry>(map.size());
          
          // ...
          
          return new InternalTermsStatsStringFacet(name, comparatorType, requiredSize, ordered, missing);
        
    }
}
