package es.xuan.horaristransp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class HorarisTransports implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Calendar avui;
	private String idioma; 	// es,ca
	private int jornada;	// 3>DISSABTES<7>FESTIUS AGOST<6>DISSABTES AGOST<5>FEINERS AGOST<4>FESTIUS<1>FEINERS LECTIUS<
	private ArrayList<Favorit> favorits;
	private ArrayList<Linia> linies;
	private ArrayList<Parada> parades;
	
	public Calendar getAvui() {
		return avui;
	}
	public void setAvui(Calendar avui) {
		this.avui = avui;
	}
	public ArrayList<Favorit> getFavorits() {
		return favorits;
	}
	public void setFavorits(ArrayList<Favorit> favorits) {
		this.favorits = favorits;
	}
	public ArrayList<Linia> getLinies() {
		return linies;
	}
	public void setLinies(ArrayList<Linia> linies) {
		this.linies = linies;
	}
	public ArrayList<Parada> getParades() {
		return parades;
	}
	public void setParades(ArrayList<Parada> parades) {
		this.parades = parades;
	}
	public String getIdioma() {
		return idioma;
	}
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
	public int getJornada() {
		return jornada;
	}
	public void setJornada(int jornada) {
		this.jornada = jornada;
	}

}
