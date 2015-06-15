package mx.gob.conaculta.msic.listado;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;

import android.os.Bundle;


import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import mx.gob.conaculta.msic.FichaActivity;
import mx.gob.conaculta.msic.R;
import mx.gob.conaculta.msic.data.MSiCDBOper;
import mx.gob.conaculta.msic.data.RecRecursosTask;
import mx.gob.conaculta.msic.maps.MapaMultiRecActivity;
import mx.gob.conaculta.msic.maps.MapaRecActivity;
import mx.gob.conaculta.msic.utils.MSiCConst;
import mx.gob.conaculta.msic.utils.Utiles;

/**
 * Created by alfonso on 08/02/15.
 */
public class ListadoRecActivity extends ActionBarActivity implements ListadoRecFragment.Callback, ListadoRecAdapter.OnImageClickListener {

    public static String stabla;
    public static LatLng posicionOri;
    public static String squeryB;

    private String sqB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listadorec);


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        stabla = preferences.getString(MSiCConst.STEMA, "museo");
        posicionOri = new LatLng(preferences.getFloat(MSiCConst.SLAT, 0.0f), preferences.getFloat(MSiCConst.SLON, 0.0f));
        handleIntent(getIntent());

        ListadoRecFragment listadoRecFragment = new ListadoRecFragment();

        this.setTitle(Utiles.obtenT2NC(stabla));

        if (savedInstanceState == null) {
            int commit = getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, listadoRecFragment)
                    .commit();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_listadorec, menu);


        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        // searchView.setOnQueryTextListener(this);

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
            Intent intent = new Intent(this, MapaMultiRecActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_refresh) {
            //MSiCSyncAdapter.syncImmediately(this);

            MSiCDBOper mSiCDBOper = new MSiCDBOper(this);
            mSiCDBOper.openDB();
            String msrMax = String.valueOf(mSiCDBOper.obtenMSRultimo(stabla));
            mSiCDBOper.closeDB();

            new RecRecursosTask(this).execute(msrMax);
            return true;
        }

        if (id == R.id.search_todos) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(Cursor cursor) {

        Toast.makeText(this, "Se recibio objeto:" + cursor.getString(4) + " " + cursor.getString(5), Toast.LENGTH_SHORT).show();


        Intent intent = new Intent(this, FichaActivity.class);
        intent.putExtra(MSiCConst.STEMA, cursor.getString(5));
        intent.putExtra(MSiCConst.SIDSIC, cursor.getString(4));
        intent.putExtra(MSiCConst.SID, cursor.getInt(0));
        startActivity(intent);

    }

    @Override
    public void onClick(View view, Object data) {


        Intent intent = new Intent(this, MapaRecActivity.class);
        intent.putExtra(MSiCConst.SID, String.valueOf(view.getId()));
        startActivity(intent);
    }


    @Override
    protected void onNewIntent(Intent intent) {

        handleIntent(intent);
    }

    /**
     * Metodo para procesar el intent
     * @param intent
     */
    private void handleIntent(Intent intent) {

        sqB=intent.getStringExtra(MSiCConst.SQUERYB);

        if(squeryB!=null && !squeryB.isEmpty() && sqB!=null && sqB.equals("")){
            squeryB="";
        }


        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            squeryB = intent.getStringExtra(SearchManager.QUERY);
        }
    }
}
