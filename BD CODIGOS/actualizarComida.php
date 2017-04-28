<?php
/**
 * Actualiza una comida especificado por su identificador
 */
require 'Comida.php';
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    // Decodificando formato Json
    $body = json_decode(file_get_contents("php://input"), true);
    // Actualizar comida
    $retorno = Comida::update(
        $body['id_comida'],
		$body['precio_comida'],
		$body['categoria_comida'],
		$body['restriccion_comida'],
		$body['descripcion_comida'],
		$body['ingredientes_comida'],
        $body['foto_comida']);
    if ($retorno) {
        $json_string = json_encode(array("estado" => 1,"mensaje" => "Actualización correcta"));
		echo $json_string;
    } else {
        $json_string = json_encode(array("estado" => 2,"mensaje" => "No se actualizó el registro"));
		echo $json_string;
    }
}
?>