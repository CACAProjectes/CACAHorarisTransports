package es.xuan.horaristransportsapp.files;

import android.os.StrictMode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;

public class GestioWeb implements Serializable {
    private static final long serialVersionUID = 1L;

    private final static String CTE_PROXY_IP = "10.126.132.10";
    private final static String CTE_PROXY_PORT = "8080";

    public static String[] obtenirHoraris(String pUrl) {
        return parserHoraris(getContingutURL(pUrl));
    }
    public static String[] obtenirTempsEspera(String pUrl, String pLinia, String pDesti) {
        return parserTempsEspera(getContingutURL(pUrl), pLinia, pDesti);
    }

    private static String[] parserTempsEspera(String pContingut, String pLinia, String pDesti) {
        String marca1 = "<td class=\"quantTrigaraCenter\">";
        String marca2 = "<td class=\"normal negro\"></td>";
        String marca3 = "<td class=\"normal negro\">";
        String marca4 = "&nbsp;";
        int ind1 = 1;
        int ind2 = 0;
        int ind3 = 0;
        String[] hores = new String[] {"?","?"};
        int iComptador = 0;
        while(ind1 > 0) {
			/*
			 	<td class="quantTrigaraCenter">
			 		<img src="DOCUMENTS/Menu/Linies/l5no.png" width="15px">
		  		</td>
		  		<td class="normal negro" title="Can Rosés">Can Rosés</td>
					<td class="normal negro">21&nbsp;min.</td>
					<td class="normal negro"></td>
			 */
            ind1 = pContingut.indexOf(marca1, ind2);
            if (ind1 > 0) {
                ind2 = pContingut.indexOf(marca2, ind1);
                String strSubText = pContingut.substring(ind1, ind2);
                if (strSubText.toLowerCase().contains(pLinia.toLowerCase())
                        && strSubText.toLowerCase().contains(pDesti.toLowerCase())) {
                    ind1 = strSubText.indexOf(marca3);
                    ind3 = strSubText.indexOf(marca4, ind1);
                    if (ind1 > 0 && ind3 > 0) {
                        hores[iComptador] = strSubText.substring(ind1 + marca3.length(), ind3);
                        iComptador++;
                    }
                }
            }
        }
        return hores;
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

    private static String getContingutURL(String pStrUrl) {
        URLConnection yc = null;
        InputStreamReader isr = null;
        StringBuilder lineStr = new StringBuilder();
        String strAux = "";
        try {
            yc = new URL(pStrUrl).openConnection();
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
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
