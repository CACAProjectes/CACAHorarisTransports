package es.xuan.horaristransportsapp.varis;

import android.graphics.Color;
import android.graphics.Typeface;

import java.io.Serializable;

public class Constants implements Serializable {
    //
    public static final String CTE_PATH_APPS = "/apps";
    public static final String CTE_PATH_CACAVOLEI = "/apps/cacavoleiwg";
    public static final String CTE_PATH_CACAVOLEI_LOGS = "/apps/cacavoleiwg/logs.txt";
    //
    public static final String CTE_PATH_BBDD = "VBRepositorio";
    public static final String CTE_PATH_FILE_CSV_WEB_TMP = "Migracio.csv";
    public static final String CTE_FILE_EXT_CSV = ".csv";
    public static final String CTE_FILE_EXT_SQL = ".sql";
    //
    public static final long CTE_TIMER_PERIODO = 10000;
    //
    public static final String CTE_SEPARADOR_ELEMENT_CLASSIF = ",";
    public static final String CTE_SEPARADOR_CLASSIFICACIO = "/";
    public static final String CTE_SEPARADOR_SETS = "/";
    public static final String CTE_CANVI_LINEA_DESCRIP = "\n";
    public static final String CTE_PARENTESIS_INI = " [";
    public static final String CTE_PARENTESIS_FIN = "] ";
    public static final String CTE_PARENTESIS_2_INI = " (";
    public static final String CTE_PARENTESIS_2_FIN = ") ";
    public static final String CTE_SEPARADOR_RESULTAT = " - ";
    public static final String CTE_SEPARADOR_RESULTAT_RED = "-";
    public static final String CTE_SEPARADOR_TEXT = ";";
    public static final String CTE_ESPAI_BLANC = " ";
    //
    public static final int CTE_DILLUNS = 2;
    public static final int CTE_DIMARTS = 3;
    public static final int CTE_DIMECRES = 4;
    public static final int CTE_DIJOUS = 5;
    public static final int CTE_DIVENDRES = 6;
    public static final int CTE_DISSABTE = 7;
    public static final int CTE_DIUMENGE = 1;
    public static final int CTE_DIES_SETMANA = 7;
    public static final long CTE_VIBRATION_MS = 50;
    public static final String CTE_CLAVE_COMPETICIO_FILTRE = "SP_COMPETICIO_FILTRE";
    //
    public static final int CTE_DESPLACAMENT = 2;
    public static final int CTE_CAL_WIDTH_NORMAL = 100;
    public static final int CTE_CAL_HEIGHT_NORMAL = 100;
    public static final int CTE_CAL_WIDTH_SEL = 130;
    public static final int CTE_CAL_HEIGHT_SEL = 130;
    public static final int CTE_CAL_TEXT_SIZE_NORMAL = 20;
    public static final int CTE_CAL_TEXT_SIZE_SEL = 25;
	public static final int CTE_CAL_TEXT_TYPE_NORMAL = Typeface.NORMAL;
	public static final int CTE_CAL_TEXT_TYPE_SEL = Typeface.BOLD;
	public static final int CTE_CAL_TEXT_COLOR_NORMAL = Color.WHITE;
	public static final int CTE_CAL_TEXT_COLOR_SEL = Color.BLACK;
    public static final String CTE_ORIGEN_GRUP = "GRUP";
    public static final String CTE_ORIGEN_FILTRE = "FILTRE";
    public static final String CTE_ORIGEN_PARTITS_EQUIP = "PARTITS_EQUIP";
    public static final String CTE_SEPARADOR_ARBITRES = "/";
    public static final int CTE_MAX_JORNADES = 22;
    public static final int CTE_TEXT_LIMIT_TEXTBOX = 40;
}
