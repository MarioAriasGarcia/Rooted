package com.rooted.model;

public class MensajeReporte extends Mensaje {
    private String tipoError; // Tipo de error reportado

    public MensajeReporte(int id, String type, String subtype, String content, String tipoError) {
        super(id, subtype, type, content, null); // Null para fecha si no se pasa
        this.tipoError = subtype;
    }

    public MensajeReporte(String type, String subtype, String content) {
        super(type, subtype, content);
        this.tipoError = subtype;
    }

    // Getter y Setter para tipoError
    public String getTipoError() {
        return tipoError;
    }

    public void setTipoError(String tipoError) {
        this.tipoError = tipoError;
    }
}
