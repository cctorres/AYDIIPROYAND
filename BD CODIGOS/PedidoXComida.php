<?php
/**
 * Representa el la estructura de la tabla PedidoXComida
 * almacenadas en la base de datos
 */
require 'Database.php';
class PedidoXComida
{
    function __construct()
    {
    }
    /**
     * Retorna la tabla 'PedidoXComida'
     *
     * @return array Datos del registro
     */
    public static function getAll()
    {
        $consulta = "SELECT * FROM PedidoXComida";
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
     * Obtiene los campos de un Pedido con un identificador
     * determinado
     *
     * @param $id_pedido	 Identificador del Pedido
     * @return mixed
     */
    public static function getById($id_pedido, $id_comida)
    {
        // Consulta de la tabla Pedido
        $consulta = "SELECT id_pedido,
                            id_comida,
							cantidad_pedidoxcomida,
                             FROM PedidoXComida
                             WHERE id_pedido = ?,
							 WHERE id_comida = ?";
        try {
            // Preparar sentencia
            $comando = Database::getInstance()->getDb()->prepare($consulta);
            // Ejecutar sentencia preparada
            $comando->execute(array($id_pedido, $id_comida));
            // Capturar primera fila del resultado
            $row = $comando->fetchAll(PDO::FETCH_ASSOC);
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
     * @param $id_pedido       		identificador del pedido
     * @param $fecha_pedido			nueva fecha del pedido
     * @param $precio_pedido		nuevo precio del pedido
	 * @param $estado_pedido		nueva estado del pedido
	 * @param $cod_empleado_pedido  nuevo codigo del empleado encargado del pedido 
	 * @param $id_mesa_pedido 		nuevo id de la mesa donde está el pedido
	 */
    public static function update(
        $id_pedido,
        $fecha_pedido,
		$precio_pedido,
		$estado_pedido,
		$cod_empleado_pedido,
		$id_mesa_pedido
    )
    {
        // Creando consulta UPDATE
        $consulta = "UPDATE Pedido" .
            " SET fecha_pedido=?, precio_pedido=?, estado_pedido=?, cod_empleado_pedido=?, id_mesa_pedido=? " .
            "WHERE id_pedido=?";
        // Preparar la sentencia
        $cmd = Database::getInstance()->getDb()->prepare($consulta);
        // Relacionar y ejecutar la sentencia
        $cmd->execute(array($fecha_pedido $precio_pedido, $estado_pedido, $cod_empleado_pedido, $id_mesa_pedido, $id_pedido));
        return $cmd;
    }
    /**
     * Insertar un nuevo Pedido
     *
     * @param $fecha_pedido				fecha del pedido
     * @param $precio_pedido			precio del pedido
	 * @param $estado_pedido			estado del pedido
	 * @param $cod_empleado_pedido   	codigo del empleado encargado del pedido
	 * @param $id_mesa_pedido			identificador de la mesa donde está el pedido
     * @return PDOStatement
     */
    public static function insert(
        $fecha_pedido,
		$precio_pedido,
		$estado_pedido,
		$cod_empleado_pedido,
		$id_mesa_pedido
    )
    {
        // Sentencia INSERT
        $comando = "INSERT INTO Pedido ( " .
            " fecha_pedido" .
			" precio_pedido" .
			" estado_pedido" .
			" cod_empleado_pedido" .
			" id_mesa_pedido" .
            " VALUES( ?,?,?,?,?)";
        // Preparar la sentencia
        $sentencia = Database::getInstance()->getDb()->prepare($comando);
        return $sentencia->execute(
            array(
                $fecha_pedido,
				$precio_pedido,
				$estado_pedido,
				$cod_empleado_pedido,
				$id_mesa_pedido
            )
        );
    }
    /**
     * Eliminar el registro con el identificador especificado
     *
     * @param $id_pedido		 identificador del Pedido
     * @return bool Respuesta de la eliminación
     */
    public static function delete($id_pedido)
    {
        // Sentencia DELETE
        $comando = "DELETE FROM Pedido WHERE id_pedido=?";
        // Preparar la sentencia
        $sentencia = Database::getInstance()->getDb()->prepare($comando);
        return $sentencia->execute(array($id_pedido));
    }
}
?>