<?php
/**
 * Obtiene todas las empleados de la base de datos
 */
require 'Empleado.php';
if ($_SERVER['REQUEST_METHOD'] == 'GET') {
    // Manejar petición GET
    $empleados = Empleado::getAll();
    if ($empleados) {
        $datos["estado"] = 1;
        $datos["empleados"] = $empleados;
        print json_encode($datos);
    } else {
        print json_encode(array(
            "estado" => 2,
            "mensaje" => "Ha ocurrido un error"
        ));
    }
}