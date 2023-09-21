--//////////////////////////////////
--// CREACION DE LA BASE DE DATOS //
--//////////////////////////////////
CREATE DATABASE CAPAS_JAVA_WEB_2023
GO

USE CAPAS_JAVA_WEB_2023
GO

CREATE TABLE CLIENTES(
	ID_CLIENTE int IDENTITY(1,1) NOT NULL PRIMARY KEY,
	NOMBRE varchar(80) NOT NULL,
	TELEFONO varchar(11) NULL,
	DIRECCION varchar(80) NULL,
)


CREATE TABLE PRODUCTOS(
	ID_PRODUCTO int IDENTITY(1,1) NOT NULL PRIMARY KEY,
	DESCRIPCION varchar(80) NOT NULL,
	PRECIOCOMPRA decimal(10,2) NOT NULL,
	PRECIOVENTA decimal(10,2) NOT NULL,
)

CREATE TABLE ENCABEZADO_FACTURA(
	ID_FACTURA int IDENTITY(1,1) NOT NULL PRIMARY KEY,
	FECHA DATETIME DEFAULT GETDATE(),
	ID_CLIENTE int NOT NULL,
	SUBTOTAL decimal(10,2) NOT NULL,
	IMPUESTO decimal(10,2) NOT NULL,
	MONTODESCUENTO decimal(10,2) NOT NULL,
	ESTADO VARCHAR(20) DEFAULT('PENDIENTE')
)

ALTER TABLE ENCABEZADO_FACTURA ADD CONSTRAINT CHK_ESTADO
CHECK(ESTADO IN('PENDIENTE','CANCELADA','VENCIDA','ANULADA'))

CREATE TABLE DETALLE_FACTURA(
	ID_FACTURA int NOT NULL,
	ID_PRODUCTO int NOT NULL,
	CANTIDAD INT,
	CONSTRAINT PK_DETALLE_FACTURA PRIMARY KEY (ID_FACTURA,ID_PRODUCTO)
)

ALTER TABLE ENCABEZADO_FACTURA ADD CONSTRAINT FK_ENCABEZADO_FACTURA
FOREIGN KEY (ID_CLIENTE) REFERENCES CLIENTES(ID_CLIENTE)

ALTER TABLE DETALLE_FACTURA ADD CONSTRAINT FK_DETALLE_FACTURA
FOREIGN KEY (ID_PRODUCTO) REFERENCES PRODUCTOS(ID_PRODUCTO)

ALTER TABLE DETALLE_FACTURA ADD CONSTRAINT FK_DETALLE_FACTURA_ENCABEZADO
FOREIGN KEY (ID_FACTURA) REFERENCES ENCABEZADO_FACTURA(ID_FACTURA)

/*Datos*/

insert into CLIENTES(NOMBRE,TELEFONO,DIRECCION)
				VALUES	('JOSEGE','8888-8888','SAN MIGUEL'),
						('MARIA','2222-2222','SAN RAMION'),
						('KARLA','3333-3333','PALMARES')
select * from clientes

insert into PRODUCTOS(DESCRIPCION,PRECIOCOMPRA,PRECIOVENTA)
				VALUES	('AROOZ',1000,1200),
						('AZUCAR',900,1100),
						('MANTECA',600,750),
						('CAFE',1600,1750)
select * from PRODUCTOS

insert into ENCABEZADO_FACTURA(ID_CLIENTE,SUBTOTAL, IMPUESTO,MONTODESCUENTO,ESTADO)
				VALUES	(1,0,5,5,'CANCELADA'),
						(2,0,5,5,'PENDIENTE'),
						(1,0,5,5,'VENCIDA')

select * from ENCABEZADO_FACTURA

insert into DETALLE_FACTURA(ID_FACTURA,ID_PRODUCTO, CANTIDAD)
				VALUES	(3,1,5),
						(3,2,5),
						(3,3,5)

select * from DETALLE_FACTURA


--////////////////////////////////
--// PROCEDIMIENTOS ALMACENADOS //
--////////////////////////////////
GO

-- Procedimiento # 1: Crear un procedimiento almacenado que permita ANULAR una factura,
-- las facturas solo se pueden anular si esta cancelada.

CREATE OR ALTER PROCEDURE AnularFactura
(
    @FacturaID INT,
    @MENSAJE VARCHAR(50) OUTPUT
)
AS
BEGIN
    -- Verifico si la factura está cancelada
    DECLARE @Estado VARCHAR(20)
    
    SELECT @Estado = ESTADO
    FROM ENCABEZADO_FACTURA
    WHERE ID_FACTURA = @FacturaID

    IF @Estado = 'CANCELADA'
    BEGIN
        -- Actualizo el estado de la factura a 'ANULADA'
        UPDATE ENCABEZADO_FACTURA
        SET ESTADO = 'ANULADA'
        WHERE ID_FACTURA = @FacturaID

        SET @MENSAJE = 'Factura anulada exitosamente.';
    END
    ELSE
    BEGIN
		-- Si no esta cancelada muestro el msj	
        SET @MENSAJE = 'La factura no puede ser anulada, ya que no está "CANCELADA".';
    END
END;
GO

-- Creo una factura en estado "CANCELADA"
INSERT INTO ENCABEZADO_FACTURA (ID_CLIENTE, SUBTOTAL, IMPUESTO, MONTODESCUENTO, ESTADO)
VALUES (1, 100.00, 5.00, 10.00, 'CANCELADA');
-- Declaro una variable para almacenar el mensaje de salida
DECLARE @Mensaje VARCHAR(50);

-- Llamo al procedimiento para anular la factura
EXEC AnularFactura @FacturaID = 1, @MENSAJE = @Mensaje OUTPUT;

-- Muestro el mensaje de salida
PRINT @Mensaje;

GO

-- Procedimiento # 2: Crear un procedimiento almacenado para buscar un cliente,
-- devolver un mensaje si el cliente existe o no existe.

CREATE OR ALTER PROCEDURE BuscarCliente
(
    @ClienteID INT,
    @MENSAJE VARCHAR(50) OUTPUT
)
AS
BEGIN
    -- Si el cliente existe
    IF EXISTS (SELECT * FROM CLIENTES WHERE ID_CLIENTE = @ClienteID)
    BEGIN
        SET @MENSAJE = 'El cliente existe en la base de datos.';
    END
    ELSE
	-- Si el cliente no existe
    BEGIN
        SET @MENSAJE = 'El cliente no existe en la base de datos.';
    END
END;
GO
-- Declaro una variable para almacenar el mensaje de salida
DECLARE @Mensaje VARCHAR(50);

-- Llamo al procedimiento para buscar un cliente existente
EXEC BuscarCliente @ClienteID = 1, @MENSAJE = @Mensaje OUTPUT;

-- Muestro el mensaje de salida
PRINT @Mensaje;
GO

-- Procedimiento # 3: Crear un procedimiento almacenado para eliminar un cliente.
-- El cliente no se puede eliminar si tiene facturas.

CREATE OR ALTER PROCEDURE EliminarCliente
(
    @ClienteID INT,
    @MENSAJE VARCHAR(100) OUTPUT
)
AS
BEGIN
    -- Verifico si el cliente tiene facturas
    IF EXISTS (SELECT 1 FROM ENCABEZADO_FACTURA WHERE ID_CLIENTE = @ClienteID)
    BEGIN
        SET @MENSAJE = 'No se puede eliminar el cliente porque tiene facturas pendientes.';
    END
    ELSE
    BEGIN
        -- Elimino el cliente si no tiene facturas
        DELETE FROM CLIENTES WHERE ID_CLIENTE = @ClienteID;
        SET @MENSAJE = 'Cliente eliminado exitosamente.';
    END
END;
GO
-- Declaro una variable para almacenar el mensaje de salida
DECLARE @Mensaje VARCHAR(100);

-- Llamo al procedimiento para eliminar un cliente
EXEC EliminarCliente @ClienteID = 1, @MENSAJE = @Mensaje OUTPUT;

-- Muestro el mensaje de salida
PRINT @Mensaje;
GO

-- Procedimiento # 4: Eliminar un detalle de factura, primero debe verificar que la factura existe,
-- solo puede eliminar un detalle de factura si la factura se encuentra pendiente, 
-- si elimina todos los detalles de la factura también la factura se debe eliminar de forma automática.

CREATE OR ALTER PROCEDURE EliminarDetalleFactura
(
    @FacturaID INT,
    @ProductoID INT,
    @MENSAJE VARCHAR(100) OUTPUT
)
AS
BEGIN
    -- Verifico si la factura existe
    IF NOT EXISTS (SELECT 1 FROM ENCABEZADO_FACTURA WHERE ID_FACTURA = @FacturaID)
    BEGIN
	-- sino existe
        SET @MENSAJE = 'La factura no existe.';
    END
    ELSE
    BEGIN
        -- Verifico si la factura está en estado "PENDIENTE"
        DECLARE @Estado VARCHAR(20)
        SELECT @Estado = ESTADO FROM ENCABEZADO_FACTURA WHERE ID_FACTURA = @FacturaID
		
        IF @Estado <> 'PENDIENTE'
        BEGIN
		-- si estado es diferente de PENDIENTE "osea no esta PENDIENTE"
            SET @MENSAJE = 'No se puede eliminar el detalle de factura porque la factura no está "PENDIENTE".';
        END
        ELSE
        BEGIN
            -- Elimino el detalle de factura
            DELETE FROM DETALLE_FACTURA WHERE ID_FACTURA = @FacturaID AND ID_PRODUCTO = @ProductoID;

            -- Verifico si se eliminaron todos los detalles de la factura
            IF NOT EXISTS (SELECT 1 FROM DETALLE_FACTURA WHERE ID_FACTURA = @FacturaID)
            BEGIN
                -- Si se eliminaron todos los detalles, elimino automáticamente la factura
                DELETE FROM ENCABEZADO_FACTURA WHERE ID_FACTURA = @FacturaID;
            END

            SET @MENSAJE = 'Detalle de factura eliminado exitosamente.';
        END
    END
END;
GO
-- Declaro una variable para almacenar el mensaje de salida
DECLARE @Mensaje VARCHAR(100);

-- Llamo al procedimiento para eliminar un detalle de factura
EXEC EliminarDetalleFactura @FacturaID = 1, @ProductoID = 2, @MENSAJE = @Mensaje OUTPUT;

-- Muestro el mensaje de salida
PRINT @Mensaje;

GO
-- Procedimiento # 5: Eliminar una factura: Solo se pueden eliminar las facturas pendientes,
-- debe también eliminar los detalles de dicha factura.

CREATE OR ALTER PROCEDURE EliminarFactura
(
    @FacturaID INT,
    @MENSAJE VARCHAR(100) OUTPUT
)
AS
BEGIN
    -- Verifico si la factura existe
    IF NOT EXISTS (SELECT 1 FROM ENCABEZADO_FACTURA WHERE ID_FACTURA = @FacturaID)
    BEGIN
	-- sino existe
        SET @MENSAJE = 'La factura no existe.';
    END
    ELSE
    BEGIN
        -- Verificar si la factura está en estado "PENDIENTE"
        DECLARE @Estado VARCHAR(20)
        SELECT @Estado = ESTADO FROM ENCABEZADO_FACTURA WHERE ID_FACTURA = @FacturaID

        IF @Estado <> 'PENDIENTE'
        BEGIN
		-- si estado es diferente de PENDIENTE "osea no esta PENDIENTE"
            SET @MENSAJE = 'No se puede eliminar la factura porque no está en estado "PENDIENTE".';
        END
        ELSE
        BEGIN
            -- Elimino los detalles de la factura
            DELETE FROM DETALLE_FACTURA WHERE ID_FACTURA = @FacturaID;

            -- Elimino la factura
            DELETE FROM ENCABEZADO_FACTURA WHERE ID_FACTURA = @FacturaID;

            SET @MENSAJE = 'Factura eliminada exitosamente.';
        END
    END
END;
GO
-- Declaro una variable para almacenar el mensaje de salida
DECLARE @Mensaje VARCHAR(100);

-- Llamo al procedimiento para eliminar una factura
EXEC EliminarFactura @FacturaID = 1, @MENSAJE = @Mensaje OUTPUT;

-- Muestro el mensaje de salida
PRINT @Mensaje;

GO
-- Procedimiento # 6: Insertar un cliente: Si el cliente existe debe modificar los datos,
-- si el cliente no existe debe ingresar el cliente.

CREATE OR ALTER PROCEDURE InsertarModificarCliente
(
    @ClienteID INT,
    @NOMBRE VARCHAR(80),
    @TELEFONO VARCHAR(11),
    @DIRECCION VARCHAR(80),
    @MENSAJE VARCHAR(100) OUTPUT
)
AS
BEGIN
    -- Verifico si el cliente existe por su ID
    IF EXISTS (SELECT 1 FROM CLIENTES WHERE ID_CLIENTE = @ClienteID)
    BEGIN
        -- Si el cliente existe, se actualizan sus datos
        UPDATE CLIENTES
        SET NOMBRE = @NOMBRE,
            TELEFONO = @TELEFONO,
            DIRECCION = @DIRECCION
        WHERE ID_CLIENTE = @ClienteID;

        SET @MENSAJE = 'Datos del cliente actualizados exitosamente.';
    END
    ELSE
    BEGIN
        -- Si el cliente no existe, se inserta como un nuevo cliente
        INSERT INTO CLIENTES (ID_CLIENTE, NOMBRE, TELEFONO, DIRECCION)
        VALUES (@ClienteID, @NOMBRE, @TELEFONO, @DIRECCION);

        SET @MENSAJE = 'Nuevo cliente insertado exitosamente.';
    END
END;
GO

-- Declaro una variable para almacenar el mensaje de salida
DECLARE @Mensaje VARCHAR(100);

-- Llamo al procedimiento para insertar o modificar un cliente
EXEC InsertarModificarCliente @ClienteID = 1, @NOMBRE = 'Jose Campos Chaves', @TELEFONO = '88990849', @DIRECCION = 'Naranjo', @MENSAJE = @Mensaje OUTPUT;

-- Muestro el mensaje de salida
PRINT @Mensaje;

GO

-- Procedimiento # 7: AGREGAR UN DETALLE DE FACTURA: Tome en cuenta que un detalle de factura
-- solo se puede agregar una factura pendiente, también debe tomar en cuenta que si un detalle
-- ya fue agregado a una factura no se puede agregar de nuevo registro, en este caso lo que
-- debe hacer es modificar la cantidad. Ejemplo: Si intenta agregar 10 unidades de arroz y
-- en esa misma factura ya hay 5 unidades debe guardar 15 unidades de arroz	

CREATE OR ALTER PROCEDURE AgregarDetalleFactura
(
    @FacturaID INT,
    @ProductoID INT,
    @Cantidad INT,
    @MENSAJE VARCHAR(100) OUTPUT
)
AS
BEGIN
    -- Verifico si la factura existe y está en estado "PENDIENTE"
    IF NOT EXISTS (SELECT 1 FROM ENCABEZADO_FACTURA WHERE ID_FACTURA = @FacturaID)
    BEGIN
	-- sino existe
        SET @MENSAJE = 'La factura no existe o no está en estado "PENDIENTE".';
    END
    ELSE
    BEGIN
        -- Verifico si el producto ya está registrado en la factura
        DECLARE @CantidadExistente INT
        SELECT @CantidadExistente = CANTIDAD
        FROM DETALLE_FACTURA
        WHERE ID_FACTURA = @FacturaID AND ID_PRODUCTO = @ProductoID

        IF @CantidadExistente IS NOT NULL
        BEGIN
            -- Si el producto ya está registrado, actualizo la cantidad
            UPDATE DETALLE_FACTURA
            SET CANTIDAD = @CantidadExistente + @Cantidad
            WHERE ID_FACTURA = @FacturaID AND ID_PRODUCTO = @ProductoID;

            SET @MENSAJE = 'Cantidad de producto actualizada en la factura.';
        END
        ELSE
        BEGIN
            -- Si el producto no está registrado, se inserta un nuevo detalle
            INSERT INTO DETALLE_FACTURA (ID_FACTURA, ID_PRODUCTO, CANTIDAD)
            VALUES (@FacturaID, @ProductoID, @Cantidad);

            SET @MENSAJE = 'Nuevo detalle de factura agregado exitosamente.';
        END
    END
END;
GO

-- Declaro una variable para almacenar el mensaje de salida
DECLARE @Mensaje VARCHAR(100);

-- Llamo al procedimiento para agregar un detalle de factura
EXEC AgregarDetalleFactura @FacturaID = 1, @ProductoID = 2, @Cantidad = 5, @MENSAJE = @Mensaje OUTPUT;

-- Muestro el mensaje de salida
PRINT @Mensaje;
GO


-- Procedimiento # 8: Guardar un producto nuevo si el producto existe debe modificarlo.

CREATE OR ALTER PROCEDURE GuardarModificarProducto
(
    @ProductoID INT,
    @DESCRIPCION VARCHAR(80),
    @PRECIOCOMPRA DECIMAL(10, 2),
    @PRECIOVENTA DECIMAL(10, 2),
    @MENSAJE VARCHAR(100) OUTPUT
)
AS
BEGIN
    -- Verifico si el producto existe con su ID
    IF EXISTS (SELECT 1 FROM PRODUCTOS WHERE ID_PRODUCTO = @ProductoID)
    BEGIN
        -- Si el producto existe, se actualizan sus datos
        UPDATE PRODUCTOS
        SET DESCRIPCION = @DESCRIPCION,
            PRECIOCOMPRA = @PRECIOCOMPRA,
            PRECIOVENTA = @PRECIOVENTA
        WHERE ID_PRODUCTO = @ProductoID;

        SET @MENSAJE = 'Datos del producto actualizados exitosamente.';
    END
    ELSE
    BEGIN
        -- Si el producto no existe, se inserta como un nuevo producto
        INSERT INTO PRODUCTOS (DESCRIPCION, PRECIOCOMPRA, PRECIOVENTA)
        VALUES (@DESCRIPCION, @PRECIOCOMPRA, @PRECIOVENTA);

        SET @MENSAJE = 'Nuevo producto insertado exitosamente.';
    END
END;
GO

-- Declaro una variable para almacenar el mensaje de salida
DECLARE @Mensaje VARCHAR(100);

-- Llamo al procedimiento para insertar o modificar un producto
EXEC GuardarModificarProducto @ProductoID = 1, @DESCRIPCION = 'Descripción del Producto', @PRECIOCOMPRA = 100.00, @PRECIOVENTA = 120.00, @MENSAJE = @Mensaje OUTPUT;

-- Muestro el mensaje de salida
PRINT @Mensaje;
GO


-- Procedimiento # 9: Guardar o modificar una factura, solo puede modificar las facturas pendientes.

CREATE OR ALTER PROCEDURE GuardarModificarFactura
(
    @FacturaID INT,
    @ID_CLIENTE INT,
    @SUBTOTAL DECIMAL(10, 2),
    @IMPUESTO DECIMAL(10, 2),
    @MONTODESCUENTO DECIMAL(10, 2),
    @ESTADO VARCHAR(20),
    @MENSAJE VARCHAR(100) OUTPUT
)
AS
BEGIN
    -- Verifico si la factura existe
    IF NOT EXISTS (SELECT 1 FROM ENCABEZADO_FACTURA WHERE ID_FACTURA = @FacturaID)
    BEGIN
	-- sino existe
        SET @MENSAJE = 'La factura no existe.';
    END
    ELSE
    BEGIN
        -- Verifico si la factura está en estado "PENDIENTE"
        DECLARE @EstadoAnterior VARCHAR(20)
        SELECT @EstadoAnterior = ESTADO FROM ENCABEZADO_FACTURA WHERE ID_FACTURA = @FacturaID

        IF @EstadoAnterior <> 'PENDIENTE'
        BEGIN
		-- no esta pendiente
            SET @MENSAJE = 'No se puede modificar la factura porque no está en estado "PENDIENTE".';
        END
        ELSE
        BEGIN
            -- Se Modifica la factura
            UPDATE ENCABEZADO_FACTURA
            SET ID_CLIENTE = @ID_CLIENTE,
                SUBTOTAL = @SUBTOTAL,
                IMPUESTO = @IMPUESTO,
                MONTODESCUENTO = @MONTODESCUENTO,
                ESTADO = @ESTADO
            WHERE ID_FACTURA = @FacturaID;

            SET @MENSAJE = 'Factura modificada exitosamente.';
        END
    END
END;
GO

-- Declaro una variable para almacenar el mensaje de salida
DECLARE @Mensaje VARCHAR(100);

-- Llamo al procedimiento para guardar o modificar una factura
EXEC GuardarModificarFactura @FacturaID = 1, @ID_CLIENTE = 2, @SUBTOTAL = 100.00, @IMPUESTO = 5.00, @MONTODESCUENTO = 10.00, @ESTADO = 'CANCELADA', @MENSAJE = @Mensaje OUTPUT;

-- Muestro el mensaje de salida
PRINT @Mensaje;
GO