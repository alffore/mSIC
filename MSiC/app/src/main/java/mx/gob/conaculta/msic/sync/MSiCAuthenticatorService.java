package mx.gob.conaculta.msic.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by alfonso on 21/02/15.
 */
public class MSiCAuthenticatorService extends Service {

    private MSiCAuthenticator mAuthenticator;


    @Override
    public void onCreate() {
        // Create a new authenticator object
        mAuthenticator = new MSiCAuthenticator(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}
