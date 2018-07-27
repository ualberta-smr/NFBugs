package org.elasticsearch.util.settings.loader;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.elasticsearch.util.io.FastStringReader;
import org.elasticsearch.util.json.Jackson;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.*;
import static com.google.common.collect.Maps.*;

public class JsonSettingsLoader implements SettingsLoader {

  @Override public Map<String, String> load(String source) throws IOException {
        JsonParser jp = jsonFactory.createJsonParser(new FastStringReader(source));
        try {
            return load(jp);
        } finally {
            jp.close();
        }
    }
}
