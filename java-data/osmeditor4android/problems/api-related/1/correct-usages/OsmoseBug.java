package de.blau.android.tasks;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.stream.JsonReader;

import android.content.Context;
import android.util.Log;
import de.blau.android.R;
import de.blau.android.osm.OsmElement;
import de.blau.android.util.DateFormatter;
import de.blau.android.util.SavingHelper;

public class OsmoseBug extends Bug implements Serializable {
 
  public String getLongDescription(Context context, boolean withElements) {
         StringBuilder result = new StringBuilder("Osmose: " + level2string(context) + "<br><br>" + (subtitle != null && subtitle.length() != 0 ? subtitle : title) + "<br>");
         if (withElements) {
             for (OsmElement osm : getElements()) {
                 if (osm.getOsmVersion() >= 0) {
                     result.append("<br>" + osm.getName() + " (" + context.getString(R.string.openstreetbug_not_downloaded) + ") #" + osm.getOsmId());
                 } else {
                     result.append("<br>" + osm.getName() + " " + osm.getDescription(false));
                 }
                 result.append("<br><br>");
             }
         }
         result.append(context.getString(R.string.openstreetbug_last_updated) + ": " + update + " " + context.getString(R.string.id) + ": " + id);
         return result.toString();
     }
}
