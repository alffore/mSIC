package mx.gob.conaculta.msic.maps;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import mx.gob.conaculta.msic.FichaActivity;
import mx.gob.conaculta.msic.R;

import mx.gob.conaculta.msic.data.MSiCDBOper;
import mx.gob.conaculta.msic.data.Recurso;
import mx.gob.conaculta.msic.utils.MSiCConst;

/**
 * Created by alfonso on 16/03/15.
 *
 * Esta clase abre un mapa y pinta los puntos donde se encuentra la infraestructura a cierta escala
 */
public class MapaMultiRecActivity extends FragmentActivity
        implements GoogleMap.OnInfoWindowClickListener,GoogleMap.OnCameraChangeListener,GoogleMap.OnMarkerClickListener {


    public HashMap<Marker,Recurso> hmMarker;
    protected GoogleMap mMap;
    private String stema;
    private LatLng posicionOri;

    private RecuperaPuntosTask recpt;


    protected int getLayoutId() {
        return R.layout.fragment_mapa_multi_rec;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        stema=preferences.getString(MSiCConst.STEMA,"");
        posicionOri=new LatLng(preferences.getFloat(MSiCConst.SLAT,0.0f),preferences.getFloat(MSiCConst.SLON,0.0f));

        hmMarker = new HashMap<>();

        recpt = new RecuperaPuntosTask(this);

        setContentView(getLayoutId());
        setUpMapIfNeeded();
    }

    /**
     *
     */
    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     *
     */
    private void setUpMapIfNeeded() {
        if (mMap != null) {
            return;
        }
        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapmulti)).getMap();

        mMap.setOnInfoWindowClickListener(this);

        mMap.setOnCameraChangeListener(this);

        mMap.setOnMarkerClickListener(this);

        mMap.setMyLocationEnabled(true);

        if (mMap != null) {

            ponCentro(posicionOri);

            pintaMarkers();
        }
    }

    /**
     *
     * @return
     */
    protected GoogleMap getMap() {
        setUpMapIfNeeded();
        return mMap;
    }

    /**
     *
     */
    protected void pintaMarkers() {
        MSiCDBOper mSiCDBOper = new MSiCDBOper(this);

        mSiCDBOper.openDB();

        ArrayList<Recurso> aLRec = mSiCDBOper.obtenRLatLonTipoD2(posicionOri,stema,30000.0);

        mSiCDBOper.closeDB();

        Toast.makeText(this,"Recupera: "+String.valueOf(aLRec.size()),Toast.LENGTH_LONG).show();

        Iterator itrec = aLRec.iterator();

        while(itrec.hasNext()){
            agregaMarker((Recurso) itrec.next());
        }

    }

    protected void remueveMarker(Marker marker){

        hmMarker.remove(marker);

        marker.remove();

    }

    protected void agregaMarker(Recurso rec){

       Marker marker= mMap.addMarker(new MarkerOptions()
                .position(new LatLng(rec.lat,rec.lon))
                .title(rec.sNombre)
                .snippet(rec.sTabla)
        );

        hmMarker.put(marker,rec);

    }

    /**
     *
     * @param marker
     */
    @Override
    public void onInfoWindowClick(Marker marker) {

        Recurso rec=hmMarker.get(marker);
        if(rec!=null) {
            Intent intent = new Intent(this, FichaActivity.class);
            intent.putExtra(MSiCConst.STEMA, rec.sTabla);
            intent.putExtra(MSiCConst.SIDSIC, String.valueOf(rec.srId));
            startActivity(intent);
        }

    }

    /**
     *
     * @param cameraPosition
     */
    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

        ParamSol ps=new ParamSol();

        ps.coords=cameraPosition.target;
        ps.dist=10000.0;
        ps.stipo=stema;

        if(recpt.getStatus().equals(AsyncTask.Status.FINISHED) ||
                recpt.getStatus().equals(AsyncTask.Status.PENDING)) {
            if(cameraPosition.zoom==14) {
                //recpt.execute(ps);
            }
        }

    }

    /**
     *
     * @param marker
     * @return
     */
    @Override
    public boolean onMarkerClick(Marker marker) {

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(marker.getPosition())
                .build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        return false;
    }

    /**
     *
     * @param latLng
     */
    private void ponCentro(LatLng latLng){
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(15)
                .build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }
}
