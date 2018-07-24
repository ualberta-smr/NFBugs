package org.subethamail.smtp.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import javax.mail.util.SharedByteArrayInputStream;

import org.apache.mina.common.BufferDataException;
import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.subethamail.smtp.server.io.SharedTmpFileInputStream;

public class SMTPCodecDecoder implements ProtocolDecoder 
{
  private class DecoderContext 
    {
        protected void reset() throws IOException 
        {
            if (thresholdReached)
            {
            	thresholdReached = false;
            	compactBuffer();
            	closeOutputStream();
            }
        }
    }
}
