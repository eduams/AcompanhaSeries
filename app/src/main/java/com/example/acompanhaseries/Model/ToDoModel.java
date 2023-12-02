package com.example.acompanhaseries.Model;

public class ToDoModel extends TaskId {
    private String serie , diaSemana , plataforma , temporada , ultimoEp , due;
    private int status;

    public String getSerie() {
        return serie;
    }
    public String getPlataforma() { return plataforma;}
    public String getTemporada() { return temporada;}
    public String getUltimoEp() { return ultimoEp;}

    public void setUltimoEp(String ultimoEp) {
        this.ultimoEp = ultimoEp;
    }

    public String getDiaSemana() { return diaSemana;}


    public int getStatus() {
        return status;
    }
}
