package com.offerready.xslt;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.databasesandlife.util.Timer;
import lombok.val;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

public class HtmlBodyExtractor {
  
  protected void extractElements(
        @Nonnull StringBuilder result, @Nonnull String input,
        @Nonnull List<Range> ranges,
        @Nonnull String start, @CheckForNull String endOfStartOrNull, @Nonnull String end
    ) {
        int startIdx = -1;
        val ourResult = new StringBuilder();
        while ((startIdx = input.indexOf(start, startIdx+1)) >= 0) {
            int idxOfEndTag = input.indexOf(end, startIdx);
            Range range = endOfStartOrNull == null 
                ? new Range(startIdx, idxOfEndTag + end.length())
                : new Range(input.indexOf(endOfStartOrNull, startIdx) + endOfStartOrNull.length(), idxOfEndTag);
            boolean rangeOverlaps = false;
            for (Range r : ranges) if (r.overlaps(range)) rangeOverlaps = true;
            if (rangeOverlaps) continue;
            ourResult.append(ignoreScripts(input.substring(range.startIncl, range.endExcl)));
            ranges.add(range);
        }
        ourResult.append("\n");
        result.insert(0, ourResult);
    }
  }
