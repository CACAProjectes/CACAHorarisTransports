package es.xuan.horaristransportsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import es.xuan.horaristransportsapp.gestor.FavoritsViewAdapter;
import es.xuan.horaristransportsapp.gestor.GestorHorarisTransports;
import es.xuan.horaristransportsapp.gestor.LiniesViewAdapter;
import es.xuan.horaristransportsapp.gestor.ParadesViewAdapter;
import es.xuan.horaristransportsapp.gestor.Repositori;
import es.xuan.horaristransportsapp.gestor.Temporitzador;
import es.xuan.horaristransportsapp.model.Favorit;
import es.xuan.horaristransportsapp.model.HorarisTransports;
import es.xuan.horaristransportsapp.model.Linia;
import es.xuan.horaristransportsapp.model.Parada;
import es.xuan.horaristransportsapp.model.TempsEspera;
import es.xuan.horaristransportsapp.utils.Serializar;
import es.xuan.horaristransportsapp.utils.Utils;

public class MainActivity extends AppCompatActivity {

    private static final boolean CTE_REPOSITORI_LECTURA = false;
    private HorarisTransports mHorarisTrans = null;
    //  Pantalla
    private TextView mTvDataAvui = null;
    private ListView mLvLinies = null;
    private ListView mLvFavorits = null;
    private ListView mLvLiniaParadesNoms = null;
    private ListView mListViewFav = null;
    private ImageView mIvFavoritAdd = null;
    private Linia mLiniaAnt = null;
    private Parada mParadaAnt = null;
    private Vibrator mVibr = null;
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
        //mHorarisTrans.setFavorits(testFavorits());
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
        mVibr = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        //
        mLvLinies = (ListView)findViewById(R.id.listViewLinies);
        mLvFavorits = (ListView)findViewById(R.id.listViewFav);
        mLvLiniaParadesNoms = (ListView)findViewById(R.id.listViewLiniaNoms);
        mIvFavoritAdd = (ImageView)findViewById(R.id.ivFavoritAdd);
        mIvFavoritAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Utils.vibrar(mVibr, CTE_VIBRATION_MS);
                for (Parada parada : mHorarisTrans.getParades()) {
                    if (parada.getSelected() != 0 &&
                        parada.getSelected() != 'F') {
                        //
                        Linia linia = null;
                        for (Linia liniaAux : mHorarisTrans.getLinies()) {
                            if (liniaAux.isSelected()) {
                                linia = liniaAux;
                                break;
                            }
                        }
                        //
                        Favorit favorit = new Favorit(linia, parada, null);
                        mHorarisTrans.getFavorits().add(favorit);
                        //
                        guardarHorarisTransports();
                        calcularTempsFavorits();
                        actualitzarPantalla();
                    }
                }
            }
        });
        mListViewFav = (ListView)findViewById(R.id.listViewFav);
        mListViewFav.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.d("Llista de favorits per esborrar", "Favorits: " + position);
                // Es comenta per evitar la doble vibració
                //Utils.vibrar(mVibr, CTE_VIBRATION_MS);
                //
                mostrarAlerta(position);
                //
                return true;
            }
        });

    }

    private void mostrarAlerta(int pPosition) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //Setting message manually and performing action on button click
        builder.setMessage(R.string.missatgeEsborrarFavorit)
                .setTitle(R.string.missateEsborrarFavoritTitle)
                .setCancelable(false)
                .setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Utils.vibrar(mVibr, CTE_VIBRATION_MS);
                        mHorarisTrans.getFavorits().remove(pPosition);
                        //
                        guardarHorarisTransports();
                        calcularTempsFavorits();
                        actualitzarPantalla();
                        //
                        dialog.cancel();
                        Toast.makeText(getApplicationContext(),R.string.missatgeEsborrarFavoritOk,
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Utils.vibrar(mVibr, CTE_VIBRATION_MS);
                        dialog.cancel();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //
        alert.show();
    }

    private void inicialitzarTimer() {
        Temporitzador temporitzador = new Temporitzador(this);
        temporitzador.inicialitzarTimerSeg();
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
        LiniesViewAdapter liniesArrayAdapter = new LiniesViewAdapter(this, mHorarisTrans.getLinies());
        // set the numbersViewAdapter for ListView
        mLvLinies.setAdapter(liniesArrayAdapter);
        mLvLinies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.d("actualitzarDadesFixes", "Linia: " + liniesArrayAdapter.getItem(position));
                //
                Utils.vibrar(mVibr, CTE_VIBRATION_MS);
                //
                ArrayList<Linia> arr = (ArrayList<Linia>) mHorarisTrans.getLinies().clone();
                if (mLiniaAnt != null)
                    mLiniaAnt.setSelected(false);
                //
                Linia linia = (Linia)arr.get(position).clone();
                linia.setSelected(true);
                arr.set(position, linia);
                //
                mLiniaAnt = linia;
                //
                liniesArrayAdapter.clear();
                liniesArrayAdapter.addAll(arr);
                //
                actualitzarPantallaLiniaNoms(liniesArrayAdapter.getItem(position).getIdLinia(),
                        liniesArrayAdapter.getItem(position).getIdSentit());
            }
        });
    }

    private void actualitzarPantallaLiniaNoms(int idLinia, int idSentit) {
        //  Mostrar en pantalla les parades
        mHorarisTrans.setParades(obtenirLiniaParades(idLinia, idSentit));
        ParadesViewAdapter paradesArrayAdapter = new ParadesViewAdapter(this, mHorarisTrans.getParades());
        // set the numbersViewAdapter for ListView
        mLvLiniaParadesNoms.setAdapter(paradesArrayAdapter);
        mLvLiniaParadesNoms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.d("actualitzarDadesFixes", "Linia: " + paradesArrayAdapter.getItem(position));
                //
                Utils.vibrar(mVibr, CTE_VIBRATION_MS);
                //
                ArrayList<Parada> arr = (ArrayList<Parada>) mHorarisTrans.getParades().clone();
                if (mParadaAnt != null)
                    mParadaAnt.setSelected('F');
                //
                Parada parada = (Parada)arr.get(position).clone();
                parada.setSelected('O');
                arr.set(position, parada);
                //
                mParadaAnt = parada;
                //
                paradesArrayAdapter.clear();
                paradesArrayAdapter.addAll(arr);
                //
                mostrarHoraParada(parada);
            }
        });
        mLvLiniaParadesNoms.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.d("actualitzarDadesFixes", "Linia: " + paradesArrayAdapter.getItem(position));
                //
                Utils.vibrar(mVibr, CTE_VIBRATION_MS);
                //
                ArrayList<Parada> arr = (ArrayList<Parada>) mHorarisTrans.getParades().clone();
                if (mParadaAnt != null)
                    mParadaAnt.setSelected('F');
                //
                Parada parada = (Parada)arr.get(position).clone();
                parada.setSelected('D');
                arr.set(position, parada);
                //
                mParadaAnt = parada;
                //
                paradesArrayAdapter.clear();
                paradesArrayAdapter.addAll(arr);
                // No debería mostrar la hora de salida, ya que depende del Origen
                //mostrarHoraParada(parada);
                //
                return true;
            }
        });
    }

    void mostrarMissate(String pMissatge) {
        Toast.makeText(getApplicationContext(), pMissatge, Toast.LENGTH_LONG).show();
    }

    private void mostrarHoraParada(Parada parada) {
        ArrayList<Linia> arr = (ArrayList<Linia>) mHorarisTrans.getLinies();
        Linia liniaAux = null;
        for(Linia linia : arr) {
            if (linia.isSelected()) {
                liniaAux = linia;
                break;
            }
        }
        ArrayList<String> hores = obtenirLiniaHores(
                liniaAux.getIdLinia(),
                liniaAux.getIdSentit(),
                mHorarisTrans.getJornada(),
                parada.getIdParada());
        String horaAvui = Utils.formatDataHora(mHorarisTrans.getAvui());
        TempsEspera tempsExpera = Utils.obtenirHoraPropera(hores, horaAvui, null);
        //
        if (tempsExpera.getTempsEsperaAnt() >= -10)
            mostrarMissate(liniaAux.getNomLinia() + System.lineSeparator() +
                    parada.getNomParada() + ", " +
                    "[" + tempsExpera.getTempsEsperaAnt() + "m] " + tempsExpera.getHora() + " [" + tempsExpera.getTempsEspera1() + "m]");
        else
            mostrarMissate(liniaAux.getNomLinia() + System.lineSeparator() +
                    parada.getNomParada() + ", " +
                    "" + tempsExpera.getHora() + " [" + tempsExpera.getTempsEspera1() + "m]");
    }

    private void actualitzarDadesTemporals() {
        //  Data d'avui
        calcularTempsAvui();
        //
        calcularTempsFavorits();
    }

    private void actualitzarDadesFixes() {
        /*
            Linies principals
         */
        mHorarisTrans.setLinies(obtenirLiniesGeneral());
    }

    private void actualitzarPantalla() {
        // Data i hora actual
        String str = Utils.formatDataComplerta(mHorarisTrans.getAvui(), mHorarisTrans.getIdioma());
        mTvDataAvui.setText(str);
        if (mHorarisTrans != null &&
                mHorarisTrans.getFavorits() != null &&
                mHorarisTrans.getFavorits().size() > 0) {
            //  Mostrar en pantalla els horaris de favorits
            FavoritsViewAdapter favoritsArrayAdapter = new FavoritsViewAdapter(this, mHorarisTrans.getFavorits());
            // create the instance of the ListView to set the numbersViewAdapter
            //ListView favoritsListView = findViewById(R.id.listViewFav);
            // set the numbersViewAdapter for ListView
            //favoritsListView.setAdapter(favoritsArrayAdapter);
            mLvFavorits.setAdapter(favoritsArrayAdapter);
        }
    }

    private void calcularTempsAvui() {
        mHorarisTrans.setAvui(Calendar.getInstance());
    }

    private void calcularTempsFavorits() {
        String horaAvui = Utils.formatDataHora(mHorarisTrans.getAvui());
        if (mHorarisTrans != null &&
                mHorarisTrans.getFavorits() != null &&
                mHorarisTrans.getFavorits().size() > 0) {
            for (Favorit favorit : mHorarisTrans.getFavorits()) {
                // ORIGEN
                ArrayList<String> hores = obtenirLiniaHores(
                        favorit.getLinia().getIdLinia(),
                        favorit.getLinia().getIdSentit(),
                        mHorarisTrans.getJornada(),
                        favorit.getParadaOrigen().getIdParada());
                //
                Parada paradaOrigen = favorit.getParadaOrigen();
                paradaOrigen.setTempsEspera(Utils.obtenirHoraPropera(hores, horaAvui, null));
                // DESTÍ
                if (favorit.getParadaDesti() != null) {
                    hores = obtenirLiniaHores(
                            favorit.getLinia().getIdLinia(),
                            favorit.getLinia().getIdSentit(),
                            mHorarisTrans.getJornada(),
                            favorit.getParadaDesti().getIdParada());
                    Parada paradaDesti = favorit.getParadaDesti();
                    paradaDesti.setTempsEspera(Utils.obtenirHoraPropera(hores, horaAvui, paradaOrigen.getTempsEspera().getHora()));
                }
            }
        }
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
        paradaOrigen.setTempsEspera(new TempsEspera("00:00",0,0,0));
        favorit.setParadaOrigen(paradaOrigen);
        Parada paradaDesti = new Parada(175, 2, "Can Rosés");
        paradaDesti.setTempsEspera(new TempsEspera("00:00",0,0,0));
        favorit.setParadaDesti(paradaDesti);
        //
        arrFavorits.add(favorit);
        //  L4 - Can Rosés-Estació RubÍ+D - Can Rosés -> Països Catalans
        favorit = new Favorit();
        linia = new Linia(4, 6, "Can Rosés-Estació RubÍ+D");
        favorit.setLinia(linia);
        paradaOrigen = new Parada(116, 1, "Can Rosés");
        paradaOrigen.setTempsEspera(new TempsEspera("00:00",0,0,0));
        favorit.setParadaOrigen(paradaOrigen);
        paradaDesti = new Parada(131, 2, "Països Catalans");
        paradaDesti.setTempsEspera(new TempsEspera("00:00",0,0,0));
        favorit.setParadaDesti(paradaDesti);
        //
        arrFavorits.add(favorit);
        //
        return arrFavorits;
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

    private ArrayList<String> obtenirLiniaHores(int idLinia,
                                                int idSentit,
                                                int idJornada,
                                                int idParada) {
        //
        ArrayList<String> horesRes = null;
        // obtenir hores del Repositori
        /*
            Obtenir hores (LINIA-SENTIT-JORNADA-PARADA)
            "4-5-1-368-06:33"
         */
        String strKey = "" + idLinia + CTE_SEPARADOR +
                idSentit + CTE_SEPARADOR +
                idJornada + CTE_SEPARADOR +
                idParada;
        horesRes = Repositori.getDades().get(strKey);
        if (horesRes != null && horesRes.size() > 0) {
            Log.d("obtenirLiniaHores","Repositori");
        }
        else {
            // obtenir hores de la Web
            Log.d("obtenirLiniaHores","Web");
            horesRes = GestorHorarisTransports.obtenirHoresParada(idLinia, idSentit, idJornada, idParada);
            // Si n'hi ha dades es guarda al Repositori
            if (horesRes != null && horesRes.size() > 0) {
                Log.d("obtenirLiniaHores","Guardar Web TO Repositori");
                Repositori.putDades(strKey, horesRes);
                guardarRepositori();
            }
        }
        return horesRes;
    }
    private ArrayList<Parada> obtenirLiniaParades(int idLinia, int idSentit) {
        //
        ArrayList<Parada> parades = null;
        // obtenir parades del Repositori
        /*
            Obtenir parades (LINIA-SENTIT)
            "4-5-Parada1"
         */
        String strKey = "" + idLinia + CTE_SEPARADOR +
                idSentit;
        ArrayList<String> paradesStr = Repositori.getDades().get(strKey);
        if (paradesStr != null && paradesStr.size() > 0) {
            Log.d("obtenirLiniaParades","Repositori");
            parades = new ArrayList<Parada>();
            int iOrdre = 0;
            for (String parada : paradesStr) {
                parades.add(new Parada(0, iOrdre, parada));
            }
        }
        else {
            // obtenir parades de la Web
            Log.d("obtenirLiniaParades","Web");
            parades = GestorHorarisTransports.obtenirParadesLinia(idLinia, idSentit);
            // Si n'hi ha dades es guarda al Repositori
            if (parades != null && parades.size() > 0) {
                Log.d("obtenirLiniaHores","Guardar Web TO Repositori");
                paradesStr = new ArrayList<String>();
                for (Parada parada : parades) {
                    paradesStr.add(parada.getNomParada());
                }
                Repositori.putDades(strKey, paradesStr);
                guardarRepositori();
            }
        }
        return parades;
    }

    private void iniXarxesSocials() {
        // RubíBUS
        ImageView button1 = (ImageView)findViewById(R.id.ivLogoXS);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Utils.vibrar(mVibr, CTE_VIBRATION_MS);
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW", Uri.parse(getString(R.string.urlRUBIBUS)));
                startActivity(viewIntent);
            }
        });
        // Enviar email - Gmail
        ImageView button2 = (ImageView)findViewById(R.id.ivGmail);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Utils.vibrar(mVibr, CTE_VIBRATION_MS);
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW", Uri.parse(getString(R.string.urlGmail)));
                startActivity(viewIntent);
            }
        });
        // Facebook
        ImageView button3 = (ImageView)findViewById(R.id.ivFacebook);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Utils.vibrar(mVibr, CTE_VIBRATION_MS);
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW", Uri.parse(getString(R.string.urlFacebook)));
                startActivity(viewIntent);
            }
        });
        // Twitter
        ImageView button4 = (ImageView)findViewById(R.id.ivTwitter);
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Utils.vibrar(mVibr, CTE_VIBRATION_MS);
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW", Uri.parse(getString(R.string.urlTwitter)));
                startActivity(viewIntent);
            }
        });
        // Instagram
        ImageView button5 = (ImageView)findViewById(R.id.ivInstagram);
        button5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Utils.vibrar(mVibr, CTE_VIBRATION_MS);
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW", Uri.parse(getString(R.string.urlInstagram)));
                startActivity(viewIntent);
            }
        });
    }

}