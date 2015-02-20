package mx.gob.conaculta.msic;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by alfonso on 08/02/15.
 */
public class ListadoRecAdapter extends CursorAdapter {


    public static class ViewHolder{

        public final TextView textNombreRec;

        public ViewHolder(View view){
            textNombreRec= (TextView) view.findViewById(R.id.textNombreRec);
        }
    }


    /**
     *
     * @param context
     * @param c
     * @param flags
     */
    public ListadoRecAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.entradarec, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }
}
