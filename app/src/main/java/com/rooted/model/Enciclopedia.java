package com.rooted.model;

import java.util.ArrayList;

public class Enciclopedia {

    ArrayList<EntradasEnciclopedia> entradasEncliclopedia;
    public Enciclopedia(){
        entradasEncliclopedia = new ArrayList<EntradasEnciclopedia>();
   }

   public void addEntrada(EntradasEnciclopedia entrada){
        this.entradasEncliclopedia.add(entrada);
   }

   public void rellenarEnciclopedia(){
        EntradasEnciclopedia tomate = new EntradasEnciclopedia("Tomate","Desc", "Rieg", "FrmP", "FrmR");
        this.addEntrada(tomate);
       EntradasEnciclopedia patata = new EntradasEnciclopedia("Tomate","Desc", "Rieg", "FrmP", "FrmR");
       this.addEntrada(patata);
       EntradasEnciclopedia maiz = new EntradasEnciclopedia("Tomate","Desc", "Rieg", "FrmP", "FrmR");
       this.addEntrada(maiz);
   }
}
