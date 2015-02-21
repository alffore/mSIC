package mx.gob.conaculta.msic.data;


import android.content.Context;

import android.database.Cursor;
import android.support.v4.content.CursorLoader;

/**
 * Created by alfonso on 19/02/15.
 */
public class MSiCLoader extends CursorLoader {

    private MSiCDBOper msicdbo;

    public MSiCLoader(Context context,MSiCDBOper msicdbo) {
        super(context);
        this.msicdbo=msicdbo;
    }


    @Override
    public Cursor loadInBackground() {

        return msicdbo.obtenCursorAll();
    }
}
