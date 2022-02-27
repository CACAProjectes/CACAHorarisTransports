package es.xuan.horaristransportsapp.model;

import java.io.Serializable;

public class Linia implements Serializable {
	private static final long serialVersionUID = 1L;
	/*
	 * <option value="5-8"  title="CAN ROSÉS-ESTACIÓ RUBÍ+D"> L5. Estació RubÍ+D-Can Rosés</option>
	 */
	private int idLinia;
	private int idSentit;
	private String nomLinia;
	private boolean selected;
	
	public Linia(int pLinia, int pSentit, String pNomLinia) {
		idLinia = pLinia;
		idSentit = pSentit;
		nomLinia = pNomLinia;
	}
	public Linia() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public Object clone() {
		try {
			return (Linia) super.clone();
		} catch (CloneNotSupportedException e) {
			return new Linia(this.idLinia, this.idSentit, this.nomLinia);
		}
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

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
