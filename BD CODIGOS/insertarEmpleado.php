<?php
/**
 * Insertar un nuevo empleado en la base de datos
 */
require 'Empleados.php';
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    // Decodificando formato Json
    $body = json_decode(file_get_contents("php://input"), true);
    // Insertar empleado
    $retorno = Empleados::insert(
        $body['idEmpleado'],
        $body['nomEmpleado'],
        $body['telEmpleado']);
    if ($retorno) {
        $json_string = json_encode(array("estado" => 1,"mensaje" => "Creación correcta"));
		echo $json_string;
    } else {
        $json_string = json_encode(array("estado" => 2,"mensaje" => "No se creó el registro"));
		echo $json_string;
    }
}
?>