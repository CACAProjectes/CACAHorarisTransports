package es.xuan.horaristransportsapp;

import java.io.Serializable;

public class Horari implements Serializable {
    private static final long serialVersionUID = 1L;

    private String linia;
    private String liniaSentit;
    private String nomParada;
    private String jornada;
    private String[] horaris;

    public String[] getHoraris() {
        return horaris;
    }

    public void setHoraris(String[] horaris) {
        this.horaris = horaris;
    }

    public String getLinia() {
        return linia;
    }

    public void setLinia(String linia) {
        this.linia = linia;
    }

    public String getLiniaSentit() {
        return liniaSentit;
    }

    public void setLiniaSentit(String liniaSentit) {
        this.liniaSentit = liniaSentit;
    }

    public String getNomParada() {
        return nomParada;
    }

    public void setNomParada(String nomParada) {
        this.nomParada = nomParada;
    }

    public String getJornada() {
        return jornada;
    }

    public void setJornada(String jornada) {
        this.jornada = jornada;
    }

}
