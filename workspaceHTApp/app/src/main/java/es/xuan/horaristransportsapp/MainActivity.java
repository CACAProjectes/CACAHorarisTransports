package es.xuan.horaristransportsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import es.xuan.horaristransportsapp.gestor.FavoritsViewAdapter;
import es.xuan.horaristransportsapp.gestor.GestorHorarisTransports;
import es.xuan.horaristransportsapp.gestor.Repositori;
import es.xuan.horaristransportsapp.gestor.Temporitzador;
import es.xuan.horaristransportsapp.model.Favorit;
import es.xuan.horaristransportsapp.model.HorarisTransports;
import es.xuan.horaristransportsapp.model.Linia;
import es.xuan.horaristransportsapp.model.Parada;
import es.xuan.horaristransportsapp.utils.Serializar;
import es.xuan.horaristransportsapp.utils.Utils;

public class MainActivity extends AppCompatActivity {

    private static final boolean CTE_REPOSITORI_LECTURA = false;
    private HorarisTransports mHorarisTrans = null;
    //  Pantalla
    private TextView mTvDataAvui = null;
    private ListView mLvLinies = null;
    private ListView mLvFavorits = null;
    private TextView mLvLiniesElement = null;
    //  Dades
    private String CTE_KEY_REPOSITORI_SP = "REPOSITORI_SP";
    private String CTE_KEY_HORARIS_TRANSPORTS_SP = "HORARIS_TRANSPORTS_SP";
    private String CTE_SEPARADOR = "-";
    private SharedPreferences mSpRepositori = null;
    private SharedPreferences mSpHorarisTransports = null;
    //
    private static final long CTE_VIBRATION_MS = 50;
    private int CTE_ACTUALITZA = 1;
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        inicialitzarDadesGlobals();
        //
        inicialitzarElementsPantalla();
        //
        iniXarxesSocials();
        //
        recuperarDades();
        //
        inicialitzarTimer();
        //
        actualitzarHorarisFixes();
        //////////////////////////////////////////////////////7
        // Només s'ecuta una vegada per proves amb FAVORITS
        mHorarisTrans.setFavorits(testFavorits());
        //guardarHorarisTransports();
        //////////////////////////////////////////////////////7
    }

    private void recuperarDades() {
        //
        recuperarRepositori();
        //
        recuperarHorarisTransports();
        //
    }

    private void guardarHorarisTransports() {
        try {
            String str = Serializar.serializar(mHorarisTrans);
            Utils.putValorSP(mSpHorarisTransports, CTE_KEY_HORARIS_TRANSPORTS_SP, str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void recuperarHorarisTransports() {
        String str = Utils.getValorSP(mSpHorarisTransports, CTE_KEY_HORARIS_TRANSPORTS_SP);
        try {
            if (str != null && !str.equals(""))
                mHorarisTrans = (HorarisTransports) Serializar.desSerializar(str);
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    private void guardarRepositori() {
        try {
            String str = Serializar.serializar(Repositori.getDades());
            Utils.putValorSP(mSpRepositori, CTE_KEY_REPOSITORI_SP, str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void recuperarRepositori() {
        String str = Utils.getValorSP(mSpRepositori, CTE_KEY_REPOSITORI_SP);
        try {
            if (str != null && !str.equals(""))
                Repositori.setDades((Hashtable<String, ArrayList<String>>) Serializar.desSerializar(str));
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    private void inicialitzarDadesGlobals() {
        //  SP
        if (mSpRepositori == null)
            mSpRepositori = getSharedPreferences(CTE_KEY_REPOSITORI_SP, Context.MODE_PRIVATE);
        if (mSpHorarisTransports == null)
            mSpHorarisTransports = getSharedPreferences(CTE_KEY_HORARIS_TRANSPORTS_SP, Context.MODE_PRIVATE);
        //  Globals
        if (mHorarisTrans == null)
            mHorarisTrans = GestorHorarisTransports.getInstance();

    }

    private void inicialitzarElementsPantalla() {
        mTvDataAvui = (TextView)findViewById(R.id.tvDataAvui);
        //
        mLvLinies = (ListView)findViewById(R.id.listView);
        mLvFavorits = (ListView)findViewById(R.id.listViewFav);
    }

    public void actualitzarHoraris(Message pMessage) {
        if (pMessage.what == CTE_ACTUALITZA) {
            actualitzarDadesTemporals();
            actualitzarPantalla();
        }
    }
    public void actualitzarHorarisFixes() {
        actualitzarDadesFixes();
        actualitzarPantallaFixes();
    }

    private void actualitzarPantallaFixes() {
        //  Mostrar en pantalla les Linies
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                //android.R.layout.simple_list_item_1, android.R.id.text1, parserLinies2Strings(mHorarisTrans.getLinies()));
                R.layout.llistalinieselement, R.id.tvLlistaLiniaElement, parserLinies2Strings(mHorarisTrans.getLinies()));
        mLvLinies.setAdapter(adapter);
        mLvLinies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.d("actualitzarDadesFixes", "Linia: " + adapter.getItem(position));
            }
        });
    }

    private void actualitzarDadesTemporals() {
        //  Data d'avui
        calcularTempsAvui();
        //  Actualitzar horaris favorits
        calcularTempsFavorits();
    }

    private void calcularTempsAvui() {
        mHorarisTrans.setAvui(Calendar.getInstance());
    }

    private void actualitzarDadesFixes() {
        /*
            Linies principals
         */
        mHorarisTrans.setLinies(obtenirLiniesGeneral());
    }

    private ArrayList<String> parserLinies2Strings(ArrayList<Linia> pLinies) {
        ArrayList<String> liniesStr = new ArrayList<>();
        for (Linia linia : pLinies) {
            liniesStr.add(Repositori.CTE_REPOSITORI_LINIA + linia.getIdLinia() +
                    " " + Repositori.CTE_SEPARADOR_TEXT_KEY + " " + linia.getNomLinia());
        }
        return liniesStr;
    }


    private ArrayList<Linia> obtenirLiniesGeneral() {
        //
        ArrayList<Linia> liniesRes = null;
        // obtenir linies del Repositori
        ArrayList<String> liniesStr = Repositori.getDades().get(Repositori.CTE_REPOSITORI_LINIA);
        if (liniesStr != null && liniesStr.size() > 0) {
            Log.d("obtenirLiniesGeneral","Repositori");
            liniesRes = Repositori.parserString2Linies(liniesStr);
        }
        else {
            // obtenir linies de la Web
            Log.d("obtenirLiniesGeneral","Web");
            ArrayList<Linia> liniesLinia = GestorHorarisTransports.obtenirLinies();
            // Si n'hi ha dades es guarda al Repositori
            if (liniesLinia != null && liniesLinia.size() > 0) {
                Log.d("obtenirLiniesGeneral","Guardar Web TO Repositori");
                Repositori.putDades(Repositori.CTE_REPOSITORI_LINIA, Repositori.parserLinies2String(liniesLinia));
                guardarRepositori();
            }
        }
        return liniesRes;
    }

    private void actualitzarPantalla() {
        // Data i hora actual
        String str = Utils.formatDataComplerta(mHorarisTrans.getAvui(), mHorarisTrans.getIdioma());
        mTvDataAvui.setText(str);
        //  Mostrar en pantalla els horaris de favorits
        FavoritsViewAdapter favoritsArrayAdapter = new FavoritsViewAdapter(this, mHorarisTrans.getFavorits());
        // create the instance of the ListView to set the numbersViewAdapter
        ListView favoritsListView = findViewById(R.id.listViewFav);
        // set the numbersViewAdapter for ListView
        favoritsListView.setAdapter(favoritsArrayAdapter);
    }

    private void inicialitzarTimer() {
        Temporitzador temporitzador = new Temporitzador(this);
        temporitzador.inicialitzarTimerSeg();
    }
    private void iniXarxesSocials() {
        final Vibrator vibr = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        // RubíBUS
        ImageView button1 = (ImageView)findViewById(R.id.ivLogoXS);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Utils.vibrar(vibr, CTE_VIBRATION_MS);
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW", Uri.parse(getString(R.string.urlRUBIBUS)));
                startActivity(viewIntent);
            }
        });
        // Enviar email - Gmail
        ImageView button2 = (ImageView)findViewById(R.id.ivGmail);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Utils.vibrar(vibr, CTE_VIBRATION_MS);
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW", Uri.parse(getString(R.string.urlGmail)));
                startActivity(viewIntent);
            }
        });
        // Facebook
        ImageView button3 = (ImageView)findViewById(R.id.ivFacebook);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Utils.vibrar(vibr, CTE_VIBRATION_MS);
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW", Uri.parse(getString(R.string.urlFacebook)));
                startActivity(viewIntent);
            }
        });
        // Twitter
        ImageView button4 = (ImageView)findViewById(R.id.ivTwitter);
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Utils.vibrar(vibr, CTE_VIBRATION_MS);
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW", Uri.parse(getString(R.string.urlTwitter)));
                startActivity(viewIntent);
            }
        });
        // Instagram
        ImageView button5 = (ImageView)findViewById(R.id.ivInstagram);
        button5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Utils.vibrar(vibr, CTE_VIBRATION_MS);
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW", Uri.parse(getString(R.string.urlInstagram)));
                startActivity(viewIntent);
            }
        });
    }

    private ArrayList<Favorit> testFavorits() {
        /*
            Només per fer proves amb FAVORITS
         */
        ArrayList<Favorit> arrFavorits = new ArrayList<>();
        //  L5 - Estació RubÍ+D-Can Rosés - Països Catalans -> Can Rosés
        Favorit favorit = new Favorit();
        Linia linia = new Linia(5, 8, "Estació RubÍ+D-Can Rosés");
        favorit.setLinia(linia);
        Parada paradaOrigen = new Parada(158, 1, "Països Catalans");
        paradaOrigen.setHora("21:34");
        paradaOrigen.setTempsEspera1(3);
        paradaOrigen.setTempsEspera2(24);
        favorit.setParadaOrigen(paradaOrigen);
        Parada paradaDesti = new Parada(175, 2, "Can Rosés");
        paradaDesti.setHora("21:34");
        paradaDesti.setTempsEspera1(3);
        paradaDesti.setTempsEspera2(24);
        favorit.setParadaDesti(paradaDesti);
        //
        arrFavorits.add(favorit);
        //  L4 - Can Rosés-Estació RubÍ+D - Can Rosés -> Països Catalans
        favorit = new Favorit();
        linia = new Linia(4, 6, "Can Rosés-Estació RubÍ+D");
        favorit.setLinia(linia);
        paradaOrigen = new Parada(116, 1, "Can Rosés");
        paradaOrigen.setHora("21:34");
        paradaOrigen.setTempsEspera1(3);
        paradaOrigen.setTempsEspera2(24);
        favorit.setParadaOrigen(paradaOrigen);
        paradaDesti = new Parada(131, 2, "Països Catalans");
        paradaDesti.setHora("21:34");
        paradaDesti.setTempsEspera1(3);
        paradaDesti.setTempsEspera2(24);
        favorit.setParadaDesti(paradaDesti);
        //
        arrFavorits.add(favorit);
        //
        return arrFavorits;
    }

    private void calcularTempsFavorits() {
        /*
            TODO: recalcular els temps d'espera
         */
        String horaAvui = Utils.formatDataHora(mHorarisTrans.getAvui());
        for (Favorit favorit : mHorarisTrans.getFavorits()) {
            // ORIGEN
            ArrayList<String> hores = obtenirTempsFavorits(favorit, favorit.getParadaOrigen().getIdParada());
            //
            Parada paradaOrigen = favorit.getParadaOrigen();
            paradaOrigen.setHora(Utils.obtenirHoraPropera(hores, horaAvui, null));
            paradaOrigen.setTempsEspera1(3);
            paradaOrigen.setTempsEspera2(24);
            // DESTÍ
            hores = obtenirTempsFavorits(favorit, favorit.getParadaDesti().getIdParada());
            //
            Parada paradaDesti = favorit.getParadaDesti();
            paradaDesti.setHora(Utils.obtenirHoraPropera(hores, horaAvui, paradaOrigen.getHora()));
            paradaDesti.setTempsEspera1(3);
            paradaDesti.setTempsEspera2(24);
        }
    }

    private ArrayList<String> obtenirTempsFavorits(Favorit pFavorit, int idParada) {
        //
        ArrayList<String> horesRes = null;
        // obtenir linies del Repositori
        /*
            Obtenir hores (LINIA-SENTIT-JORNADA-PARADA)
            "4-5-1-368-06:33"
         */
        String strKey = "" + pFavorit.getLinia().getIdLinia() + CTE_SEPARADOR +
                pFavorit.getLinia().getIdSentit() + CTE_SEPARADOR +
                mHorarisTrans.getJornada() + CTE_SEPARADOR +
                idParada;
        horesRes = Repositori.getDades().get(strKey);
        if (CTE_REPOSITORI_LECTURA && horesRes != null && horesRes.size() > 0) {
            Log.d("obtenirTempsFavorits","Repositori");
        }
        else {
            // obtenir linies de la Web
            Log.d("obtenirTempsFavorits","Web");
            horesRes = GestorHorarisTransports.obtenirHoresParada(pFavorit.getLinia().getIdLinia(),
                    pFavorit.getLinia().getIdSentit(),
                    mHorarisTrans.getJornada(),
                    idParada);
            // Si n'hi ha dades es guarda al Repositori
            if (horesRes != null && horesRes.size() > 0) {
                Log.d("obtenirTempsFavorits","Guardar Web TO Repositori");
                Repositori.putDades(strKey, horesRes);
                guardarRepositori();
            }
        }
        return horesRes;
    }
}