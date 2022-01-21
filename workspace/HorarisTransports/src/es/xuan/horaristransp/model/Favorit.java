package es.xuan.horaristransp.model;

import java.io.Serializable;

public class Favorit implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Linia linia;
	private Parada paradaOrigen;
	private Parada paradaDesti;
	
	public Linia getLinia() {
		return linia;
	}
	public void setLinia(Linia linia) {
		this.linia = linia;
	}
	public Parada getParadaOrigen() {
		return paradaOrigen;
	}
	public void setParadaOrigen(Parada paradaOrigen) {
		this.paradaOrigen = paradaOrigen;
	}
	public Parada getParadaDesti() {
		return paradaDesti;
	}
	public void setParadaDesti(Parada paradaDesti) {
		this.paradaDesti = paradaDesti;
	}

}
