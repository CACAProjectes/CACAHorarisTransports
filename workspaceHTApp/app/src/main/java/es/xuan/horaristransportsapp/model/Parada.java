package es.xuan.horaristransportsapp.model;

import java.io.Serializable;

public class Parada implements Serializable {
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
	
	public Parada(int pIdParada, int pOrdre, String pNomParada) {
		idParada = pIdParada;
		nomParada = pNomParada;
		ordre = pOrdre;
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
}
