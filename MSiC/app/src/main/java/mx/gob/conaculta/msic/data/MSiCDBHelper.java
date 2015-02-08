package mx.gob.conaculta.msic.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by alfonso on 08/02/15.
 */
public class MSiCDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "msic.db";
    
    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;


    public MSiCDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                        int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
