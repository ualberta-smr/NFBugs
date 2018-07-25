package org.elasticsearch.ingest;

import com.carrotsearch.randomizedtesting.generators.RandomNumbers;
import com.carrotsearch.randomizedtesting.generators.RandomPicks;
import com.carrotsearch.randomizedtesting.generators.RandomStrings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public final class RandomDocumentPicks{

  public static String randomFieldName(Random random) {
        int numLevels = RandomNumbers.randomIntBetween(random, 1, 5);
        StringBuilder fieldName = new StringBuilder();
        for (int i = 0; i < numLevels; i++) {
            if (i > 0) {
                fieldName.append('.');
            }
            fieldName.append(randomString(random));
        }
        return fieldName.toString();
    }
}

