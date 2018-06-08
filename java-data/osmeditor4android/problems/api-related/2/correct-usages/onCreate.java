
package de.blau.android;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;
import android.widget.RelativeLayout.LayoutParams;
import de.blau.android.exception.FollowGpsException;
import de.blau.android.exception.OsmException;
import de.blau.android.exception.OsmServerException;
import de.blau.android.osb.Bug;
import de.blau.android.osb.CommitTask;
import de.blau.android.osb.Database;
import de.blau.android.osm.BoundingBox;
import de.blau.android.osm.Node;
import de.blau.android.osm.OsmElement;
import de.blau.android.osm.Server;
import de.blau.android.osm.StorageDelegator;
import de.blau.android.osm.Way;
import de.blau.android.presets.TagKeyAutocompletionAdapter;
import de.blau.android.presets.TagValueAutocompletionAdapter;
import de.blau.android.resources.Paints;
import de.blau.android.views.overlay.OpenStreetBugsOverlay;
import de.blau.android.views.overlay.OpenStreetMapViewOverlay;

/**
 * This is the main Activity from where other Activities will be started.
 * 
 * @author mb
 */
public class Main extends Activity {
	private Map map;

protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(
			Context.LOCATION_SERVICE);
		getWindow().requestFeature(Window.FEATURE_LEFT_ICON);
		getWindow().requestFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		getWindow().requestFeature(Window.FEATURE_RIGHT_ICON);

		RelativeLayout rl = new RelativeLayout(getApplicationContext());
		if (map != null) {
			map.onDestroy();
		}
		map = new Map(getApplicationContext());
		dialogFactory = new DialogFactory(this);

		//Register some Listener
		MapTouchListener mapTouchListener = new MapTouchListener();
		map.setOnTouchListener(mapTouchListener);
		map.setOnCreateContextMenuListener(mapTouchListener);
		map.setOnKeyListener(new MapKeyListener());
		
		rl.addView(map);
		
		// Set up the zoom in/out controls
		final ZoomControls zc = new ZoomControls(getApplicationContext());
		zc.setOnZoomInClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				logic.zoom(Logic.ZOOM_IN);
				zc.setIsZoomInEnabled(logic.canZoom(Logic.ZOOM_IN));
				zc.setIsZoomOutEnabled(logic.canZoom(Logic.ZOOM_OUT));
			}
		});
		zc.setOnZoomOutClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				logic.zoom(Logic.ZOOM_OUT);
				zc.setIsZoomInEnabled(logic.canZoom(Logic.ZOOM_IN));
				zc.setIsZoomOutEnabled(logic.canZoom(Logic.ZOOM_OUT));
			}
		});
		RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		rl.addView(zc, rlp);
		
		setContentView(rl);

		//Load previous logic (inkl. StorageDelegator)
		logic = (Logic) getLastNonConfigurationInstance();
		if (logic == null) {
			logic = new Logic(locationManager, map, new Paints(getApplicationContext().getResources()));
			if (isLastActivityAvailable()) {
				resumeLastActivity();
			} else {
				gotoBoxPicker();
			}
		} else {
			logic.setMap(map);
		}
	}
}
