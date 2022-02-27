package es.xuan.horaristransportsapp.model;

import java.io.Serializable;

public class Parada implements Serializable, Comparable {
	private static final long serialVersionUID = 1L;
	/*
	 * ORIGEN: Països Catalans	17:50 -  2 min - 15 min
	 * DESTÍ:  Can Rosés		18:10
	 */	
	private int idParada;
	private String nomParada;
	private TempsEspera tempsEspera;
	private boolean esOrigen;
	private int ordre;

	public char getSelected() {
		return selected;
	}

	public void setSelected(char selected) {
		this.selected = selected;
	}

	private char selected;
	
	public Parada(int pIdParada, int pOrdre, String pNomParada) {
		idParada = pIdParada;
		nomParada = pNomParada;
		ordre = pOrdre;
	}
	@Override
	public Object clone() {
		try {
			return (Parada) super.clone();
		} catch (CloneNotSupportedException e) {
			return new Parada(this.idParada, this.ordre, this.nomParada);
		}
	}
	public int getOrdre() {
		return ordre;
	}
	public void setOrdre(int ordre) {
		this.ordre = ordre;
	}
	public int getIdParada() {
		return idParada;
	}
	public void setIdParada(int idParada) {
		this.idParada = idParada;
	}
	public String getNomParada() {
		return nomParada;
	}
	public void setNomParada(String nomParada) {
		this.nomParada = nomParada;
	}
	public boolean isEsOrigen() {
		return esOrigen;
	}
	public void setEsOrigen(boolean esOrigen) {
		this.esOrigen = esOrigen;
	}

	public TempsEspera getTempsEspera() {
		return tempsEspera;
	}

	public void setTempsEspera(TempsEspera tempsEspera) {
		this.tempsEspera = tempsEspera;
	}
	@Override
	public int compareTo(Object o) {
		Parada other = (Parada)o;
		if (this == other)
			return 0;
		if (this.ordre < other.ordre) return -1;
		else if (this.ordre == other.ordre) return 0;
		else return 1;

	}
}
