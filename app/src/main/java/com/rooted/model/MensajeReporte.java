package com.rooted.model;

public class MensajeReporte extends Mensaje{

    private String tipoError;
    public MensajeReporte(int id, String type) {
        super(id, type);
    }

    private String getTipoError(){
        return tipoError;
    }
}
