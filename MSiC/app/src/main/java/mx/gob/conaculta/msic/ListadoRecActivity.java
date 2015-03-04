package mx.gob.conaculta.msic;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import mx.gob.conaculta.msic.utils.MSiCConst;

/**
 * Created by alfonso on 08/02/15.
 */
public class ListadoRecActivity extends ActionBarActivity implements ListadoRecFragment.Callback {

    private SharedPreferences mpref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listadorec);


        String stema=getIntent().getStringExtra(MSiCConst.STEMA);
        Bundle arguments = new Bundle();
        arguments.putString(MSiCConst.STEMA, stema);

        mpref= getSharedPreferences(MSiCConst.SSPREF, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor ed = mpref.edit();
        ed.putString(MSiCConst.STEMA, stema);


        ListadoRecFragment listadoRecFragment=new ListadoRecFragment();
        listadoRecFragment.setArguments(arguments);

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
            /*Intent intent = new Intent(this, MapaActivity.class);
            startActivity(intent);*/
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(Cursor cursor) {

        Toast.makeText(this,"Se recibio objeto:"+cursor.getString(4)+" "+cursor.getString(5),Toast.LENGTH_SHORT).show();

        /*String url = "http://sic.gob.mx/ficha.php?table="+cursor.getString(5)+"&table_id="+cursor.getString(4);
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);*/

        Intent intent = new Intent(this,FichaActivity.class);
        intent.putExtra(MSiCConst.STEMA,cursor.getString(5));
        intent.putExtra(MSiCConst.SIDSIC,cursor.getString(4));
        startActivity(intent);

    }
}
