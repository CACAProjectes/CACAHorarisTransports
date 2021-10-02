/**
 * 
 */
package es.xuan.horaristransp.utils;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * @author jcamposp
 *
 */
public class Utils implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static int convertString2Int(String pNum) {
		NumberFormat nf = NumberFormat.getInstance();		
		try {
			return nf.parse(pNum).intValue();
		} catch (ParseException e) {
		}
		return 0;
	}

}
