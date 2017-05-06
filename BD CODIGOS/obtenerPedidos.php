<?php
/**
 * Obtiene todas los pedidos de la base de datos
 */
require 'Pedido.php';
if ($_SERVER['REQUEST_METHOD'] == 'GET') {
    // Manejar petición GET
    $pedidos = Pedido::getAll();
    if ($pedidos) {
        $datos["estado"] = 1;
        $datos["pedidos"] = $pedidos;
        print json_encode($datos);
    } else {
        print json_encode(array(
            "estado" => 2,
            "mensaje" => "Ha ocurrido un error"
        ));
    }
}