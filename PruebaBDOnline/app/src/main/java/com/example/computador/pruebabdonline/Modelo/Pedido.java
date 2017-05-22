package com.example.computador.pruebabdonline.Modelo;


import android.text.format.Time;

import java.util.ArrayList;
import java.util.Date;

public class Pedido {

    private int idPedido, precioPedido, codEmpleadoPedido, idMesaPedido;
    private String estadoPedido, codigosComidaPedido, fechaPedido;

    public Pedido() {
    }

    public Pedido(int idPedido, int precioPedido, int codEmpleadoPedido, int idMesaPedido, String estadoPedido, String codigosComidaPedido, String fechaPedido) {
        this.idPedido = idPedido;
        this.precioPedido = precioPedido;
        this.codEmpleadoPedido = codEmpleadoPedido;
        this.idMesaPedido = idMesaPedido;
        this.estadoPedido = estadoPedido;
        this.codigosComidaPedido = codigosComidaPedido;
        this.fechaPedido = fechaPedido;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getPrecioPedido() {
        return precioPedido;
    }

    public void setPrecioPedido(int precioPedido) {
        this.precioPedido = precioPedido;
    }

    public int getCodEmpleadoPedido() {
        return codEmpleadoPedido;
    }

    public void setCodEmpleadoPedido(int codEmpleadoPedido) {
        this.codEmpleadoPedido = codEmpleadoPedido;
    }

    public int getIdMesaPedido() {
        return idMesaPedido;
    }

    public void setIdMesaPedido(int idMesaPedido) {
        this.idMesaPedido = idMesaPedido;
    }

    public String getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(String estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public String getCodigosComidaPedido() {
        return codigosComidaPedido;
    }

    public void setCodigosComidaPedido(String codigosComidaPedido) {
        this.codigosComidaPedido = codigosComidaPedido;
    }

    public String getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(String fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    /**
     *
     * @param listaPedidos Array con todos los pedidos
     * @param codEmpleado cod del empleado al cual se le buscará los pedidos
     * @return Retorna un array con los pedidos del empleado
     */
    public ArrayList<Pedido> filtrarPedidoEmpleado(ArrayList<Pedido> listaPedidos, int codEmpleado){
        ArrayList<Pedido> listaFiltrada = new ArrayList<>();
        for(int i = 0; i<listaPedidos.size();i++){
            if(listaPedidos.get(i).getCodEmpleadoPedido() == codEmpleado){
                listaFiltrada.add(listaPedidos.get(i));
            }
        }
        return (listaFiltrada);
    }

    /**
     *
     * @param listaPedidos Array con todos los pedidos
     * @param codEmpleado cod del empleado al cual se le buscará los pedidos
     * @return Retorna un array con los pedidos del empleado hechos en el día actual
     */
    public ArrayList<Pedido> filtrarPedidoEmpleadoYDia(ArrayList<Pedido> listaPedidos, int codEmpleado){
        Time dia = new Time (Time.getCurrentTimezone());
        dia.setToNow();
        String diaString = Integer.toString(dia.monthDay);
        ArrayList<Pedido> listaFiltrada = new ArrayList<>();
        for(int i = 0; i<listaPedidos.size();i++){
            String diaPedido = listaPedidos.get(i).getFechaPedido().substring(0,2);
            if(listaPedidos.get(i).getCodEmpleadoPedido() == codEmpleado &
                    diaPedido.equalsIgnoreCase(diaString)){
                listaFiltrada.add(listaPedidos.get(i));
            }
        }
        return (listaFiltrada);
    }



}
