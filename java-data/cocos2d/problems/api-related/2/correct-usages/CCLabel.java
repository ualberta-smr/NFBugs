
package org.cocos2d.nodes;

import java.lang.ref.WeakReference;

import org.cocos2d.opengl.CCTexture2D;
import org.cocos2d.opengl.GLResourceHelper;
import org.cocos2d.protocols.CCLabelProtocol;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

public class CCLabel extends CCSprite implements CCLabelProtocol {
    
    private static class StringReloader implements GLResourceHelper.GLResourceLoader {
    	
      private WeakReference<CCLabel> label;
    	
    	public StringReloader(CCLabel holder) {
    		label = new WeakReference<CCLabel>(holder);
		   }
    	
    }

}
