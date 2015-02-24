package mx.gob.conaculta.msic;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * Created by alfonso on 07/02/15.
 * <p/>
 * <p/>
 * Revisar modelo en https://github.com/googlemaps/android-maps-utils
 */
public class MapaActivity extends FragmentActivity {

    private GoogleMap mMap;

    protected int getLayoutId() {
        return R.layout.fragment_mapa;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        if (mMap != null) {
            return;
        }
        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        if (mMap != null) {
            Toast.makeText(this, "Listo para acción", Toast.LENGTH_SHORT).show();
            pintaMarker();
        }
    }


    protected GoogleMap getMap() {
        setUpMapIfNeeded();
        return mMap;
    }


    protected void pintaMarker() {
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(19.495139, -99.251436))
                .title("Casa"));
    }
}