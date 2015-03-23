package mx.gob.conaculta.msic;

import android.test.AndroidTestCase;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import mx.gob.conaculta.msic.data.MSiCDBOper;
import mx.gob.conaculta.msic.data.Recurso;

/**
 * Created by alfonso on 22/03/15.
 */
public class TestODB extends AndroidTestCase {

    public static final String LOG_TAG = TestODB.class.getSimpleName();



    public void testRecCoord() throws Throwable{
        MSiCDBOper mSiCDBOper=new MSiCDBOper(mContext);

        mSiCDBOper.openDB();

        ArrayList<Recurso> aLRec= mSiCDBOper.obtenRLatLonTipoD(new LatLng(19.02,-98.3),null,10000);

        assertTrue("Hay resultados: "+String.valueOf(aLRec.size()),aLRec.size()>0);

        mSiCDBOper.closeDB();
    }
}
