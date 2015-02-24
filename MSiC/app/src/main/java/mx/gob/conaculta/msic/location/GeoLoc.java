package mx.gob.conaculta.msic.location;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.*;
import com.google.android.gms.location.LocationServices;

/**
 * Created by alfonso on 23/02/15.
 */
public class GeoLoc implements ConnectionCallbacks, OnConnectionFailedListener {

    private final String LOG_TAG = GeoLoc.class.getSimpleName();

    public GoogleApiClient mGoogleApiClient;
    public Location mLastLocation;

    private Context context;

    public GeoLoc(Context context) {
        this.context=context;
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

    }


    @Override
    public void onConnected(Bundle bundle) {
        // Provides a simple way of getting a device's location and is well suited for
        // applications that do not require a fine-grained location and that do not need location
        // updates. Gets the best and most recent location currently available, which may be null
        // in rare cases when a location is not available.
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {

            Toast.makeText(context,"Lat:"+mLastLocation.getLatitude()+" Lon:"+mLastLocation.getLongitude(),Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(context, "No localizable", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(LOG_TAG, "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
    }
}
