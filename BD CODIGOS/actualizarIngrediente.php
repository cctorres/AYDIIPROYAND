<?php
/**
 * Actualiza un ingrediente especificado por su identificador
 */
require 'Ingrediente.php';
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    // Decodificando formato Json
    $body = json_decode(file_get_contents("php://input"), true);
    // Actualizar ingrediente
    $retorno = Ingrediente::update(
        $body['id_ingrediente'],
		$body['nombre_ingrediente'],
        $body['cantidad_ingrediente']);
    if ($retorno) {
        $json_string = json_encode(array("estado" => 1,"mensaje" => "Actualización correcta"));
		echo $json_string;
    } else {
        $json_string = json_encode(array("estado" => 2,"mensaje" => "No se actualizó el registro"));
		echo $json_string;
    }
}
?>