package mx.gob.conaculta.msic;


import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;


/**
 * Created by alfonso on 08/02/15.
 */
public class ListadoRecFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    private  ListadoRecAdapter listadoRecAdapter=null;


    public ListadoRecFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_listadorec, container, false);


        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {


        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
