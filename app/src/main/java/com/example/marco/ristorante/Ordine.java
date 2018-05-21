package com.example.marco.ristorante;

public class Ordine {
    private int id;
    private String tipo;
    private String specifiche;
    private String ora;
    private String tavolo;
    private String note;

    public int getId(){
        return this.id;
    }
    public String getTipo(){
        return this.tipo;
    }
    public String getSpecifiche(){
        return this.specifiche;
    }
    public String getOra(){
        return this.ora;
    }
    public String getTavolo(){
        return this.tavolo;
    }
    public String getNote(){
        return this.note;
    }

    public void setId(int id){
        this.id=id;
    }
    public void setTipo(String tipo){
        this.tipo=tipo;
    }
    public void setSpecifiche(String specifiche){
        this.specifiche=specifiche;
    }
    public void setOra(String ora){
        this.ora=ora;
    }
    public void setNote(String note){
        this.note=note;
    }
    public void setTavolo(String tavolo){
        this.tavolo=tavolo;
    }
}
