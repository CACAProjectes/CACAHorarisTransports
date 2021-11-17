package es.xuan.horaristransportsapp.varis;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.Html;
import android.text.Spanned;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utils implements Serializable {
	public static final String CTE_FORMAT_DATA_JSON = "dd-MM-yyyy";
    public static final String CTE_FORMAT_DATA_BD = "yyyy-MM-dd HH:mm:ss";
    public static final String CTE_FORMAT_DATA_RED_BD = "yyyy-MM-dd";
	public static final String CTE_FORMAT_DATA_COMP = "dd/MM/yyyy HH:mm:ss";
	public static final String CTE_FORMAT_DATA = "dd/MM/yyyy HH:mm";
	public static final String CTE_FORMAT_DATA_YY = "dd/MM/yy (HH:mm)";
	public static final String CTE_FORMAT_DATA_RED = "dd/MM/yyyy";
	public static final String CTE_FORMAT_DATA_HORA = "HH:mm";
	public static final String CTE_FORMAT_DATA_FILE = "dd-MM";
	public static final String CTE_FORMAT_DATA_DESCRIPCIO = "yyyyMMdd";
	private static final String CTE_FORMAT_DATA_SETMANA = "EEEE, dd/MM/yyyy HH:mm";

	public static void putValorSP(SharedPreferences p_spDades, String p_strKey, String p_strValor) {
		if (p_spDades != null) {
			SharedPreferences.Editor ed = p_spDades.edit();
			ed.putString(p_strKey, p_strValor);
			ed.commit();
		}
	}
	public static String getValorSP(SharedPreferences p_spDades, String p_strKey) {
		//To retrieve data from shared preference
		if (p_spDades != null)
			return p_spDades.getString(p_strKey, "");
		return "";
	}
	public static String data2StringFile(Date p_data) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(CTE_FORMAT_DATA_FILE, Locale.getDefault());
		try {
			return dateFormat.format(p_data);
		} catch (Exception e) {
			//e.printStackTrace();
		} 
		return null;
	}
	public static Date addSetmana(Date pDate) {
		Calendar cal = Calendar.getInstance();    
		cal.setTime(pDate);    
		cal.add(Calendar.DATE, 6);
		return cal.getTime();
	}
	public static void addSetmanaCal(Calendar pCalendar, Boolean pSumar) {
		if (pSumar)
			pCalendar.add(Calendar.DAY_OF_YEAR, 7);
		else
			pCalendar.add(Calendar.DAY_OF_YEAR, -7);
	}
    public static String data2StringBD(Date p_data) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(CTE_FORMAT_DATA_RED_BD, Locale.getDefault());
        try {
            return dateFormat.format(p_data);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return null;
    }
	public static Date string2DataJSON(String p_data) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(CTE_FORMAT_DATA_JSON, Locale.getDefault());
		try {
			return dateFormat.parse(p_data.trim());
		} catch (ParseException e) {
			//e.printStackTrace();
		}
		return null;
	}

	public static Date string2DataBD(String p_data) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(CTE_FORMAT_DATA_BD, Locale.getDefault());
		try {
			return dateFormat.parse(p_data.trim());
		} catch (ParseException e) {
			//e.printStackTrace();
		}
		return null;
	}
	public static Calendar string2DataDescrip(Integer p_data) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(CTE_FORMAT_DATA_DESCRIPCIO, Locale.getDefault());
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(dateFormat.parse("" + p_data));
			return cal;
		} catch (ParseException e) {
			//e.printStackTrace();
		}
		return null;
	}
	public static Date string2DataRed(String p_data) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(CTE_FORMAT_DATA_RED, Locale.getDefault());
		try {
			return dateFormat.parse(p_data.trim());
		} catch (ParseException e) {
			//e.printStackTrace();
		} 
		return null;
	}
	public static Date string2DataYY(String p_data) {
		// Fecha larga
		SimpleDateFormat dateFormat = new SimpleDateFormat(CTE_FORMAT_DATA_YY, Locale.getDefault());
		try {
			return dateFormat.parse(p_data.trim());
		} catch (ParseException e) {
		}
		return null;
	}
	public static Date string2Data(String p_data) {
		// Fecha larga
		SimpleDateFormat dateFormat = new SimpleDateFormat(CTE_FORMAT_DATA, Locale.getDefault());
		try {
			return dateFormat.parse(p_data.trim());
		} catch (ParseException e) {} 
		// Fecha corta
		dateFormat = new SimpleDateFormat(CTE_FORMAT_DATA_RED, Locale.getDefault());
		try {
			return dateFormat.parse(p_data.trim());
		} catch (ParseException e) {}
		return null;
	}
	public static String data2StringComp(Date p_data) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(CTE_FORMAT_DATA_COMP, Locale.getDefault());
		try {
			return dateFormat.format(p_data);
		} catch (Exception e) {
			//e.printStackTrace();
		} 
		return null;
	}
	public static String data2StringSetmana(Date p_data) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(CTE_FORMAT_DATA_SETMANA, Locale.getDefault());
		try {
			return dateFormat.format(p_data);
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return null;
	}
	public static String data2String(Date p_data) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(CTE_FORMAT_DATA, Locale.getDefault());
		try {
			return dateFormat.format(p_data);
		} catch (Exception e) {
			//e.printStackTrace();
		} 
		return null;
	}
	public static String data2StringRed(Date p_data) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(CTE_FORMAT_DATA_RED, Locale.getDefault());
		try {
			if (p_data != null)
				return dateFormat.format(p_data);
		} catch (Exception e) {
			//e.printStackTrace();
		} 
		return null;
	}
	public static Integer data2StringDescrip(Calendar p_data) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(CTE_FORMAT_DATA_DESCRIPCIO, Locale.getDefault());
		try {
			if (p_data != null)
				return Integer.parseInt(dateFormat.format(p_data.getTime()));
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return null;
	}

	public static String data2StringHora(Date p_data) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(CTE_FORMAT_DATA_HORA, Locale.getDefault());
		try {
			if (p_data != null)
				return dateFormat.format(p_data);
		} catch (Exception e) {
			//e.printStackTrace();
		} 
		return null;
	}
	public static Date addDia(Date pDate, int iDias) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(pDate);
		cal.add(Calendar.DATE, iDias);
		return cal.getTime();
	}
	public static Calendar addDia(Calendar pCal, int iDias) {
		Calendar cal = (Calendar) pCal.clone();
		cal.add(Calendar.DATE, iDias);
		return cal;
	}
	public static Date addDia(Date pDate) {
		Calendar cal = Calendar.getInstance();    
		cal.setTime(pDate);    
		cal.add(Calendar.DATE, 1);
		return cal.getTime();	
	}
	public static String string2DataMigStr(String pStrData, String pStrHora) {
		// 06/10/2018	18:15
		if (pStrData == null || pStrData.equals(""))
			pStrData = "01/01/0001";
		if (pStrHora == null || pStrHora.equals(""))
			pStrHora = "00:00";
		SimpleDateFormat dateFormat = new SimpleDateFormat(CTE_FORMAT_DATA, Locale.getDefault());
		try {
			Date dataDateUtil = new Date(dateFormat.parse((pStrData + " " + pStrHora).trim()).getTime());
			java.sql.Timestamp dataDate = new java.sql.Timestamp(dataDateUtil.getTime());
			SimpleDateFormat dateFormat2 = new SimpleDateFormat(CTE_FORMAT_DATA_BD, Locale.getDefault());
			return dateFormat2.format(dataDate);
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return null;
	}

	public static java.sql.Timestamp string2DataMig(String pStrData, String pStrHora) {
		// 06/10/2018	18:15
		if (pStrData == null || pStrData.equals(""))
			pStrData = "01/01/0001";
		if (pStrHora == null || pStrHora.equals(""))
			pStrHora = "00:00";
		SimpleDateFormat dateFormat = new SimpleDateFormat(CTE_FORMAT_DATA, Locale.getDefault());
		try {
			Date dataDateUtil = new Date(dateFormat.parse((pStrData + " " + pStrHora).trim()).getTime());
			java.sql.Timestamp dataDate = new java.sql.Timestamp(dataDateUtil.getTime());
			return dataDate;
		} catch (ParseException e) {
		} 
		return null;
	}

	public static String omplirBlancs2Like(String pText) {
		return pText.replaceAll(" ", "%");
	}

	public static int data2DiaSetmanaAvui() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DAY_OF_WEEK);
	}
	public static int data2DiaSetmana(Calendar pCal) {
		return pCal.get(Calendar.DAY_OF_WEEK);
	}

	public static int data2Mes(Calendar m_dataFiltre) {
		return m_dataFiltre.get(Calendar.MONTH);
	}

	public static int data2DiaMes() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	public static Calendar data2SumarDiaSel(Calendar pCal, int pDifDies) {
		Calendar cal = (Calendar) pCal.clone();
		cal.add(Calendar.DAY_OF_YEAR, pDifDies);
		return cal;
	}
    public static int data2SumarDia1(Calendar pCal, int pDifDies) {
		Calendar cal = (Calendar) pCal.clone();
		cal.add(Calendar.DAY_OF_YEAR, pDifDies);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public static Calendar data2Avui() {
        return Calendar.getInstance();
    }

    public static Calendar data2SumarDiaCal(Calendar pCal, int pDifDies) {
		Calendar cal = (Calendar) pCal.clone();
		cal.add(Calendar.DAY_OF_YEAR, pDifDies);
		return cal;
    }

    public static Date addDies2Diumenge(Date pDataPartit) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(pDataPartit);
        // Buscar el primer diumenge de la setmana
        while (cal.get(Calendar.DAY_OF_WEEK) != Constants.CTE_DIUMENGE) {
            cal.add(Calendar.DAY_OF_YEAR, 1);
        }
		cal.add(Calendar.DAY_OF_YEAR, 1);	// Ajustament pel BETWEEN de la BBDD SQLite
        return cal.getTime();
    }

	@SuppressWarnings("deprecation")
	public static void vibrar(Vibrator pVibrator, long pTempsVib) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			pVibrator.vibrate(VibrationEffect.createOneShot(pTempsVib, VibrationEffect.DEFAULT_AMPLITUDE));
		} else {
			pVibrator.vibrate(pTempsVib);
		}
	}

	@SuppressWarnings("deprecation")
	public static Spanned fromHtml(String html){
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
		} else {
			return Html.fromHtml(html);
		}
	}

	public static Integer[][] calcularDiesSetmana(Calendar pDataFiltre) {
		/*
			0-Dilluns
			1-Dimarts
			2-Dimecres
			3-Dijous
			4-Divendres
			5-Dissabte
			6-Diumenge
			--------------------------
			0-Width
			1-Height
			2-TextSize
			3-Typeface
			4-TextColor
			5-Text
		 */
		Integer[][] diesSetmana = new Integer[][] {
				{0,0,0,0,0,0,0},	// Dilluns
				{0,0,0,0,0,0,0},	// Dimarts
				{0,0,0,0,0,0,0},	// Dimecres
				{0,0,0,0,0,0,0},	// Dijous
				{0,0,0,0,0,0,0},	// Divendres
				{0,0,0,0,0,0,0},	// Dissabte
				{0,0,0,0,0,0,0}		// Diumenge
		};
		// Actualizar el dia de la setmana seleccionat
		int diaSetmana = Utils.data2DiaSetmana(pDataFiltre);
		int iPosicio = 0;
		int iValor = 0;
		int iDia = 0;
		iPosicio = diaSetmana - Constants.CTE_DESPLACAMENT;	// 2
		// Ajustamente pel DIUMENGE = 1
		if (iPosicio < 0)
			iPosicio = 6;
		// Recorrer ARRAY fins DIUMENGE
		for (int i=iPosicio;i<Constants.CTE_DIES_SETMANA;i++) {
			Calendar calAux = data2SumarDiaCal(pDataFiltre, iDia++);
			iValor = calAux.get(Calendar.DAY_OF_MONTH);	// Dia del mes
			// Atributs caselles
			if (i==iPosicio) {
				diesSetmana[i][0] = Constants.CTE_CAL_WIDTH_SEL;
				diesSetmana[i][1] = Constants.CTE_CAL_HEIGHT_SEL;
				diesSetmana[i][2] = Constants.CTE_CAL_TEXT_SIZE_SEL;
				if (esAvui(calAux)) {
					diesSetmana[i][3] = Constants.CTE_CAL_TEXT_TYPE_SEL;
					diesSetmana[i][4] = Constants.CTE_CAL_TEXT_COLOR_SEL;
				}
				else {
					diesSetmana[i][3] = Constants.CTE_CAL_TEXT_TYPE_NORMAL;
					diesSetmana[i][4] = Constants.CTE_CAL_TEXT_COLOR_NORMAL;
				}
			}
			else {
				diesSetmana[i][0] = Constants.CTE_CAL_WIDTH_NORMAL;
				diesSetmana[i][1] = Constants.CTE_CAL_HEIGHT_NORMAL;
				diesSetmana[i][2] = Constants.CTE_CAL_TEXT_SIZE_NORMAL;
				if (esAvui(calAux)) {
					diesSetmana[i][3] = Constants.CTE_CAL_TEXT_TYPE_SEL;
					diesSetmana[i][4] = Constants.CTE_CAL_TEXT_COLOR_SEL;
				}
				else {
					diesSetmana[i][3] = Constants.CTE_CAL_TEXT_TYPE_NORMAL;
					diesSetmana[i][4] = Constants.CTE_CAL_TEXT_COLOR_NORMAL;
				}
			}
			diesSetmana[i][5] = iValor;
			diesSetmana[i][6] = data2StringDescrip(calAux);
		}
		iDia = 0;
		// Recorrer ARRAY fins DILLUNS
		for (int i=iPosicio-1;i>=0;i--) {
			Calendar calAux = data2SumarDiaCal(pDataFiltre, --iDia);
			iValor = calAux.get(Calendar.DAY_OF_MONTH);	// Dia del mes
			diesSetmana[i][0] = Constants.CTE_CAL_WIDTH_NORMAL;
			diesSetmana[i][1] = Constants.CTE_CAL_HEIGHT_NORMAL;
			diesSetmana[i][2] = Constants.CTE_CAL_TEXT_SIZE_NORMAL;
			if (esAvui(calAux)) {
				diesSetmana[i][3] = Constants.CTE_CAL_TEXT_TYPE_SEL;
				diesSetmana[i][4] = Constants.CTE_CAL_TEXT_COLOR_SEL;
			}
			else {
				diesSetmana[i][3] = Constants.CTE_CAL_TEXT_TYPE_NORMAL;
				diesSetmana[i][4] = Constants.CTE_CAL_TEXT_COLOR_NORMAL;
			}
			diesSetmana[i][5] = iValor;
			diesSetmana[i][6] = data2StringDescrip(calAux);
		}
		return diesSetmana;
	}

	private static boolean esAvui(Calendar pData) {
		Calendar calAvui = data2Avui();
		return pData.get(Calendar.DAY_OF_MONTH) == calAvui.get(Calendar.DAY_OF_MONTH) &&
				pData.get(Calendar.MONTH) == calAvui.get(Calendar.MONTH) &&
				pData.get(Calendar.YEAR) == calAvui.get(Calendar.YEAR);
	}

	public static Calendar string2DataBDCalendari(String p_data) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(CTE_FORMAT_DATA_DESCRIPCIO, Locale.getDefault());
		try {
			Date data = dateFormat.parse(p_data);
			Calendar cal = Calendar.getInstance();
			cal.setTime(data);
			return cal;
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return null;
	}

    public static String[] desglosarSetsResultats(String pResultatSets, Boolean pIsLocal) {
		/*
		 * 	VOLEI VILASSAR;3;0;CVB BARÇA;25-16/27-25/25-20
		 */
		/*
			25-16/27-25/25-20
		 */
		String resultats[] = new String[] {"","","","",""};
		String sets[] = new String[] {"0-0","0-0","0-0","0-0","0-0"};
		try {
			sets = pResultatSets.split(Constants.CTE_SEPARADOR_SETS);
		} catch (Exception ex) {}
		// Set-1
		try {
			resultats[0] = (pIsLocal ? sets[0].split(Constants.CTE_SEPARADOR_RESULTAT_RED)[0] : sets[0].split(Constants.CTE_SEPARADOR_RESULTAT_RED)[1]);
		} catch (Exception ex) {
			resultats[0] = "0";
		}
		// Set-1
		try {
			resultats[1] = (pIsLocal ? sets[1].split(Constants.CTE_SEPARADOR_RESULTAT_RED)[0] : sets[1].split(Constants.CTE_SEPARADOR_RESULTAT_RED)[1]);
		} catch (Exception ex) {
			resultats[1] = "0";
		}
		// Set-1
		try {
			resultats[2] = (pIsLocal ? sets[2].split(Constants.CTE_SEPARADOR_RESULTAT_RED)[0] : sets[2].split(Constants.CTE_SEPARADOR_RESULTAT_RED)[1]);
		} catch (Exception ex) {
			resultats[2] = "0";
		}
		// Set-1
		try {
			resultats[3] = (pIsLocal ? sets[3].split(Constants.CTE_SEPARADOR_RESULTAT_RED)[0] : sets[3].split(Constants.CTE_SEPARADOR_RESULTAT_RED)[1]);
		} catch (Exception ex) {
			resultats[3] = "0";
		}
		// Set-1
		try {
			resultats[4] = (pIsLocal ? sets[4].split(Constants.CTE_SEPARADOR_RESULTAT_RED)[0] : sets[4].split(Constants.CTE_SEPARADOR_RESULTAT_RED)[1]);
		} catch (Exception ex) {
			resultats[4] = "0";
		}
		return resultats;
	}

    public static int convertJornada2Int(String pText, String pSufixe) {
		// Jornada nº 1
		int iJornada = 0;
		if (pText != null && !pText.equals("")) {
			String strRes = pText.substring(pSufixe.length() + 1);
			try {
				iJornada = Integer.parseInt(strRes);
			}
			catch (Exception e) {
				// Ha seleccionat la primera opció - Jornada Actual - Jornada=0
			}
		}
		return iJornada;
    }

	public static String parsearText(String pStrText) {
		StringBuilder strAux = new StringBuilder("");
		strAux.append(pStrText);
		String strText = pStrText.replaceAll("#8216;", "'");
		strText = strText.replaceAll("\t", "");
		strText = strText.replaceAll("\u0091", "'");
		strText = strText.replaceAll("#39;", "'");
		strText = strText.replaceAll("Aacute;", "Á");
		strText = strText.replaceAll("aacute;", "á");
		strText = strText.replaceAll("&eacute;", "é");
		strText = strText.replaceAll("&Eacute;", "É");
		strText = strText.replaceAll("&iacute;", "í");
		strText = strText.replaceAll("&Iacute;", "Í");
		strText = strText.replaceAll("&oacute;", "ó");
		strText = strText.replaceAll("&Oacute;", "Ó");
		strText = strText.replaceAll("&uacute;", "ú");
		strText = strText.replaceAll("&Uacute;", "Ú");
		strText = strText.replaceAll("&ntilde;", "ñ");
		strText = strText.replaceAll("&Ntilde;", "Ñ");
		strText = strText.replaceAll("&period;", ".");
		strText = strText.replaceAll("&iuml;", "ï");
		strText = strText.replaceAll("&Iuml;", "Ï");
		strText = strText.replaceAll("&comma;", "'");
		strText = strText.replaceAll("&ccedil;", "ç");
		strText = strText.replaceAll("&Ccedil;", "Ç");
        strText = strText.replaceAll("&ordm;", "º");
		strText = strText.replaceAll("&", "");
		return strText;
	}

	public static String netejarString(String pText) {
		String strText = "";
		if (pText != null && !pText.equals("")) {
			strText = pText.replaceAll("<ps>", "");
			strText = strText.replaceAll("</ps>", "");
			strText = strText.replaceAll("<g3>", "");
			strText = strText.replaceAll("</g3>", "");
			strText = strText.replaceAll("<g2>", "");
			strText = strText.replaceAll("</g2>", "");
			strText = strText.replaceAll("<p1>", "");
			strText = strText.replaceAll("</p1>", "");
			strText = strText.replaceAll("<p0>", "");
			strText = strText.replaceAll("</p0>", "");
		}
		return strText;
	}

    public static String netejarText(String pText) {
		String strText = "";
		if (pText != null && !pText.equals("")) {
			strText = pText.replaceAll("<strong>", "");
			strText = strText.replaceAll("</strong>", "");
            strText = strText.replaceAll("<td>", "");
            strText = strText.replaceAll("<td >", "");
			if (strText.contains("<img")) {
				int iInd1 = strText.indexOf("<img");
				int iInd2 = strText.indexOf(">", iInd1);
				strText = strText.substring(0, iInd1) + strText.substring(iInd2 + 1);
			}
		}
		return strText;
    }

    public static boolean dataIguals(Date pData1, Date pData2) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(CTE_FORMAT_DATA_RED, Locale.getDefault());
		try {
			return dateFormat.format(pData1).equals(dateFormat.format(pData2));
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return false;
    }

	public static int stringToInt(String pNum) {
		if (pNum.equals(""))
			return 0;
		NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
		Number number = 0;
		try {
			number = format.parse(pNum);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return number.intValue();
	}

	public static String data2StringJSON(Date p_date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(CTE_FORMAT_DATA_JSON, Locale.getDefault());
		try {
			return dateFormat.format(p_date);
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return null;
	}
}
