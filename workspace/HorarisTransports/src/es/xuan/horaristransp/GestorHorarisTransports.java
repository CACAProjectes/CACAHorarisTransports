package es.xuan.horaristransp;

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
import java.util.Calendar;

import es.xuan.horaristransp.model.HorarisTransports;
import es.xuan.horaristransp.model.Linia;
import es.xuan.horaristransp.model.Parada;
import es.xuan.horaristransp.utils.Utils;

public class GestorHorarisTransports implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final static String CTE_PROXY_IP = "10.126.132.10";
	private final static String CTE_PROXY_PORT = "8080";
	
	private static final String CTE_IDIOMA = "es";
	private static final String CTE_OBTENIR_LINIES_SENTITS = "https://rubibus.com/index.asp?seccion=calculoRecorrido&lang=";
	private static final String CTE_OBTENIR_PARADES_LINIA = "https://rubibus.com/index.asp?seccion=planolinia&linia=#&lang=";

	private static final long CTE_TEMPS_MAX_EXECUCIO = 1000;	// Temps en ms	

	private static HorarisTransports mHorarisTrans = null; 
	private static ArrayList<Linia> mArrLinies = null;
	private static ArrayList<Parada> mArrParades = null;
			
	public static HorarisTransports getInstance() {
		Calendar calAvui = Calendar.getInstance();
		mHorarisTrans = new HorarisTransports();
		mHorarisTrans.setIdioma(CTE_IDIOMA);
		mHorarisTrans.setAvui(calAvui);
		mHorarisTrans.setJornada(Utils.calcularJornada(calAvui));
		mHorarisTrans.setLinies(obtenirLinies());
		return mHorarisTrans;
	}
	
	public static ArrayList<Parada> obtenirParadesLinia(int pLinia, int pSentit) {
		//	obtenirLinies
		Thread thread = new Thread(){
			public void run(){
				System.out.println("Thread obtenirParadesLinia Running");
				// obtenirLinies
				String strUrl = CTE_OBTENIR_PARADES_LINIA.replaceFirst("#", "" + pLinia) + mHorarisTrans.getIdioma();
				String contingut = getContingutURL(strUrl);
				// Treure informació de la Web				
				mArrParades = parserObtenirParadesLinia(contingut, pSentit);
			}
		};
		try {
			thread.start();
			thread.join(CTE_TEMPS_MAX_EXECUCIO);
			//	Si no acaba a temps retorna valors fixes de Linies
			if(thread.isAlive()) {
				System.out.println("Thread obtenirParadesLinia NO finalizat");
				// Finalitzar Thread
				thread.interrupt();
				// Treure informació constant NO de Web 
				// TODO recollir dades del REPOSITORI GENERAL
			}
			// Consulta finalitzada
			System.out.println("Thread obtenirParadesLinia finalizat");
		} catch (InterruptedException e) {
			System.out.println("Thread obtenirParadesLinia Interrumput");
			thread.interrupt();
			e.printStackTrace();
		}
		return mArrParades;
	}

	public static ArrayList<Linia> obtenirLinies() {
		//	obtenirLinies
		Thread thread = new Thread(){
			public void run(){
				System.out.println("Thread obtenirLinies Running");
				// obtenirLinies
				String contingut = getContingutURL(CTE_OBTENIR_LINIES_SENTITS + mHorarisTrans.getIdioma());
				// TODO Treure informació de la Web				
				mArrLinies = parserObtenirLinies(contingut);
			}
		};
		try {
			thread.start();
			thread.join(CTE_TEMPS_MAX_EXECUCIO);
			//	Si no acaba a temps retorna valors fixes de Linies
			if(thread.isAlive()) {
				System.out.println("Thread obtenirLinies NO finalizat");
				// Finalitzar Thread
				thread.interrupt();
				// TODO Treure informació constant NO de Web 
				mArrLinies = parserObtenirLinies();
			}
			// Consulta finalitzada
			System.out.println("Thread obtenirLinies finalizat");
		} catch (InterruptedException e) {
			System.out.println("Thread obtenirLinies Interrumput");
			thread.interrupt();
			e.printStackTrace();
		}
		return mArrLinies;
	}
	
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

	public static ArrayList<Linia> parserObtenirLinies(String pContingut) {
		/*
		 * Parser el contingut de la Web a Linies
		 */
		/*
		 	<option value="0"  selected >
		 	<option value="1-2"  title="EL PINAR-CAN SERRAFOSSÀ"> L1. Can Serrafossà-El Pinar</option>
			<option value="1-1"  title="EL PINAR-CAN SERRAFOSSÀ"> L1. El Pinar-Can Serrafossà</option>
			<option value="2-4"  title="CAN VALLHONRAT-EL PINAR"> L2. El Pinar/Can Rosés-Can Vallhonrat</option>
			<option value="2-3"  title="CAN VALLHONRAT-EL PINAR"> L2. Can Vallhonrat-El Pinar/Can Rosés</option>
			<option value="3-14" title="ESTACIÓ RUBÍ+D-CASTELLNOU"> L3. Estació RubÍ+D-Castellnou</option>
			<option value="3-13" title="ESTACIÓ RUBÍ+D-CASTELLNOU"> L3. Castellnou-Estació RubÍ+D</option>
			<option value="4-6"  title="ESTACIÓ RUBÍ+D-CAN ROSÉS"> L4. Can Rosés-Estació RubÍ+D</option>
			<option value="4-5"  title="ESTACIÓ RUBÍ+D-CAN ROSÉS"> L4. Estació RubÍ+D-Can Rosés</option>
			<option value="5-8"  title="CAN ROSÉS-ESTACIÓ RUBÍ+D"> L5. Estació RubÍ+D-Can Rosés</option>
			<option value="5-7"  title="CAN ROSÉS-ESTACIÓ RUBÍ+D"> L5. Can Rosés-Estació RubÍ+D</option>
			<option value="6-12" title="RUBÍ SUD-POL. LA BASTIDA"> L6. Pol. La Bastida-Pol. Rubí Sud</option>
			<option value="6-9"  title="RUBÍ SUD-POL. LA BASTIDA"> L6. Pol. Rubí Sud-Pol. La Bastida</option>
			<option value="7-16" title="ESTACIÓ RUBÍ+D-SANT MUÇ"> L7. Sant Muç-Estació RubÍ+D</option>
			<option value="7-15" title="ESTACIÓ RUBÍ+D-SANT MUÇ"> L7. Estació RubÍ+D-Sant Muç</option>
		 */
		ArrayList<Linia> arrLinies = new ArrayList<Linia>();
		//
		int ind0 = pContingut.indexOf("<option value=\"0\"  selected >") + "<option value=\"0\"".length();
		int ind1 = 0;
		int ind2 = ind0;
		do {
			try {
				ind1 = pContingut.indexOf("<option value=", ind2);
				ind2 = pContingut.indexOf("</option>", ind1);
				//
				Linia linia = tractarLinia(pContingut.substring(ind1, ind2));
				if (linia == null)
					break;
				arrLinies.add(linia);
			} catch (Exception ex) {	
				break;
			}
		} while (ind1 > 0);
		
		return arrLinies;
	}
	
	private static Linia tractarLinia(String pContingut) {
		/*
		 * <option value="1-2"  title="EL PINAR-CAN SERRAFOSSÀ"> L1. Can Serrafossà-El Pinar</option>
		 */
		Linia linia = null;
		try {
			int ind1 = pContingut.indexOf("value=\"") + "value=\"".length();
			int ind2 = pContingut.indexOf("\"", ind1);
			String strLiniaSentit = pContingut.substring(ind1, ind2);
			//
			ind1 = pContingut.indexOf(">", ind2);
			ind2 = pContingut.indexOf(".", ind1) + 1;
			String strNomLiniaSentit = pContingut.substring(ind2);
			//
			if (ind1 <= 0 || ind2 <= 0)
				return null;
			//
			linia = new Linia(
					Integer.parseInt(strLiniaSentit.split("-")[0]), 
					Integer.parseInt(strLiniaSentit.split("-")[1]), 
					strNomLiniaSentit.trim());
		}
		catch (Exception ex) {			
		}
		return linia;
	}

	public static ArrayList<Linia> parserObtenirLinies() {
		/*
		 * Obté les linies sense informació. Retorna valors fixes
		 */
		ArrayList<Linia> arrLinies = new ArrayList<Linia>();
		arrLinies.add(new Linia(1, 2, "Can Serrafossà-El Pinar"));
		arrLinies.add(new Linia(1, 1, "El Pinar-Can Serrafossà"));
		arrLinies.add(new Linia(2, 4, "El Pinar/Can Rosés-Can Vallhonrat"));
		arrLinies.add(new Linia(2, 3, "Can Vallhonrat-El Pinar/Can Rosés"));
		arrLinies.add(new Linia(3, 14, "Estació RubÍ+D-Castellnou"));
		arrLinies.add(new Linia(3, 13, "Castellnou-Estació RubÍ+D"));
		arrLinies.add(new Linia(4, 6, "Can Rosés-Estació RubÍ+D"));
		arrLinies.add(new Linia(4, 5, "Estació RubÍ+D-Can Rosés"));
		arrLinies.add(new Linia(5, 8, "Estació RubÍ+D-Can Rosés"));
		arrLinies.add(new Linia(5, 7, "Can Rosés-Estació RubÍ+D"));
		arrLinies.add(new Linia(6, 12, "Pol. La Bastida-Pol. Rubí Sud"));
		arrLinies.add(new Linia(6, 9, "Pol. Rubí Sud-Pol. La Bastida"));
		arrLinies.add(new Linia(7, 16, "Sant Muç-Estació RubÍ+D"));
		arrLinies.add(new Linia(7, 15, "Estació RubÍ+D-Sant Muç"));	
		return arrLinies;
	}
	

	public static ArrayList<Parada> parserObtenirParadesLinia(String pContingut, int pSentit) {
		/*
		 	<area id="HP-136" shape="rect" coords="87,133,228,157" href="index.asp?seccion=horarioLinia&idParada=136&idSentido=7&idLinia=5&lang=es&idJornada=1" alt="Can Rosés" title="Can Rosés" class="muestraTitle"/>
			<area id="HP-137" shape="rect" coords="91,164,198,183" href="index.asp?seccion=horarioLinia&idParada=137&idSentido=7&idLinia=5&lang=es&idJornada=1" alt="Terranova" title="Terranova" class="muestraTitle"/>
			<area id="HP-138" shape="rect" coords="95,193,180,214" href="index.asp?seccion=horarioLinia&idParada=138&idSentido=7&idLinia=5&lang=es&idJornada=1" alt="Sevilla" title="Sevilla" class="muestraTitle"/>
			<area id="HP-139" shape="rect" coords="96,227,211,244" href="index.asp?seccion=horarioLinia&idParada=139&idSentido=7&idLinia=5&lang=es&idJornada=1" alt="Primer de Maig" title="Primer de Maig" class="muestraTitle"/>
			<area id="HP-140" shape="rect" coords="98,254,187,275" href="index.asp?seccion=horarioLinia&idParada=140&idSentido=7&idLinia=5&lang=es&idJornada=1" alt="La Serreta" title="La Serreta" class="muestraTitle"/>
			<area id="HP-141" shape="rect" coords="94,283,171,302" href="index.asp?seccion=horarioLinia&idParada=141&idSentido=7&idLinia=5&lang=es&idJornada=1" alt="Sabadell" title="Sabadell" class="muestraTitle"/>
			<area id="HP-142" shape="rect" coords="94,310,168,328" href="index.asp?seccion=horarioLinia&idParada=142&idSentido=7&idLinia=5&lang=es&idJornada=1" alt="Orso" title="Orso" class="muestraTitle"/>
			<area id="HP-143" shape="rect" coords="89,338,191,354" href="index.asp?seccion=horarioLinia&idParada=143&idSentido=7&idLinia=5&lang=es&idJornada=1" alt="Santa Eulàlia" title="Santa Eulàlia" class="muestraTitle"/>
			<area id="HP-144" shape="rect" coords="98,367,191,387" href="index.asp?seccion=horarioLinia&idParada=144&idSentido=7&idLinia=5&lang=es&idJornada=1" alt="Zamenhoff" title="Zamenhoff" class="muestraTitle"/>
			<area id="HP-145" shape="rect" coords="91,367,178,388" href="index.asp?seccion=horarioLinia&idParada=145&idSentido=7&idLinia=5&lang=es&idJornada=1" alt="Sardana" title="Sardana" class="muestraTitle"/>
			<area id="HP-146" shape="rect" coords="89,424,192,442" href="index.asp?seccion=horarioLinia&idParada=146&idSentido=7&idLinia=5&lang=es&idJornada=1" alt="Pau Clarís" title="Pau Clarís" class="muestraTitle"/>
			<area id="HP-147" shape="rect" coords="96,452,166,475" href="index.asp?seccion=horarioLinia&idParada=147&idSentido=7&idLinia=5&lang=es&idJornada=1" alt="Cadmo" title="Cadmo" class="muestraTitle"/>
			<area id="HP-151" shape="rect" coords="93,515,183,537" href="index.asp?seccion=horarioLinia&idParada=151&idSentido=7&idLinia=5&lang=es&idJornada=1" alt="Sant Joan" title="Sant Joan" class="muestraTitle"/>
			<area id="HP-152" shape="rect" coords="93,534,209,562" href="index.asp?seccion=horarioLinia&idParada=152&idSentido=7&idLinia=5&lang=es&idJornada=1" alt="Espoz i Mina" title="Espoz i Mina" class="muestraTitle"/>
			<area id="HP-153" shape="rect" coords="90,566,225,592" href="index.asp?seccion=horarioLinia&idParada=153&idSentido=7&idLinia=5&lang=es&idJornada=1" alt="Estació RubÍ+D" title="Estació RubÍ+D" class="muestraTitle"/>
			<area id="HP-851" shape="rect" coords="84,482,171,499" href="index.asp?seccion=horarioLinia&idParada=851&idSentido=7&idLinia=5&lang=es&idJornada=1" alt="Bullidor" title="Bullidor" class="muestraTitle"/>

			<area id="HP-154" shape="rect" coords="73,134,210,156" href="index.asp?seccion=horarioLinia&idParada=154&idSentido=8&idLinia=5&lang=es&idJornada=1" alt="Estació RubÍ+D" title="Estació RubÍ+D" class="muestraTitle"/>
			<area id="HP-155" shape="rect" coords="91,157,185,176" href="index.asp?seccion=horarioLinia&idParada=155&idSentido=8&idLinia=5&lang=es&idJornada=1" alt="Joan Puig" title="Joan Puig" class="muestraTitle"/>
			<area id="HP-156" shape="rect" coords="90,180,158,194" href="index.asp?seccion=horarioLinia&idParada=156&idSentido=8&idLinia=5&lang=es&idJornada=1" alt="Servet" title="Servet" class="muestraTitle"/>
			<area id="HP-157" shape="rect" coords="93,199,216,217" href="index.asp?seccion=horarioLinia&idParada=157&idSentido=8&idLinia=5&lang=es&idJornada=1" alt="Doctor Ferran" title="Doctor Ferran" class="muestraTitle"/>
			<area id="HP-158" shape="rect" coords="91,217,217,233" href="index.asp?seccion=horarioLinia&idParada=158&idSentido=8&idLinia=5&lang=es&idJornada=1" alt="Països Catalans" title="Països Catalans" class="muestraTitle"/>
			<area id="HP-160" shape="rect" coords="90,257,259,274" href="index.asp?seccion=horarioLinia&idParada=160&idSentido=8&idLinia=5&lang=es&idJornada=1" alt="Anton de Borja" title="Anton de Borja" class="muestraTitle"/>
			<area id="HP-161" shape="rect" coords="89,278,223,293" href="index.asp?seccion=horarioLinia&idParada=161&idSentido=8&idLinia=5&lang=es&idJornada=1" alt="25 de Setembre" title="25 de Setembre" class="muestraTitle"/>
			<area id="HP-166" shape="rect" coords="91,395,235,410" href="index.asp?seccion=horarioLinia&idParada=166&idSentido=8&idLinia=5&lang=es&idJornada=1" alt="Plana de Can Bertran" title="Plana de Can Bertran" class="muestraTitle"/>
			<area id="HP-167" shape="rect" coords="93,416,176,431" href="index.asp?seccion=horarioLinia&idParada=167&idSentido=8&idLinia=5&lang=es&idJornada=1" alt="Lourdes" title="Lourdes" class="muestraTitle"/>
			<area id="HP-168" shape="rect" coords="94,435,179,450" href="index.asp?seccion=horarioLinia&idParada=168&idSentido=8&idLinia=5&lang=es&idJornada=1" alt="Ca nOriol" title="Ca nOriol" class="muestraTitle"/>
			<area id="HP-169" shape="rect" coords="79,455,181,470" href="index.asp?seccion=horarioLinia&idParada=169&idSentido=8&idLinia=5&lang=es&idJornada=1" alt="Segòvia" title="Segòvia" class="muestraTitle"/>
			<area id="HP-170" shape="rect" coords="78,474,191,491" href="index.asp?seccion=horarioLinia&idParada=170&idSentido=8&idLinia=5&lang=es&idJornada=1" alt="Mallorca" title="Mallorca" class="muestraTitle"/>
			<area id="HP-172" shape="rect" coords="94,513,179,532" href="index.asp?seccion=horarioLinia&idParada=172&idSentido=8&idLinia=5&lang=es&idJornada=1" alt="El Ferrol" title="El Ferrol" class="muestraTitle"/>
			<area id="HP-171" shape="rect" coords="75,495,218,510" href="index.asp?seccion=horarioLinia&idParada=171&idSentido=8&idLinia=5&lang=es&idJornada=1" alt="Font del Ferro" title="Font del Ferro" class="muestraTitle"/>
			<area id="HP-173" shape="rect" coords="94,533,171,551" href="index.asp?seccion=horarioLinia&idParada=173&idSentido=8&idLinia=5&lang=es&idJornada=1" alt="Els Nius" title="Els Nius" class="muestraTitle"/>
			<area id="HP-174" shape="rect" coords="73,554,191,568" href="index.asp?seccion=horarioLinia&idParada=174&idSentido=8&idLinia=5&lang=es&idJornada=1" alt="Olimpíades" title="Olimpíades" class="muestraTitle"/>
			<area id="HP-175" shape="rect" coords="71,573,198,592" href="index.asp?seccion=horarioLinia&idParada=175&idSentido=8&idLinia=5&lang=es&idJornada=1" alt="Can Rosés" title="Can Rosés" class="muestraTitle"/>
			<area id="HP-374" shape="rect" coords="91,296,189,315" href="index.asp?seccion=horarioLinia&idParada=374&idSentido=8&idLinia=5&lang=es&idJornada=1" alt="Sant Cugat" title="Sant Cugat" class="muestraTitle"/>
			<area id="HP-375" shape="rect" coords="95,315,193,334" href="index.asp?seccion=horarioLinia&idParada=375&idSentido=8&idLinia=5&lang=es&idJornada=1" alt="Cervantes" title="Cervantes" class="muestraTitle"/>
			<area id="HP-376" shape="rect" coords="95,337,170,354" href="index.asp?seccion=horarioLinia&idParada=376&idSentido=8&idLinia=5&lang=es&idJornada=1" alt="Mercat" title="Mercat" class="muestraTitle"/>
			<area id="HP-377" shape="rect" coords="95,356,210,372" href="index.asp?seccion=horarioLinia&idParada=377&idSentido=8&idLinia=5&lang=es&idJornada=1" alt="Magí Ramentol" title="Magí Ramentol" class="muestraTitle"/>
			<area id="HP-378" shape="rect" coords="94,377,192,393" href="index.asp?seccion=horarioLinia&idParada=378&idSentido=8&idLinia=5&lang=es&idJornada=1" alt="Biblioteca" title="Biblioteca" class="muestraTitle"/>
			<area id="HP-775" shape="rect" coords="97,233,195,252" href="index.asp?seccion=horarioLinia&idParada=775&idSentido=8&idLinia=5&lang=es&idJornada=1" alt="Antoni Sedó" title="Antoni Sedó" class="muestraTitle"/>
		 */
		ArrayList<Parada> arrParades = new ArrayList<Parada>();
		//
		int ind00 = pContingut.indexOf("mapaCiutat");	// Fi de les parades
		int ind0 = 0;
		int ind1 = 0;
		// mapaLiniaIZQ
		ind0 = pContingut.indexOf("<map name=\"mapaLiniaIZQ\">");			
		do {
			try {
				ind0 = pContingut.indexOf("<area", ind1) + "<area".length();			// <area id="HP-775" shape="rect" coords="97,233,195,252" href="index.asp?seccion=horarioLinia&idParada=775&idSentido=8&idLinia=5&lang=es&idJornada=1" alt="Antoni Sedó" title="Antoni Sedó" class="muestraTitle"/>
				ind1 = pContingut.indexOf("\"/>", ind0);	
				if (ind0 < 0 || ind1 < 0)
					break;
				//
				Parada parada = tractarParada(pContingut.substring(ind0, ind1), pSentit);
				if (parada != null)
					arrParades.add(parada);
				// Fi de parades?
				if (ind0 > ind00 || ind1 > ind00)
					break;
			}
			catch (Exception ex) {	
				break;
			}
		} while (ind1 > 0);
		//
		return arrParades;
	}

	private static Parada tractarParada(String pContingut, int pSentit) {
		/*
		 * <area id="HP-775" shape="rect" coords="97,233,195,252" href="index.asp?seccion=horarioLinia&idParada=775&idSentido=8&idLinia=5&lang=es&idJornada=1" alt="Antoni Sedó" title="Antoni Sedó" class="muestraTitle"/>
		 */
		Parada parada = null;
		try {
			// coords
			int ind00 = pContingut.indexOf("coords=\"") + "coords=\"".length();			// coords="97,233,195,252"
			int ind01 = pContingut.indexOf("\"", ind00);			
			String varCoord = pContingut.substring(ind00, ind01);
			String[] varCoordSplit = varCoord.split(",");
			// idParada
			int ind40 = pContingut.indexOf("idParada=") + "idParada=".length();			// idParada=775
			int ind41 = pContingut.indexOf("&", ind40);			
			String varIdParada = pContingut.substring(ind40, ind41);
			// idSentido		
			int ind10 = pContingut.indexOf("idSentido=", ind01) + "idSentido=".length();	// idSentido=8
			int ind11 = pContingut.indexOf("&", ind10);	
			String varSentit = pContingut.substring(ind10, ind11);
			if (Integer.parseInt(varSentit) != pSentit)
				return null;
			// idLinia
			int ind20 = pContingut.indexOf("idLinia=", ind11) + "idLinia=".length();	// idSentido=8
			int ind21 = pContingut.indexOf("&", ind20);	
			//String varLinia = pContingut.substring(ind20, ind21);
			// title
			int ind30 = pContingut.indexOf("title=\"", ind21) + "title=\"".length();	// idSentido=8
			int ind31 = pContingut.indexOf("\"", ind30);	
			String varNomParada = pContingut.substring(ind30, ind31);
			//
			parada = new Parada(Integer.parseInt(varIdParada), Integer.parseInt(varCoordSplit[1]), varNomParada);
		}
		catch(Exception ex) {			
		}
		//
		return parada;
	}
}

