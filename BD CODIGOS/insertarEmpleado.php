<?php
/**
 * Insertar un nuevo empleado en la base de datos
 */
require 'Empleado.php';
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    // Decodificando formato Json
    $body = json_decode(file_get_contents("php://input"), true);
    // Insertar empleado
    $retorno = Empleado::insert(
        $body['id_empleado'],
        $body['nombre_empleado'],
		$body['cargo_empleado'],
		$body['telefono_empleado'],
        $body['contraseña_empleado']);
    if ($retorno) {
        $json_string = json_encode(array("estado" => 1,"mensaje" => "Creación correcta"));
		echo $json_string;
    } else {
        $json_string = json_encode(array("estado" => 2,"mensaje" => "No se creó el registro"));
		echo $json_string;
    }
}
?>