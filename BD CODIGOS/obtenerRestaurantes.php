<?php
/**
 * Obtiene todas las restaurantes de la base de datos
 */
require 'Restaurante.php';
if ($_SERVER['REQUEST_METHOD'] == 'GET') {
    // Manejar petición GET
    $Restaurante = Restaurante::getAll();
    if ($Restaurante) {
        $datos["estado"] = 1;
        $datos["Restaurante"] = $Restaurante;
        print json_encode($datos);
    } else {
        print json_encode(array(
            "estado" => 2,
            "mensaje" => "Ha ocurrido un error"
        ));
    }
}