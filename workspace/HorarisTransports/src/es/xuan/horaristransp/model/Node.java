package es.xuan.horaristransp.model;

import java.io.Serializable;
import java.util.Hashtable;

import es.xuan.horaristransp.utils.Constants;

public class Node implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String nom;
	private Hashtable<String, String[]> horaris;
	private Coordenada coordenada;
	
	public Node(String[] strValors) {
		/*
		 * L4;Estació RubÍ+D;06:30;06:51;07:13;07:35;07:58;08:22;08:46;09:10;09:34;09:58;10:22;10:46;11:10;11:34;11:58;12:22;12:46;13:10;13:34;13:58;14:22;14:46;15:10;15:34;15:58;16:22;16:46;17:10;17:34;17:58;18:22;18:46;19:10;19:34;19:57;20:19;20:41;21:03;21:25;21:46;22:06
		 */
		String[] valors = new String[0];
		setNom(strValors[1]);			// Nom - Estació RubÍ+D
		if (strValors.length > 2) {		// No tots els nodes tenen horaris
			//setHoraris(strValors[2]);	// Horaris - 06:30;06:51;07:13;07:35;07:58;08:22;08:46;09:10;09:34;09:58;10:22;10:46;11:10;11:34;11:58;12:22;12:46;13:10;13:34;13:58;14:22;14:46;15:10;15:34;15:58;16:22;16:46;17:10;17:34;17:58;18:22;18:46;19:10;19:34;19:57;20:19;20:41;21:03;21:25;21:46;22:06
			valors = strValors[2].split(Constants.CNT_SEPARADOR_COMA);
		}
		setHoraris(new Hashtable<String, String[]>());
		// L4 - 06:30;06:51;07:13;07:35;07:58,...
		getHoraris().put(strValors[0], valors);
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
	public Hashtable<String, String[]> getHoraris() {
		return horaris;
	}
	/**
	 * @param horaris the horaris to set
	 */
	public void setHoraris(Hashtable<String, String[]> horaris) {
		this.horaris = horaris;
	}
}
