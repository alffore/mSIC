package mx.gob.conaculta.msic.data;


import android.content.Context;

import android.database.Cursor;
import android.support.v4.content.CursorLoader;

/**
 * Created by alfonso on 19/02/15.
 */
public class MSiCLoader extends CursorLoader {

    private MSiCDBOper msicdbo;

    private String stema;

    /**
     * @param context
     * @param msicdbo
     */
    public MSiCLoader(Context context, MSiCDBOper msicdbo,String stema) {
        super(context);

        this.msicdbo = msicdbo;


        this.stema=stema;
    }


    @Override
    public Cursor loadInBackground() {
        if(stema.isEmpty()) {
            return msicdbo.obtenCursorAll();
        }else{
            return msicdbo.obtenCursor(stema);
        }
    }

}
