package com.rooted.model.entities;

public class EntradasEnciclopedia {
    String nombre;
    String descripcion;
    String riego;
    String forma_plantar;
    String forma_recoger;

    public EntradasEnciclopedia(String nombre, String descripcion, String riego, String forma_plantar, String forma_recoger){
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.riego = riego;
        this.forma_plantar = forma_plantar;
        this.forma_recoger = forma_recoger;
    }

    public String getNombre(){
        return this.nombre;
    }

    public String toStringNombre(){
        StringBuilder sb = new StringBuilder();
        sb.append("Nombre: " + this.nombre + " ");
        return sb.toString();
    }
    public String toStringDescripcion(){
        StringBuilder sb = new StringBuilder();
        sb.append("Descripción: " + this.descripcion + " ");
        return sb.toString();
    }
    public String toStringRiego(){
        StringBuilder sb = new StringBuilder();
        sb.append("Riego: " + this.riego + " " );
        return sb.toString();
    }
    public String toStringFormaPlantar(){
        StringBuilder sb = new StringBuilder();
        sb.append("Forma de plantar: " + this.forma_plantar + " ");
        return sb.toString();
    }
    public String toStringFormaRecoger(){
        StringBuilder sb = new StringBuilder();
        sb.append("Forma de recoger: " + this.forma_recoger + " ");
        return sb.toString();
    }

}
