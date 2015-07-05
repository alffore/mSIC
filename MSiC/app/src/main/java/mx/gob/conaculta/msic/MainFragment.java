package mx.gob.conaculta.msic;




import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import mx.gob.conaculta.msic.listado.ListadoRecActivity;
import mx.gob.conaculta.msic.utils.MSiCConst;

/**
 * Created by alfonso on 01/02/15.
 */
public class MainFragment extends Fragment {


    public MainFragment() {


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_menutemas, container, false);

        GridView gridview = (GridView) rootView.findViewById(R.id.gridView);

        gridview.setAdapter(new MenuTemasAdapter(getActivity()));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();


                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(MSiCConst.STEMA, MSiCConst.MT_ARRAY_MOD[position]);
                editor.apply();

                Intent intent = new Intent(getActivity(), ListadoRecActivity.class);
                intent.putExtra(MSiCConst.SQUERYB, "");
                startActivity(intent);


            }
        });


        return rootView;
    }


}
