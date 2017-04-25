<?php
/**
 * Actualiza un empleado especificado por su identificador
 */
require 'Empleados.php';
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    // Decodificando formato Json
    $body = json_decode(file_get_contents("php://input"), true);
    // Actualizar empleado
    $retorno = Empleados::update(
        $body['codEmpleado'],
		$body['idEmpleado'],
        $body['nomEmpleado'],
        $body['telEmpleado']);
    if ($retorno) {
        $json_string = json_encode(array("estado" => 1,"mensaje" => "Actualización correcta"));
		echo $json_string;
    } else {
        $json_string = json_encode(array("estado" => 2,"mensaje" => "No se actualizó el registro"));
		echo $json_string;
    }
}
?>