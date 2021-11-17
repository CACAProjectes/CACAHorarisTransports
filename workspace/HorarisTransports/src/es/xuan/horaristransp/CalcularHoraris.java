/**
 * 
 */
package es.xuan.horaristransp;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author jcamposp
 *
 */
public class CalcularHoraris implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private static String[][] m_linies = new String[20][3];

	public static void main(String[] args) {
		// Inicializar
		inicialitzarLinies();
		String hora[] = cercaParadaHorari("L5", "Pa�sos Catalans", "Can Ros�s", Calendar.getInstance());
		System.out.println("Sortida: " + hora[0] + " - Arribada: " + hora[1]);
	}

	private static String[] cercaParadaHorari(String pLinia, String pParadaOrigen, String pParadaDestino, Calendar pData) {
		String horaris[];
		String horaSortida = "";
		String horaArribada = "";
		String strFeiner = "";
		if (pData.get(Calendar.DAY_OF_WEEK) > 0 && pData.get(Calendar.DAY_OF_WEEK) < 6)
			strFeiner = "L";
		else if (pData.get(Calendar.DAY_OF_WEEK) == 6)
			strFeiner = "S";
		else if (pData.get(Calendar.DAY_OF_WEEK) == 0)
			strFeiner = "F";
		String strHora = pData.get(Calendar.HOUR_OF_DAY) + ":" + pData.get(Calendar.MINUTE);
		//
		for(String[] linia : m_linies) {
			if (linia[0].equalsIgnoreCase(pLinia) && 
				linia[1].contains(pParadaOrigen) && 
				linia[2].startsWith(strFeiner)) {
				horaris = linia[2].substring(2).split(",");	// L,06:30,06:52,07:14,07:36,08:00,08:24,08:48,09:12,09:36,10:00,10:24,10:48,11:12,11:36,12:00,12:24,12:48,13:12,13:36,14:00,14:24,14:48,15:12,15:36,16:00,16:24,16:48,17:12,17:36,18:00,18:24,18:48,19:12,19:36,19:58,20:20,20:42,21:04,21:25,21:45
				for (String horaItem : horaris) {
					if (strHora.compareToIgnoreCase(horaItem) <= 0) {
						horaSortida = horaItem;
						break;
					}
				}
				if (!horaSortida.equals(""))
					break;
			}
		}
		if (!horaSortida.equals("")) {
			for(String[] linia : m_linies) {
				if (linia[0].equalsIgnoreCase(pLinia) && 
					linia[1].contains(pParadaDestino) && 
					linia[2].startsWith(strFeiner)) {
					horaris = linia[2].substring(2).split(",");	// L,06:30,06:52,07:14,07:36,08:00,08:24,08:48,09:12,09:36,10:00,10:24,10:48,11:12,11:36,12:00,12:24,12:48,13:12,13:36,14:00,14:24,14:48,15:12,15:36,16:00,16:24,16:48,17:12,17:36,18:00,18:24,18:48,19:12,19:36,19:58,20:20,20:42,21:04,21:25,21:45
					for (String horaItem : horaris) {
						if (horaSortida.compareToIgnoreCase(horaItem) <= 0) {
							horaArribada = horaItem;
							break;
						}
					}
					if (!horaArribada.equals(""))
						break;
				}
			}
		}
		String[] hores = {horaSortida, horaArribada};
		return hores ;
	}

	private static void inicialitzarLinies() {
		m_linies[0] = "L4;[T]Can Ros�s;L,06:30,06:52,07:14,07:36,08:00,08:24,08:48,09:12,09:36,10:00,10:24,10:48,11:12,11:36,12:00,12:24,12:48,13:12,13:36,14:00,14:24,14:48,15:12,15:36,16:00,16:24,16:48,17:12,17:36,18:00,18:24,18:48,19:12,19:36,19:58,20:20,20:42,21:04,21:25,21:45".split(";");
		m_linies[1] = "L4;[T]Pa�sos Catalans;L,06:46,07:08,07:30,07:53,08:17,08:41,09:05,09:29,09:53,10:17,10:41,11:05,11:29,11:53,12:17,12:41,13:05,13:29,13:53,14:17,14:41,15:05,15:29,15:53,16:17,16:41,17:05,17:29,17:53,18:17,18:41,19:05,19:29,19:52,20:14,20:36,20:58,21:20,21:41,22:01".split(";");
		m_linies[2] = "L4;[T]Estaci� Rub�+D;L,06:51,07:13,07:35,07:58,08:22,08:46,09:10,09:34,09:58,10:22,10:46,11:10,11:34,11:58,12:22,12:46,13:10,13:34,13:58,14:22,14:46,15:10,15:34,15:58,16:22,16:46,17:10,17:34,17:58,18:22,18:46,19:10,19:34,19:57,20:19,20:41,21:03,21:25,21:46,22:06".split(";");
		m_linies[3] = "L5;[T]Estaci� Rub�+D;L,06:41,07:03,07:25,07:50,08:14,08:38,09:02,09:26,09:50,10:14,10:38,11:02,11:26,11:50,12:14,12:38,13:02,13:26,13:50,14:14,14:38,15:02,15:26,15:50,16:14,16:38,17:02,17:26,17:50,18:14,18:38,19:02,19:26,19:49,20:11,20:33,20:55,21:17,21:37".split(";");
		m_linies[4] = "L5;[T]Estaci� Rub�+D;F,08:35,09:19,10:03,10:47,11:31,12:15,12:59,13:43,14:27,15:11,15:55,16:39,17:23,18:07,18:51,19:35,20:19,21:01,21:43".split(";");
		m_linies[5] = "L5;[T]Estaci� Rub�+D;S,08:01,08:49,09:37,10:25,11:13,12:01,12:49,13:37,14:24,15:11,15:55,16:39,17:23,18:07,18:51,19:35,20:19,21:01,21:43".split(";");
		m_linies[6] = "L5;[T]Pa�sos Catalans;L,06:43,07:05,07:27,07:52,08:16,08:40,09:04,09:28,09:52,10:16,10:40,11:04,11:28,11:52,12:16,12:40,13:04,13:28,13:52,14:16,14:40,15:04,15:28,15:52,16:16,16:40,17:04,17:28,17:52,18:16,18:40,19:04,19:28,19:51,20:13,20:35,20:57,21:19,21:39".split(";");
		m_linies[7] = "L5;[T]Pa�sos Catalans;F,08:37,09:21,10:05,10:49,11:33,12:17,13:01,13:45,14:29,15:13,15:57,16:41,17:25,18:09,18:53,19:37,20:21,21:03,21:45".split(";");
		m_linies[8] = "L5;[T]Pa�sos Catalans;S,15:13,15:57,16:41,17:25,18:09,18:53,19:37,20:21,21:03,21:45".split(";");
		m_linies[9] = "L5;[T]Can Ros�s;L,07:03,07:25,07:48,08:13,08:37,09:01,09:25,09:49,10:13,10:37,11:01,11:25,11:49,12:13,12:37,13:01,13:25,13:49,14:13,14:37,15:01,15:25,15:49,16:13,16:37,17:01,17:25,17:49,18:13,18:37,19:01,19:25,19:48,20:11,20:33,20:55,21:17,21:39,21:59".split(";");
		m_linies[10] = "L5;[T]Can Ros�s;F,08:56,09:40,10:24,11:08,11:52,12:36,13:20,14:04,14:48,15:32,16:16,17:00,17:44,18:28,19:12,19:56,20:40,21:22,22:04".split(";");
		m_linies[11] = "L5;[T]Can Ros�s;S,08:24,09:12,10:00,10:48,11:36,12:24,13:12,14:00,14:47,15:32,16:16,17:00,17:44,18:28,19:12,19:56,20:40,21:22,22:04".split(";");
	}

}
