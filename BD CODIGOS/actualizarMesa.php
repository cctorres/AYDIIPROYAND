<?php
/**
 * Actualiza una mesa especificado por su identificador
 */
require 'Mesa.php';
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    // Decodificando formato Json
    $body = json_decode(file_get_contents("php://input"), true);
    // Actualizar mesa
    $retorno = Mesa::update(
        $body['id_mesa'],
		$body['estado_mesa'],
        $body['id_pedido_mesa']);
    if ($retorno) {
        $json_string = json_encode(array("estado" => 1,"mensaje" => "Actualización correcta"));
		echo $json_string;
    } else {
        $json_string = json_encode(array("estado" => 2,"mensaje" => "No se actualizó el registro"));
		echo $json_string;
    }
}
?>