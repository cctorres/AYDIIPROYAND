<?php
/**
 * Obtiene el detalle de un empleado especificado por
 * su identificador "codEmpleado"
 */
require 'Empleados.php';
if ($_SERVER['REQUEST_METHOD'] == 'GET') {
    if (isset($_GET['codEmpleado'])) {
        // Obtener parámetro codEmpleado
        $parametro = $_GET['codEmpleado'];
        // Tratar retorno
        $retorno = Empleados::getById($parametro);
        if ($retorno) {
            $empleado["estado"] = 1;		// cambio "1" a 1 porque no coge bien la cadena.
            $empleado["empleado"] = $retorno;
            // Enviar objeto json del empleado
            print json_encode($empleado);
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