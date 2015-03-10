package mx.gob.conaculta.msic;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import mx.gob.conaculta.msic.data.MSiCDBOper;
import mx.gob.conaculta.msic.data.Recurso;
import mx.gob.conaculta.msic.utils.MSiCConst;


/**
 * Created by alfonso on 07/02/15.
 * <p/>
 * <p/>
 * Revisar modelo en https://github.com/googlemaps/android-maps-utils
 */
public class MapaActivity extends FragmentActivity {

    protected GoogleMap mMap;

    private MSiCDBOper mSiCDBOper;

    private String sid;

    protected int getLayoutId() {
        return R.layout.fragment_mapa;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sid=this.getIntent().getStringExtra(MSiCConst.SID);


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
            Toast.makeText(this, "Listo para la acción", Toast.LENGTH_SHORT).show();
            pintaMarker2();
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


    protected void pintaMarker2() {

        mSiCDBOper=new MSiCDBOper(this);

        mSiCDBOper.openDB();

        Toast.makeText(this, "SID: "+sid, Toast.LENGTH_LONG).show();

       Recurso rec=mSiCDBOper.obtenRecId2(sid);


        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(rec.lat,rec.lon))
                .title(rec.sNombre));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(rec.lat,rec.lon))
                .zoom(15)
                .build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        mSiCDBOper.closeDB();
    }
}