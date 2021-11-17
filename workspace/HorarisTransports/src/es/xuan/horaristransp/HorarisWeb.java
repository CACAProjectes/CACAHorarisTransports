package es.xuan.horaristransp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;

public class HorarisWeb {

	private final static String CTE_PROXY_IP = "10.126.132.10";
	private final static String CTE_PROXY_PORT = "8080";

	private final static String CTE_Horaris_L5_Estacio_RubID_Ida = "https://rubibus.com/consultes.asp?proces=HorarioLinea&idioma=ca&idLinia=5&idSentido=8&IdJornada=1&NomParada=154";
	private final static String CTE_Horaris_L5_Paisos_Catalans_Ida = "https://rubibus.com/consultes.asp?proces=HorarioLinea&idioma=ca&idLinia=5&idSentido=8&IdJornada=1&NomParada=158";
	private final static String CTE_Horaris_L5_Can_Roses_Ida = "https://rubibus.com/consultes.asp?proces=HorarioLinea&idioma=ca&idLinia=5&idSentido=8&IdJornada=1&NomParada=175";
	private final static String CTE_Horaris_L4_Can_Roses_Vuelta = "https://rubibus.com/consultes.asp?proces=HorarioLinea&idioma=ca&idLinia=4&idSentido=6&IdJornada=1&NomParada=116";
	private final static String CTE_Horaris_L4_Paisos_Catalans_Vuelta = "https://rubibus.com/consultes.asp?proces=HorarioLinea&idioma=ca&idLinia=4&idSentido=6&IdJornada=1&NomParada=131";
		
	private final static String CTE_Espera_L5_Paisos_Catalans_Ida = "https://rubibus.com/consultes.asp?proces=ConsultarProximos&idLinia=5&idSentido=8&IdJornada=1&NomParada=158";
	private final static String CTE_Espera_L4_Can_Roses_Vuelta = "https://rubibus.com/consultes.asp?proces=ConsultarProximos&idioma=ca&idLinia=4&idSentido=6&IdJornada=1&NomParada=116";
		
	public static void main(String[] args) {
		String contingut = getContingutURL(CTE_Horaris_L5_Estacio_RubID_Ida);
		String[] horaris = parserHoraris(contingut);
		System.out.println("Nº elements: " + horaris.length);
		for (String hora : horaris)
			System.out.println(hora);
		String temps = "";
		contingut = getContingutURL(CTE_Espera_L5_Paisos_Catalans_Ida);
		temps = parserTempsEspera(contingut, "L5", "Can Rosés");
		System.out.println("Temps espera: " + temps);
		contingut = getContingutURL(CTE_Espera_L4_Can_Roses_Vuelta);
		temps = parserTempsEspera(contingut, "L4", "Estació RubÍ+D");
		System.out.println("Temps espera: " + temps);
	}
	private static String parserTempsEspera(String pContingut, String pLinia, String pDesti) {
		String marca1 = "<td class=\"quantTrigaraCenter\">";
		String marca2 = "<td class=\"normal negro\"></td>";
		String marca3 = "<td class=\"normal negro\">";
		String marca4 = "&nbsp;";
		int ind1 = 1;
		int ind2 = 0;
		while(ind1 > 0) {
			/*
			 	<td class="quantTrigaraCenter">
			 		<img src="DOCUMENTS/Menu/Linies/l5no.png" width="15px">
		  		</td>
		  		<td class="normal negro" title="Can Rosés">Can Rosés</td>
					<td class="normal negro">21&nbsp;min.</td>
					<td class="normal negro"></td>
			 */
			ind1 = pContingut.indexOf(marca1, ind1);
			ind2 = pContingut.indexOf(marca2, ind1);
			String strSubText = pContingut.substring(ind1, ind2);
			if (strSubText.toLowerCase().contains(pLinia.toLowerCase())
				&& strSubText.toLowerCase().contains(pDesti.toLowerCase())) {
				ind1 = strSubText.indexOf(marca3);
				ind2 = strSubText.indexOf(marca4, ind1);
				if (ind1 > 0 && ind2 > 0)
					return strSubText.substring(ind1 + marca3.length(), ind2);
			}	
			ind1 = ind2;
		}
		return "";
	}
	private static String[] parserHoraris(String pText) {
		int ind1 = 1;
		int ind2 = 1;
		String marca1 = "<div style=\"background:#d8d9da;\">&nbsp;";
		String marca2 = "</div>";
		ArrayList<String> horaris = new ArrayList<String>();
		while (ind1 > 0) {
			ind1 = pText.indexOf(marca1, ind2);
			if (ind1 > 0) {
				ind2 = pText.indexOf(marca2, ind1 + 1);
				String hora = pText.substring(ind1 + marca1.length(), ind2); 
				hora = (hora.length() == 4 ? "0" + hora : hora);
				horaris.add(hora);
			}
		}
		Collections.sort(horaris);
		return horaris.toArray(new String[horaris.size()]);
	}
	//@SuppressWarnings("null")
	private static String getContingutURL(String pStrUrl) {
		URLConnection yc = null;
	    InputStreamReader isr = null;
		StringBuilder lineStr = new StringBuilder();
		String strAux = "";
		try {
			yc = new URL(pStrUrl).openConnection();
			//StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			//StrictMode.setThreadPolicy(policy);
			isr = new InputStreamReader(yc.getInputStream(), StandardCharsets.ISO_8859_1);
		} catch (Exception ex) {	
			// Hi ha PROXY
		}	
		if (isr == null) {
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(CTE_PROXY_IP, Integer.parseInt(CTE_PROXY_PORT)));
		    try {
				yc = new URL(pStrUrl).openConnection(proxy);
				isr = new InputStreamReader(yc.getInputStream(), StandardCharsets.ISO_8859_1);
			} catch (IOException e) {
				e.printStackTrace();
				return "";
			}
		}
		try {
			//
			BufferedReader in = new BufferedReader(isr);
		    while((strAux = in.readLine()) != null) {
		    	lineStr.append(strAux);
		    }
		    in.close();
		    isr.close();
		}
		catch (Exception ex) {
			System.err.println("Error -getContingutURL.1-: " + ex);
		}
		return lineStr.toString();
	}

}
