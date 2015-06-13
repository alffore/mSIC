package mx.gob.conaculta.msic.utils;

import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;

import mx.gob.conaculta.msic.data.Recurso;

/**
 * Created by alfonso on 08/02/15.
 */
public class Utiles {


    /**
     * @param rec1
     * @param rec2
     * @return
     */
    public static double distRR(Recurso rec1, Recurso rec2) {
        double daux = 0;

        return daux;
    }

    /**
     * @param rec
     * @param lat
     * @param lon
     * @return
     */
    public static double distRecPunto(Recurso rec, double lat, double lon) {

        double lat0=rec.lat*MSiCConst.D2R;
        double lon0=rec.lon*MSiCConst.D2R;
        double lat1=lat*MSiCConst.D2R;
        double lon1=lon*MSiCConst.D2R;

        double daux = Math.sin(lat0) * Math.sin(lat1);

        daux += Math.cos(lat0) * Math.cos(lon0) * Math.cos(lat1) * Math.cos(lon1);

        daux += Math.cos(lat0) * Math.sin(lon0) * Math.cos(lat1) * Math.sin(lon1);

        daux = MSiCConst.RT * Math.acos(daux);

        return daux;
    }

    public static double distRecPunto(Recurso rec, LatLng latLng){
        return Utiles.distRecPunto(rec,latLng.latitude,latLng.longitude);
    }

    /**
     * Método para calcular la distancia entre puntos en metros
     * @param lat0
     * @param lon0
     * @param lat
     * @param lon
     * @return
     */
    public static double distPP(double lat0, double lon0, double lat, double lon) {

        lat0*=MSiCConst.D2R;
        lon0*=MSiCConst.D2R;
        lat*=MSiCConst.D2R;
        lon*=MSiCConst.D2R;

        double daux = Math.sin(lat0) * Math.sin(lat);

        daux += Math.cos(lat0) * Math.cos(lon0) * Math.cos(lat) * Math.cos(lon);

        daux += Math.cos(lat0) * Math.sin(lon0) * Math.cos(lat) * Math.sin(lon);

        daux = MSiCConst.RT * Math.acos(daux);

        return daux;
    }

    /**
     * Método para formatear la salidad de distancias
     * @param dist
     * @return
     */
    public static String salidaDist(double dist) {
        String sal = "";

        DecimalFormat formatter = new DecimalFormat("#,###.##");

        if (dist >= 1000.00) {

            sal = " (" + formatter.format(dist / 1000.00) + "km)";
        } else {
            sal = " (" + formatter.format(dist) + "m)";
        }

        return sal;
    }

    /**
     * Metodo que regresa el nombre comun de un modulo
     * @param stabla
     * @return
     */
    public static String obtenT2NC(String stabla){
        int tam=MSiCConst.MT_ARRAY_MOD.length;
        for(int i=0;i<tam;i++){

            if(MSiCConst.MT_ARRAY_MOD[i].equals(stabla)){
                return MSiCConst.MT_ARRAY[i];
            }
        }

        return null;
    }
}
