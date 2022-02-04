package es.xuan.horaristransportsapp.model;

import java.io.Serializable;
import java.util.Hashtable;

public class Hores implements Serializable {
	private static final long serialVersionUID = 1L;
	// L5;[T]Estació RubÍ+D;F,08:35,09:19,10:03,10:47,11:31,12:15,12:59,13:43,14:27,15:11,15:55,16:39,17:23,18:07,18:51,19:35,20:19,21:01,21:43
	Hashtable<String, String[]> hores;	// [L/S/F, HORES]
	
	public Hashtable<String, String[]> getHores() {
		return hores;
	}

	public void setHores(Hashtable<String, String[]> hores) {
		this.hores = hores;
	}

	public void add(String[] pHores) {
		// F,08:35,09:19,10:03,10:47,11:31,12:15,12:59,13:43,14:27,15:11,15:55,16:39,17:23,18:07,18:51,19:35,20:19,21:01,21:43
		if (pHores != null && pHores.length > 1) {
			String[] auxHores = new String[pHores.length - 1];
			for(int i=1;i<pHores.length;i++) {
				auxHores[i-1] = pHores[i];
			}
			if (getHores() == null)
				setHores(new Hashtable<String, String[]>());
			getHores().put(pHores[0], auxHores);
		}		
	}
}
