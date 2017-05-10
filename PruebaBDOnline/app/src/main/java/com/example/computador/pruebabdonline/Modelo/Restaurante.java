package com.example.computador.pruebabdonline.Modelo;

/**
 * Clase que representa el objeto Restaurante
 */

public class Restaurante {

    //Atributos
    int id;
    String nombre;
    String direccion;
    int numeroMesas;
    String telefono;

    public Restaurante(int id, String nombre, String direccion, int numeroMesas, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.numeroMesas = numeroMesas;
        this.telefono = telefono;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getNumeroMesas() {
        return numeroMesas;
    }

    public void setNumeroMesas(int numeroMesas) {
        this.numeroMesas = numeroMesas;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
