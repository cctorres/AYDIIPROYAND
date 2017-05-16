package com.example.computador.pruebabdonline.Modelo;


import java.io.Serializable;
import java.util.ArrayList;

public class Comida implements Serializable{

    int idComida, precioComida;
    String nombreComida, categoriaComida, restriccionComida, descripcionComida, ingredientesComida, fotoComida;

    public Comida() {
    }

    public Comida(int idComida, int precioComida, String nombreComida, String categoriaComida, String restriccionComida,
                  String descripcionComida, String ingredientesComida, String fotoComida) {
        this.idComida = idComida;
        this.precioComida = precioComida;
        this.nombreComida = nombreComida;
        this.categoriaComida = categoriaComida;
        this.restriccionComida = restriccionComida;
        this.descripcionComida = descripcionComida;
        this.ingredientesComida = ingredientesComida;
        this.fotoComida = fotoComida;
    }

    public int getIdComida() {
        return idComida;
    }

    public void setIdComida(int idComida) {
        this.idComida = idComida;
    }

    public int getPrecioComida() {
        return precioComida;
    }

    public void setPrecioComida(int precioComida) {
        this.precioComida = precioComida;
    }

    public String getNombreComida() {
        return nombreComida;
    }

    public void setNombreComida(String nombreComida) {
        this.nombreComida = nombreComida;
    }

    public String getCategoriaComida() {
        return categoriaComida;
    }

    public void setCategoriaComida(String categoriaComida) {
        this.categoriaComida = categoriaComida;
    }

    public String getRestriccionComida() {
        return restriccionComida;
    }

    public void setRestriccionComida(String restriccionComida) {
        this.restriccionComida = restriccionComida;
    }

    public String getDescripcionComida() {
        return descripcionComida;
    }

    public void setDescripcionComida(String descripcionComida) {
        this.descripcionComida = descripcionComida;
    }

    public String getIngredientesComida() {
        return ingredientesComida;
    }

    public void setIngredientesComida(String ingredientesComida) {
        this.ingredientesComida = ingredientesComida;
    }

    public String getFotoComida() {
        return fotoComida;
    }

    public void setFotoComida(String fotoComida) {
        this.fotoComida = fotoComida;
    }

    public ArrayList<Comida> filtrarCategoria(ArrayList<Comida> listaComidas, String categoria){
        if(categoria.equalsIgnoreCase("todas")){
            return(listaComidas);
        }
        ArrayList<Comida> resultado = new ArrayList<Comida>();
        for(int i = 0;i<listaComidas.size();i++){
            if(listaComidas.get(i).getCategoriaComida().equalsIgnoreCase(categoria)){
                resultado.add(listaComidas.get(i));
            }
        }
        return resultado;
    }

    public ArrayList<Comida> filtrarRestriccion(ArrayList<Comida> listaComidas, String restriccion){
        ArrayList<Comida> resultado = new ArrayList<Comida>();
        for(int i = 0;i<listaComidas.size();i++){
            if(!listaComidas.get(i).getRestriccionComida().equalsIgnoreCase(restriccion)){
                resultado.add(listaComidas.get(i));
            }
        }
        return resultado;
    }
}
