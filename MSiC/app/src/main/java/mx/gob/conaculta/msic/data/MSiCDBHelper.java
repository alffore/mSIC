package mx.gob.conaculta.msic.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import mx.gob.conaculta.msic.data.MSiCContract.InfraPatEntry;

/**
 * Created by alfonso on 08/02/15.
 */
public class MSiCDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "msic.db";


    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 2;

    /**
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    public MSiCDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                        int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    /**
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_INFRAPAT_TABLE = "CREATE TABLE " + InfraPatEntry.TABLA_NOMBRE + " (" +
                InfraPatEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                InfraPatEntry.COLUMNA_SRID + " INTEGER NOT NULL, " +
                InfraPatEntry.COLUMNA_TABLA + " TEXT NOT NULL, " +
                InfraPatEntry.COLUMNA_NOMBRE + " TEXT NOT NULL, " +
                InfraPatEntry.COLUMNA_ADS + " TEXT, " +
                InfraPatEntry.COLUMNA_LAT + " REAL, " +
                InfraPatEntry.COLUMNA_MSR + " INTEGER NOT NULL, " +
                InfraPatEntry.COLUMNA_LON + " REAL );";

        db.execSQL(SQL_CREATE_INFRAPAT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + InfraPatEntry.TABLA_NOMBRE);
        onCreate(db);
    }

}
