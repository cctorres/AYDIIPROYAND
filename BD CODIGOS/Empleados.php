<?php
/**
 * Representa el la estructura de las Empleados
 * almacenadas en la base de datos
 */
require 'Database.php';
class Empleados
{
    function __construct()
    {
    }
    /**
     * Retorna en la fila especificada de la tabla 'Empleado'
     *
     * @param $idEmpleado Identificador del registro
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
     * @param $idEmpleado Identificador del empleado
     * @return mixed
     */
    public static function getById($codEmpleado)
    {
        // Consulta de la tabla Empleado
        $consulta = "SELECT codEmpleado,
                            idEmpleado,
                            apeEmpleado
							telEmpleado
                             FROM Empleado
                             WHERE codEmpleado = ?";
        try {
            // Preparar sentencia
            $comando = Database::getInstance()->getDb()->prepare($consulta);
            // Ejecutar sentencia preparada
            $comando->execute(array($codEmpleado));
            // Capturar primera fila del resultado
            $row = $comando->fetch(PDO::FETCH_ASSOC);
            return $row;
        } catch (PDOException $e) {
            // Aquí puedes clasificar el error dependiendo de la excepción
            // para presentarlo en la respuesta Json
            return -1;
        }
    }
    /**
     * Actualiza un registro de la bases de datos basado
     * en los nuevos valores relacionados con un identificador
     *
     * @param $codEmpleado      identificador interno del empleado
     * @param $idEmpleado		nuevo numero identificacion
     * @param $apeEmpleado		nuevo apellido
	 * @param $telEmpleado		nuevo telefono
     
     */
    public static function update(
        $codEmpleado,
        $idEmpleado,
        $apeEmpleado,
		$telEmpleado
    )
    {
        // Creando consulta UPDATE
        $consulta = "UPDATE Empleado" .
            " SET idEmpleado=?, nomEmpleado=?, telEmpleado=? " .
            "WHERE codEmpleado=?";
        // Preparar la sentencia
        $cmd = Database::getInstance()->getDb()->prepare($consulta);
        // Relacionar y ejecutar la sentencia
        $cmd->execute(array($codEmpleado, $nomEmpleado, $telEmpleado, $idEmpleado));
        return $cmd;
    }
    /**
     * Insertar un nuevo empleado
     *
     * @param $idEmpleado		Identificación del nuevo registro
     * @param $apeEmpleado		Nombre dle nuevo registro
	 * @param $telEmpleado		Apellido del nuevo registro
     * @return PDOStatement
     */
    public static function insert(
        $idEmpleado,
        $apeEmpleado,
		$telEmpleado
    )
    {
        // Sentencia INSERT
        $comando = "INSERT INTO Empleado ( " .
            " idEmpleado," .
            " nomEmpleado," .
			" telEmpleado)" .
            " VALUES( ?,?,?)";
        // Preparar la sentencia
        $sentencia = Database::getInstance()->getDb()->prepare($comando);
        return $sentencia->execute(
            array(
                $idEmpleado,
				$apeEmpleado,
				$telEmpleado
            )
        );
    }
    /**
     * Eliminar el registro con el identificador especificado
     *
     * @param $codEmpleado identificador de la tabla Empleado
     * @return bool Respuesta de la eliminación
     */
    public static function delete($codEmpleado)
    {
        // Sentencia DELETE
        $comando = "DELETE FROM Empleado WHERE codEmpleado=?";
        // Preparar la sentencia
        $sentencia = Database::getInstance()->getDb()->prepare($comando);
        return $sentencia->execute(array($codEmpleado));
    }
}
?>