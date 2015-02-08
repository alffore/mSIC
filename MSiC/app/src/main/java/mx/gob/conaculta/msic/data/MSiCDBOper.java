package mx.gob.conaculta.msic.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;



import mx.gob.conaculta.msic.data.MSiCContract.InfraPatEntry;

/**
 * Created by alfonso on 08/02/15.
 */
public class MSiCDBOper {

    private MSiCDBHelper mSiCDBHelper;
    private SQLiteDatabase database;


    private String[] allColumns={InfraPatEntry.COLUMN_LAT,InfraPatEntry.COLUMN_LON,InfraPatEntry.COLUMN_NAME,InfraPatEntry.COLUMN_SRID,InfraPatEntry.COLUMN_TYPE};

    /**
     *
     * @param context
     */
    public MSiCDBOper(Context context){

        mSiCDBHelper = new MSiCDBHelper(context,"",null,1);

    }

    /**
     *
     */
    public void openDB(){
        database=mSiCDBHelper.getWritableDatabase();
    }

    /**
     *
     */
    public void closeDB(){
        mSiCDBHelper.close();
    }

    /**
     *
     * @return
     */
    public List<Recurso> obtenTodos(){

        List<Recurso> lrec = new ArrayList<Recurso>();

        Cursor cursor = database.query(InfraPatEntry.TABLE_NAME,allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            lrec.add(this.cursor2recurso(cursor));
            cursor.moveToNext();
        }

        return lrec;
    }

    /**
     *
     * @param stipo
     * @return
     */
    public List<Recurso> obtenxTipo(String stipo){
        List<Recurso> lrec = new ArrayList<Recurso>();

        String sWhere="tipo=?";
        String sArgs[]={stipo};

        Cursor cursor = database.query(InfraPatEntry.TABLE_NAME,allColumns, sWhere, sArgs, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            lrec.add(this.cursor2recurso(cursor));
            cursor.moveToNext();
        }

        return lrec;
    }

    /**
     *
     * @param lat0
     * @param lon0
     * @param lat1
     * @param lon1
     * @return
     */
    public List<Recurso> obtenxLatLon(double lat0,double lon0,double lat1,double lon1){
        List<Recurso> lrec = new ArrayList<Recurso>();

        String sWhere="latitud <= ? AND latitud >= ? AND longitud <= ? AND longitud >= ?";
        String sArgs[]={};

        Cursor cursor = database.query(InfraPatEntry.TABLE_NAME,allColumns, sWhere, sArgs, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            lrec.add(this.cursor2recurso(cursor));
            cursor.moveToNext();
        }

        return lrec;
    }


    /**
     *
     * @param cu
     * @return
     */
    private Recurso cursor2recurso(Cursor cu){

        Recurso rec = new Recurso();


        rec.lat=cu.getDouble(0);
        rec.lon=cu.getDouble(1);

        rec.sNombre=cu.getString(2);
        rec.sr_id=cu.getInt(3);
        rec.sTipo=cu.getString(4);

        return rec;
    }

}
