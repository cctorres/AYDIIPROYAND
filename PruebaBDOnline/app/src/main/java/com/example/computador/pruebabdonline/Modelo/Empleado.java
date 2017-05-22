package com.example.computador.pruebabdonline.Modelo;


import java.io.Serializable;
import java.util.ArrayList;

public class Empleado implements Serializable{
    int codEmpleado, idEmpleado,telefonoEmpleado;
    String nombreEmpleado, cargoEmpleado, contraseñaEmpleado;

    public Empleado() {
    }

    public Empleado(int codEmpleado, int idEmpleado, int telefonoEmpleado, String nombreEmpleado, String cargoEmpleado, String contraseñaEmpleado) {
        this.codEmpleado = codEmpleado;
        this.idEmpleado = idEmpleado;
        this.telefonoEmpleado = telefonoEmpleado;
        this.nombreEmpleado = nombreEmpleado;
        this.cargoEmpleado = cargoEmpleado;
        this.contraseñaEmpleado = contraseñaEmpleado;
    }

    public int getCodEmpleado() {
        return codEmpleado;
    }

    public void setCodEmpleado(int codEmpleado) {
        this.codEmpleado = codEmpleado;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getTelefonoEmpleado() {
        return telefonoEmpleado;
    }

    public void setTelefonoEmpleado(int telefonoEmpleado) {
        this.telefonoEmpleado = telefonoEmpleado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getCargoEmpleado() {
        return cargoEmpleado;
    }

    public void setCargoEmpleado(String cargoEmpleado) {
        this.cargoEmpleado = cargoEmpleado;
    }

    public String getContraseñaEmpleado() {
        return contraseñaEmpleado;
    }

    public void setContraseñaEmpleado(String contraseñaEmpleado) {
        this.contraseñaEmpleado = contraseñaEmpleado;
    }

}
