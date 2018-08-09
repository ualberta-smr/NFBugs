package com.facebook.device.yearclass;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;

import java.io.*;

public class DeviceInfo {

    private static int getCoresFromFileInfo(String fileLocation) {
      InputStream is = new FileInputStream(fileLocation);
      BufferedReader buf = new BufferedReader(new InputStreamReader(is));
      buf.close();
  }

}
