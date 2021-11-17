package es.xuan.horaristransportsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import org.apache.commons.lang3.StringUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import es.xuan.horaristransportsapp.files.GestioFitxers;
import es.xuan.horaristransportsapp.files.GestioWeb;
import es.xuan.horaristransportsapp.varis.Constants;
import es.xuan.horaristransportsapp.varis.Utils;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private final static String CTE_FILE_PATH = "/sdcard/Download/cacahoraristransports";
    private final static String CTE_MAP_PATH = "/sdcard/Download/cacahoraristransports/Fons L4-L5_Noms_2.png";
    private final static String CTE_NAME_FILE = "horaris.csv";
    //
    private final static String CTE_JORNADA = "#JORNADA#";
    // Horaris
    //private final static String CTE_Horaris_L5_Estacio_RubID_Ida = "https://rubibus.com/consultes.asp?proces=HorarioLinea&idioma=ca&idLinia=5&idSentido=8&IdJornada=[JORNADA]&NomParada=154";
    private final static String CTE_Horaris_L5_Paisos_Catalans_Ida = "https://rubibus.com/consultes.asp?proces=HorarioLinea&idioma=ca&idLinia=5&idSentido=8&IdJornada=#JORNADA#&NomParada=158";
    private final static String CTE_Horaris_L5_Can_Roses_Ida = "https://rubibus.com/consultes.asp?proces=HorarioLinea&idioma=ca&idLinia=5&idSentido=8&IdJornada=#JORNADA#&NomParada=175";
    private final static String CTE_Horaris_L4_Can_Roses_Vuelta = "https://rubibus.com/consultes.asp?proces=HorarioLinea&idioma=ca&idLinia=4&idSentido=6&IdJornada=#JORNADA#&NomParada=116";
    private final static String CTE_Horaris_L4_Paisos_Catalans_Vuelta = "https://rubibus.com/consultes.asp?proces=HorarioLinea&idioma=ca&idLinia=4&idSentido=6&IdJornada=#JORNADA#&NomParada=131";
    //  Temps d'espera
    private final static String CTE_Espera_L5_Paisos_Catalans_Ida = "https://rubibus.com/consultes.asp?proces=ConsultarProximos&idLinia=5&idSentido=8&IdJornada=1&NomParada=158";
    private final static String CTE_Espera_L4_Can_Roses_Vuelta = "https://rubibus.com/consultes.asp?proces=ConsultarProximos&idioma=ca&idLinia=4&idSentido=6&IdJornada=1&NomParada=116";
    //
    private static ArrayList<Horari> m_horaris = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        comprovarPermissos();
        //
        llegirHorarisFavorits();
        //
        omplirPlanol();
        //
        omplirLinies();
        //
        inicializarTimer();
        //
        configurarPlanol();
    }

    private void configurarPlanol() {
        ImageView image = findViewById(R.id.ivPlanol);
        Bitmap bMap = BitmapFactory.decodeFile(CTE_MAP_PATH);
        image.setImageBitmap(bMap);
    }

    private String obtenirJornada(Date pData) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(pData);
        if (esFestiu(cal))
            return "4";
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)     // Dissabte
            return "3";
        else if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)  // Diumenge
            return "4";
        // Lectiu
        return "1"; // Dia lectiu
    }

    private boolean esFestiu(Calendar pData) {
        String[] festius = getResources().getStringArray(R.array.festius);
        for(String dia : festius) {
            if (dia.equals(pData.get(Calendar.DAY_OF_MONTH) + "/" + (1 + pData.get(Calendar.MONTH))))
                return true;
        }
        return false;
    }

    private void omplirPlanol() {
        String jornada = obtenirJornada(new Date());
        //
        omplirJornada(jornada);
        //
        //omplirPlanolHora("L5", "Estació RubÍ+D", jornada, findViewById(R.id.tvESRUIDL5));
        String strHora = omplirPlanolHora("L5", "Països Catalans", jornada, findViewById(R.id.tvPACATL5), "00:00");
        strHora = omplirPlanolHora("L5", "Can Rosés", jornada, findViewById(R.id.tvCROSL5), strHora);
        strHora = omplirPlanolHora("L4", "Can Rosés", jornada, findViewById(R.id.tvCROSL4), "00:00");
        strHora = omplirPlanolHora("L4", "Països Catalans", jornada, findViewById(R.id.tvPACATL4), strHora);
        //omplirPlanolHora("L4", "Estació RubÍ+D", jornada, findViewById(R.id.tvESRUIDL4));
        ///////////////////////////////////////////////////////////////////
        // Temps d'espera
        String[] temps = omplirPlanolTempsEspera(CTE_Espera_L5_Paisos_Catalans_Ida, "L5", "Can Rosés", findViewById(R.id.tvPACATL5Prox), null);
        omplirPlanolTempsEspera(null, null, null, findViewById(R.id.tvProximFava1), temps[0]);
        omplirPlanolTempsEspera(null, null, null, findViewById(R.id.tvProximFava2), temps[1]);
        //
        temps = omplirPlanolTempsEspera(CTE_Espera_L4_Can_Roses_Vuelta, "L4", "Estació RubÍ+D", findViewById(R.id.tvCROSL4Prox), null);
        omplirPlanolTempsEspera(null, null, null, findViewById(R.id.tvProximFavb1), temps[0]);
        omplirPlanolTempsEspera(null, null, null, findViewById(R.id.tvProximFavb2), temps[1]);
    }

    private void omplirJornada(String pJornada) {
        TextView tvFeiner = (TextView)findViewById(R.id.tvFeiner);
        TextView tvDissabte = (TextView)findViewById(R.id.tvDissabte);
        TextView tvFestiu = (TextView)findViewById(R.id.tvFestiu);
        if (pJornada.equals("3")) {    // DISSABTE
            tvDissabte.setBackgroundColor(getColor(R.color.foscLight));
            tvFeiner.setBackgroundColor(getColor(R.color.fosc));
            tvFestiu.setBackgroundColor(getColor(R.color.fosc));
            //
            tvDissabte.setTextColor(getColor(R.color.black));
            tvFeiner.setTextColor(getColor(R.color.foscLight));
            tvFestiu.setTextColor(getColor(R.color.foscLight));
        }
        else if (pJornada.equals("4")) {    // FESTIU
            tvFestiu.setBackgroundColor(getColor(R.color.foscLight));
            tvDissabte.setBackgroundColor(getColor(R.color.fosc));
            tvFeiner.setBackgroundColor(getColor(R.color.fosc));
            //
            tvFestiu.setTextColor(getColor(R.color.black));
            tvDissabte.setTextColor(getColor(R.color.foscLight));
            tvFeiner.setTextColor(getColor(R.color.foscLight));
        }
        else {    // FEINER
            tvFeiner.setBackgroundColor(getColor(R.color.foscLight));
            tvDissabte.setBackgroundColor(getColor(R.color.fosc));
            tvFestiu.setBackgroundColor(getColor(R.color.fosc));
            //
            tvFeiner.setTextColor(getColor(R.color.black));
            tvDissabte.setTextColor(getColor(R.color.foscLight));
            tvFestiu.setTextColor(getColor(R.color.foscLight));
        }
    }

    private String[] omplirPlanolTempsEspera(String pUrl, String pLinia, String pDesti, TextView pTvTempsEsp, String pTemps) {
        String strTemps = pTemps;
        String[] temps = new String[]{"?","?"};
        if (strTemps == null) {
            temps = GestioWeb.obtenirTempsEspera(pUrl, pLinia, pDesti);
            strTemps = temps[0];
        }
        if (strTemps != null && !strTemps.equals("")) {
            pTvTempsEsp.setText(strTemps + "min");
            try {
                int iNum = Integer.parseInt(strTemps);
                if (iNum > 10) {
                    pTvTempsEsp.setBackgroundColor(getColor(R.color.semaforVerdFons));
                    pTvTempsEsp.setTextColor(getColor(R.color.semaforVerdText));
                } else if (iNum > 4) {
                    pTvTempsEsp.setBackgroundColor(getColor(R.color.semaforGrocFons));
                    pTvTempsEsp.setTextColor(getColor(R.color.semaforGrocText));
                } else {
                    pTvTempsEsp.setBackgroundColor(getColor(R.color.semaforVermellFons));
                    pTvTempsEsp.setTextColor(getColor(R.color.semaforVermellText));
                }
            } catch (Exception ex) {
                pTvTempsEsp.setText("?");
                pTvTempsEsp.setBackgroundColor(getColor(R.color.semaforVerdFons));
                pTvTempsEsp.setTextColor(getColor(R.color.semaforVerdText));
            }
        }
        return temps;
    }

    private String omplirPlanolHora(String pLinia, String pParada, String pJornada, TextView pTvHora, String pHora) {
        for(Horari horari : m_horaris) {
            if (horari.getLinia().equals(pLinia) &&
                    horari.getNomParada().equals(pParada) &&
                    horari.getJornada().equals(pJornada)) {
                String hora = buscarHora(horari.getHoraris(), new Date(), pHora);
                pTvHora.setText(hora);
                return hora;
            }
        }
        return "00:00";
    }

    private String buscarHora(String[] pHoraris, Date pData, String pHora) {
        String strData = Utils.data2StringHora(pData);
        for(String hora : pHoraris) {
            if (hora.length() == 5 && hora.compareTo(strData) > 0 && hora.compareTo(pHora) > 0)
                return hora;
        }
        return "00:00";
    }

    private void comprovarPermissos() {
        //
        if (!GestioFitxers.isReadStoragePermissionGranted(this))
            Log.e("comprovarPermissos","isReadStoragePermissionGranted KO");
    }
    private void llegirHorarisFavorits() {
        if (m_horaris != null && m_horaris.size() > 0)
            return;
        //
        String jornada = obtenirJornada(new Date());
        //
        m_horaris = new ArrayList<Horari>();
        String[] hores = GestioWeb.obtenirHoraris(CTE_Horaris_L5_Paisos_Catalans_Ida.replaceAll(CTE_JORNADA, jornada));
        Horari horari = new Horari();
        horari.setLinia("L5");   // L4, L5
        horari.setLiniaSentit("[A]"); // [A], [T]
        horari.setNomParada("Països Catalans");
        horari.setJornada(jornada);
        horari.setHoraris(hores);
        m_horaris.add(horari);
        //
        hores = GestioWeb.obtenirHoraris(CTE_Horaris_L5_Can_Roses_Ida.replaceAll(CTE_JORNADA, jornada));
        horari = new Horari();
        horari.setLinia("L5");   // L4, L5
        horari.setLiniaSentit("[A]"); // [A], [T]
        horari.setNomParada("Can Rosés");
        horari.setJornada(jornada);
        horari.setHoraris(hores);
        m_horaris.add(horari);
        //
        hores = GestioWeb.obtenirHoraris(CTE_Horaris_L4_Can_Roses_Vuelta.replaceAll(CTE_JORNADA, jornada));
        horari = new Horari();
        horari.setLinia("L4");   // L4, L5
        horari.setLiniaSentit("[A]"); // [A], [T]
        horari.setNomParada("Can Rosés");
        horari.setJornada(jornada);
        horari.setHoraris(hores);
        m_horaris.add(horari);
        //
        hores = GestioWeb.obtenirHoraris(CTE_Horaris_L4_Paisos_Catalans_Vuelta.replaceAll(CTE_JORNADA, jornada));
        horari = new Horari();
        horari.setLinia("L4");   // L4, L5
        horari.setLiniaSentit("[A]"); // [A], [T]
        horari.setNomParada("Països Catalans");
        horari.setJornada(jornada);
        horari.setHoraris(hores);
        m_horaris.add(horari);
        /*
        //
        ArrayList<String> arrLinies = GestioFitxers.llegirFile(CTE_FILE_PATH, CTE_NAME_FILE);
        //
        m_horaris = new ArrayList<Horari>();
        //
        for(String linia : arrLinies) {
            Horari horari = new Horari();
            String[] arrDades = linia.split(";");
            horari.setLinia(arrDades[0]);   // L4, L5
            horari.setLiniaSentit(arrDades[1].substring(0,3)); // [A], [T]
            horari.setNomParada(arrDades[1].substring(3));
            if (arrDades.length > 2) {
                String[] arrDadesHoraris = arrDades[2].split(",");  // L,06:30,06:52,07:14,07:38,08:02,08:26,08:50,09:14,09:38,10:02,10:26,10:50,11:14,11:38,12:02,12:26,12:50,13:14,13:38,14:02,14:26,14:50,15:14,15:38,16:02,16:26,16:50,17:14,17:38,18:02,18:26,18:50,19:14,19:38,20:00,20:22,20:44,21:06,21:28,21:47,22:06
                horari.setJornada(arrDadesHoraris[0]);
                horari.setHoraris(arrDadesHoraris);
            }
            m_horaris.add(horari);
        }
         */
        //
        Log.d("llegirHoraris","Nº registres: " + m_horaris.size());
    }

    private void omplirLinies() {
        ArrayList<String> arrLinies = obtenirLinies();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arrLinies);
        adapter.notifyDataSetChanged();
        //
        Spinner spLinies = (Spinner) findViewById(R.id.spLinies);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLinies.setAdapter(adapter);
        spLinies.setOnItemSelectedListener(this);
    }
    private void omplirParades(String pNomLinia) {
        ArrayList<String> arrParades = obtenirParades(pNomLinia);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arrParades);
        adapter.notifyDataSetChanged();
        //
        Spinner spParades = (Spinner) findViewById(R.id.spParades);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spParades.setAdapter(adapter);
        //spParades.setOnItemSelectedListener(this);
    }
    private void omplirJornades(String pNomLinia) {
        ArrayList<String> arrJornades = obtenirJornades(pNomLinia);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arrJornades);
        adapter.notifyDataSetChanged();
        //
        Spinner spJornades = (Spinner) findViewById(R.id.spJornades);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spJornades.setAdapter(adapter);
        //spJornades.setOnItemSelectedListener(this);
    }

    private ArrayList<String> obtenirJornades(String pNomLinia) {
        Hashtable<String, String> hshJornades = new Hashtable<String, String>();
        for(Horari horari : m_horaris) {
            if (horari.getJornada() != null) {
                int id = getResources().getIdentifier("jornada_" + horari.getJornada(), "string", getPackageName());
                hshJornades.put(getString(id), "");
            }
        }
        return new ArrayList<String>(hshJornades.keySet());
    }

    private ArrayList<String> obtenirParades(String pNomLinia) {
        String strParada = "";
        if (!pNomLinia.equals("")) {
            ArrayList<String> arrParades = new ArrayList<String>();
            for (Horari horari : m_horaris) {
                if (horari.getLinia().equals(pNomLinia) && !strParada.equals(horari.getLiniaSentit() + horari.getNomParada())) {
                    arrParades.add(horari.getLiniaSentit() + horari.getNomParada());
                    strParada = horari.getLiniaSentit() + horari.getNomParada();
                }
            }
            return arrParades;
        }
        return null;
    }

    private ArrayList<String> obtenirLinies() {
        Hashtable<String, String> hshLinies = new Hashtable<String, String>();
        for(Horari horari : m_horaris) {
            hshLinies.put(horari.getLinia(), "");
        }
        return new ArrayList<String>(hshLinies.keySet());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String liniaName = (String) parent.getItemAtPosition(position);
        //
        omplirParades(liniaName);
        //
        omplirJornades(liniaName);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void actualitzarTemps() {
        //
        omplirPlanol();
        //
        omplirDataHora();
        //
        //Log.d("actualizarTiempos","" + new Date());
    }

    private void omplirDataHora() {
        TextView tvData = findViewById(R.id.tvDataAvui);
        tvData.setText(StringUtils.capitalize(Utils.data2StringSetmana(new Date())));
    }

    private void inicializarTimer() {
        final Handler handler = new Handler();
        Runnable varRun;
        runOnUiThread(varRun = new Runnable() {
            @Override
            public void run() {
                actualitzarTemps();
                //
                handler.postDelayed(this, Constants.CTE_TIMER_PERIODO);
            }
        });
        handler.postDelayed(varRun, Constants.CTE_TIMER_PERIODO);
    }
}