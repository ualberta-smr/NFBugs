package com.facebook.imagepipeline.producers;

import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

import android.util.Pair;

import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Sets;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.imagepipeline.common.Priority;

public abstract class MultiplexProducer<K, T extends Closeable> implements Producer<T> {

   public void onNextResult(final ForwardingConsumer consumer,final T closeableObject,final boolean isFinal) {
      Iterator<Pair<Consumer<T>, ProducerContext>> iterator;

      while (iterator.hasNext()) {
        Pair<Consumer<T>, ProducerContext> pair = iterator.next();
        synchronized (pair) {
          pair.first.onNewResult(cloneOrNull(closeableObject), isFinal);
        }
      }
      closeSafely(closeableObject);
    }
}
