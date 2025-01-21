package com.rooted.model;

import java.util.Date;

public class Mensaje {

    protected int id;

    protected String type;

    protected String content;

    protected Date fecha;

    public Mensaje(int id, String type){
        this.id = id;
        this.type = type;
    }

    public int getId(){
        return id;
    }

    public String getType() {
        return type;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getContent() {
        return content;
    }
}
