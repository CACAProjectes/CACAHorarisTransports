package es.xuan.horaristransp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
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
	
	public void addNode(String pClau, Node pNode) {
		if (getListaNodes().get(pClau) == null)
			getListaNodes().put(pClau, new ArrayList<Node>());
		//
		boolean trobat = false;
		for (Node node : getListaNodes().get(pClau)) {
			if (node.getNom().equalsIgnoreCase(pNode.getNom())) {	
				String strKeyAux = pNode.getHoraris().getHoraris().get(pClau).getHores().keys().nextElement();
				String[] strHoresAux = pNode.getHoraris().getHoraris().get(pClau).getHores().elements().nextElement();
				ArrayList<String> arr = new ArrayList<String>();
				arr.add(strKeyAux);
				Collections.addAll(arr, strHoresAux);
				String[] myArray = new String[arr.size()];
				node.getHoraris().add(pClau, arr.toArray(myArray));
				trobat = true;
				break;
			}			
		}
		if (!trobat)
			getListaNodes().get(pClau).add(pNode);
	}

	public int tamany(String pClau) {
		ArrayList<Node> arrNodes = getListaNodes().get(pClau);
		if (arrNodes == null)
			return 0;
		return arrNodes.size();
	}
}
