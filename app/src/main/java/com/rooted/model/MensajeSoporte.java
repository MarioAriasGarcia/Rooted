package com.rooted.model;

public class MensajeSoporte extends Mensaje {
    private String aspectosMejorables; // Información sobre sugerencias/mejoras

    public MensajeSoporte(int id, String type, String subtype, String content) {
        super(id, subtype, type, content, null); // Null para fecha si no se pasa
        this.aspectosMejorables = subtype;
    }

    public MensajeSoporte(String type, String subtype, String content) {
        super(type, subtype, content);
        this.aspectosMejorables = subtype;
    }

    // Getter y Setter para aspectosMejorables
    public String getAspectosMejorables() {
        return aspectosMejorables;
    }

    public void setAspectosMejorables(String aspectosMejorables) {
        this.aspectosMejorables = aspectosMejorables;
    }
}
