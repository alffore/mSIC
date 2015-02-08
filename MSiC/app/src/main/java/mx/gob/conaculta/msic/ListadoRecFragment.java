package mx.gob.conaculta.msic;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;


/**
 * Created by alfonso on 08/02/15.
 */
public class ListadoRecFragment extends Fragment {

    public ListadoRecFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_listadorec, container, false);


        return rootView;
    }
}
