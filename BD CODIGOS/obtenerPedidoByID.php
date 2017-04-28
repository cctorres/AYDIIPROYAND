<?php
/**
 * Obtiene el detalle de un pedido especificado por
 * su identificador "id_pedido"
 */
require 'Pedido.php';
if ($_SERVER['REQUEST_METHOD'] == 'GET') {
    if (isset($_GET['id_pedido'])) {
        // Obtener parámetro id_pedido
        $parametro = $_GET['id_pedido'];
        // Tratar retorno
        $retorno = Pedido::getById($parametro);
        if ($retorno) {
            $Pedido["estado"] = 1;		// cambio "1" a 1 porque no coge bien la cadena.
            $Pedido["pedido"] = $retorno;
            // Enviar objeto json del pedido
            print json_encode($Pedido);
        } else {
            // Enviar respuesta de error general
            print json_encode(
                array(
                    'estado' => '2',
                    'mensaje' => 'No se obtuvo el registro'
                )
            );
        }
    } else {
        // Enviar respuesta de error
        print json_encode(
            array(
                'estado' => '3',
                'mensaje' => 'Se necesita un identificador'
            )
        );
    }
}