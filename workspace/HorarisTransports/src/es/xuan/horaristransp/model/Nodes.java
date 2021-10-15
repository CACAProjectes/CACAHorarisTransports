package es.xuan.horaristransp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

public class Nodes implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Hashtable<String, ArrayList<Node>> nodes = null;

	public Nodes() {
		setNodes(new Hashtable<String, ArrayList<Node>>());
	}


	
	public void addNode(String pClau, Node pNode) {
		if (getNodes().get(pClau) == null)
			getNodes().put(pClau, new ArrayList<Node>());
		//
		boolean trobat = false;
		for (Node node : getNodes().get(pClau)) {
			if (node.getNom().equalsIgnoreCase(pNode.getNom())) {	
				if (pNode.getHoraris().getHoraris().get(pClau) != null) {
					String strKeyAux = pNode.getHoraris().getHoraris().get(pClau).getHores().keys().nextElement();
					String[] strHoresAux = pNode.getHoraris().getHoraris().get(pClau).getHores().elements().nextElement();
					node.getHoraris().add(pClau, strKeyAux, strHoresAux);
				}
				else {
					String strKeyAux = pNode.getHoraris().getHoraris().keys().nextElement();
					node.getHoraris().getHoraris().put(strKeyAux, pNode.getHoraris().getHoraris().get(strKeyAux));
				}
				trobat = true;
				break;
			}			
		}
		if (!trobat)
			getNodes().get(pClau).add(pNode);
	}

	public int tamany(String pClau) {
		ArrayList<Node> arrNodes = getNodes().get(pClau);
		if (arrNodes == null)
			return 0;
		return arrNodes.size();
	}
	/**
	 * @return the nodes
	 */
	public Hashtable<String, ArrayList<Node>> getNodes() {
		return nodes;
	}
	/**
	 * @param nodes the nodes to set
	 */
	public void setNodes(Hashtable<String, ArrayList<Node>> nodes) {
		this.nodes = nodes;
	}
}
