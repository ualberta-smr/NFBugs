package com.b44t.messenger;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;

import com.b44t.ui.Components.AvatarDrawable;

import java.io.InputStream;
import java.util.HashMap;

public class ContactsController {
  public static void setupAvatarByStrings(final View avtView,
                                   final ImageReceiver avtImageReceiver,
                                   final AvatarDrawable avtDrawable,
                                   String tempEmail,
                                   String tempName){
       

        final String email = tempEmail;
        final String fallbackName = tempName;
        synchronized (s_sync) {
            avtImageReceiver.m_userDataUnique = email+fallbackName;
        }
    }
}

       

                 
