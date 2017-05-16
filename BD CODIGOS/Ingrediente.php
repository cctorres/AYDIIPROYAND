<?php
/**
 * Representa el la estructura de la tabla Ingrediente
 * almacenadas en la base de datos
 */
require 'Database.php';
class Ingrediente
{
    function __construct()
    {
    }
    /**
     * Retorna la tabla 'Ingrediente'
     *
     * @return array Datos del registro
     */
    public static function getAll()
    {
        $consulta = "SELECT * FROM Ingrediente";
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
     * Obtiene los campos de un Ingrediente con un identificador
     * determinado
     *
     * @param $id_ingrediente	 Identificador del Ingrediente
     * @return mixed
     */
    public static function getById($id_ingrediente)
    {
        // Consulta de la tabla Ingrediente
        $consulta = "SELECT *
                             FROM Ingrediente
                             WHERE id_ingrediente = ?";
        try {
            // Preparar sentencia
            $comando = Database::getInstance()->getDb()->prepare($consulta);
            // Ejecutar sentencia preparada
            $comando->execute(array($id_ingrediente));
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
     * @param $id_ingrediente       	Identificador del Ingrediente
     * @param $nombre_ingrediente		nueva nombre del ingrediente
     * @param $cantidad_ingrediente		nueva cantidad del ingrediente
	 */
    public static function update(
        $id_ingrediente,
        $nombre_ingrediente,		
		$cantidad_ingrediente
    )
    {
        // Creando consulta UPDATE
        $consulta = "UPDATE Ingrediente" .
            " SET nombre_ingrediente=?, cantidad_ingrediente=? " .
            "WHERE id_ingrediente=?";
        // Preparar la sentencia
        $cmd = Database::getInstance()->getDb()->prepare($consulta);
        // Relacionar y ejecutar la sentencia
        $cmd->execute(array($nombre_ingrediente, $cantidad_ingrediente, $id_ingrediente));
        return $cmd;
    }
    /**
     * Insertar un nuevo Ingrediente
	 * @param $nombre_ingrediente   	nombre del ingrediente
	 * @param $cantidad_ingrediente		cantidad del ingrediente
     * @return PDOStatement
     */
    public static function insert(
        $nombre_ingrediente,
        $cantidad_ingrediente
    )
    {
        // Sentencia INSERT
        $comando = "INSERT INTO Ingrediente ( " .
            " nombre_ingrediente," .
            " cantidad_ingrediente)" .
            " VALUES( ?,?)";
        // Preparar la sentencia
        $sentencia = Database::getInstance()->getDb()->prepare($comando);
        return $sentencia->execute(
            array(
                $nombre_ingrediente,
				$cantidad_ingrediente
            )
        );
    }
    /**
     * Eliminar el registro con el identificador especificado
     *
     * @param $id_ingrediente		 identidicador del ingrediente
     * @return bool Respuesta de la eliminación
     */
    public static function delete($id_ingrediente)
    {
        // Sentencia DELETE
        $comando = "DELETE FROM Ingrediente WHERE id_ingrediente=?";
        // Preparar la sentencia
        $sentencia = Database::getInstance()->getDb()->prepare($comando);
        return $sentencia->execute(array($id_ingrediente));
    }
}
?>