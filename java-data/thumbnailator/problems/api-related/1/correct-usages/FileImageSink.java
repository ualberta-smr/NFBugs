package net.coobird.thumbnailator.tasks.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import net.coobird.thumbnailator.ThumbnailParameter;
import net.coobird.thumbnailator.tasks.UnsupportedFormatException;
import net.coobird.thumbnailator.util.BufferedImages;
import net.coobird.thumbnailator.util.ThumbnailatorUtils;


public class FileImageSink extends AbstractImageSink<File>
{
  public void write(BufferedImage img) throws IOException
	{
		
		String formatName = outputFormat;
		
		Iterator<ImageWriter> writers = 
			ImageIO.getImageWritersByFormatName(formatName);
		
		ImageWriter writer = writers.next();
		
		ImageWriteParam writeParam = writer.getDefaultWriteParam();

		ImageOutputStream ios;
		FileOutputStream fos;
		fos = new FileOutputStream(destinationFile);
		ios = ImageIO.createImageOutputStream(fos);
		
		writer.setOutput(ios);
		writer.write(null, new IIOImage(img, null, null), writeParam);

		writer.dispose();
		
		ios.close();
		fos.close();
	}
}

}
