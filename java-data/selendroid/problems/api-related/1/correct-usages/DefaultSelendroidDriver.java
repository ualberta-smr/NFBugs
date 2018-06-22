package io.selendroid.server.model;

import io.selendroid.ServerInstrumentation;
import io.selendroid.android.AndroidWait;
import io.selendroid.android.InstrumentedKeySender;
import io.selendroid.android.KeySender;
import io.selendroid.android.ViewHierarchyAnalyzer;
import io.selendroid.android.WindowType;
import io.selendroid.android.internal.Dimension;
import io.selendroid.exceptions.NoSuchElementException;
import io.selendroid.exceptions.SelendroidException;
import io.selendroid.server.Session;
import io.selendroid.server.model.internal.AbstractNativeElementContext;
import io.selendroid.server.model.internal.AbstractWebElementContext;
import io.selendroid.server.model.internal.execute_native.FindElementByAndroidTag;
import io.selendroid.server.model.internal.execute_native.FindRId;
import io.selendroid.server.model.internal.execute_native.GetL10nKeyTranslation;
import io.selendroid.server.model.internal.execute_native.InvokeMenuAction;
import io.selendroid.server.model.internal.execute_native.IsElementDisplayedInViewport;
import io.selendroid.server.model.internal.execute_native.NativeExecuteScript;
import io.selendroid.server.model.js.AndroidAtoms;
import io.selendroid.util.Preconditions;
import io.selendroid.util.SelendroidLogger;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.Display;
import android.view.View;
import android.webkit.WebView;


public class DefaultSelendroidDriver implements SelendroidDriver {

  public String initializeSession(JSONObject desiredCapabilities) {
  
    Random random = new Random();
    this.session = new Session(desiredCapabilities,
        new UUID(random.nextLong(), random.nextLong()).toString());
    return session.getSessionId();
  }




}
