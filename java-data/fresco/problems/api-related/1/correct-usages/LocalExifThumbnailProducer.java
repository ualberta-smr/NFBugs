package com.facebook.imagepipeline.producers;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Executor;

import android.content.ContentResolver;
import android.database.Cursor;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Pair;

import com.facebook.common.internal.ImmutableMap;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.util.UriUtil;
import com.facebook.imageformat.ImageFormat;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.memory.PooledByteBuffer;
import com.facebook.imagepipeline.memory.PooledByteBufferFactory;
import com.facebook.imagepipeline.memory.PooledByteBufferInputStream;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imageutils.BitmapUtil;
import com.facebook.imageutils.JfifUtil;

public class LocalExifThumbnailProducer implements ThumbnailProducer<EncodedImage> {

  private EncodedImage buildEncodedImage(PooledByteBuffer imageBytes,ExifInterface exifInterface) {
    
    CloseableReference<PooledByteBuffer> closeableByteBuffer = CloseableReference.of(imageBytes);
    try {
      encodedImage = new EncodedImage(closeableByteBuffer);
    } finally {
      CloseableReference.closeSafely(closeableByteBuffer);
    }
    return encodedImage;
  }
}
