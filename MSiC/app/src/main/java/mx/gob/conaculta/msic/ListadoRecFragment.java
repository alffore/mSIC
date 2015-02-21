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
import android.widget.ListView;

import mx.gob.conaculta.msic.data.MSiCContract;
import mx.gob.conaculta.msic.data.MSiCDBOper;
import mx.gob.conaculta.msic.data.MSiCLoader;
import mx.gob.conaculta.msic.data.MSiCContract.InfraPatEntry;


/**
 * Created by alfonso on 08/02/15.
 */
public class ListadoRecFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    private ListadoRecAdapter listadoRecAdapter = null;
    private ListView mListView;

    private MSiCDBOper msicdbo;
    private int mposition;


    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callback {
        /**
         * DetailFragmentCallback for when an item has been selected.
         */
        public void onItemSelected(int _id);
    }


    public ListadoRecFragment() {
        mposition = 0;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        msicdbo = new MSiCDBOper(getActivity());
        msicdbo.openDB();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        listadoRecAdapter = new ListadoRecAdapter(getActivity(), null, 0);

        View rootView = inflater.inflate(R.layout.fragment_listrecs, container, false);


        mListView = (ListView) rootView.findViewById(R.id.listRecs);
        mListView.setAdapter(listadoRecAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Cursor cursor = listadoRecAdapter.getCursor();
                if (cursor != null && cursor.moveToPosition(position)) {
                    ((Callback) getActivity())
                            .onItemSelected(cursor.getInt(0));
                }
                mposition = position;
            }
        });


        getLoaderManager().initLoader(1, null, this);

        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new MSiCLoader(this.getActivity(), msicdbo);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        listadoRecAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        listadoRecAdapter.swapCursor(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        msicdbo.closeDB();
    }

}
