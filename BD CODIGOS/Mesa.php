<?php
/**
 * Representa el la estructura de las Mesas
 * almacenadas en la base de datos
 */
require 'Database.php';
class Mesa
{
    function __construct()
    {
    }
    /**
     * Retorna la tabla 'Mesa'
     *
     * @return array Datos del registro
     */
    public static function getAll()
    {
        $consulta = "SELECT * FROM Mesa";
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
     * Obtiene los campos de una Mesa con un identificador
     * determinado
     *
     * @param $id_mesa	 Identificador de la mesa
     * @return mixed
     */
    public static function getById($id_mesa)
    {
        // Consulta de la tabla Mesa
        $consulta = "SELECT *
                             FROM Mesa
                             WHERE id_mesa = ?";
        try {
            // Preparar sentencia
            $comando = Database::getInstance()->getDb()->prepare($consulta);
            // Ejecutar sentencia preparada
            $comando->execute(array($id_mesa));
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
     * @param $id_mesa       	identificador interno del mesa
     * @param $estado_mesa		nuevo estado de la mesa
     * @param $id_pedido_mesa	nueva identificador del pedido asociado a la mesa
	
     
     */
    public static function update(
        $id_mesa,
        $estado_mesa,
        $id_pedido_mesa
    )
    {
        // Creando consulta UPDATE
        $consulta = "UPDATE Mesa" .
            " SET estado_mesa=?, id_pedido_mesa=? " .
            "WHERE id_mesa=?";
        // Preparar la sentencia
        $cmd = Database::getInstance()->getDb()->prepare($consulta);
        // Relacionar y ejecutar la sentencia
        $cmd->execute(array($estado_mesa, $id_pedido_mesa, $id_mesa));
        return $cmd;
    }
    /**
     * Insertar una nueva Mesa
     *
     * @param $estado_mesa				estado del nuevo registro
	 * @param $id_pedido_mesa			id del pedido de la mesa
     * @return PDOStatement
     */
    public static function insert(
        $estado_mesa,
        $id_pedido_mesa
    )
    {
        // Sentencia INSERT
        $comando = "INSERT INTO Mesa ( " .
            " estado_mesa," .
            " id_pedido_mesa)" .
            " VALUES( ?,?)";
        // Preparar la sentencia
        $sentencia = Database::getInstance()->getDb()->prepare($comando);
        return $sentencia->execute(
            array(
                $estado_mesa,
				$id_pedido_mesa
            )
        );
    }
    /**
     * Eliminar el registro con el identificador especificado
     *
     * @param $id_mesa identificador de la tabla Mesa
     * @return bool Respuesta de la eliminación
     */
    public static function delete($id_mesa)
    {
        // Sentencia DELETE
        $comando = "DELETE FROM Mesa WHERE id_mesa=?";
        // Preparar la sentencia
        $sentencia = Database::getInstance()->getDb()->prepare($comando);
        return $sentencia->execute(array($id_mesa));
    }
}
?>