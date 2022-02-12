package es.xuan.horaristransportsapp.model;

import java.io.Serializable;

public class TempsEspera  implements Serializable {
    private static final long serialVersionUID = 1L;

    private String hora;
    private int tempsEsperaAnt;
    private int tempsEspera1;
    private int tempsEspera2;

    public TempsEspera() {
    }

    public TempsEspera(String pHora, int pTempsAnterior, int pTempsSeg1, int pTempsSeg2) {
        setHora(pHora);
        setTempsEsperaAnt(pTempsAnterior);
        setTempsEspera1(pTempsSeg1);
        setTempsEspera2(pTempsSeg2);
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getTempsEsperaAnt() {
        return tempsEsperaAnt;
    }

    public void setTempsEsperaAnt(int tempsEsperaAnt) {
        this.tempsEsperaAnt = tempsEsperaAnt;
    }

    public int getTempsEspera1() {
        return tempsEspera1;
    }

    public void setTempsEspera1(int tempsEspera1) {
        this.tempsEspera1 = tempsEspera1;
    }

    public int getTempsEspera2() {
        return tempsEspera2;
    }

    public void setTempsEspera2(int tempsEspera2) {
        this.tempsEspera2 = tempsEspera2;
    }
}
