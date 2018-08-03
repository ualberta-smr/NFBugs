package com.facebook.device.yearclass;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;

import java.io.*;

public class DeviceInfo {

    private static int getCoresFromFileInfo(String fileLocation) {
        InputStream is = null;
        try {
          is = new FileInputStream(fileLocation);
          // ...
        } catch (IOException e) {
          // ...
        } finally {
          if (is != null) {
            try {
              is.close();
            } catch (IOException e) {
                // ...
            }
          }
        }
      }
}
