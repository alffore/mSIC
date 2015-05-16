package mx.gob.conaculta.msic;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;


import mx.gob.conaculta.msic.maps.MapaRecActivity;
import mx.gob.conaculta.msic.utils.MSiCConst;

/**
 *
 */
public class FichaActivity extends ActionBarActivity {

    private ShareActionProvider mShareActionProvider;

    public final String LOG_TAG = FichaActivity.class.getSimpleName();

    private String sCadURL_share;

    private String stema;
    private String sidsic;
    private String sid;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha);
        sCadURL_share = null;
        stema = getIntent().getStringExtra(MSiCConst.STEMA);
        sidsic = getIntent().getStringExtra(MSiCConst.SIDSIC);
        sid = getIntent().getStringExtra(MSiCConst.SID);


        cargaFicha();

    }

    /**
     *
     */
    private void cargaFicha() {
        Uri fichaUri = Uri.parse(MSiCConst.SFICHA_URL).buildUpon()
                .appendQueryParameter(MSiCConst.STEMA, stema)
                .appendQueryParameter(MSiCConst.SIDSIC, sidsic)
                .build();

        try {
            URL url = new URL(fichaUri.toString());
            WebView myWebView = (WebView) findViewById(R.id.webview);
            myWebView.loadUrl(url.toString());

            sCadURL_share = url.toString();

            Log.d(LOG_TAG, sCadURL_share);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    /**
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ficha, menu);

        MenuItem menuItem = menu.findItem(R.id.action_share);

        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        if (sCadURL_share != null) {
            mShareActionProvider.setShareIntent(createShareMSiCIntent());
        }

        return true;
    }

    /**
     * @return
     */
    private Intent createShareMSiCIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, sCadURL_share + MSiCConst.MSIC_SHARE_HASHTAG);
        return shareIntent;
    }

    /**
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_mapa) {
            Toast.makeText(this, "Mapa", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MapaRecActivity.class);
            intent.putExtra(MSiCConst.SIDSIC, sid);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
