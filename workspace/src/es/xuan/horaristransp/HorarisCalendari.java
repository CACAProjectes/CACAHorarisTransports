package es.xuan.horaristransp;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class HorarisCalendari implements Serializable {
	private static final long serialVersionUID = 1L;
	/*
	 * LINIA;ORIGEN;DESTÍ;JORNADA;PARADA0;PARADA1;...;PARADAn
	 */
	private static String[][] mHorarisComplerts = {
			{"0","L4","CAN ROSÉS","ESTACIÓ RUBÍ+D","FEINERS LECTIUS","CAN ROSÉS", "PAISOS CATALANS"}, 
			{"1","L4","CAN ROSÉS","ESTACIÓ RUBÍ+D","FEINERS LECTIUS","06:30","06:46"},
			{"1","L4","CAN ROSÉS","ESTACIÓ RUBÍ+D","FEINERS LECTIUS","06:52","07:08"},
			{"1","L4","CAN ROSÉS","ESTACIÓ RUBÍ+D","FEINERS LECTIUS","07:14","07:30"},
			{"1","L4","CAN ROSÉS","ESTACIÓ RUBÍ+D","FEINERS LECTIUS","07:36","07:53"},
			{"1","L4","CAN ROSÉS","ESTACIÓ RUBÍ+D","FEINERS LECTIUS","08:00","08:17"},
			{"1","L4","CAN ROSÉS","ESTACIÓ RUBÍ+D","FEINERS LECTIUS","08:24","08:41"},
			{"1","L4","CAN ROSÉS","ESTACIÓ RUBÍ+D","FEINERS LECTIUS","08:48","09:05"},
			{"1","L4","CAN ROSÉS","ESTACIÓ RUBÍ+D","FEINERS LECTIUS","09:12","09:29"},
			{"1","L4","CAN ROSÉS","ESTACIÓ RUBÍ+D","FEINERS LECTIUS","09:36","09:53"},
			{"1","L4","CAN ROSÉS","ESTACIÓ RUBÍ+D","FEINERS LECTIUS","10:00","10:17"},
			{"1","L4","CAN ROSÉS","ESTACIÓ RUBÍ+D","FEINERS LECTIUS","10:24","10:41"},
			{"1","L4","CAN ROSÉS","ESTACIÓ RUBÍ+D","FEINERS LECTIUS","10:48","11:05"},
			{"1","L4","CAN ROSÉS","ESTACIÓ RUBÍ+D","FEINERS LECTIUS","11:12","11:29"},
			{"1","L4","CAN ROSÉS","ESTACIÓ RUBÍ+D","FEINERS LECTIUS","11:36","11:53"},
			{"1","L4","CAN ROSÉS","ESTACIÓ RUBÍ+D","FEINERS LECTIUS","12:00","12:17"},
			{"1","L4","CAN ROSÉS","ESTACIÓ RUBÍ+D","FEINERS LECTIUS","12:24","12:41"},
			{"1","L4","CAN ROSÉS","ESTACIÓ RUBÍ+D","FEINERS LECTIUS","12:48","13:05"},
			{"1","L4","CAN ROSÉS","ESTACIÓ RUBÍ+D","FEINERS LECTIUS","13:12","13:29"},
			{"1","L4","CAN ROSÉS","ESTACIÓ RUBÍ+D","FEINERS LECTIUS","13:36","13:53"},
			{"1","L4","CAN ROSÉS","ESTACIÓ RUBÍ+D","FEINERS LECTIUS","14:00","14:17"},
			{"1","L4","CAN ROSÉS","ESTACIÓ RUBÍ+D","FEINERS LECTIUS","14:24","14:41"},
			{"1","L4","CAN ROSÉS","ESTACIÓ RUBÍ+D","FEINERS LECTIUS","14:48","15:05"},
			{"1","L4","CAN ROSÉS","ESTACIÓ RUBÍ+D","FEINERS LECTIUS","15:12","15:29"},
			{"1","L4","CAN ROSÉS","ESTACIÓ RUBÍ+D","FEINERS LECTIUS","15:36","15:53"},
			{"1","L4","CAN ROSÉS","ESTACIÓ RUBÍ+D","FEINERS LECTIUS","16:00","16:17"},
			{"1","L4","CAN ROSÉS","ESTACIÓ RUBÍ+D","FEINERS LECTIUS","16:24","16:41"},
			{"1","L4","CAN ROSÉS","ESTACIÓ RUBÍ+D","FEINERS LECTIUS","16:48","17:05"},
			{"1","L4","CAN ROSÉS","ESTACIÓ RUBÍ+D","FEINERS LECTIUS","17:12","17:29"},
			{"1","L4","CAN ROSÉS","ESTACIÓ RUBÍ+D","FEINERS LECTIUS","17:36","17:53"},
			{"1","L4","CAN ROSÉS","ESTACIÓ RUBÍ+D","FEINERS LECTIUS","18:00","18:17"},
			{"1","L4","CAN ROSÉS","ESTACIÓ RUBÍ+D","FEINERS LECTIUS","18:24","18:41"},
			{"1","L4","CAN ROSÉS","ESTACIÓ RUBÍ+D","FEINERS LECTIUS","18:48","19:05"},
			{"1","L4","CAN ROSÉS","ESTACIÓ RUBÍ+D","FEINERS LECTIUS","19:12","19:29"},
			{"1","L4","CAN ROSÉS","ESTACIÓ RUBÍ+D","FEINERS LECTIUS","19:36","19:52"},
			{"1","L4","CAN ROSÉS","ESTACIÓ RUBÍ+D","FEINERS LECTIUS","19:58","20:14"},
			{"1","L4","CAN ROSÉS","ESTACIÓ RUBÍ+D","FEINERS LECTIUS","20:20","20:36"},
			{"1","L4","CAN ROSÉS","ESTACIÓ RUBÍ+D","FEINERS LECTIUS","20:42","20:58"},
			{"1","L4","CAN ROSÉS","ESTACIÓ RUBÍ+D","FEINERS LECTIUS","21:04","21:20"},
			{"1","L4","CAN ROSÉS","ESTACIÓ RUBÍ+D","FEINERS LECTIUS","21:25","21:41"},
			{"1","L4","CAN ROSÉS","ESTACIÓ RUBÍ+D","FEINERS LECTIUS","21:45","22:01"},
			{"0","L5","ESTACIÓ RUBÍ+D","CAN ROSÉS","FEINERS LECTIUS","PAISOS CATALANS","CAN ROSÉS"},
			{"1","L5","ESTACIÓ RUBÍ+D","CAN ROSÉS","FEINERS LECTIUS","06:43","07:03"},
			{"1","L5","ESTACIÓ RUBÍ+D","CAN ROSÉS","FEINERS LECTIUS","07:05","07:25"},
			{"1","L5","ESTACIÓ RUBÍ+D","CAN ROSÉS","FEINERS LECTIUS","07:27","07:48"},
			{"1","L5","ESTACIÓ RUBÍ+D","CAN ROSÉS","FEINERS LECTIUS","07:52","08:13"},
			{"1","L5","ESTACIÓ RUBÍ+D","CAN ROSÉS","FEINERS LECTIUS","08:16","08:37"},
			{"1","L5","ESTACIÓ RUBÍ+D","CAN ROSÉS","FEINERS LECTIUS","08:40","09:01"},
			{"1","L5","ESTACIÓ RUBÍ+D","CAN ROSÉS","FEINERS LECTIUS","09:04","09:25"},
			{"1","L5","ESTACIÓ RUBÍ+D","CAN ROSÉS","FEINERS LECTIUS","09:28","09:49"},
			{"1","L5","ESTACIÓ RUBÍ+D","CAN ROSÉS","FEINERS LECTIUS","09:52","10:13"},
			{"1","L5","ESTACIÓ RUBÍ+D","CAN ROSÉS","FEINERS LECTIUS","10:16","10:37"},
			{"1","L5","ESTACIÓ RUBÍ+D","CAN ROSÉS","FEINERS LECTIUS","10:40","11:01"},
			{"1","L5","ESTACIÓ RUBÍ+D","CAN ROSÉS","FEINERS LECTIUS","11:04","11:25"},
			{"1","L5","ESTACIÓ RUBÍ+D","CAN ROSÉS","FEINERS LECTIUS","11:28","11:49"},
			{"1","L5","ESTACIÓ RUBÍ+D","CAN ROSÉS","FEINERS LECTIUS","11:52","12:13"},
			{"1","L5","ESTACIÓ RUBÍ+D","CAN ROSÉS","FEINERS LECTIUS","12:16","12:37"},
			{"1","L5","ESTACIÓ RUBÍ+D","CAN ROSÉS","FEINERS LECTIUS","12:40","13:01"},
			{"1","L5","ESTACIÓ RUBÍ+D","CAN ROSÉS","FEINERS LECTIUS","13:04","13:25"},
			{"1","L5","ESTACIÓ RUBÍ+D","CAN ROSÉS","FEINERS LECTIUS","13:28","13:49"},
			{"1","L5","ESTACIÓ RUBÍ+D","CAN ROSÉS","FEINERS LECTIUS","13:52","14:13"},
			{"1","L5","ESTACIÓ RUBÍ+D","CAN ROSÉS","FEINERS LECTIUS","14:16","14:37"},
			{"1","L5","ESTACIÓ RUBÍ+D","CAN ROSÉS","FEINERS LECTIUS","14:40","15:01"},
			{"1","L5","ESTACIÓ RUBÍ+D","CAN ROSÉS","FEINERS LECTIUS","15:04","15:25"},
			{"1","L5","ESTACIÓ RUBÍ+D","CAN ROSÉS","FEINERS LECTIUS","15:28","15:49"},
			{"1","L5","ESTACIÓ RUBÍ+D","CAN ROSÉS","FEINERS LECTIUS","15:52","16:13"},
			{"1","L5","ESTACIÓ RUBÍ+D","CAN ROSÉS","FEINERS LECTIUS","16:16","16:37"},
			{"1","L5","ESTACIÓ RUBÍ+D","CAN ROSÉS","FEINERS LECTIUS","16:40","17:01"},
			{"1","L5","ESTACIÓ RUBÍ+D","CAN ROSÉS","FEINERS LECTIUS","17:04","17:25"},
			{"1","L5","ESTACIÓ RUBÍ+D","CAN ROSÉS","FEINERS LECTIUS","17:28","17:49"},
			{"1","L5","ESTACIÓ RUBÍ+D","CAN ROSÉS","FEINERS LECTIUS","17:52","18:13"},
			{"1","L5","ESTACIÓ RUBÍ+D","CAN ROSÉS","FEINERS LECTIUS","18:16","18:37"},
			{"1","L5","ESTACIÓ RUBÍ+D","CAN ROSÉS","FEINERS LECTIUS","18:40","19:01"},
			{"1","L5","ESTACIÓ RUBÍ+D","CAN ROSÉS","FEINERS LECTIUS","19:04","19:25"},
			{"1","L5","ESTACIÓ RUBÍ+D","CAN ROSÉS","FEINERS LECTIUS","19:28","19:48"},
			{"1","L5","ESTACIÓ RUBÍ+D","CAN ROSÉS","FEINERS LECTIUS","19:51","20:11"},
			{"1","L5","ESTACIÓ RUBÍ+D","CAN ROSÉS","FEINERS LECTIUS","20:13","20:33"},
			{"1","L5","ESTACIÓ RUBÍ+D","CAN ROSÉS","FEINERS LECTIUS","20:35","20:55"},
			{"1","L5","ESTACIÓ RUBÍ+D","CAN ROSÉS","FEINERS LECTIUS","20:57","21:17"},
			{"1","L5","ESTACIÓ RUBÍ+D","CAN ROSÉS","FEINERS LECTIUS","21:19","21:39"},
			{"1","L5","ESTACIÓ RUBÍ+D","CAN ROSÉS","FEINERS LECTIUS","21:39","21:59"}
	};

	private static String getJornadaAvui() {
		return "FEINERS LECTIUS";
	}
	private static int getDayLight() {
		return -1;
	}
	private static int getPosicioParada(String pLinia, String pParada) {
		/*
		 * LINIA - L5
		 * PARADA - PAISOS CATALANS
		 */
		for (String[] horari : mHorarisComplerts) {
			if (horari[0].equals("0")	&&			// És capçalera?
					horari[1].equals(pLinia)) {		// Linia
					for(int i=5;i<horari.length;i++) {
						if (horari[i].equals(pParada))
							return i;	// Posició trobada
					}
			}
		}
		return 0;	// Posició no trobada
	}
	/*
	 * Retorna el primer transport que passarà per l'estació en aquest moment
	 */
	public static String[] calculaTemps(String pLinia, String pParada, Date pData) {
		SimpleDateFormat formatData = new SimpleDateFormat("HH:mm");
		Calendar cal = Calendar.getInstance();
		cal.setTime(pData);
		String sData = formatData.format(cal.getTime());
		int iPosicionParada = getPosicioParada(pLinia, pParada);	// Ordre/posició de la parada 
		for (String[] horari : mHorarisComplerts) {
			if (!horari[0].equals("0") &&												// Només informació horaries. No CAPÇALERES
					horari[1].equals(pLinia) &&										// Linia
					horari[4].equals(getJornadaAvui()) &&						// Jornada Actual: estiu, cap de setmana, feiners lectius,...
					horari[iPosicionParada].compareTo(sData) >= 0		// Horari >= Data Avui
					) {
				return horari;																// Primer transport que passarà a partir d'aquest moment
			}
		}
		return null;			// No hi ha transports en aquest horari
	}
	public static String getTempsEspera(String[] pHorari, String pParada, Date pData) {
		SimpleDateFormat formatData = new SimpleDateFormat("HH:mm:ss");
		//	Data d'avui
		Calendar calAvui = Calendar.getInstance();
		calAvui.setTime(pData);
		//	Data de la parada
		int iPosicionParada = getPosicioParada(pHorari[1], pParada);		// Ordre/posició de la parada - Linia en la posición 1
		String horaParada = pHorari[iPosicionParada];
		Date dateParada = null;
		Calendar calParada = Calendar.getInstance();
		calParada.set(Calendar.HOUR_OF_DAY, Integer.parseInt(horaParada.split(":")[0]));
		calParada.set(Calendar.MINUTE, Integer.parseInt(horaParada.split(":")[1]));
		calParada.set(Calendar.SECOND, 0);
		// Diferencia entre dates
		long difTime = calParada.getTime().getTime() - calAvui.getTime().getTime();
		Calendar calDifTime = Calendar.getInstance();
		calDifTime.setTimeInMillis(difTime);
		calDifTime.add(Calendar.HOUR_OF_DAY, getDayLight());													// Restar HORARI D'ESTIU
		return formatData.format(calDifTime.getTime());
	}
	
	public static String getTempsArribada(String[] pHorari, String pParada) {
		int iPosicionParada = getPosicioParada(pHorari[1], pParada);		// Ordre/posició de la parada - Linia en la posición 1
		return pHorari[iPosicionParada];
	}
}
