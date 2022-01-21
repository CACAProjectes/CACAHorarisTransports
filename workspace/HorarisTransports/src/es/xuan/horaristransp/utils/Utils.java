/**
 * 
 */
package es.xuan.horaristransp.utils;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
/**
 * @author jcamposp
 *
 */
public class Utils implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static int convertString2Int(String pNum) {
		NumberFormat nf = NumberFormat.getInstance();		
		try {
			return nf.parse(pNum).intValue();
		} catch (ParseException e) {
		}
		return 0;
	}
	
	public static int calcularJornada(Calendar pCal) {
		/*
		 * 3>DISSABTES<7>FESTIUS AGOST<6>DISSABTES AGOST<5>FEINERS AGOST<4>FESTIUS<1>FEINERS LECTIUS<
		 */
		if (pCal.get(Calendar.MONTH) == 7) {				
			// AGOST
			if (pCal.get(Calendar.DAY_OF_WEEK) > 0 &&
					pCal.get(Calendar.DAY_OF_WEEK) < 6)		// 5>FEINERS AGOST
					return 5;			// 5>FEINERS AGOST
			else if (pCal.get(Calendar.DAY_OF_WEEK) == 6)	// 6>DISSABTES AGOST
					return 6;			// 6>DISSABTES AGOST
			else if (esFestiu(pCal))						// 7>FESTIUS AGOST
					return 7;			// 7>FESTIUS AGOST
		}
		else if (esFestiu(pCal))							// 4>FESTIUS
			return 4;					// 4>FESTIUS
		else if (pCal.get(Calendar.DAY_OF_WEEK) == 6)		// 3>DISSABTES
			return 3;			// 3>DISSABTES
		else if (pCal.get(Calendar.DAY_OF_WEEK) > 0 &&
			pCal.get(Calendar.DAY_OF_WEEK) < 6)				// 1>FEINERS LECTIUS (NO AGOST)
			return 1;		// 1>FEINERS LECTIUS
		//
		return 1;	// 1>FEINERS LECTIUS (por defecto)
	}
	private static boolean esFestiu(Calendar pCal) {
		// TODO Afegir Calendari Anual amb els festius de Rub�
		return false;
	}
	
	public static String formatDataComplerta(Calendar pCal, String pIdioma) {
		String pattern = "EEEEE, dd/MM/yyyy - HH:mm";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale(pIdioma.toLowerCase(), pIdioma.toUpperCase()));
		return capitalize(simpleDateFormat.format(pCal.getTime()));
	}
	private static String capitalize(String str)
	{
	    if(str == null) return str;
	    return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
}
