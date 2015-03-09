package mx.gob.conaculta.msic;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by alfonso on 08/02/15.
 */
public class ListadoRecAdapter extends CursorAdapter {

private Context context2;


    /**
     * Clase para manejar los componentes de la entrada de la lista
     */
    public static class ViewHolder {

        public final TextView textNombreRec;
        public final TextView textExtraRec;
        public final ImageView iv_mapa;


        public ViewHolder(View view) {
            textNombreRec = (TextView) view.findViewById(R.id.textNombreRec);
            textExtraRec = (TextView) view.findViewById(R.id.textExtraRec);
            iv_mapa = (ImageView) view.findViewById(R.id.imageIconMapa);

        }
    }


    public interface OnImageClickListener {
        public void onClick(View view, Object data); // Object data [Optional]
    }


    /**
     * @param context
     * @param c
     * @param flags
     */
    public ListadoRecAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        context2 =context;
    }


    /**
     *
     * @param context
     * @param cursor
     * @param parent
     * @return
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_recurso, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    /**
     *
     * @param view
     * @param context
     * @param cursor
     */
    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();

        String sNombreRec = cursor.getString(3);
        viewHolder.textNombreRec.setText(sNombreRec);

        String sExtraRec = cursor.getString(6);
        viewHolder.textExtraRec.setText(sExtraRec);

        viewHolder.iv_mapa.setId(cursor.getInt(0));

        viewHolder.iv_mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((OnImageClickListener)context).onClick(v,null);
            }
        });



    }

    /**
     *
     * @return
     */
    @Override
    public int getViewTypeCount() {
        return 1;
    }


}
