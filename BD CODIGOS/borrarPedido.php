<?php
/**
 * Elimina un pedido de la base de datos
 * distinguido por su identificador
 */
require 'Pedido.php';
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    // Decodificando formato Json
    $body = json_decode(file_get_contents("php://input"), true);
    $retorno = Pedido::delete($body['id_pedido']);
	//$json_string = json_encode($clientes);
	//echo 'antes de entrar';
    if ($retorno) {
        $json_string = json_encode(array("estado" => 1,"mensaje" => "Eliminación exitosa"));
		echo $json_string;
    } else {
        $json_string = json_encode(array("estado" => 2,"mensaje" => "No se eliminó el registro"));
		echo $json_string;
    }
}
?>