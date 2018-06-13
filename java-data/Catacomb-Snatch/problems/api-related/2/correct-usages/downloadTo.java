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
		URL url = new URL(turl);

		URLConnection urlC = url.openConnection();

		// allow both GZip and Deflate (ZLib) encodings

		urlC.setRequestProperty("Accept-Encoding", "gzip, deflate");

		// set the user agent to pass Cloud-Flare

		urlC.setRequestProperty("User-agent", Downloader.getUserAgent());

		
		String encoding = urlC.getContentEncoding();

		InputStream is = null;

		if (encoding != null && encoding.equalsIgnoreCase("gzip")) {
			is = new GZIPInputStream(urlC.getInputStream());
		} else if (encoding != null && encoding.equalsIgnoreCase("deflate")) {
			is = new InflaterInputStream(urlC.getInputStream(), new Inflater(true));
		} else {
			is = urlC.getInputStream();
		}

		ReadableByteChannel rbc = Channels.newChannel(is);
		
		FileOutputStream fos = new FileOutputStream(dest);
		fos.getChannel().transferFrom(rbc, 0, 1 << 24);
		// DownloadScreen.drawGraph(fileSize/2, fileSize);
		fos.close();
		return;
	}
}
