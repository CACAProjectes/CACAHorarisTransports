package es.xuan.horaristransp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import es.xuan.horaristransp.file.GestioFitxers;
import es.xuan.horaristransp.model.Node;

public class ConsultaHoraris {

	public static void main(String[] args) {
		Node node = carregarNodes2File();
		System.out.println("");
		//System.out.println("Nº de nodes: " + nodes.getNodes().size());
		//System.out.println("Nodes: " + nodes.toString("L4"));
	}
	
	private static Node carregarNodes2File() {
		ArrayList<String> linies = GestioFitxers.llegirFile("c:\\CACAProjectes\\CACAHorarisTransports\\Documentació\\fitxer_importació_horaris.csv");
		/*
		Node node = new Node();
		for(String linia : linies) {
			Node nodeNou = new Node(linia);
			//node.addNode(nodeNou.getDades().keys().nextElement(), nodeNou); // Agafa el primer element del Node, per obtener la CLAU/CATEGORIA
			node.addNode(nodeNou);
		}
		return node;
		*/
		return null;
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
		System.out.println("Hora d'arribada a CAN ROSÉS: " + HorarisCalendari.getTempsArribada(horari, "CAN ROSÉS"));
	}
}
