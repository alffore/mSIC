package mx.gob.conaculta.msic.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


import mx.gob.conaculta.msic.data.MSiCContract.InfraPatEntry;
import mx.gob.conaculta.msic.utils.Utiles;

/**
 * Created by alfonso on 08/02/15.
 */
public class MSiCDBOper {

    public final String LOG_TAG = MSiCDBOper.class.getSimpleName();
    private MSiCDBHelper mSiCDBHelper;
    private SQLiteDatabase database;
    private Context context;


    private String[] allColumns = {InfraPatEntry._ID, InfraPatEntry.COLUMN_LAT, InfraPatEntry.COLUMN_LON,
            InfraPatEntry.COLUMN_NAME, InfraPatEntry.COLUMN_SRID, InfraPatEntry.COLUMN_TYPE, InfraPatEntry.COLUMN_ADS};

    /**
     * @param context
     */
    public MSiCDBOper(Context context) {
        this.context = context;
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

    public Cursor obtenCursor(String stema) {

        String sWhere = "tipo=?";
        String sArgs[] = {stema};

        return database.query(InfraPatEntry.TABLE_NAME, allColumns, sWhere, sArgs, null, null, null);
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
     * Metodo que recupera
     *
     * @param lat0
     * @param lon0
     * @param lat1
     * @param lon1
     * @return
     */
    public List<Recurso> obtenRLatLon(double lat0, double lon0, double lat1, double lon1) {
        List<Recurso> lrec = new ArrayList<Recurso>();

        double lat_max = Math.max(lat0, lat1);
        double lat_min = Math.min(lat0, lat1);

        double lon_max = Math.max(lon0, lon1);
        double lon_min = Math.min(lon0, lon1);


        String sWhere = "lat <= ? AND lat >= ? AND lon <= ? AND lon >= ?";
        String sArgs[] = {String.valueOf(lat_max), String.valueOf(lat_min), String.valueOf(lon_max), String.valueOf(lon_min)};

        Cursor cursor = database.query(InfraPatEntry.TABLE_NAME, allColumns, sWhere, sArgs, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            lrec.add(this.cursor2recurso(cursor));
            cursor.moveToNext();
        }

        cursor.close();
        return lrec;
    }

    /**
     * @param lat0
     * @param lon0
     * @param lat1
     * @param lon1
     * @param stipo Tipo de objeto
     * @return
     */
    public List<Recurso> obtenRLatLonTipo(double lat0, double lon0, double lat1, double lon1, String stipo) {

        if (stipo == null || (stipo != null && stipo.length() == 0)) {
            return this.obtenRLatLon(lat0, lon0, lat1, lon1);
        }

        List<Recurso> lrec = new ArrayList<Recurso>();

        double lat_max = Math.max(lat0, lat1);
        double lat_min = Math.min(lat0, lat1);

        double lon_max = Math.max(lon0, lon1);
        double lon_min = Math.min(lon0, lon1);

        String sWhere = "lat <= ? AND lat >= ? AND lon <= ? AND lon >= ? AND tipo = ?";
        String sArgs[] = {String.valueOf(lat_max), String.valueOf(lat_min), String.valueOf(lon_max), String.valueOf(lon_min), stipo};

        Cursor cursor = database.query(InfraPatEntry.TABLE_NAME, allColumns, sWhere, sArgs, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            lrec.add(this.cursor2recurso(cursor));
            cursor.moveToNext();
        }

        cursor.close();


        return lrec;
    }


    /**
     * Método que recupera los Recursos de un cierto tipo (o no) que se encuentra a menor o igual distancia <strong>dist (en metros)</strong>
     * que las coordenadas <strong>latlng</strong>
     *
     * @param latLng
     * @param stipo
     * @param dist
     * @return
     */
    public ArrayList<Recurso> obtenRLatLonTipoD(LatLng latLng, String stipo, double dist) {

        ArrayList<Recurso> lrec = new ArrayList<Recurso>();


        String sWhere = "tipo = ?";
        String sArgs[] = {stipo};

        if (stipo == null) {
            sWhere = null;
            sArgs = null;
        }

        Cursor cursor = database.query(InfraPatEntry.TABLE_NAME, allColumns, sWhere, sArgs, null, null, null);


        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            Recurso recaux = cursor2recurso(cursor);

            double daux = Utiles.distRecPunto(recaux, latLng.latitude, latLng.longitude);
            if (daux <= dist) {
                lrec.add(recaux);
            }
            cursor.moveToNext();
        }

        cursor.close();


        return lrec;

    }


    public ArrayList<Recurso> obtenRLatLonTipoD2(LatLng latLng, String stipo, double dist) {
        ArrayList<Recurso> lrec = new ArrayList<Recurso>();
        String sQUERY = "SELECT " + InfraPatEntry._ID + "," + InfraPatEntry.COLUMN_LAT + ", " + InfraPatEntry.COLUMN_LON + ", " +
                InfraPatEntry.COLUMN_NAME + ", " + InfraPatEntry.COLUMN_SRID + ", " + InfraPatEntry.COLUMN_TYPE + ", " +
                InfraPatEntry.COLUMN_ADS + " FROM " + InfraPatEntry.TABLE_NAME;

        if (stipo != null) {
            sQUERY += " WHERE " + InfraPatEntry.COLUMN_TYPE + "='" + stipo + "'";
        }

        Cursor cursor = database.rawQuery(sQUERY, null);


        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            Recurso recaux = cursor2recurso(cursor);

            double daux = Utiles.distRecPunto(recaux, latLng);
            if (daux <= dist) {
                lrec.add(recaux);
            }
            cursor.moveToNext();
        }

        cursor.close();


        return lrec;

    }

    /**
     * @param sid
     * @return
     */
    public Recurso obtenRecId(String sid) {

        final String SQUERY = "SELECT * FROM " + InfraPatEntry.TABLE_NAME + " WHERE " + InfraPatEntry._ID + "='" + sid + "'";

        Cursor cursor = database.rawQuery(SQUERY, null);
        cursor.moveToFirst();

        return cursor2recurso(cursor);
    }

    /**
     * @param sid
     * @return
     */
    public Recurso obtenRecId2(String sid) {
        String sWhere = InfraPatEntry._ID + "=?";
        String sArgs[] = {sid};

        Cursor cursor = database.query(InfraPatEntry.TABLE_NAME, allColumns, sWhere, sArgs, null, null, null);
        cursor.moveToFirst();

        return cursor2recurso(cursor);
    }


    /**
     * @param cu
     * @return
     */
    private Recurso cursor2recurso(Cursor cu) {

        Recurso rec = new Recurso();

        rec.id = cu.getInt(0);
        rec.lat = cu.getDouble(1);
        rec.lon = cu.getDouble(2);

        rec.sNombre = cu.getString(3);
        rec.srId = cu.getInt(4);
        rec.sTipo = cu.getString(5);
        rec.sAdscripcion = cu.getString(6);

        rec.cuenta_imp = 0;

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

        database.execSQL(SQUERY_BORRA);
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
                    InfraPatEntry.COLUMN_NAME +
                    ") VALUES ('" +
                    jrec.getString(InfraPatEntry.COLUMN_ADS) + "'," +
                    jrec.getInt(InfraPatEntry.COLUMN_MSR) + "," +
                    jrec.getInt(InfraPatEntry.COLUMN_SRID) + ",'" +
                    jrec.getString(InfraPatEntry.COLUMN_TYPE) + "','" +
                    jrec.getString(InfraPatEntry.COLUMN_LON) + "','" +
                    jrec.getString(InfraPatEntry.COLUMN_LAT) + "','" +
                    jrec.getString(InfraPatEntry.COLUMN_NAME) + "')";

            database.execSQL(SQUERY_INSERTA);
            Log.d(LOG_TAG, "Inserta: " + SQUERY_INSERTA);
        }

        return true;
    }

    /**
     * Método que borra toda la tabla
     *
     * @return
     */
    public boolean borraT() {

        final String SQUERY_BORRAT = "DELETE FROM " + InfraPatEntry.TABLE_NAME;

        database.execSQL(SQUERY_BORRAT);

        return true;
    }

    /**
     * Método que recupera el ultimo <strong>msr</strong> de la BD para fines de actualización
     *
     * @return
     */
    public long obtenMSRultimo() {

        long cuenta = 0;

        Cursor cursor = database.rawQuery("SELECT COUNT(*) FROM " + InfraPatEntry.TABLE_NAME, null);

        if (cursor != null) {
            cursor.moveToFirst();
            if (cursor.getInt(0) > 0) {

                String[] sa = {"MAX(msr)"};

                cursor = database.query(InfraPatEntry.TABLE_NAME, sa, null, null, null, null, null);
                cursor.moveToFirst();

                cuenta = cursor.getLong(0);
            }
        }

        cursor.close();
        return cuenta;

    }


}
