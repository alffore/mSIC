package mx.gob.conaculta.msic.data;


import android.content.Context;

import android.database.Cursor;
import android.support.v4.content.CursorLoader;

/**
 * Created by alfonso on 19/02/15.
 */
public class MSiCLoader extends CursorLoader {

    public MSiCLoader(Context context) {
        super(context);
    }


    @Override
    public Cursor loadInBackground() {

        return null;
    }
}
