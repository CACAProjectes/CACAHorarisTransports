package es.xuan.horaristransp.model;

import java.io.Serializable;

public class Parada implements Serializable, Comparable {
	private static final long serialVersionUID = 1L;
	/*
	 * ORIGEN: Països Catalans	17:50 -  2 min - 15 min
	 * DESTÍ:  Can Rosés		18:10
	 */	
	private int idParada;
	private String nomParada;
	private String hora;
	private int tempsEspera1;
	private int tempsEspera2;
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
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public int getTempsEspera1() {
		return tempsEspera1;
	}
	public void setTempsEspera1(int tempsEspera1) {
		this.tempsEspera1 = tempsEspera1;
	}
	public int getTempsEspera2() {
		return tempsEspera2;
	}
	public void setTempsEspera2(int tempsEspera2) {
		this.tempsEspera2 = tempsEspera2;
	}
	public boolean isEsOrigen() {
		return esOrigen;
	}
	public void setEsOrigen(boolean esOrigen) {
		this.esOrigen = esOrigen;
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
