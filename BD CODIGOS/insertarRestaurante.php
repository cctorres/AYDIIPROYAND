<?php
/**
 * Insertar un nuevo restaurante en la base de datos
 */
require 'Restaurante.php';
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    // Decodificando formato Json
    $body = json_decode(file_get_contents("php://input"), true);
    // Insertar restaurante
    $retorno = restaurante::insert(
        $body['nombre_restaurante'],
		$body['direccion_restaurante'],
        $body['num_mesas_restausante'],
        $body['telefono_restaurante']);
    if ($retorno) {
        $json_string = json_encode(array("estado" => 1,"mensaje" => "Creación correcta"));
		echo $json_string;
    } else {
        $json_string = json_encode(array("estado" => 2,"mensaje" => "No se creó el registro"));
		echo $json_string;
    }
}
?>