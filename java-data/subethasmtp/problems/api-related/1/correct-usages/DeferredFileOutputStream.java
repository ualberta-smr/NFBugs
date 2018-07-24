package org.subethamail.smtp.server.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DeferredFileOutputStream extends ThresholdingOutputStream
{
  @Override
	protected void thresholdReached(int current, int predicted) throws IOException
	{
		ByteArrayOutputStream baos = (ByteArrayOutputStream) this.output;
		baos.writeTo(this.outFileStream);
		baos.flush();
		baos.close();
	}
}
