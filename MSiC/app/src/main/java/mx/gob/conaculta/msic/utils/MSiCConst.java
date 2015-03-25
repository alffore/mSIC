package mx.gob.conaculta.msic.utils;

/**
 * Created by alfonso on 01/03/15.
 */
public class MSiCConst {

    //campos de preferencias
    public static final String STEMA = "tabla";
    public static final String SIDSIC = "id";
    public static final String SMSR = "msr";
    public static final String SID="sid";


    //datos
    public static final String[] MT_ARRAY = {"Museo", "Teatro", "Casas y Centros Culturales", "Librerías", "Galerías", "Bibliotecas"};
    public static final String[] MT_ARRAY_MOD = {"museo", "teatro", "centro_cultural", "libreria", "galeria", "bibilioteca"};


    //URLs solicitudes
    public static final String SDBSIC_BASE_URL = "http://msic.conaculta.gob.mx/app/infra2.php?";
    public static final String SFICHA_URL = "http://msic.conaculta.gob.mx/ficha.php?";


    public static final String SSPREF = "msicpref";


    //opciones para calculo distancias

    public static final double RT=6300000.00;

    public static final String SLAT="lat";
    public static final String SLON="lon";

    public static final double D2R=Math.PI/180.0;

    //opciones compartir
    public static final String MSIC_SHARE_HASHTAG = " #MSiCApp";
}
