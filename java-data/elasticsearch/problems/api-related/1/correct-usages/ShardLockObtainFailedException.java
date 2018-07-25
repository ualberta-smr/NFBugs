package org.elasticsearch.env;

import org.elasticsearch.index.shard.ShardId;

public class ShardLockObtainFailedException extends Exception {

    public String getMessage() {
        StringBuilder sb = new StringBuilder();
        
        sb.append(shardId.toString());
        sb.append(": ");
        sb.append(super.getMessage());
        
        return sb.toString();
    }
}
