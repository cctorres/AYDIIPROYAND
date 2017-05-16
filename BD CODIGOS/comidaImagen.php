<?php

try
{
    if (   isset($_POST["nombre_comida"]) 
		&& isset($_POST["precio_comida"])
		&& isset($_POST["categoria_comida"])
		&& isset($_POST["restriccion_comida"])
		&& isset($_POST["descripcion_comida"])
		&& isset($_POST["ingredientes_comida"])
		&& isset($_POST["foto_comida"])) 
    {
        $con = new PDO('mysql:host=sql10.freesqldatabase.com;dbname=sql10170497', 'user=sql10170497', 'pass=FYvYCmgR1a');
        $con->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
   
        $stmt = $con->prepare('INSERT INTO Comida (
													nombre_comida,
													precio_comida, 
													categoria_comida, 
													restriccion_comida,
													descripcion_comida,
													ingredientes_comida,
													foto_comida)
													VALUES ' .
																'(  :nombre_comida,
																	:precio_comida,
																	:categoria_comida,
																	:restriccion_comida,
																	:descripcion_comida,
																	:ingredientes_comida,
																	:foto_comida)');
        $rows = $stmt->execute( array( 
            ':nombre_comida'  => $_POST['nombre_comida'],
			':precio_comida'  => $_POST['precio_comida'],
			':categoria_comida'  => $_POST['categoria_comida'],
			':restriccion_comida'  => $_POST['restriccion_comida'],
			':descripcion_comida'  => $_POST['descripcion_comida'],
			':ingredientes_comida'  => $_POST['ingredientes_comida'],
            ':foto_comida' => base64_decode($_POST['foto_comida'])
        ));
    }
}
catch(PDOException $e)
{
    die('Error');
}
