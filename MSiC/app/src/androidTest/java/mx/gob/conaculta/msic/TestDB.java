package mx.gob.conaculta.msic;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

import java.util.Map;
import java.util.Set;

import mx.gob.conaculta.msic.data.MSiCDBHelper;
import mx.gob.conaculta.msic.data.MSiCContract.InfraPatEntry;

/**
 * Created by alfonso on 24/02/15.
 */
public class TestDB extends AndroidTestCase {
    public static final String LOG_TAG = TestDB.class.getSimpleName();

    public void testCreateDb() throws Throwable {
        mContext.deleteDatabase(MSiCDBHelper.DATABASE_NAME);
        SQLiteDatabase db = new MSiCDBHelper(
                this.mContext,null,null,0).getWritableDatabase();
        assertEquals(true, db.isOpen());
        db.close();
    }


    public void testInsertRecordDb() throws  Throwable{

        MSiCDBHelper dbHelper = new MSiCDBHelper(mContext,null,null,0);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues testValues = createRecursoValues();

        long locationRowId;
        locationRowId = db.insert(InfraPatEntry.TABLA_NOMBRE, null, testValues);

        // Verify we got a row back.
        assertTrue(locationRowId != -1);
        Log.d(LOG_TAG, "New row id: " + locationRowId);

        Cursor cursor = db.query(
                InfraPatEntry.TABLA_NOMBRE,  // Table to Query
                null, // all columns
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        validateCursor(cursor, testValues);
    }


public void testSelectRec() throws Throwable{
    MSiCDBHelper dbHelper = new MSiCDBHelper(mContext,null,null,0);
    SQLiteDatabase db = dbHelper.getReadableDatabase();


    String[] allColumns = {InfraPatEntry._ID, InfraPatEntry.COLUMNA_LAT, InfraPatEntry.COLUMNA_LON,
            InfraPatEntry.COLUMNA_NOMBRE, InfraPatEntry.COLUMNA_SRID, InfraPatEntry.COLUMNA_TABLA, InfraPatEntry.COLUMNA_ADS};

    String sWhere=InfraPatEntry._ID+"=?";
    String sArgs[]={"1"};

    Cursor cursor=db.query(InfraPatEntry.TABLA_NOMBRE,allColumns,sWhere,sArgs,null,null,null);
    cursor.moveToFirst();
}


    static ContentValues createRecursoValues() {
        // Create a new map of values, where column names are the keys
        ContentValues testValues = new ContentValues();


        testValues.put(InfraPatEntry.COLUMNA_ADS,"CONACULTA/INAH");
        testValues.put(InfraPatEntry.COLUMNA_LAT,"19.02");
        testValues.put(InfraPatEntry.COLUMNA_LON,"-98.3");
        testValues.put(InfraPatEntry.COLUMNA_MSR,1424379833);
        testValues.put(InfraPatEntry.COLUMNA_NOMBRE,"Museo prueba para guardar");
        testValues.put(InfraPatEntry.COLUMNA_SRID,1234);
        testValues.put(InfraPatEntry.COLUMNA_TABLA,"museo");



        return testValues;
    }

    static void validateCursor(Cursor valueCursor, ContentValues expectedValues) {

        assertTrue(valueCursor.moveToFirst());

        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse(idx == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals(expectedValue, valueCursor.getString(idx));
        }
        valueCursor.close();
    }
}
