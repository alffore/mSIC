package mx.gob.conaculta.msic.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by alfonso on 21/02/15.
 */
public class MSiCSyncService extends Service {
    private static final Object sSyncAdapterLock = new Object();
    private static MSiCSyncAdapter mSiCSyncAdapter = null;

    @Override
    public void onCreate() {
        Log.d("MSiCSyncService", "onCreate - MSiCSyncService");
        synchronized (sSyncAdapterLock) {
            if (mSiCSyncAdapter == null) {
                mSiCSyncAdapter = new MSiCSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mSiCSyncAdapter.getSyncAdapterBinder();
    }
}
