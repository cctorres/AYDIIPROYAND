<?php
/**
 * Insertar un nuevo Pedido en la base de datos
 */
require 'Pedido.php';
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    // Decodificando formato Json
    $body = json_decode(file_get_contents("php://input"), true);
    // Insertar pedido
    $retorno = Pedido::insert(
        $body['id_pedido'],
		$body['fecha_pedido'],
		$body['precio_pedido'],
		$body['estado_pedido'],
		$body['codigos_comida_pedido'],		
		$body['cod_empleado_pedido'],
        $body['id_mesa_pedido']);
    if ($retorno) {
        $json_string = json_encode(array("estado" => 1,"mensaje" => "Creación correcta"));
		echo $json_string;
    } else {
        $json_string = json_encode(array("estado" => 2,"mensaje" => "No se creó el registro"));
		echo $json_string;
    }
}
?>