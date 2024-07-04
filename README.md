### README.md

# Examen Tecnico

Este proyecto es una aplicación Java que utiliza Maven como sistema de construcción. El proyecto incluye clases para gestionar clientes, cuentas y seguros, y métodos para realizar diversas operaciones con estos datos.

## Prerrequisitos

Antes de comenzar, asegúrate de tener instalados los siguientes programas en tu sistema:

- [Java JDK 8 o superior](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Apache Maven 3.6.0 o superior](https://maven.apache.org/download.cgi)

## Compilar el Proyecto

Para compilar el proyecto, navega hasta el directorio raíz del proyecto y ejecuta el siguiente comando:

> mvn clean compile

## Ejecutar las Pruebas Unitarias

Este proyecto incluye pruebas unitarias utilizando JUnit. Para ejecutar todas las pruebas, utiliza el siguiente comando:

> mvn test

## Ejecutar la Aplicación

Para ejecutar la aplicación principal, utiliza el siguiente comando:


> mvn exec:java -Dexec.mainClass="Main"


## Estructura del Proyecto

- `pom.xml`: Archivo de configuración de Maven.
- `src/main/java/resources`: Contiene las clases de recursos `Cliente`, `Cuenta` y `Seguro`.
- `src/main/java`: Contiene la clase principal `Main`.
- `src/test/java`: Contiene las pruebas unitarias para el proyecto.

