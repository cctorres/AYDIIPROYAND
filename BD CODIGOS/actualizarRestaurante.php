<?php
/**
 * Actualiza un restaurante especificado por su identificador
 */
require 'Restaurante.php';
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    // Decodificando formato Json
    $body = json_decode(file_get_contents("php://input"), true);
    // Actualizar restaurante
    $retorno = Empleados::update(
        $body['id_restaurante'],
		$body['nombre_restaurante'],
        $body['direccion_restaurante'],
		$body['num_mesas_restausante'],
        $body['telefono_restaurante']);
    if ($retorno) {
        $json_string = json_encode(array("estado" => 1,"mensaje" => "Actualización correcta"));
		echo $json_string;
    } else {
        $json_string = json_encode(array("estado" => 2,"mensaje" => "No se actualizó el registro"));
		echo $json_string;
    }
}