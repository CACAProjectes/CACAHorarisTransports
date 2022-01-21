package es.xuan.horaristransp;

import java.util.ArrayList;
import java.util.Calendar;

import es.xuan.horaristransp.model.HorarisTransports;
import es.xuan.horaristransp.model.Parada;
import es.xuan.horaristransp.utils.Utils;

public class HorarisApp {

	public static void main(String[] args) {
		//
		HorarisTransports horarisTrans = GestorHorarisTransports.getInstance();
		//
		ArrayList<Parada> parades = GestorHorarisTransports.obtenirParadesLinia(5, 8);
		//
		System.out.println(Utils.formatDataComplerta(horarisTrans.getAvui(), horarisTrans.getIdioma()));
		System.out.println("Nº parades: " + parades.size());
	}
}
