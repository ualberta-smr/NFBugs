package org.elasticsearch.action.admin.cluster.node.info;

import org.elasticsearch.Version;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.common.io.stream.Streamable;
import org.elasticsearch.common.xcontent.ToXContent;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentBuilderString;

import java.io.IOException;
import java.io.Serializable;

public class PluginInfo implements Streamable, Serializable, ToXContent {

  @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PluginInfo{");
        sb.append("name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", site=").append(site);
        sb.append(", jvm=").append(jvm);
        sb.append(", version='").append(version).append('\'');
        sb.append(", isolation=").append(isolation);
        sb.append('}');
        return sb.toString();
    }
}
