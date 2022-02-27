package es.xuan.horaristransp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import es.xuan.horaristransp.model.HorarisTransports;
import es.xuan.horaristransp.model.Parada;
import es.xuan.horaristransp.utils.Utils;

public class HorarisApp {

	private static int mMinut = 0;
	
	public static void main(String[] args) {
		//
		HorarisTransports horarisTrans = GestorHorarisTransports.getInstance();
		
		//
		ArrayList<Parada> parades = GestorHorarisTransports.obtenirParadesLinia(5, 8);
		//ArrayList<String> hores = GestorHorarisTransports.obtenirHoresParada(4, 5, 1, 368);
		//ArrayList<String> tempsEspera = GestorHorarisTransports.obtenirTempsEspera(4, 5, 1, 368);
		//ArrayList<String> tempsEspera = GestorHorarisTransports.obtenirTempsEspera(3, 13, 1, 294);
		//
		//System.out.println(Utils.formatDataComplerta(horarisTrans.getAvui(), horarisTrans.getIdioma()));
		//System.out.println("Hores: ");
		//for(String hora:hores) {
		//	System.out.println(hora);
		//}
		//System.out.println("Temps d'espera: ");
		//for(String temps:tempsEspera) {
		//	System.out.println(temps + " min.");
		//}		
		System.out.println("Nº parades: " + parades.size());
		System.out.println("Parades: ");
		for(Parada parada:parades) {
			System.out.println(parada.getOrdre() + " - " + parada.getNomParada());
		}		
		//inicialitzarTimerSeg();
	}
	
    private static void inicialitzarTimerSeg() {
    	SimpleDateFormat formateador = new SimpleDateFormat("mm");    	
    	mMinut = Integer.parseInt(formateador.format(new Date()));
    	//
        TimerTask repeatedTask = new TimerTask() {
            public void run() {
                System.out.println("Task performed on inicialitzarTimerSeg - " + new Date());
                int minutAux = Integer.parseInt(formateador.format(new Date()));
                if (minutAux != mMinut) {
                	this.cancel(); 
                    inicialitzarTimerMin();
                }
            }
        };
        Timer timer = new Timer("Timer");
        long delay = 1000L;
        long period = 1000L;
        timer.scheduleAtFixedRate(repeatedTask, delay, period);
    }
    private static void inicialitzarTimerMin() {
        TimerTask repeatedTask = new TimerTask() {
            public void run() {
                System.out.println("Task performed on inicialitzarTimerMin - " + new Date());
            }
        };
        Timer timer = new Timer("Timer");
        long delay = 1000L;
        long period = 5 * 1000L;
        timer.scheduleAtFixedRate(repeatedTask, delay, period);
    }
}
