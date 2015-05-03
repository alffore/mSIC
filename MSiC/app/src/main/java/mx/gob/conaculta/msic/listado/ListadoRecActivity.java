package mx.gob.conaculta.msic.listado;


import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import mx.gob.conaculta.msic.FichaActivity;
import mx.gob.conaculta.msic.R;
import mx.gob.conaculta.msic.data.MSiCDBOper;
import mx.gob.conaculta.msic.data.RecRecursosTask;
import mx.gob.conaculta.msic.maps.MapaRecActivity;
import mx.gob.conaculta.msic.utils.MSiCConst;

/**
 * Created by alfonso on 08/02/15.
 */
public class ListadoRecActivity extends ActionBarActivity implements ListadoRecFragment.Callback, ListadoRecAdapter.OnImageClickListener {

    public static String stabla;
    public static LatLng posicionOri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listadorec);


        /*stabla = getIntent().getStringExtra(MSiCConst.STEMA);
        if (stabla.isEmpty()) {
            stabla = "museo";
        }
        Bundle arguments = new Bundle();
        arguments.putString(MSiCConst.STEMA, stabla);*/


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        stabla =preferences.getString(MSiCConst.STEMA,"museo");
        posicionOri=new LatLng(preferences.getFloat(MSiCConst.SLAT,0.0f),preferences.getFloat(MSiCConst.SLON,0.0f));


        ListadoRecFragment listadoRecFragment = new ListadoRecFragment();
        // listadoRecFragment.setArguments(arguments);

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


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(Cursor cursor) {

        Toast.makeText(this, "Se recibio objeto:" + cursor.getString(4) + " " + cursor.getString(5), Toast.LENGTH_SHORT).show();

        /*String url = "http://sic.gob.mx/ficha.php?table="+cursor.getString(5)+"&table_id="+cursor.getString(4);
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);*/

        Intent intent = new Intent(this, FichaActivity.class);
        intent.putExtra(MSiCConst.STEMA, cursor.getString(5));
        intent.putExtra(MSiCConst.SIDSIC, cursor.getString(4));
        startActivity(intent);

    }

    @Override
    public void onClick(View view, Object data) {
        //Toast.makeText(this,"Se recibio objeto para Mapa:"+((Cursor)data).getPosition(),Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Se recibio objeto para Mapa: " + view.getId(), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this,MapaRecActivity.class);
        intent.putExtra(MSiCConst.SID,String.valueOf(view.getId()));
        startActivity(intent);
    }
}
