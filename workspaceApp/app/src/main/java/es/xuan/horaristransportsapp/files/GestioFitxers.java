/**
 * 
 */
package es.xuan.horaristransportsapp.files;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author jcamposp
 *
 */
public class GestioFitxers implements Serializable {
    private static final long serialVersionUID = 1L;

    public static boolean isReadStoragePermissionGranted(Activity pActivity) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (pActivity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("","Permission is granted1");
                return true;
            } else {
                Log.v("","Permission is revoked1 -> granted1");
                ActivityCompat.requestPermissions(pActivity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("","Permission is granted1");
            return true;
        }
    }

    public static boolean isWriteStoragePermissionGranted(Activity pActivity) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (pActivity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("","Permission is granted2");
                return true;
            } else {

                Log.v("","Permission is revoked2 -> granted2");
                ActivityCompat.requestPermissions(pActivity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("","Permission is granted2");
            return true;
        }
    }
    public static ArrayList<String> llegirFile(String pathFile, String nameFile) {
        File dir = new File(pathFile);
        if(!dir.exists()) {
            dir.mkdir();
        }
        File file = new File(dir, nameFile);
        file.setWritable(true);
        file.setReadable(true);
        file.setExecutable(true);
        ArrayList<String> linies = new ArrayList<String>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "ISO-8859-1"));
            //Leer la primera linea, guardando en un String
            String texto = br.readLine();
            //Repetir mientras no se llegue al final del fichero
            while(texto != null)
            {
                linies.add(texto);
                //Leer la siguiente linea
                texto = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            Log.e("llegirFile", e.getMessage());
            e.printStackTrace();
        }
        return linies;
    }

}
