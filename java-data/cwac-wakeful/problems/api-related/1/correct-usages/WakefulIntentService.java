import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

public class WakefulIntentService extends IntentService {
	private static PowerManager.WakeLock lockStatic = null;
	
	public static void example() {
		lockStatic.acquire();
	
		try {
			// ...
		}
		finally {
			lockStatic.release();
		}
	}
}	
