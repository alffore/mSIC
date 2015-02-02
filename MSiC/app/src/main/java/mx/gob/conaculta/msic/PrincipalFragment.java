package mx.gob.conaculta.msic;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by alfonso on 01/02/15.
 */
public class PrincipalFragment extends Fragment {


    public PrincipalFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_principal, container, false);
        return rootView;
    }


}
