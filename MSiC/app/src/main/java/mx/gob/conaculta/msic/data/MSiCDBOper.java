package mx.gob.conaculta.msic.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


import mx.gob.conaculta.msic.data.MSiCContract.InfraPatEntry;

/**
 * Created by alfonso on 08/02/15.
 */
public class MSiCDBOper {

    public final String LOG_TAG = MSiCDBOper.class.getSimpleName();
    private MSiCDBHelper mSiCDBHelper;
    private SQLiteDatabase database;


    private String[] allColumns = {InfraPatEntry._ID, InfraPatEntry.COLUMN_LAT, InfraPatEntry.COLUMN_LON, InfraPatEntry.COLUMN_NAME, InfraPatEntry.COLUMN_SRID, InfraPatEntry.COLUMN_TYPE, InfraPatEntry.COLUMN_ADS};

    /**
     * @param context
     */
    public MSiCDBOper(Context context) {

        mSiCDBHelper = new MSiCDBHelper(context, "", null, 2);

    }

    /**
     *
     */
    public void openDB() {
        database = mSiCDBHelper.getWritableDatabase();
    }

    /**
     *
     */
    public void closeDB() {
        mSiCDBHelper.close();
    }

    /**
     * @return
     */
    public List<Recurso> obtenTodos() {

        List<Recurso> lrec = new ArrayList<Recurso>();

        Cursor cursor = database.query(InfraPatEntry.TABLE_NAME, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            lrec.add(this.cursor2recurso(cursor));
            cursor.moveToNext();
        }

        return lrec;
    }

    /**
     * @return
     */
    public Cursor obtenCursorAll() {
        return database.query(InfraPatEntry.TABLE_NAME, allColumns, null, null, null, null, null);
    }

    /**
     * @param stipo
     * @return
     */
    public List<Recurso> obtenxTipo(String stipo) {
        List<Recurso> lrec = new ArrayList<Recurso>();

        String sWhere = "tipo=?";
        String sArgs[] = {stipo};

        Cursor cursor = database.query(InfraPatEntry.TABLE_NAME, allColumns, sWhere, sArgs, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            lrec.add(this.cursor2recurso(cursor));
            cursor.moveToNext();
        }

        return lrec;
    }

    /**
     * @param lat0
     * @param lon0
     * @param lat1
     * @param lon1
     * @return
     */
    public List<Recurso> obtenxLatLon(double lat0, double lon0, double lat1, double lon1) {
        List<Recurso> lrec = new ArrayList<Recurso>();

        String sWhere = "latitud <= ? AND latitud >= ? AND longitud <= ? AND longitud >= ?";
        String sArgs[] = {};

        Cursor cursor = database.query(InfraPatEntry.TABLE_NAME, allColumns, sWhere, sArgs, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            lrec.add(this.cursor2recurso(cursor));
            cursor.moveToNext();
        }

        return lrec;
    }


    /**
     * @param cu
     * @return
     */
    private Recurso cursor2recurso(Cursor cu) {

        Recurso rec = new Recurso();


        rec.lat = cu.getDouble(0);
        rec.lon = cu.getDouble(1);

        rec.sNombre = cu.getString(2);
        rec.srId = cu.getInt(3);
        rec.sTipo = cu.getString(4);
        rec.sAdscripcion = cu.getString(5);

        return rec;
    }


    /**
     * Método que guarda el recurso recuperado en un objeto JSON
     *
     * @param jrec
     * @return
     */
    public boolean guardaJSONRec(JSONObject jrec) throws JSONException {

        //borramos
        final String SQUERY_BORRA = "DELETE FROM " + InfraPatEntry.TABLE_NAME + " WHERE " +
                InfraPatEntry.COLUMN_TYPE + "='" + jrec.getString(InfraPatEntry.COLUMN_TYPE) + "' AND " +
                InfraPatEntry.COLUMN_SRID + "=" + jrec.getInt(InfraPatEntry.COLUMN_SRID);

        //database.execSQL(SQUERY_BORRA);
        Log.d(LOG_TAG, "Borra: " + SQUERY_BORRA);

        if (jrec.getBoolean(InfraPatEntry.COLUMN_INFOP)) {

            //insertamos
            final String SQUERY_INSERTA = "INSERT INTO " + InfraPatEntry.TABLE_NAME + " (" +
                    InfraPatEntry.COLUMN_ADS + "," +
                    InfraPatEntry.COLUMN_MSR + "," +
                    InfraPatEntry.COLUMN_SRID + "," +
                    InfraPatEntry.COLUMN_TYPE + "," +
                    InfraPatEntry.COLUMN_LON + "," +
                    InfraPatEntry.COLUMN_LAT + "," +
                    InfraPatEntry.COLUMN_NAME + "," +
                    ") VALUES ('" +
                    jrec.getString(InfraPatEntry.COLUMN_ADS) + "'," +
                    jrec.getInt(InfraPatEntry.COLUMN_MSR) + "," +
                    jrec.getInt(InfraPatEntry.COLUMN_SRID) + ",'" +
                    jrec.getString(InfraPatEntry.COLUMN_TYPE) + "','" +
                    jrec.getString(InfraPatEntry.COLUMN_LON) + "','" +
                    jrec.getString(InfraPatEntry.COLUMN_LAT) + "','" +
                    jrec.getString(InfraPatEntry.COLUMN_NAME) + "')";

            // database.execSQL(SQUERY_INSERTA);
        }

        return true;
    }
}
