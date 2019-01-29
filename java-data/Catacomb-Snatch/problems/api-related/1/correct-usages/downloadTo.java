package com.mojang.mojam.downloader;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Date;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class ChannelDownloader implements IDownloader {

	@Override
	public void downloadTo(String turl, String dest) throws IOException {

		ReadableByteChannel rbc = Channels.newChannel(is);
		
		FileOutputStream fos = new FileOutputStream(dest);
		fos.getChannel().transferFrom(rbc, 0, 1 << 24);

		fos.close();
		return;
	}
}
