-- Crear la base de datos
CREATE DATABASE MantenimientoCliente;
GO

USE MantenimientoCliente;
GO

-- Crear la tabla clientes
CREATE TABLE clientes (
    idCliente     INT IDENTITY(1,1) PRIMARY KEY,
    nombres       VARCHAR(100)  NOT NULL,
    apellidoPaterno VARCHAR(100) NOT NULL,
    apellidoMaterno VARCHAR(100) NOT NULL,
    fechaNacimiento DATE         NOT NULL,
    sexo          CHAR(1)       NOT NULL CHECK (Sexo IN ('M', 'F')),
    direccion     VARCHAR(200)  NULL,
    correoElectronico VARCHAR(150) NULL
);
GO

-- Insertar un registro de ejemplo
INSERT INTO clientes (nombres, apellidoPaterno, apellidoMaterno, fechaNacimiento, sexo, direccion, correoElectronico)
VALUES (
    'Juan Alva',
    'Peralta',
    'Alvarado',
    '1881-06-16',
    'M',
    'Av. Siempreviva 742 - Springfield',
    'juan_peralta@test.com'
);
GO

-- Verificar el registro insertado
SELECT 
    idCliente,
    nombres,
    apellidoPaterno,
    apellidoMaterno,
    CONVERT(VARCHAR, fechaNacimiento, 103) AS fechaNacimiento,  -- Formato DD/MM/YYYY
    CASE sexo WHEN 'M' THEN 'Masculino' ELSE 'Femenino' END AS sexo,
    direccion,
    correoElectronico AS correo
FROM clientes;
GO
-- Stored Procedure para Eliminar Cliente
CREATE PROCEDURE sp_EliminarCliente
    @idCliente INT
AS
BEGIN
    SET NOCOUNT ON;

    -- Verificar que el cliente existe
    IF NOT EXISTS (SELECT 1 FROM clientes WHERE idCliente = @idCliente)
    BEGIN
        RAISERROR('El cliente con ID %d no existe.', 16, 1, @idCliente);
        RETURN;
    END

    -- Eliminar el cliente
    DELETE FROM clientes
    WHERE idCliente = @idCliente;

    SELECT 'Cliente eliminado correctamente.' AS Mensaje;
END;
GO

-- Ejemplo de ejecución
EXEC sp_EliminarCliente @idCliente = 1;