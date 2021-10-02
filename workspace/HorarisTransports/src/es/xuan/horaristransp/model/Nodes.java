/**
 * 
 */
package es.xuan.horaristransp.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Serializable;
import java.util.ArrayList;

import es.xuan.horaristransp.utils.Constants;

/**
 * @author jcamposp
 *
 */
public class Nodes implements Serializable {
	private static final long serialVersionUID = 1L;

	private ArrayList<Node> nodes;

	public ArrayList<Node> getNodes() {
		return nodes;
	}

	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}
	
	public void addNode(Node pNode) {
		if (getNodes() == null)
			setNodes(new ArrayList<Node>());
		getNodes().add(pNode);
	}
	
	public void carregarNodes2File(String pNomFile) {
		ArrayList<String> linies = llegirFile(pNomFile);
		int iComptador = 0;
		String clauLinia = ""; 
		for(String linia : linies) {
			//addNode(convertLinia2Node(linia));
			// L4;Estació RubÍ+D;06:30;07:30;08:30;
			String[] dades = linia.split(Constants.CNT_SEPARADOR_LINIA);
			/*
			Node node = new Node();
			clauLinia = dades[0];	// Nom de la linia
			node.setNom(dades[1]);	// Nom del node			
			String[] arrHoraris = getHoraris(dades, 2);
			node.addDada(clauLinia, arrHoraris);
			//	Gestionar el node següent
			// TODO
			// Avança una posició a la cadena
			iComptador++;
			// Afegeix un NODE a la llista
			addNode(node);
			*/
		}		
	}
	
	private String[] getHoraris(String[] pDades, int pIndexIni) {
		String[] res = new String[pDades.length - pIndexIni]; 
		for (int i=pIndexIni;i<pDades.length;i++) {
			res[i-pIndexIni] = pDades[i];
		}
		return res;
	}

	private ArrayList<String> llegirFile(String pNomFile) {
		ArrayList<String> linies = new ArrayList<String>();
        //Declarar una variable BufferedReader
        BufferedReader br = null;
        try {
           //Crear un objeto BufferedReader al que se le pasa 
           //   un objeto FileReader con el nombre del fichero
           br = new BufferedReader(new FileReader(pNomFile));
           //Leer la primera línea, guardando en un String
           String texto = br.readLine();
           //Repetir mientras no se llegue al final del fichero
           while(texto != null)
           {
        	   linies.add(texto);
               //Leer la siguiente línea
               texto = br.readLine();
           }
        }
        catch (FileNotFoundException e) {
            System.err.println("Error: Fichero no encontrado");
            System.err.println(e.getMessage());
        }
        catch(Exception e) {
            System.err.println("Error de lectura del fichero");
            System.err.println(e.getMessage());
        }
        finally {
            try {
                if(br != null)
                    br.close();
            }
            catch (Exception e) {
                System.err.println("Error al cerrar el fichero");
                System.err.println(e.getMessage());
            }
        }
        return linies;
	}

	public String toString(String pLiniaNode) {
		String strRes = "";
		for (Node node : getNodes()) {
			/*
			if (node.getDades().get(pLiniaNode) != null) {
				strRes += node.getNom();
				strRes += Constants.CLAU_OBERTA + node.getCoordenada().getX() + Constants.CNT_SEPARADOR_COMA + node.getCoordenada().getY() + Constants.CLAU_TANCADA;
				if (node.getNodesSeguents() != null 
					&& node.getNodesSeguents().get(pLiniaNode) != null) {
					strRes += " -> ";
				}
			}
			*/
		}
		return strRes;
	}		
}
