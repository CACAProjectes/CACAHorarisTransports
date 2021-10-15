package es.xuan.horaristransp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Locale;
import java.util.TimeZone;

import es.xuan.horaristransp.file.GestioFitxers;
import es.xuan.horaristransp.model.Coordenada;
import es.xuan.horaristransp.model.Node;
import es.xuan.horaristransp.model.Nodes;
import es.xuan.horaristransp.utils.Constants;

public class ConsultaHoraris {


	public static void main(String[] args) {
		Nodes nodes = carregarNodes2File();
		System.out.println("Nº de nodes L4: " + nodes.tamany("L4"));
		System.out.println("Nº de nodes L5: " + nodes.tamany("L5"));
	}
	private static Nodes carregarNodes2File() {
		ArrayList<String> linies = GestioFitxers.llegirFile("c:\\CACAProjectes\\CACAHorarisTransports\\Documentació\\fitxer_importació_horaris.csv");
		String strClau = "";
		int coordX = 0, coordY = 0;
		Nodes nodes = new Nodes();
		for(String linia : linies) {
			/*
			 * L5;[T]Estació RubÍ+D;F,08:35,09:19,10:03,10:47,11:31,12:15,12:59,13:43,14:27,15:11,15:55,16:39,17:23,18:07,18:51,19:35,20:19,21:01,21:43
			 */
			if (linia != null && !linia.equals("")) {
				String[] strValors = linia.split(Constants.CNT_SEPARADOR_LINIA);
				Node nodeNou = new Node(strValors);				
	        	if (!strClau.equals(strValors[0]) && !strClau.equals("") ) {
	        		coordX = 0;
	        		coordY++;
	        	}
	        	nodeNou.setCoordenada(new Coordenada(coordX++, coordY));
	        	//Node nodeMultiple = cercarNode(nodes, nodeNou);
				//nodes.addNode(strValors[0], nodeMultiple);	// [CLAU, NODE]
	        	nodes.addNode(strValors[0], nodeNou);	// [CLAU, NODE]
	    		strClau = strValors[0];
	    		//System.out.print(nodeNou + " - ");
			}
		}
		return nodes;
	}

	private static Node cercarNode(Nodes pNodes, Node pNode) {
        Enumeration<ArrayList<Node>> e = pNodes.getListaNodes().elements();  
        // print elements of hashtable using enumeration
        while (e.hasMoreElements()) {
        	for(Node node : e.nextElement()) {
        		if (node.getNom().equalsIgnoreCase(pNode.getNom())) {
        			//String clauNova = pNode.getHoraris().keys().nextElement();
        			//node.getHoraris().put(clauNova, pNode.getHoraris().get(clauNova));
        			return node;
        		}
        	}        	
        }        	
		return pNode;
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
