package mx.gob.conaculta.msic.utils;

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

        double daux = Math.sin(rec.lat) * Math.sin(lat);

        daux += Math.cos(rec.lat) * Math.cos(rec.lon) * Math.cos(lat) * Math.cos(lon);

        daux += Math.cos(rec.lat) * Math.sin(rec.lon) * Math.cos(lat) * Math.sin(lon);

        daux = MSiCConst.RT * Math.acos(daux);

        return daux;
    }

    /**
     *
     * @param lat0
     * @param lon0
     * @param lat
     * @param lon
     * @return
     */
    public static double distPP(double lat0, double lon0, double lat, double lon) {
        double daux = Math.sin(lat0) * Math.sin(lat);

        daux += Math.cos(lat0) * Math.cos(lon0) * Math.cos(lat) * Math.cos(lon);

        daux += Math.cos(lat0) * Math.sin(lon0) * Math.cos(lat) * Math.sin(lon);

        daux = MSiCConst.RT * Math.acos(daux);

        return daux;
    }

    /**
     *
     * @param dist
     * @return
     */
    public static String salidaDist(double dist) {
        String sal = "";

        if (dist >= 1000) {
            DecimalFormat formatter = new DecimalFormat("#,###.##");
            sal = "(" + formatter.format(dist / 1000) + "km)";
        } else {
            sal = "(" + String.valueOf(dist) + "m)";
        }

        return sal;
    }
}
