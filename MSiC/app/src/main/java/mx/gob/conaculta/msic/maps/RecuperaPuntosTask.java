package mx.gob.conaculta.msic.maps;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import mx.gob.conaculta.msic.data.MSiCDBOper;
import mx.gob.conaculta.msic.data.Recurso;

/**
 * Created by alfonso on 16/03/15.
 */
public class RecuperaPuntosTask extends AsyncTask<ParamSol, Void, ArrayList<Recurso>> {

    private final MapaMultiRecActivity mActivity;

    private MSiCDBOper mSiCDBOper = null;

    /**
     * @param activity
     */
    public RecuperaPuntosTask(MapaMultiRecActivity activity) {

        this.mActivity = activity;
        mSiCDBOper = new MSiCDBOper(mActivity);
    }


    @Override
    protected ArrayList<Recurso> doInBackground(ParamSol... params) {

        mSiCDBOper.openDB();

        ArrayList<Recurso> aLRec = mSiCDBOper.obtenRLatLonTipoD2(params[0].coords, params[0].stipo, params[0].dist);

        mSiCDBOper.closeDB();

        return aLRec;
    }

    /**
     * @param aLRec
     */
    @Override
    protected void onPostExecute(ArrayList<Recurso> aLRec) {
        if (aLRec == null || aLRec.size() == 0) return;

        Toast.makeText(mActivity, "Termino de cargar", Toast.LENGTH_SHORT);
    }
}
