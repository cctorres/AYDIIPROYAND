package com.example.computador.pruebabdonline.Modelo;

/**
* Clase que representa el objeto Mesa
 */

public class Mesa {

    //Atributos
    private int idMesa;
    private String estadoMesa;
    private int idPedidoMesa;

    public Mesa(int idMesa, String estadoMesa, int idPedidoMesa) {
        this.idMesa = idMesa;
        this.estadoMesa = estadoMesa;
        this.idPedidoMesa = idPedidoMesa;
    }

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public String getEstadoMesa() {
        return estadoMesa;
    }

    public void setEstadoMesa(String estadoMesa) {
        this.estadoMesa = estadoMesa;
    }

    public int getIdPedidoMesa() {
        return idPedidoMesa;
    }

    public void setIdPedidoMesa(int idPedidoMesa) {
        this.idPedidoMesa = idPedidoMesa;
    }

    /**
     * Compara los atributos de dos mesas
     * @param mesa mesa externa
     * @return true si son iguales, false si hay cambios
     */
    public boolean compararCon(Mesa mesa) {
        return (this.estadoMesa.equalsIgnoreCase(mesa.estadoMesa)  &&
                this.idPedidoMesa == mesa.idPedidoMesa);
    }
}
