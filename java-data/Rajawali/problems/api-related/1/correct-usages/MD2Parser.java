package rajawali.parser;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;

import rajawali.animation.mesh.AAnimationObject3D;
import rajawali.animation.mesh.IAnimationFrame;
import rajawali.animation.mesh.VertexAnimationFrame;
import rajawali.animation.mesh.VertexAnimationObject3D;
import rajawali.materials.DiffuseMaterial;
import rajawali.materials.TextureManager;
import rajawali.renderer.RajawaliRenderer;
import rajawali.util.LittleEndianDataInputStream;
import rajawali.util.RajLog;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class MD2Parser extends AParser implements IParser {

  	private float[] getTexCoords(BufferedInputStream stream, byte[] bytes) throws IOException {
      
      LittleEndianDataInputStream is = new LittleEndianDataInputStream(ba);
      float[] coords = new float[mHeader.numTexCoord * 2];

      // ...
      is.close();
      return coords;
	}

}
