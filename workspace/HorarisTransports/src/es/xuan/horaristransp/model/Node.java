package es.xuan.horaristransp.model;

import java.io.Serializable;

import es.xuan.horaristransp.utils.Constants;

public class Node implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String nom;
	private Horaris horaris;
	private Coordenada coordenada;
	
	public Node(String[] strValors) {
		/*
		 * L5;[T]Estació RubÍ+D;F,08:35,09:19,10:03,10:47,11:31,12:15,12:59,13:43,14:27,15:11,15:55,16:39,17:23,18:07,18:51,19:35,20:19,21:01,21:43
		 */
		String[] valors = new String[0];
		setNom(strValors[1]);			// Nom - Estació RubÍ+D
		if (strValors.length > 2) {		// No tots els nodes tenen horaris
			// Horaris - F,08:35,09:19,10:03,10:47,11:31,12:15,12:59,13:43,14:27,15:11,15:55,16:39,17:23,18:07,18:51,19:35,20:19,21:01,21:43
			valors = strValors[2].split(Constants.CNT_SEPARADOR_COMA);
			setHoraris(new Horaris());
			// L4 - F,08:35,09:19,10:03,10:47,11:31,12:15,...
			getHoraris().add(strValors[0], valors);
		}
		setCoordenada(new Coordenada(0,0));
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	/**
	 * @return the coordenada
	 */
	public Coordenada getCoordenada() {
		return coordenada;
	}
	/**
	 * @param coordenada the coordenada to set
	 */
	public void setCoordenada(Coordenada coordenada) {
		this.coordenada = coordenada;
	}	
	
	@Override
	public String toString() {
		return getNom() + getCoordenada();
	}
	/**
	 * @return the horaris
	 */
	public Horaris getHoraris() {
		return horaris;
	}
	/**
	 * @param horaris the horaris to set
	 */
	public void setHoraris(Horaris horaris) {
		this.horaris = horaris;
	}
}
