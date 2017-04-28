<?php
/**
 * Representa el la estructura de las Empleados
 * almacenadas en la base de datos
 */
require 'Database.php';
class Empleado
{
    function __construct()
    {
    }
    /**
     * Retorna todas las filas de la tabla 'Empleado'
     *
     * @return array Datos del registro
     */
    public static function getAll()
    {
        $consulta = "SELECT * FROM Empleado";
        try {
            // Preparar sentencia
            $comando = Database::getInstance()->getDb()->prepare($consulta);
            // Ejecutar sentencia preparada
            $comando->execute();
            return $comando->fetchAll(PDO::FETCH_ASSOC);
        } catch (PDOException $e) {
            return false;
        }
    }
    /**
     * Obtiene los campos de un Empleado con un identificador
     * determinado
     *
     * @param $cod_empleado Identificador interno del empleado
     * @return mixed
     */
    public static function getById($cod_empleado)
    {
        // Consulta de la tabla Empleado
        $consulta = "SELECT *
                             FROM Empleado
                             WHERE cod_empleado = ?";
        try {
            // Preparar sentencia
            $comando = Database::getInstance()->getDb()->prepare($consulta);
            // Ejecutar sentencia preparada
            $comando->execute(array($cod_empleado));
            // Capturar primera fila del resultado
            $row = $comando->fetch(PDO::FETCH_ASSOC);
            return $row;
        } catch (PDOException $e) {
            //Clasificación de errores
            return -1;
        }
    }
    /**
     * Actualiza un registro de la bases de datos basado
     * en los nuevos valores relacionados con un identificador
     *
     * @param $cod_empleado       	identificador interno del empleado
     * @param $id_empleado		  	nuevo numero identificacion
     * @param $nombre_empleado	  	nuevo nombre
	 * @param $cargo_empleado	  	nuevo cargo
	 * @param $telefono_empleado  	nuevo telefono
	 * @param $contraseña_empleado	nueva contraseña
     
     */
    public static function update(
        $cod_empleado,
        $id_empleado,
        $nombre_empleado,
		$cargo_empleado,
		$telefono_empleado,
		$contraseña_empleado
    )
    {
        // Creando consulta UPDATE
        $consulta = "UPDATE Empleado" .
            " SET id_empleado=?, nombre_empleado=?, cargo_empleado=?, telefono_empleado=?, contraseña_empleado=? " .
            "WHERE cod_empleado=?";
        // Preparar la sentencia
        $cmd = Database::getInstance()->getDb()->prepare($consulta);
        // Relacionar y ejecutar la sentencia
        $cmd->execute(array($id_empleado, $nombre_empleado, $cargo_empleado, $telefono_empleado, $contraseña_empleado, $cod_empleado));
        return $cmd;
    }
    /**
     * Insertar un nuevo empleado
     *
     * @param $id_empleado		  	numero identificacion
     * @param $nombre_empleado	  	nombre
	 * @param $cargo_empleado	  	cargo
	 * @param $telefono_empleado  	telefono
	 * @param $contraseña_empleado	contraseña
     * @return PDOStatement
     */
    public static function insert(
        $id_empleado,
        $nombre_empleado,
		$cargo_empleado,
		$telefono_empleado,
		$contraseña_empleado
    )
    {
        // Sentencia INSERT
        $comando = "INSERT INTO Empleado ( " .
            " id_empleado," .
			" nombre_empleado," .
			" cargo_empleado," .
		    " telefono_empleado," .
			" contraseña_empleado)" .
            " VALUES(?,?,?,?,?)";
        // Preparar la sentencia
        $sentencia = Database::getInstance()->getDb()->prepare($comando);
        return $sentencia->execute(
            array(
				$id_empleado,
				$nombre_empleado,
				$cargo_empleado,
				$telefono_empleado,
				$contraseña_empleado
            )
        );
    }
    /**
     * Eliminar el registro con el identificador especificado
     *
     * @param $cod_empleado identificador de la tabla Empleado
     * @return bool Respuesta de la eliminación
     */
    public static function delete($cod_empleado)
    {
        // Sentencia DELETE
        $comando = "DELETE FROM Empleado WHERE cod_empleado=?";
        // Preparar la sentencia
        $sentencia = Database::getInstance()->getDb()->prepare($comando);
        return $sentencia->execute(array($cod_empleado));
    }
}
?>