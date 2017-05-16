package com.example.computador.pruebabdonline.Modelo;



public class Ingrediente {

    private int idIngrediente, cantIngrediente;
    private String nombreIngrediente;

    public Ingrediente() {
    }

    public Ingrediente(int idIngrediente, String nombreIngrediente, int cantIngrediente) {
        this.idIngrediente = idIngrediente;
        this.cantIngrediente = cantIngrediente;
        this.nombreIngrediente = nombreIngrediente;
    }

    public int getIdIngrediente() {
        return idIngrediente;
    }

    public void setIdIngrediente(int idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    public int getCantIngrediente() {
        return cantIngrediente;
    }

    public void setCantIngrediente(int cantIngrediente) {
        this.cantIngrediente = cantIngrediente;
    }

    public String getNombreIngrediente() {
        return nombreIngrediente;
    }

    public void setNombreIngrediente(String nombreIngrediente) {
        this.nombreIngrediente = nombreIngrediente;
    }
}
