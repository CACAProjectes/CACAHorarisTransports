/**
 * 
 */
package es.xuan.horaristransp.model;

import java.io.Serializable;

/**
 * @author jcamposp
 *
 */
public class Coordenada implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int X;
	private int Y;
	
	public Coordenada(int coordX, int coordY) {
		setX(coordX);
		setY(coordY);
	}
	public int getX() {
		return X;
	}
	public void setX(int x) {
		X = x;
	}
	public int getY() {
		return Y;
	}
	public void setY(int y) {
		Y = y;
	}
	
	@Override
	public String toString() {
		return "(" + getX() + "," + getY() + ")";
	}
}
