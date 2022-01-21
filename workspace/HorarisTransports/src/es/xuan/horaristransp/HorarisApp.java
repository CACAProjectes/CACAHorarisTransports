package es.xuan.horaristransp;

import es.xuan.horaristransp.model.HorarisTransports;

public class HorarisApp {

	public static void main(String[] args) {
		//
		HorarisTransports horarisTrans = GestorHorarisTransports.getInstance();
		//
		System.out.println(horarisTrans.getAvui());
	}
}
