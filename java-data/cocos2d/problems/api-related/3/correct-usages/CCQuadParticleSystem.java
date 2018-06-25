package org.cocos2d.particlesystem;

import java.lang.ref.WeakReference;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import org.cocos2d.config.ccConfig;
import org.cocos2d.config.ccMacros;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.opengl.CCTexture2D;
import org.cocos2d.opengl.GLResourceHelper;
import org.cocos2d.opengl.GLResourceHelper.Resource;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.ccBlendFunc;
import org.cocos2d.utils.BufferProvider;
import org.cocos2d.utils.FastFloatBuffer;

public class CCQuadParticleSystem extends CCParticleSystem implements Resource {

    private static class QuadParticleLoader implements GLResourceHelper.GLResourceLoader {

		private WeakReference<CCQuadParticleSystem> weakRef;
    	
    public QuadParticleLoader(CCQuadParticleSystem holder) {
    		weakRef = new WeakReference<CCQuadParticleSystem>(holder);
        }
      
      // ...
   }
}
