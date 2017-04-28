<?php
/**
 * Obtiene todas las Comidas de la base de datos
 */
require 'Comida.php';
if ($_SERVER['REQUEST_METHOD'] == 'GET') {
    // Manejar petición GET
    $comidas = Comida::getAll();
    if ($comidas) {
        $datos["estado"] = 1;
        $datos["comidas"] = $comidas;
        print json_encode($datos);
    } else {
        print json_encode(array(
            "estado" => 2,
            "mensaje" => "Ha ocurrido un error"
        ));
    }
}