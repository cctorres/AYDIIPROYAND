<?php
/**
 * Representa el la estructura de la tabla Comida
 * almacenadas en la base de datos
 */
require 'Database.php';
class Comida
{
    function __construct()
    {
    }
    /**
     * Retorna la tabla 'Comida'
     *
     * @return array Datos del registro
     */
    public static function getAll()
    {
        $consulta = "SELECT * FROM Comida";
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
     * Obtiene los campos de una Comida con un identificador
     * determinado
     *
     * @param $id_comida	 Identificador de la Comida
     * @return mixed
     */
    public static function getById($id_comida)
    {
        // Consulta de la tabla Mesa
        $consulta = "SELECT *
                             FROM Comida
                             WHERE id_comida = ?";
        try {
            // Preparar sentencia
            $comando = Database::getInstance()->getDb()->prepare($consulta);
            // Ejecutar sentencia preparada
            $comando->execute(array($id_comida));
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
     * @param $id_comida       		identificador de la comida
	 * @param $nombre_comida       	nuevo nombre de la comida
     * @param $precio_comida		nuevo precio de la comida
     * @param $categoria_comida		nueva categoria de la comida
	 * @param $restriccion_comida	nueva restriccion de la comida
	 * @param $descripcion_comida   nueva descripcion de la comida
	 * @param $ingredientes_comida	nuevos ingredientes de la comida
	 * @param $foto_comida			nueva foto de la comida
	 */
    public static function update(
        $id_comida,
        $nombre_comida,
		$precio_comida,
		$categoria_comida,
        $restriccion_comida,
		$descripcion_comida,
		$ingredientes_comida,
		$foto_comida
    )
    {
        // Creando consulta UPDATE
        $consulta = "UPDATE Comida" .
            " SET nombre_comida=?, precio_comida=?, categoria_comida=?, restriccion_comida=?, descripcion_comida=?, ingredientes_comida=?, foto_comida=? " .
            "WHERE id_comida=?";
        // Preparar la sentencia
        $cmd = Database::getInstance()->getDb()->prepare($consulta);
        // Relacionar y ejecutar la sentencia
        $cmd->execute(array($nombre_comida, $precio_comida, $categoria_comida, $restriccion_comida, $descripcion_comida, $ingredientes_comida, $foto_comida, $id_comida));
        return $cmd;
    }
    /**
     * Insertar una nueva Comida
     * @param $nombre_comida		nombre de la comida
     * @param $precio_comida		precio de la comida
     * @param $categoria_comida		categoria de la comida
	 * @param $restriccion_comida	restriccion de la comida
	 * @param $descripcion_comida   descripcion de la comida
	 * @param $ingredientes_comida	ingredientes de la comida
	 * @param $foto_comida			foto de la comida
     * @return PDOStatement
     */
    public static function insert(
		$nombre_comida,
        $precio_comida,
		$categoria_comida,
		$restriccion_comida,
		$descripcion_comida,
		$ingredientes_comida,
        $foto_comida
    )
    {
        // Sentencia INSERT
        $comando = "INSERT INTO Comida ( " .
            " nombre_comida," .
			" precio_comida," .
			" categoria_comida," .
			" restriccion_comida," .
			" descripcion_comida," .
			" ingredientes_comida," .
            " foto_comida)" .
            " VALUES( ?,?,?,?,?,?,?)";
        // Preparar la sentencia
        $sentencia = Database::getInstance()->getDb()->prepare($comando);
        return $sentencia->execute(
            array(
				$nombre_comida,
                $precio_comida,
				$categoria_comida,
				$restriccion_comida,
				$descripcion_comida,
				$ingredientes_comida,
				$foto_comida
            )
        );
    }
    /**
     * Eliminar el registro con el identificador especificado
     *
     * @param $id_comida identificador de la Comida
     * @return bool Respuesta de la eliminación
     */
    public static function delete($id_comida)
    {
        // Sentencia DELETE
        $comando = "DELETE FROM Comida WHERE id_comida=?";
        // Preparar la sentencia
        $sentencia = Database::getInstance()->getDb()->prepare($comando);
        return $sentencia->execute(array($id_comida));
    }
}
?>