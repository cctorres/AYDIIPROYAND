package com.example.computador.pruebabdonline.Controlador;



public class PHPGetter {

    //Clase que solamente da el getter de las URL de los PHP

    //URL del servidor
    String URLD = "http://aydandroid.esy.es";
    //rutas de los Web Services de la Tabla Mesa
    String obtenerMesas = URLD + "/obtenerMesas.php";
    String obtenerMesaByID = URLD + "/obtenerMesaByID.php";
    String insertarMesa = URLD + "/insertarMesa.php";
    String borrarMesa = URLD + "/borrarMesa.php";
    String actualizarMesa = URLD + "/actualizarMesa.php";
    //rutas de los Web Services de la Tabla Comida
    String obtenerComidas = URLD + "/obtenerComidas.php";
    String obtenerComidaByID = URLD + "/obtenerComidaByID.php";
    String insertarComida = URLD + "/insertarComida.php";
    String borrarComida = URLD + "/borrarComida.php";
    String actualizarComida = URLD + "/actualizarComida.php";
    //rutas de los Web Services de la Tabla Empleado
    String obtenerEmpleados = URLD + "/obtenerEmpleados.php";
    String obtenerEmpleadoByID = URLD + "/obtenerEmpleadoByID.php";
    String insertarEmpleado = URLD + "/insertarEmpleado.php";
    String borrarEmpleado = URLD + "/borrarEmpleado.php";
    String actualizarEmpleado = URLD + "/actualizarEmpleado.php";
    //rutas de los Web Services de la Tabla Pedido
    String obtenerPedidos = URLD + "/obtenerPedidos.php";
    String obtenerPedidoByID = URLD + "/obtenerPedidoByID.php";
    String insertarPedido = URLD + "/insertarPedido.php";
    String borrarPedido = URLD + "/borrarPedido.php";
    String actualizarPedido = URLD + "/actualizarPedido.php";
    //rutas de los Web Services de la Tabla Restaurante
    String obtenerRestaurantes = URLD + "/obtenerRestaurantes.php";
    String insertarRestaurante = URLD + "/insertarRestaurante.php";
    String borrarRestaurante = URLD + "/borrarRestaurante.php";
    String actualizarRestaurante = URLD + "/actualizarRestaurante.php";
    //rutas de los Web Services de la tabla Ingrediente
    String obtenerIngredientes = URLD + "/obtenerIngrediente.php";
    String obtenerIngredienteByID = URLD + "/obtenerIngredienteByID.php";
    String insertarIngrediente = URLD + "/insertarIngrediente.php";
    String borrarIngrediente = URLD + "/borrarIngrediente.php";
    String actualizarIngrediente = URLD + "/actualizarIngrediente.php";

    public String getURLD() {
        return URLD;
    }

    public String getObtenerMesas() {
        return obtenerMesas;
    }

    public String getObtenerMesaByID() {
        return obtenerMesaByID;
    }

    public String getInsertarMesa() {
        return insertarMesa;
    }

    public String getBorrarMesa() {
        return borrarMesa;
    }

    public String getActualizarMesa() {
        return actualizarMesa;
    }

    public String getObtenerComidas() {
        return obtenerComidas;
    }

    public String getObtenerComidaByID() {
        return obtenerComidaByID;
    }

    public String getInsertarComida() {
        return insertarComida;
    }

    public String getBorrarComida() {
        return borrarComida;
    }

    public String getActualizarComida() {
        return actualizarComida;
    }

    public String getObtenerEmpleados() {
        return obtenerEmpleados;
    }

    public String getObtenerEmpleadoByID() {
        return obtenerEmpleadoByID;
    }

    public String getInsertarEmpleado() {
        return insertarEmpleado;
    }

    public String getBorrarEmpleado() {
        return borrarEmpleado;
    }

    public String getActualizarEmpleado() {
        return actualizarEmpleado;
    }

    public String getObtenerPedidos() {
        return obtenerPedidos;
    }

    public String getObtenerPedidoByID() {
        return obtenerPedidoByID;
    }

    public String getInsertarPedido() {
        return insertarPedido;
    }

    public String getBorrarPedido() {
        return borrarPedido;
    }

    public String getActualizarPedido() {
        return actualizarPedido;
    }

    public String getObtenerRestaurantes() {
        return obtenerRestaurantes;
    }

    public String getInsertarRestaurante() {
        return insertarRestaurante;
    }

    public String getBorrarRestaurante() {
        return borrarRestaurante;
    }

    public String getActualizarRestaurante() {
        return actualizarRestaurante;
    }

    public String getObtenerIngredientes() {
        return obtenerIngredientes;
    }

    public void setObtenerIngredientes(String obtenerIngredientes) {
        this.obtenerIngredientes = obtenerIngredientes;
    }

    public String getObtenerIngredienteByID() {
        return obtenerIngredienteByID;
    }

    public void setObtenerIngredienteByID(String obtenerIngredienteByID) {
        this.obtenerIngredienteByID = obtenerIngredienteByID;
    }

    public String getInsertarIngrediente() {
        return insertarIngrediente;
    }

    public void setInsertarIngrediente(String insertarIngrediente) {
        this.insertarIngrediente = insertarIngrediente;
    }

    public String getBorrarIngrediente() {
        return borrarIngrediente;
    }

    public void setBorrarIngrediente(String borrarIngrediente) {
        this.borrarIngrediente = borrarIngrediente;
    }

    public String getActualizarIngrediente() {
        return actualizarIngrediente;
    }

    public void setActualizarIngrediente(String actualizarIngrediente) {
        this.actualizarIngrediente = actualizarIngrediente;
    }
}
