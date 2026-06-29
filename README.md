# Mantenimiento de Clientes

Prueba tecnica. Es un CRUD basico de clientes con frontend en Angular y backend en Spring Boot, usando SQL Server como base de datos.

## Que hace la aplicacion

La pantalla principal tiene un formulario y una tabla con todos los clientes registrados.

Desde ahi puedes:
- Registrar un cliente nuevo
- Actualizar uno existente (haces clic en la fila de la tabla para cargar sus datos en el formulario)
- Eliminar el cliente seleccionado

Los campos del cliente son: nombres, apellido paterno, apellido materno, fecha de nacimiento, sexo, direccion y correo electronico. El formulario valida que los nombres solo tengan letras, que el correo sea valido y que no dejes campos vacios.

## Como funciona por dentro

El flujo es el tipico de una app con API REST:

1. Angular corre en el puerto 4200 y muestra la interfaz.
2. Cuando haces una accion (listar, crear, editar o borrar), el frontend llama a `http://localhost:8080/api/clientes`.
3. Spring Boot recibe la peticion, la procesa en el servicio y habla con la base de datos.
4. Para listar, registrar y actualizar usa JPA (Spring Data).
5. Para eliminar usa el stored procedure `sp_EliminarCliente` que esta en el script SQL.

La base de datos se llama `MantenimientoCliente` y la tabla es `clientes`.

## Estructura del proyecto

```
prueba/
├── bdd.sql                  # Script para crear la BD, tabla, datos de ejemplo y SP
├── demo/demo/               # Backend (Spring Boot)
└── mantenimiento-cliente/   # Frontend (Angular 5)
```

## Requisitos

- Java 8 o superior
- Maven
- Node.js y npm (para Angular necesitas una version compatible, yo use Node 14 con este proyecto)
- SQL Server corriendo en localhost puerto 1433
- Angular CLI 1.5 (`npm install -g @angular/cli@1.5.0`)

## Como ejecutarlo

### 1. Base de datos

Abre SQL Server Management Studio (o la herramienta que uses) y ejecuta el archivo `bdd.sql`. Eso crea la base de datos, la tabla, un cliente de prueba y el procedimiento almacenado para eliminar.

### 2. Backend

Entra a la carpeta del backend y revisa las credenciales en `application.properties`:

```
spring.datasource.username=sa
spring.datasource.password=12345678
```

Cambialas si tu SQL Server usa otro usuario y contrasena.

Luego levanta el servidor:

```
cd demo/demo
mvn spring-boot:run
```

Si todo va bien, la API queda en `http://localhost:8080`.

### 3. Frontend

En otra terminal:

```
cd mantenimiento-cliente
npm install
ng serve
```

Abre el navegador en `http://localhost:4200`.

Importante: el backend tiene que estar corriendo antes, si no el frontend no va a poder cargar los datos.

## Endpoints de la API

| Metodo | Ruta                    | Descripcion        |
|--------|-------------------------|--------------------|
| GET    | /api/clientes           | Lista todos        |
| POST   | /api/clientes           | Registra uno nuevo |
| PUT    | /api/clientes/{id}      | Actualiza por id   |
| DELETE | /api/clientes/{id}      | Elimina por id     |

## Notas

- El backend tiene CORS habilitado para que Angular pueda consumir la API sin problemas.
- La fecha en el formulario se maneja con un input tipo date y se convierte a formato DD/MM/YYYY antes de mandarla al backend.
- Si algo falla al conectar con la BD, revisa que SQL Server este activo y que el usuario/contrasena del `application.properties` sean los correctos.
