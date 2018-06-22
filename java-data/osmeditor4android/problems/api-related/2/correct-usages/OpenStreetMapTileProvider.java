
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

public class OpenStreetMapTileProvider implements ServiceConnection,OpenStreetMapViewConstants{
	
	protected Context mCtx;
	
	public pattern(final Context ctx,final Handler aDownloadFinishedListener) {
		mCtx = ctx;
		ctx.bindService(new Intent(IOpenStreetMapTileProviderService.class.getName()), this, Context.BIND_AUTO_CREATE)) 
		
		// ...
		
		mCtx.unbindService(this);
		
	}
}
			
			

