package net.robotmedia.billing;

import java.util.LinkedList;

import static net.robotmedia.billing.BillingRequest.*;

import net.robotmedia.billing.utils.Compatibility;

import com.android.vending.billing.IMarketBillingService;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;


public class BillingService extends Service implements ServiceConnection {


public void onDestroy() {
		super.onDestroy();
		//Ensure not leaking android market billing service
		if (mService != null) {
			try {
				unbindService(this);
			} catch (IllegalArgumentException e) {
				// This might happen if the service was disconnected
			}
		}
	}
}
