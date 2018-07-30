package org.elasticsearch.search.fetch.source;

import com.google.common.collect.ImmutableMap;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.io.stream.BytesStreamOutput;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.search.SearchParseElement;
import org.elasticsearch.search.fetch.FetchSubPhase;
import org.elasticsearch.search.internal.InternalSearchHit;
import org.elasticsearch.search.internal.SearchContext;
import org.elasticsearch.search.lookup.SourceLookup;

import java.io.IOException;
import java.util.Map;

public class FetchSourceSubPhase implements FetchSubPhase {
  
    @Override
    public void hitExecute(SearchContext context, HitContext hitContext) throws ElasticsearchException {
       
        SourceLookup source = context.lookup().source();
        final int initialCapacity = Math.min(1024, source.internalSourceRef().length());
        BytesStreamOutput streamOutput = new BytesStreamOutput(initialCapacity);
        XContentBuilder builder = new XContentBuilder(context.lookup().source().sourceContentType().xContent(), streamOutput);
    }
}
