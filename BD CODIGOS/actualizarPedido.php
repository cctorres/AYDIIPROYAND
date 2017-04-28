<?php
/**
 * Actualiza un pedido especificado por su identificador
 */
require 'Pedido.php';
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    // Decodificando formato Json
    $body = json_decode(file_get_contents("php://input"), true);
    // Actualizar pedido
    $retorno = Pedido::update(
        $body['id_pedido'],
		$body['fecha_pedido'],
		$body['precio_pedido'],
		$body['estado_pedido'],
		$body['codigos_comida_pedido'],	
		$body['cod_empleado_pedido'],
        $body['id_mesa_pedido']);
    if ($retorno) {
        $json_string = json_encode(array("estado" => 1,"mensaje" => "Actualización correcta"));
		echo $json_string;
    } else {
        $json_string = json_encode(array("estado" => 2,"mensaje" => "No se actualizó el registro"));
		echo $json_string;
    }
}
?>