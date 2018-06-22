package de.blau.android.imageryoffset;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.ActionMode.Callback;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import de.blau.android.Application;
import de.blau.android.HelpViewer;
import de.blau.android.Logic.Mode;
import de.blau.android.Map;
import de.blau.android.R;
import de.blau.android.dialogs.Progress;
import de.blau.android.osm.BoundingBox;
import de.blau.android.osm.Server;
import de.blau.android.prefs.Preferences;
import de.blau.android.resources.TileLayerServer;
import de.blau.android.util.DateFormatter;
import de.blau.android.util.GeoMath;
import de.blau.android.util.NetworkStatus;
import de.blau.android.util.Offset;
import de.blau.android.util.ThemeUtils;
import de.blau.android.util.jsonreader.JsonReader;
import de.blau.android.util.jsonreader.JsonToken;

public class BackgroundAlignmentActionModeCallback implements Callback {
	
	private final Uri offsetServerUri;
		
	private class OffsetLoader extends AsyncTask<Integer, Void, ArrayList<ImageryOffset>> {
		
		public void pattern(Integer... params) {
	    	
			BoundingBox bbox = Application.mainActivity.getMap().getViewBox();
			double centerLon = (bbox.getLeft() + ((long)bbox.getRight() - (long)bbox.getLeft())/2L) / 1E7d;
			Integer radius = params[0];
			String radiusString = radius != null && radius > 0 ? String.valueOf(radius) : "";
			
			Uri uriBuilder = offsetServerUri.buildUpon()
					.appendPath("get")
					.appendQueryParameter("lat", String.valueOf(bbox.getCenterLat()))
					.appendQueryParameter("lon", String.valueOf(centerLon))
					.appendQueryParameter("radius", radiusString)
					.appendQueryParameter("imagery", osmts.getImageryOffsetId())
					.appendQueryParameter("format", "json")
					.build();
		
		}
	}
}
		
