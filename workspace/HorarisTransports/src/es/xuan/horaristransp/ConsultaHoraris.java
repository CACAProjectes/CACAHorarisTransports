package es.xuan.horaristransp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import es.xuan.horaristransp.file.GestioFitxers;
import es.xuan.horaristransp.model.Node;
import es.xuan.horaristransp.model.Nodes;
import es.xuan.horaristransp.utils.Constants;

public class ConsultaHoraris {


	public static void main(String[] args) {
		Nodes nodes = carregarNodes2File();
		System.out.println("N� de nodes L4: " + nodes.tamany("L4"));
		System.out.println("N� de nodes L5: " + nodes.tamany("L5"));
		//System.out.println("Nodes: " + nodes.toString("L4"));
	}
	
	private static Nodes carregarNodes2File() {
		ArrayList<String> linies = GestioFitxers.llegirFile("c:\\CACAProjectes\\CACAHorarisTransports\\Documentaci�\\fitxer_importaci�_horaris.csv");
		Nodes nodes = new Nodes();
		for(String linia : linies) {
			/*
			 * L4;Estaci� Rub�+D;06:30;06:51;07:13;07:35;07:58;08:22;08:46;09:10;09:34;09:58;10:22;10:46;11:10;11:34;11:58;12:22;12:46;13:10;13:34;13:58;14:22;14:46;15:10;15:34;15:58;16:22;16:46;17:10;17:34;17:58;18:22;18:46;19:10;19:34;19:57;20:19;20:41;21:03;21:25;21:46;22:06
			 */
			if (linia != null && !linia.equals("")) {
				String[] strValors = linia.split(Constants.CNT_SEPARADOR_LINIA);
				Node nodeNou = new Node(strValors);
				nodes.addNode(strValors[0], nodeNou);	// [CLAU, NODE]
			}
		}
		return nodes;
	}

	private static String horatiToString(String[] pHorari) {
		String strRes = "";
		for (String horari : pHorari) {
			strRes += horari + ";";			
		}
		return strRes;
	}
	private void calcularTemps() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Madrid"), Locale.getDefault());
		String[] horari = HorarisCalendari.calculaTemps("L5", "PAISOS CATALANS", cal.getTime());
		System.out.println(horatiToString(horari));
		//
		System.out.println("Temps d'espera en PAISOS CATALANS: " + HorarisCalendari.getTempsEspera(horari, "PAISOS CATALANS", cal.getTime()));
		//
		System.out.println("Hora d'arribada a CAN ROS�S: " + HorarisCalendari.getTempsArribada(horari, "CAN ROS�S"));
	}
}
