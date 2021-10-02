/**
 * 
 */
package es.xuan.horaristransp.model;

import java.io.Serializable;
import java.util.Hashtable;

import es.xuan.horaristransp.utils.Constants;

/**
 * @author jcamposp
 *
 */
public class Node implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String nom;
	private Coordenada coordenada;
	private Hashtable<String, String[]> dades;	// L5, [HORARIS0..HORARISn]
	private Hashtable<String, Node> nodesSeguents;		// L5, NODE

	public Node() {
	}
	
	public Node(String pLinia) {
		String[] dades = pLinia.split(Constants.CNT_SEPARADOR_LINIA);
		String clauLinia = dades[0];	// Nom de la linia
		setNom(dades[1]);				// Nom del node			
		String[] arrHoraris = getHoraris(dades, 2);
		addDada(clauLinia, arrHoraris);
	}
	public Hashtable<String, String[]> getDades() {
		return dades;
	}
	public void setDades(Hashtable<String, String[]> dades) {
		this.dades = dades;
	}
	public Hashtable<String, Node> getNodesSeguents() {
		return nodesSeguents;
	}
	public void setNodesSeguents(Hashtable<String, Node> nodesSeguents) {
		this.nodesSeguents = nodesSeguents;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}

	public Coordenada getCoordenada() {
		return coordenada;
	}
	public void setCoordenada(Coordenada coordenada) {
		this.coordenada = coordenada;
	}		
	
	public void addDada(String pClave, String[] pDades) {
		if (getDades() == null)
			setDades(new Hashtable<String, String[]>());
		getDades().put(pClave, pDades);
	}
	private String[] getHoraris(String[] pDades, int pIndexIni) {
		String[] res = new String[pDades.length - pIndexIni]; 
		for (int i=pIndexIni;i<pDades.length;i++) {
			res[i-pIndexIni] = pDades[i];
		}
		return res;
	}
	public void addNodeSeguent(Node pNode) {
		// Add node
		String strClau = pNode.getDades().keys().nextElement();	// Nom de la linia
		if (getNodesSeguents() == null)
			setNodesSeguents(new Hashtable<String, Node>());
		getNodesSeguents().put(strClau, pNode);		
	}
	public void addNode(Node pNode) {
		// Add node
		Node node = null;
		String strClau = pNode.getDades().keys().nextElement();	// Nom de la linia
		if (getNodesSeguents() == null)
			// Primer Node
			node = this;
		else
			// Nodes seg�ents
			//node = buscarNodeDarrer(this, strClau);
			node = buscarNodeDarrer(pNode);
		//node.addNodeSeguent(pNode);
	}	
	//private Node buscarNodeDarrer(Node pNode, String pClau) {
	private void buscarNodeDarrer(Node pNode) {
		while (getDades().keys().hasMoreElements()) {
			String pClau = getDades().keys().nextElement();
			Node nNode = this;
			while (nNode != null &&
				nNode.getNodesSeguents() != null &&
				nNode.getNodesSeguents().get(pClau) != null) {
				nNode = nNode.getNodesSeguents().get(pClau);
			}
			nNode.setNodesSeguents(new Hashtable<String, Node>());
			getNodesSeguents().put(pClau, pNode);	
		}
	}
}
