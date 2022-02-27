package es.xuan.horaristransportsapp.gestor;

import android.content.SharedPreferences;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

import es.xuan.horaristransportsapp.model.Linia;

public class Repositori implements Serializable {
    private static final long serialVersionUID = 1L;

    private static Hashtable<String, ArrayList<String>> mHstDades = new Hashtable<>();
    public static String CTE_REPOSITORI_LINIA = "L";
    public static String CTE_REPOSITORI_ORIGEN = "ORIGEN";
    public static String CTE_REPOSITORI_DESTI = "DESTI";
    public static final String CTE_SEPARADOR_TEXT_KEY = "-";

    public static Hashtable<String, ArrayList<String>> getDades() {
        return mHstDades;
    }
    public static void setDades(Hashtable<String, ArrayList<String>> mHstDades) {
        Repositori.mHstDades = mHstDades;
    }

    /*
            Obtenir Linies i Sentits ()
                "L-1-2-Can Serrafossà-El Pinar"
                "L-1-1-El Pinar-Can Serrafossà"
                "L-2-4-El Pinar/Can Rosés-Can Vallhonrat"
                "L-2-3-Can Vallhonrat-El Pinar/Can Rosés"
                "L-3-14-Estació RubÍ+D-Castellnou"
                "L-3-13-Castellnou-Estació RubÍ+D"
                "L-4-6-Can Rosés-Estació RubÍ+D"
                "L-4-5-Estació RubÍ+D-Can Rosés"
                "L-5-8-Estació RubÍ+D-Can Rosés"
                "L-5-7-Can Rosés-Estació RubÍ+D"
                "L-6-12-La Bastida-Pol. Rubí Sud"
                "L-6-9-Pol. Rubí Sud-Pol. La Bastida"
                "L-7-16-Sant Muç-Estació RubÍ+D"
                "L-7-15-Estació RubÍ+D-Sant Muç"

            Obtenir parades (SENTIT)
                "8-775-Antoni Sedó"
                "8-154-Estació RubÍ+D"
                "8-155-Joan Puig"
                "8-156-Servet"
                "8-157-Doctor Ferran"
                "8-158-Països Catalans"
                "8-160-Anton de Borja"
                "8-161-25 de Setembre"
                "8-166-Plana de Can Bertran"
                "8-167-Lourdes"
                "8-168-Ca nOriol"
                "8-169-Segòvia"
                "8-170-Mallorca"
                "8-171-Font del Ferro"
                "8-172-El Ferrol"
                "8-173-Els Nius"
                "8-174-Olimpíades"
                "8-175-Can Rosés"
                "8-818-Antoni Sedó Mercadal"
                "8-374-Sant Cugat"
                "8-375-Cervantes"
                "8-376-Mercat"
                "8-377-Magí Ramentol"
                "8-378-Biblioteca"

            Obtenir hores (LINIA-SENTIT-JORNADA-PARADA)
                "4-5-1-368-06:33"
                "4-5-1-368-06:55"
                "4-5-1-368-07:17"
                "4-5-1-368-07:41"
                "4-5-1-368-08:05"
                "4-5-1-368-08:29"
                "4-5-1-368-08:53"
                "4-5-1-368-09:17"
                "4-5-1-368-09:41"
                "4-5-1-368-10:05"
                "4-5-1-368-10:29"
                "4-5-1-368-10:53"
                "4-5-1-368-11:17"
                "4-5-1-368-11:41"
                "4-5-1-368-12:05"
                "4-5-1-368-12:29"
                "4-5-1-368-12:53"
                "4-5-1-368-13:17"
                "4-5-1-368-13:41"
                "4-5-1-368-14:05"
                "4-5-1-368-21:09"
                "4-5-1-368-21:31"
                "4-5-1-368-21:50"
                "4-5-1-368-22:09"

         */
    public static ArrayList<String> getDadesKey(String pKey) {
        return mHstDades.get(pKey);
    }
    public static void putDades(String pKey, ArrayList<String> pDades) {
        mHstDades.put(pKey, pDades);
    }

    public static ArrayList<Linia> parserString2Linies(ArrayList<String> pLinies) {
        ArrayList<Linia> linies = new ArrayList<>();
        /*
            Obtenir Linies i Sentits ()
                "L-1-2-Can Serrafossà-El Pinar"
                "L-1-1-El Pinar-Can Serrafossà"
                "L-2-4-El Pinar/Can Rosés-Can Vallhonrat"
                "L-2-3-Can Vallhonrat-El Pinar/Can Rosés"
                "L-3-14-Estació RubÍ+D-Castellnou"
                "L-3-13-Castellnou-Estació RubÍ+D"
                "L-4-6-Can Rosés-Estació RubÍ+D"
                "L-4-5-Estació RubÍ+D-Can Rosés"
                "L-5-8-Estació RubÍ+D-Can Rosés"
                "L-5-7-Can Rosés-Estació RubÍ+D"
                "L-6-12-La Bastida-Pol. Rubí Sud"
                "L-6-9-Pol. Rubí Sud-Pol. La Bastida"
                "L-7-16-Sant Muç-Estació RubÍ+D"
                "L-7-15-Estació RubÍ+D-Sant Muç"
         */
        for (String linia : pLinies) {
            String[] strAux = linia.split(CTE_SEPARADOR_TEXT_KEY);
            Linia liniaAux = new Linia(Integer.parseInt(strAux[1]), Integer.parseInt(strAux[2]), strAux[3] + CTE_SEPARADOR_TEXT_KEY + strAux[4]);
            linies.add(liniaAux);
        }
        return linies;
    }
    public static ArrayList<String> parserLinies2String(ArrayList<Linia> pLinies) {
        ArrayList<String> linies = new ArrayList<>();
        /*
            Obtenir Linies i Sentits ()
                "L-1-2-Can Serrafossà-El Pinar"
                "L-1-1-El Pinar-Can Serrafossà"
                "L-2-4-El Pinar/Can Rosés-Can Vallhonrat"
                "L-2-3-Can Vallhonrat-El Pinar/Can Rosés"
                "L-3-14-Estació RubÍ+D-Castellnou"
                "L-3-13-Castellnou-Estació RubÍ+D"
                "L-4-6-Can Rosés-Estació RubÍ+D"
                "L-4-5-Estació RubÍ+D-Can Rosés"
                "L-5-8-Estació RubÍ+D-Can Rosés"
                "L-5-7-Can Rosés-Estació RubÍ+D"
                "L-6-12-La Bastida-Pol. Rubí Sud"
                "L-6-9-Pol. Rubí Sud-Pol. La Bastida"
                "L-7-16-Sant Muç-Estació RubÍ+D"
                "L-7-15-Estació RubÍ+D-Sant Muç"
         */
        for (Linia linia : pLinies) {
            String liniaAux = CTE_REPOSITORI_LINIA + CTE_SEPARADOR_TEXT_KEY +
                    linia.getIdLinia() + CTE_SEPARADOR_TEXT_KEY +
                    linia.getIdSentit() + CTE_SEPARADOR_TEXT_KEY +
                    linia.getNomLinia();
            linies.add(liniaAux);
        }
        return linies;
    }
    /*
    public static ArrayList<String> parserString2Hores(ArrayList<String> pHores) {
        /*
            Obtenir hores (LINIA-SENTIT-JORNADA-PARADA)
            "4-5-1-368-06:33"
            "4-5-1-368-06:55"
            "4-5-1-368-07:17"
            "4-5-1-368-07:41"
            "4-5-1-368-08:05"
            "4-5-1-368-08:29"
            "4-5-1-368-08:53"
            "4-5-1-368-09:17"
            "4-5-1-368-09:41"
            "4-5-1-368-10:05"
            "4-5-1-368-10:29"
            "4-5-1-368-10:53"
            "4-5-1-368-11:17"
            "4-5-1-368-11:41"
            "4-5-1-368-12:05"
            "4-5-1-368-12:29"
            "4-5-1-368-12:53"
            "4-5-1-368-13:17"
            "4-5-1-368-13:41"
            "4-5-1-368-14:05"
            "4-5-1-368-21:09"
            "4-5-1-368-21:31"
            "4-5-1-368-21:50"
            "4-5-1-368-22:09"
        *
        ArrayList<String> hores = new ArrayList<>();
        for (String hora : pHores) {
            hores.add(hora);
        }
        return hores;
    }
     */
}
