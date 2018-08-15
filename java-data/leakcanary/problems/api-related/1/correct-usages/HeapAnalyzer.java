package com.squareup.leakcanary;

import com.squareup.haha.perflib.ArrayInstance;
import com.squareup.haha.perflib.ClassInstance;
import com.squareup.haha.perflib.ClassObj;
import com.squareup.haha.perflib.Field;
import com.squareup.haha.perflib.HprofParser;
import com.squareup.haha.perflib.Instance;
import com.squareup.haha.perflib.RootObj;
import com.squareup.haha.perflib.RootType;
import com.squareup.haha.perflib.Snapshot;
import com.squareup.haha.perflib.Type;
import com.squareup.haha.perflib.io.HprofBuffer;
import com.squareup.haha.perflib.io.MemoryMappedFileBuffer;
import com.squareup.haha.trove.THashMap;
import com.squareup.haha.trove.TObjectProcedure;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.N_MR1;
import static com.squareup.leakcanary.AnalysisResult.failure;
import static com.squareup.leakcanary.AnalysisResult.leakDetected;
import static com.squareup.leakcanary.AnalysisResult.noLeak;
import static com.squareup.leakcanary.HahaHelper.asString;
import static com.squareup.leakcanary.HahaHelper.classInstanceValues;
import static com.squareup.leakcanary.HahaHelper.extendsThread;
import static com.squareup.leakcanary.HahaHelper.fieldValue;
import static com.squareup.leakcanary.HahaHelper.hasField;
import static com.squareup.leakcanary.HahaHelper.threadName;
import static com.squareup.leakcanary.LeakTraceElement.Holder.ARRAY;
import static com.squareup.leakcanary.LeakTraceElement.Holder.CLASS;
import static com.squareup.leakcanary.LeakTraceElement.Holder.OBJECT;
import static com.squareup.leakcanary.LeakTraceElement.Holder.THREAD;
import static com.squareup.leakcanary.LeakTraceElement.Type.ARRAY_ENTRY;
import static com.squareup.leakcanary.LeakTraceElement.Type.INSTANCE_FIELD;
import static com.squareup.leakcanary.LeakTraceElement.Type.STATIC_FIELD;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

public final class HeapAnalyzer {

  public AnalysisResult checkForLeak(File heapDumpFile, String referenceKey) {
      long analysisStartNanoTime = System.nanoTime();
      try {
        HprofBuffer buffer = new MemoryMappedFileBuffer(heapDumpFile);
        HprofParser parser = new HprofParser(buffer);
        Snapshot snapshot = parser.parse();
        deduplicateGcRoots(snapshot);

        Instance leakingRef = findLeakingReference(referenceKey, snapshot);
        return findLeakTrace(analysisStartNanoTime, snapshot, leakingRef);
      }
    }
}
