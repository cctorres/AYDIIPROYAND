<?php
/**
 * Obtiene el detalle de un  ingrediente especificado por
 * su identificador "id_ingrediente"
 */
require 'Ingrediente.php';
if ($_SERVER['REQUEST_METHOD'] == 'GET') {
    if (isset($_GET['id_ingrediente'])) {
        // Obtener parámetro id_ingrediente
        $parametro = $_GET['id_ingrediente'];
        // Tratar retorno
        $retorno = Ingrediente::getById($parametro);
        if ($retorno) {
            $ingredientes["estado"] = 1;		// cambio "1" a 1 porque no coge bien la cadena.
            $ingredientes["ingredientes"] = $retorno;
            // Enviar objeto json de la mesa
            print json_encode($ingredientes);
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