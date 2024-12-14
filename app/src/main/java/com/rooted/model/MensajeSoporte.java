package com.rooted.model;

public class MensajeSoporte extends Mensaje{

    private String aspectosMejorables;
    public MensajeSoporte(int id, String type) {
        super(id, type);
    }

    private String getAspectosMejorables(){
        return aspectosMejorables;
    }

}
