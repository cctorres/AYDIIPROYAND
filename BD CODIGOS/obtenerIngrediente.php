<?php
/**
 * Obtiene todos los ingredientes de la base de datos
 */
require 'Ingrediente.php';
if ($_SERVER['REQUEST_METHOD'] == 'GET') {
    // Manejar petición GET
    $ingredientes = Ingrediente::getAll();
    if ($ingredientes) {
        $datos["estado"] = 1;
        $datos["ingredientes"] = $ingredientes;
        print json_encode($datos);
    } else {
        print json_encode(array(
            "estado" => 2,
            "mensaje" => "Ha ocurrido un error"
        ));
    }
}