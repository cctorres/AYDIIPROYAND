<?php
/**
 * Obtiene el detalle de una comida especificado por
 * su identificador "id_comida"
 */
require 'Comida.php';
if ($_SERVER['REQUEST_METHOD'] == 'GET') {
    if (isset($_GET['id_comida'])) {
        // Obtener parámetro id_comida
        $parametro = $_GET['id_comida'];
        // Tratar retorno
        $retorno = Comida::getById($parametro);
        if ($retorno) {
            $Comida["estado"] = 1;		// cambio "1" a 1 porque no coge bien la cadena.
            $Comida["comida"] = $retorno;
            // Enviar objeto json de la comida
            print json_encode($Comida);
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