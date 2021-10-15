package es.xuan.horaristransp.model;

import java.io.Serializable;
import java.util.Hashtable;

public class Horaris implements Serializable {
	private static final long serialVersionUID = 1L;
	// L5;[T]Estació RubÍ+D;F,08:35,09:19,10:03,10:47,11:31,12:15,12:59,13:43,14:27,15:11,15:55,16:39,17:23,18:07,18:51,19:35,20:19,21:01,21:43
	Hashtable<String, Hores> horaris;	// [[LINIA],[L/S/F, HORES]]
	
	public Hashtable<String, Hores> getHoraris() {
		return horaris;
	}

	public void setHoraris(Hashtable<String, Hores> horaris) {
		this.horaris = horaris;
	}

	public void add(String pLinia, String[] pHores) {
		Hores hores = new Hores();
		hores.add(pHores);
		if (getHoraris() == null)
			setHoraris(new Hashtable<String, Hores>());
		getHoraris().put(pLinia, hores);
	}
}
