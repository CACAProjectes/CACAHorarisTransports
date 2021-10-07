package es.xuan.horaristransp;

import es.xuan.horaristransp.model.Node;

public class ProvaNodes {

	public static void main(String[] args) {
		Node nodePrincipal = new Node("PP");
		Node node0 = new Node("P0");
		Node node1 = new Node("P1");
		Node node2 = new Node("P2");
		Node node3 = new Node("P3");
		Node node4 = new Node("P4");
		Node node5 = new Node("P5");
		Node node6 = new Node("P6");
		// Caso afegir a l'ultim	
		nodePrincipal.addNodeFinal(node1);
		nodePrincipal.addNodeFinal(node2);
		nodePrincipal.addNodeFinal(node3);
		nodePrincipal.addNodeFinal(node4);		
		System.out.println(nodePrincipal.toString());
		//
		nodePrincipal.addNode(node0);					// Afegir un node a la posició següent a la actual
		System.out.println(nodePrincipal.toString());
		//
		nodePrincipal.addNode(node5, 0);				// Afegir un node a la posició [0..n] - No sustituye el primero NODO
		System.out.println(nodePrincipal.toString());
		//
		nodePrincipal.addNode(node6, node1);			// Afegir un node(arg0) a la posició següent del NODE(arg1)
		System.out.println(nodePrincipal.toString());
		//
		Node node = nodePrincipal.getNode("P2");		// retorna el node amb el nom de entrada 
		System.out.println(node.toString());
		//
		node = nodePrincipal.getNode(0);				// retorna el node amb el ordre de entrada [0..n]
		System.out.println(node.toString());
		//
		long vTam = nodePrincipal.length();				// retorna el número de nodos del NODO PRINCIPAL
		System.out.println("" + vTam);
		//
		nodePrincipal.delNode(node6);			// Esborra un node(arg0) a la posició següent del NODE(arg1)
		System.out.println(nodePrincipal.toString());
	}	
}
