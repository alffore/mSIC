package mx.gob.conaculta.msic;

import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import java.net.MalformedURLException;
import java.net.URL;

import mx.gob.conaculta.msic.utils.MSiCConst;


public class FichaActivity extends ActionBarActivity {


    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha);

        cargaFicha();

    }

    /**
     *
     */
    private void cargaFicha(){
        Uri fichaUri = Uri.parse(MSiCConst.SFICHA_URL).buildUpon()
                .appendQueryParameter(MSiCConst.STEMA, getIntent().getStringExtra(MSiCConst.STEMA))
                .appendQueryParameter(MSiCConst.SIDSIC, getIntent().getStringExtra(MSiCConst.SIDSIC))
                .build();

        try {
            URL url = new URL(fichaUri.toString());
            WebView myWebView = (WebView) findViewById(R.id.webview);
            myWebView.loadUrl(url.toString());


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ficha, menu);
        return true;
    }

    /**
     *
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

        return super.onOptionsItemSelected(item);
    }
}
