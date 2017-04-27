<?php
/**
 * Obtiene todas las mesas de la base de datos
 */
require 'Mesa.php';
if ($_SERVER['REQUEST_METHOD'] == 'GET') {
    // Manejar petición GET
    $mesas = Mesa::getAll();
    if ($mesas) {
        $datos["estado"] = 1;
        $datos["mesas"] = $mesas;
        print json_encode($datos);
    } else {
        print json_encode(array(
            "estado" => 2,
            "mensaje" => "Ha ocurrido un error"
        ));
    }
}