package mx.gob.conaculta.msic.data;

import android.provider.BaseColumns;

/**
 * Created by alfonso on 08/02/15.
 */
public class MSiCContract {

    public static final String CONTENT_AUTHORITY = "mx.gob.conaculta.msic";

    /**
     *
     */
    public class InfraPatEntry implements BaseColumns {

        //nombre de la tabla
        public static final String TABLE_NAME="infrapat";



        //id del recurso
        public static final String COLUMN_SRID="sr_id";

        //tipo de recurso
        public static final String COLUMN_TYPE ="tipo";

        //nombre del recurso
        public static final String COLUMN_NAME ="nombre";

        //latitud
        public static final String COLUMN_LAT="latitud";

        //longitud
        public static final String COLUMN_LON="longitud";


    }
}
