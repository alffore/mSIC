package mx.gob.conaculta.msic;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;

import android.view.MenuItem;

/**
 * Created by alfonso on 07/02/15.
 */
public class MapaActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mapa);



       /*if (savedInstanceState == null) {

            FragmentManager fragmentManager= getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            MapaFragment mapaF = new MapaFragment();

            fragmentTransaction.add(R.id.map, mapaF);
            fragmentTransaction.commit();
        }*/

       /* setContentView(R.layout.activity_mapa);
        if (savedInstanceState == null) {
            int commit = getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MapaFragment())
                    .commit();
        }*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_mapa, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
