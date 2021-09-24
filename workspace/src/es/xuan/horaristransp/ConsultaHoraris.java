package es.xuan.horaristransp;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class ConsultaHoraris {

	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Madrid"), Locale.getDefault());
		String[] horari = HorarisCalendari.calculaTemps("L5", "PAISOS CATALANS", cal.getTime());
		System.out.println(horatiToString(horari));
		//
		System.out.println("Temps d'espera en PAISOS CATALANS: " + HorarisCalendari.getTempsEspera(horari, "PAISOS CATALANS", cal.getTime()));
		//
		System.out.println("Hora d'arribada a CAN ROSÉS: " + HorarisCalendari.getTempsArribada(horari, "CAN ROSÉS"));
	}
	
	private static String horatiToString(String[] pHorari) {
		String strRes = "";
		for (String horari : pHorari) {
			strRes += horari + ";";			
		}
		return strRes;
	}
}
