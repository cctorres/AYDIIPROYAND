<?php
/**
 * Representa el la estructura de las Restaurantes
 * almacenadas en la base de datos
 */
require 'Database.php';
class Restaurante
{
    function __construct()
    {
    }
    /**
     * Retorna la tabla 'Restaurante'
     *
     * @return array Datos del registro
     */
    public static function getAll()
    {
        $consulta = "SELECT * FROM Restaurante";
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
     * Actualiza un registro de la bases de datos basado
     * en los nuevos valores relacionados con un identificador
     *
     * @param $id_restaurante       	identificador interno del Restaurante
     * @param $nombre_restaurante		nuevo nombre del restaurante
     * @param $direccion_restaurante	nueva direccion del restaurante
	 * @param $num_mesas_restausante	nuevo numero de mesas del restaurante
	 * @param $telefono_restaurante		nuevo telefono del restaurante
     
     */
    public static function update(
        $id_restaurante,
        $nombre_restaurante,
        $direccion_restaurante,
		$num_mesas_restaurante,
		$telefono_restaurante
    )
    {
        // Creando consulta UPDATE
        $consulta = "UPDATE Restaurante" .
            " SET nombre_restaurante=?, direccion_restaurante=?, num_mesas_restaurante=?, telefono_restaurante=? " .
            "WHERE id_restaurante=?";
        // Preparar la sentencia
        $cmd = Database::getInstance()->getDb()->prepare($consulta);
        // Relacionar y ejecutar la sentencia
        $cmd->execute(array($nombre_restaurante, $direccion_restaurante, $num_mesas_restaurante, $telefono_restaurante, $id_restaurante));
        return $cmd;
    }
    /**
     * Insertar un nuevo Restaurante
     *
     * @param $nombre_restaurante		nombre del nuevo registro
     * @param $direccion_restaurante	direccion del nuevo registro
	 * @param $num_mesas_restausante	numero de mesas del nuevo registro
	 * @param $telefono_restaurante		telefono del nuevo registro
     * @return PDOStatement
     */
    public static function insert(
        $nombre_restaurante,
        $direccion_restaurante,
		$num_mesas_restausante,
		$telefono_restaurante
    )
    {
        // Sentencia INSERT
        $comando = "INSERT INTO Restaurante ( " .
            " nombre_restaurante," .
            " direccion_restaurante," .
			" num_mesas_restausante," .
			" telefono_restaurante)" .
            " VALUES( ?,?,?,?)";
        // Preparar la sentencia
        $sentencia = Database::getInstance()->getDb()->prepare($comando);
        return $sentencia->execute(
            array(
                $nombre_restaurante,
				$direccion_restaurante,
				$num_mesas_restausante,
				$telefono_restaurante
            )
        );
    }
    /**
     * Eliminar el registro con el identificador especificado
     *
     * @param $id_restaurante identificador de la tabla Restaurante
     * @return bool Respuesta de la eliminación
     */
    public static function delete($id_restaurante)
    {
        // Sentencia DELETE
        $comando = "DELETE FROM Restaurante WHERE id_restaurante=?";
        // Preparar la sentencia
        $sentencia = Database::getInstance()->getDb()->prepare($comando);
        return $sentencia->execute(array($id_restaurante));
    }
}
?>