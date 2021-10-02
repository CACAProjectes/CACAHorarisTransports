package es.xuan.horaristransp.model;

import java.io.Serializable;

public class Node implements Serializable {
	private static final long serialVersionUID = 1L;

	protected String nom;
	protected Node seguent;
	
	public Node(String pNom) {
		nom = pNom;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public Node getSeguent() {
		return seguent;
	}
	public void setSeguent(Node seguent) {
		this.seguent = seguent;
	}
	public void addNodeFinal(Node pNode) {
		if (getSeguent() == null)
			setSeguent(pNode);
		else
			getSeguent().addNodeFinal(pNode);
	}
	@Override
	public String toString() {
		if (getSeguent() == null)
			return getNom();			
		return getNom() + " - " + getSeguent().toString();
	}
	public void addNode(Node pNode) {
		pNode.setSeguent(getSeguent());
		setSeguent(pNode);
	}
	public void addNode(Node pNode, long pPosicio) {
		if (pPosicio < 1 || getSeguent() == null)
			addNode(pNode);
		else
			getSeguent().addNode(pNode, pPosicio-1);
	}
	public void addNode(Node pNode, Node pNodeVar) {
		Node node = pNodeVar.getSeguent();
		pNodeVar.setSeguent(pNode);
		pNode.setSeguent(node);
	}
	public Node getNode(String pNom) {
		if (getNom().equalsIgnoreCase(pNom) || getSeguent() == null)
			return this;
		return getSeguent().getNode(pNom);
	}
	
	public Node getNode(int pIndex) {
		if (pIndex < 0 || getSeguent() == null)
			return this;
		return getSeguent().getNode(pIndex - 1);
	}
	public long length() {
		if (getSeguent() == null)
			return -1;
		else
			return getSeguent().length() + 1;
	}
}
