package mx.gob.conaculta.msic;


import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import mx.gob.conaculta.msic.data.MSiCDBOper;
import mx.gob.conaculta.msic.location.GeoLoc;
import mx.gob.conaculta.msic.maps.MapaMultiRecActivity;
import mx.gob.conaculta.msic.utils.MSiCConst;


/**
 *
 */
public class MainActivity extends ActionBarActivity {


    protected GeoLoc geoLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            int commit = getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MainFragment())
                    .commit();
        }

        buildGoogleApiClient();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

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

           /* Intent intent = new Intent(this, MapaActivity.class);
            startActivity(intent);*/
            return true;
        }

        if(id==R.id.action_multimapa){
            Intent intent = new Intent(this, MapaMultiRecActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_listado) {
            Intent intent = new Intent(this, ListadoRecActivity.class);
            intent.putExtra(MSiCConst.STEMA,"museo");
            startActivity(intent);
            return true;
        }


        if (id == R.id.action_refresh) {
            //MSiCSyncAdapter.syncImmediately(this);

            MSiCDBOper mSiCDBOper = new MSiCDBOper(this);
            mSiCDBOper.openDB();
            String msrMax= String.valueOf(mSiCDBOper.obtenMSRultimo());
            mSiCDBOper.closeDB();

            new RecRecursosTask(this).execute(msrMax);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    protected synchronized void buildGoogleApiClient() {

        geoLoc = new GeoLoc(this);

    }


    @Override
    protected void onStart() {
        super.onStart();
        geoLoc.mGoogleApiClient.connect();


    }

    @Override
    protected void onStop() {
        super.onStop();
        if (geoLoc.mGoogleApiClient.isConnected()) {
            geoLoc.mGoogleApiClient.disconnect();
        }
    }
}
