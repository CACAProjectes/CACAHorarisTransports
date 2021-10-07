/**
 * 
 */
package es.xuan.horaristransp.file;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * @author jcamposp
 *
 */
public class GestioFitxers {
	
	public static ArrayList<String> llegirFile(String pNomFile) {
		ArrayList<String> linies = new ArrayList<String>();
        //Declarar una variable BufferedReader
        BufferedReader br = null;
        try {
           //Crear un objeto BufferedReader al que se le pasa 
           //   un objeto FileReader con el nombre del fichero
           br = new BufferedReader(new InputStreamReader(
        	         new FileInputStream(pNomFile), "ISO-8859-1"));
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
}
