package net.coobird.thumbnailator;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.builders.BufferedImageBuilder;
import net.coobird.thumbnailator.builders.ThumbnailParameterBuilder;
import net.coobird.thumbnailator.filters.ImageFilter;
import net.coobird.thumbnailator.makers.FixedSizeThumbnailMaker;
import net.coobird.thumbnailator.makers.ScaledThumbnailMaker;
import net.coobird.thumbnailator.name.Rename;
import net.coobird.thumbnailator.resizers.DefaultResizerFactory;
import net.coobird.thumbnailator.resizers.Resizer;
import net.coobird.thumbnailator.tasks.FileThumbnailTask;
import net.coobird.thumbnailator.tasks.StreamThumbnailTask;
import net.coobird.thumbnailator.tasks.ThumbnailTask;
import net.coobird.thumbnailator.tasks.UnsupportedFormatException;

public final class Thumbnailator{

  public static void createThumbnail(ThumbnailTask<?, ?> task) throws IOException
	{
    BufferedImage sourceImage = task.read();
    
    // ...
    
    sourceImage.flush();
  
  }

}
