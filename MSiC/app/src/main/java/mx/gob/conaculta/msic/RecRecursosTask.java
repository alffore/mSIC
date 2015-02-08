package mx.gob.conaculta.msic;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.net.HttpURLConnection;

/**
 * Created by alfonso on 08/02/15.
 */
public class RecRecursosTask extends AsyncTask<String, Void, String[]> {

    private final Context mContext;

    public RecRecursosTask(Context context){
        mContext=context;
    }

    @Override
    protected String[] doInBackground(String... params) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;





        return null;
    }
}
