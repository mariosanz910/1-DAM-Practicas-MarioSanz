# Akihabara DB  
## Sistema de Gestión de Tienda Otaku y Clientes  
Proyecto Java + MySQL + Swing + IA

---

## Descripción general

Este proyecto es una aplicación Java con interfaz gráfica que permite gestionar productos, clientes y pedidos de una tienda temática Otaku. Utiliza JDBC para conectarse a una base de datos MySQL y ofrece un conjunto de funcionalidades CRUD completas.

---

## Características principales

- Gestión de productos: agregar, listar, modificar y eliminar.
- Gestión de clientes: registrar, editar, buscar y eliminar.
- Búsqueda de clientes por nombre.
- Interfaz gráfica con Swing (menús intuitivos).
- Base de datos relacional con MySQL.
- Uso del patrón DAO para acceso a datos.
- Carga de datos de prueba automática al iniciar.

---

## Tecnologías utilizadas

- Java 17  
- MySQL 8.x (Base de datos: `akihabara_db`)  
- JDBC  
- API IA de OpenRouter (para descripciones y categorización)

---

## Objetivo del proyecto

El propósito principal de esta aplicación es permitir la gestión completa de productos otaku mediante una interfaz de consola. El sistema permite realizar operaciones CRUD (crear, leer, actualizar y eliminar) sobre los productos, así como búsquedas por nombre o ID. Además, incorpora funcionalidades innovadoras basadas en inteligencia artificial, como la generación automática de descripciones y la sugerencia de categorías.

## Resumen del proyecto:


Este proyecto ha sido desarrollado como parte de las prácticas del primer curso de Desarrollo de Aplicaciones Multiplataforma (DAM) en el Campus Emprende de Humanes. 
Se trata de una aplicación de consola en Java que permite gestionar un catálogo de productos otaku, conectada a una base de datos MySQL. 
El desarrollo se ha realizado en base a un documento PDF facilitado por los instructores, en el que se detallaban los requisitos funcionales y técnicos de la práctica.

---

## Arquitectura y componentes

### Conexión a Base de Datos  
**`Conexion.java`**  
Gestiona la conexión con la base de datos `akihabara_db` usando JDBC. Centraliza los parámetros de conexión (URL, usuario, contraseña) y expone un método estático `getConnection()`.

### Modelo de Datos  
**`ProductoOtaku.java`**  
Clase que representa el modelo de producto, incluyendo atributos como ID, nombre, categoría, precio y stock. Contiene constructores, getters y setters.

### Acceso a Datos  
**`ProductoDAO.java`**  
Contiene todas las operaciones CRUD sobre los productos en la base de datos. Utiliza SQL parametrizado para evitar inyecciones y mejora de seguridad. También incluye métodos para búsquedas parciales por nombre.

### Inicialización de Datos  
**`SetupDatos.java`**  
Carga automáticamente datos de prueba (figuras, mangas, pósters) si la base de datos está vacía. Muestra los productos en consola para verificación.

### Interfaz de Usuario (Vista)  
**`InterfazConsola.java`**  
Muestra un menú interactivo mediante un bucle y estructura `switch`. Permite agregar, listar, buscar, modificar y eliminar productos. Integra funciones de inteligencia artificial.

### Inteligencia Artificial  
**`FuncionesIA.java`**  
Incluye dos funciones principales:  
- `generarDescripcionIA(idProducto)`  
- `sugerirCategoriaIA(nombreProducto)`

**`LlmService.java`**  
Clase que gestiona las peticiones a la API de OpenRouter y construye prompts adecuados.

### Clase Principal  
**`MainApp.java`**  
Punto de entrada del proyecto. Lanza la inicialización de datos (`SetupDatos`) y la interfaz de usuario (`InterfazConsola`).

---

## Integración con IA

Se ha integrado OpenRouter, una API gratuita de inteligencia artificial, para automatizar tareas como la creación de descripciones de productos o la asignación de categorías, mejorando así la eficiencia y funcionalidad del sistema.

---

## Resumen académico

Este proyecto ha sido desarrollado como parte de las prácticas del primer curso de Desarrollo de Aplicaciones Multiplataforma (DAM) en el Campus Emprende de Humanes. El desarrollo se ha basado en un documento PDF proporcionado por los instructores que detallaba los requisitos funcionales y técnicos.

---

## Notas finales

Este proyecto es una excelente introducción a la arquitectura de aplicaciones Java con acceso a bases de datos y una primera aproximación a la integración de servicios de inteligencia artificial. El diseño es claro, modular y extensible, ideal para estudiantes que se inician en el desarrollo backend.

