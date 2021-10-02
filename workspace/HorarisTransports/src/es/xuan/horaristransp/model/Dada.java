/**
 * 
 */
package es.xuan.horaristransp.model;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author jcamposp
 *
 */
public class Dada implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Calendar hora;

	public Calendar getHora() {
		return hora;
	}

	public void setHora(Calendar hora) {
		this.hora = hora;
	}

}
