package es.xuan.horaristransp.model;

import java.io.Serializable;

public class Linia implements Serializable {
	private static final long serialVersionUID = 1L;
	/*
	 * <option value="5-8"  title="CAN ROS�S-ESTACI� RUB�+D"> L5. Estaci� Rub�+D-Can Ros�s</option>
	 */
	private int idLinia;
	private int idSentit;
	private String nomLinia;
	
	public Linia(int pLinia, int pSentit, String pNomLinia) {
		idLinia = pLinia;
		idSentit = pSentit;
		nomLinia = pNomLinia;
	}
	public Linia() {
		// TODO Auto-generated constructor stub
	}
	public int getIdLinia() {
		return idLinia;
	}
	public void setIdLinia(int idLinia) {
		this.idLinia = idLinia;
	}
	public int getIdSentit() {
		return idSentit;
	}
	public void setIdSentit(int idSentit) {
		this.idSentit = idSentit;
	}
	public String getNomLinia() {
		return nomLinia;
	}
	public void setNomLinia(String nomLinia) {
		this.nomLinia = nomLinia;
	}
	@Override
	public String toString() {
		return "" + getIdLinia() + ", " + getIdSentit() + ", " + getNomLinia();
	}
}
