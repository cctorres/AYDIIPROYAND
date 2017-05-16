<?php
/**
 * Insertar una nueva Comida en la base de datos
 */
require 'Comida.php';
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    // Decodificando formato Json
    $body = json_decode(file_get_contents("php://input"), true);
    // Insertar comida
    $retorno = Comida::insert(
		$body['nombre_comida'],
		$body['precio_comida'],
		$body['categoria_comida'],
		$body['restriccion_comida'],
		$body['descripcion_comida'],
		$body['ingredientes_comida'],
        $body['foto_comida']);
    if ($retorno) {
        $json_string = json_encode(array("estado" => 1,"mensaje" => "Creación correcta"));
		echo $json_string;
    } else {
        $json_string = json_encode(array("estado" => 2,"mensaje" => "No se creó el registro"));
		echo $json_string;
    }
}
?>