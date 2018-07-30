package org.elasticsearch.index.store;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.apache.lucene.store.*;
import org.apache.lucene.util.IOUtils;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.collect.MapBuilder;
import org.elasticsearch.common.compress.Compressor;
import org.elasticsearch.common.compress.CompressorFactory;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.lucene.Directories;
import org.elasticsearch.common.lucene.store.BufferedChecksumIndexOutput;
import org.elasticsearch.common.lucene.store.ChecksumIndexOutput;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.index.CloseableIndexComponent;
import org.elasticsearch.index.settings.IndexSettings;
import org.elasticsearch.index.shard.AbstractIndexShardComponent;
import org.elasticsearch.index.shard.ShardId;
import org.elasticsearch.index.store.distributor.Distributor;
import org.elasticsearch.index.store.support.ForceSyncDirectory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Adler32;

public class StoreDirectory extends Directory implements ForceSyncDirectory {
  
  @Override
  public IndexInput openInput(String name, IOContext context) throws IOException {
      
      IndexInput in = metaData.directory().openInput(name, context);
      boolean success = false;
      try {
          // ...
      } finally {
          if (!success) {
              IOUtils.closeWhileHandlingException(in);
          }
      }
      return in;
  }

}
