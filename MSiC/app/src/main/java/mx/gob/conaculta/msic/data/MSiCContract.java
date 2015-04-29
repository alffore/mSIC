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
        public static final String TABLA_NOMBRE = "infrapat";

        //id del recurso
        public static final String COLUMNA_SRID = "sid";

        //tipo de recurso
        public static final String COLUMNA_TABLA = "tabla";

        //nombre del recurso
        public static final String COLUMNA_NOMBRE = "nom";

        //adscripcion
        public static final String COLUMNA_ADS = "ads";

        //latitud
        public static final String COLUMNA_LAT = "lat";

        //longitud
        public static final String COLUMNA_LON = "lon";

        //serial_renic
        public static final String COLUMNA_MSR = "msr";


        public static final String COLUMNA_INFOP = "infop";

    }
}
