package mx.gob.conaculta.msic;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import mx.gob.conaculta.msic.data.MSiCDBOper;
import mx.gob.conaculta.msic.data.RecRecursosTask;
import mx.gob.conaculta.msic.listado.ListadoRecActivity;
import mx.gob.conaculta.msic.location.GeoLoc;
import mx.gob.conaculta.msic.maps.MapaMultiRecActivity;
import mx.gob.conaculta.msic.utils.MSiCConst;


/**
 *
 */
public class MainActivity extends Activity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;


    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mMenuTitulos;

    //instancia para geolocalización
    protected GeoLoc geoLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        mTitle = mDrawerTitle = getTitle();
        mMenuTitulos = getResources().getStringArray(R.array.titulos_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);


        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mMenuTitulos));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, new MainFragment()).commit();
            /*int commit = getFragmentManager().beginTransaction()
                    .add(R.id.container, new MainFragment())
                    .commit();*/
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
            startActivity(new Intent(this,PreferenciasActivity.class));
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

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
        /*Fragment fragment = new PlanetFragment();
        Bundle args = new Bundle();
        args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mPlanetTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);*/
    }
}
