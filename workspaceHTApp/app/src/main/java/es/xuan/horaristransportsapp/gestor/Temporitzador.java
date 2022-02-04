package es.xuan.horaristransportsapp.gestor;

import android.os.Handler;
import android.os.Message;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import es.xuan.horaristransportsapp.MainActivity;

public class Temporitzador implements Serializable {
    private static final long serialVersionUID = 1L;

    private MainActivity mActivity = null;
    private int CTE_SECOND_MINUT = 60;
    private long CTE_DELAY = 1000L;
    private long CTE_PERIOD = 1000L;
    private int CTE_ACTUALITZA = 1;
    private int CTE_NO_ACTUALITZA = 0;

    private Handler updateLabel = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
            mActivity.actualitzarHoraris(msg);
        }
    };

    public Temporitzador(MainActivity pActivity) {
        mActivity = pActivity;
    }

    public void inicialitzarTimerSeg() {
        SimpleDateFormat formateador = new SimpleDateFormat("ss");
        //mMinut = Integer.parseInt(formateador.format(new Date()));
        //
        updateLabel.sendEmptyMessage(CTE_ACTUALITZA);    // Actualitza la primera vegada dels SEGONS
        //
        TimerTask repeatedTask = new TimerTask() {
            public void run() {
                //actualitzarHoraris();
                updateLabel.sendEmptyMessage(CTE_NO_ACTUALITZA);    // Durant els segons, NO actualitza
                //
                int segonsAux = Integer.parseInt(formateador.format(new Date()));
                if (segonsAux == 0) {
                    this.cancel();
                    inicialitzarTimerMin();
                }
            }
        };
        Timer timer = new Timer("TimerSeg");
        // Per segon
        timer.scheduleAtFixedRate(repeatedTask, CTE_DELAY, CTE_PERIOD);
    }
    private void inicialitzarTimerMin() {
        TimerTask repeatedTask = new TimerTask() {
            public void run() {
                //actualitzarHoraris();
                updateLabel.sendEmptyMessage(CTE_ACTUALITZA);    // Durant els minuts, actualitza sempre
            }
        };
        Timer timer = new Timer("TimerMin");
        long period = CTE_SECOND_MINUT * CTE_PERIOD;
        // Per minut
        timer.scheduleAtFixedRate(repeatedTask, CTE_DELAY, period);
    }
}
