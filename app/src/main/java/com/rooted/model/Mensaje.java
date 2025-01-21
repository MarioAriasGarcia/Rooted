package com.rooted.model;

import java.util.Date;

public class Mensaje {
    protected int id;
    protected String type; // Tipo de mensaje (e.g., Reporte, Sugerencia)
    protected String subtype;
    protected String content; // Contenido del mensaje
    protected Date fecha; // Fecha de creación

    public Mensaje(int id, String type, String subtype, String content, Date fecha) {
        this.id = id;
        this.type = type;
        this.subtype = subtype;
        this.content = content;
        this.fecha = fecha;
    }

    public Mensaje(String type, String subtype, String content) {
        this.type = type;
        this.subtype = subtype;
        this.content = content;
        this.fecha = new Date(); // Fecha actual por defecto
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getSubtype(){
        return subtype;
    }

    public String getContent() {
        return content;
    }

    public Date getFecha() {
        return fecha;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
