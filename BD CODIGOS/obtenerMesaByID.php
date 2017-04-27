<?php
/**
 * Obtiene el detalle de una mesaespecificado por
 * su identificador "id_mesa"
 */
require 'Mesa.php';
if ($_SERVER['REQUEST_METHOD'] == 'GET') {
    if (isset($_GET['id_mesa'])) {
        // Obtener parámetro id_mesa
        $parametro = $_GET['id_mesa'];
        // Tratar retorno
        $retorno = Mesa::getById($parametro);
        if ($retorno) {
            $mesa["estado"] = 1;		// cambio "1" a 1 porque no coge bien la cadena.
            $mesa["mesa"] = $retorno;
            // Enviar objeto json de la mesa
            print json_encode($mesa);
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