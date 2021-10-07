package es.xuan.horaristransp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

public class Nodes implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Hashtable<String, ArrayList<Node>> listaNodes = null;

	public Nodes() {
		listaNodes = new Hashtable<String, ArrayList<Node>>();
	}

	/**
	 * @return the listaNodes
	 */
	public Hashtable<String, ArrayList<Node>> getListaNodes() {
		return listaNodes;
	}

	/**
	 * @param listaNodes the listaNodes to set
	 */
	public void setListaNodes(Hashtable<String, ArrayList<Node>> listaNodes) {
		this.listaNodes = listaNodes;
	}
	
	public void addNode(String pClau, Node pNode ) {
		if (getListaNodes().get(pClau) == null)
			getListaNodes().put(pClau, new ArrayList<Node>());
		getListaNodes().get(pClau).add(pNode);
	}

	public int tamany(String pClau) {
		ArrayList<Node> arrNodes = getListaNodes().get(pClau);
		if (arrNodes == null)
			return 0;
		return arrNodes.size();
	}
}
