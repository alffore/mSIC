package mx.gob.conaculta.msic;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import mx.gob.conaculta.msic.data.MSiCDBOper;


/**
 * Created by alfonso on 08/02/15.
 */
public class RecRecursosTask extends AsyncTask<String, Void, String[]> {

    private final String LOG_TAG = RecRecursosTask.class.getSimpleName();

    private final Context mContext;

    private MSiCDBOper msicdbo = null;

    /**
     * @param context
     */
    public RecRecursosTask(Context context) {
        mContext = context;
        msicdbo = new MSiCDBOper(context);
    }


    @Override
    protected String[] doInBackground(String... params) {


        if (params.length == 0) {
            return null;
        }

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;


        String recursosJsonStr = null;

        try {

            final String DBSIC_BASE_URL =
                    "http://sic.gob.mx/msic/infra2.php?";

            final String MSR_PARAM = "msr";

            Uri uriRec = Uri.parse(DBSIC_BASE_URL).buildUpon()
                    .appendQueryParameter(MSR_PARAM, params[0])
                    .build();

            URL url = new URL(uriRec.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();


            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            if (inputStream == null) {
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }

            recursosJsonStr = buffer.toString();

        } catch (IOException e) {
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        try {
            return obtenRecursosFromJson(recursosJsonStr);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }

        return null;
    }


    /**
     * @param recusosJsonStr
     * @return
     * @throws JSONException
     */
    private String[] obtenRecursosFromJson(String recusosJsonStr) throws JSONException {

        JSONArray recursosJson = new JSONArray(recusosJsonStr);
        msicdbo.openDB();

        for (int i = 0; i < recursosJson.length(); i++)
            msicdbo.guardaJSONRec(recursosJson.getJSONObject(i));

        msicdbo.closeDB();

        return null;
    }
}
