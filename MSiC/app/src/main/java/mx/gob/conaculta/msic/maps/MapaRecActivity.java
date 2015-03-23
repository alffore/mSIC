package mx.gob.conaculta.msic.maps;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;


import mx.gob.conaculta.msic.FichaActivity;
import mx.gob.conaculta.msic.R;
import mx.gob.conaculta.msic.data.MSiCDBOper;
import mx.gob.conaculta.msic.data.Recurso;
import mx.gob.conaculta.msic.utils.MSiCConst;


/**
 * Created by alfonso on 07/02/15.
 * <p/>
 * Revisar modelo en https://github.com/googlemaps/android-maps-utils
 */
public class MapaRecActivity extends FragmentActivity implements OnInfoWindowClickListener {

    protected GoogleMap mMap;

    private MSiCDBOper mSiCDBOper;

    private String sid;

    private Recurso rec;


    protected int getLayoutId() {
        return R.layout.fragment_mapa;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sid = this.getIntent().getStringExtra(MSiCConst.SID);


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

        mMap.setOnInfoWindowClickListener(this);


        if (mMap != null) {
           // Toast.makeText(this, "Listo para la acción", Toast.LENGTH_SHORT).show();
            pintaMarker();
        }
    }


    protected GoogleMap getMap() {
        setUpMapIfNeeded();
        return mMap;
    }

    /**
     *
     */
    protected void pintaMarker() {

        mSiCDBOper = new MSiCDBOper(this);

        mSiCDBOper.openDB();

        //Toast.makeText(this, "SID: " + sid, Toast.LENGTH_LONG).show();

        rec = mSiCDBOper.obtenRecId2(sid);


        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(rec.lat, rec.lon))
                .title(rec.sNombre));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(rec.lat, rec.lon))
                .zoom(14)
                .build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        mSiCDBOper.closeDB();
    }

    /**
     *
     * @param marker
     */
    @Override
    public void onInfoWindowClick(Marker marker) {
        //Toast.makeText(this, rec.sTipo+" "+String.valueOf(rec.srId), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, FichaActivity.class);
        intent.putExtra(MSiCConst.STEMA, rec.sTipo);
        intent.putExtra(MSiCConst.SIDSIC, String.valueOf(rec.srId));
        startActivity(intent);

    }
}