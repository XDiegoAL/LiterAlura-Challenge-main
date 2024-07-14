<h1 align="center"> Literalura App </h1>


<p align="center">
  <img src="https://github.com/BogdanRivera/LiterAlura-Challenge/assets/121648408/d619d2ce-b997-4514-b510-254c03d3dec8" alt="Logo proyecto">
</p>

   <p align="center">
   <img src="https://img.shields.io/badge/STATUS-FINALIZADO-green">
   </p>
   
# Descripción del proyecto
Este proyecto es una aplicación de gestión de libros que utiliza Spring Boot y JPA para interactuar con una base de datos PostgreSQL. La aplicación permite buscar y almacenar libros obtenidos desde la API de Gutendex, gestionar autores y sus obras, y realizar análisis estadísticos sobre la biblioteca.

## Funcionalidades

### Gestión de Libros y Autores
- **Agregar Libros**: Permite agregar libros obtenidos desde la API de Gutendex a la base de datos.
- **Relación Libro-Autor**: Cada libro puede tener un único autor, mientras que un autor puede tener múltiples libros.
- **Evitar Duplicados**: La aplicación verifica si un libro con el mismo título y autor ya existe antes de agregarlo a la base de datos.

### Estadísticas
- **Número Total de Libros**: Calcula el número total de libros en la base de datos.
- **Libros por Categoría**: Cuenta el número de libros en cada categoría.
- **Promedio de Descargas**: Calcula el promedio de descargas por libro.
- **Libros más Descargados**: Obtiene una lista de los libros con el mayor número de descargas.

- ### Consultas Personalizadas
- **Buscar libros registrados**: Encuentra autores cuya fecha de nacimiento y muerte se encuentre entre dos valores ingresados por el usuario.
- **Buscar Autores por Nombre**: Permite buscar autores cuyo nombre contenga una palabra clave, sin importar mayúsculas o minúsculas.
- **Buscar Autores por Rango de Fechas de Vida**: Encuentra autores cuya fecha de nacimiento y muerte se encuentre entre dos valores ingresados por el usuario.
- **Buscar Libros por Idioma**: Encuentra libros que coincidan con el idioma ingresado por el usuario.

## Tecnologías Utilizadas
- **Spring Boot**: Para la configuración y desarrollo de la aplicación.
- **Spring Data JPA**: Para la persistencia de datos en PostgreSQL.
- **PostgreSQL**: Base de datos relacional.
- **Gutendex API**: Fuente de datos para obtener información sobre libros.

## Instalación

1. Clona el repositorio:
   ```bash
   git clone https://github.com/tu-usuario/nombre-del-proyecto.git
   cd nombre-del-proyecto
   ```
1. Clona el repositorio:
   ```bash
   git clone https://github.com/tu-usuario/nombre-del-proyecto.git
   cd nombre-del-proyecto
   ```
2. Configura correctamente los datos de 'application.properties':
     ```bash
    spring.application.name=LiterAlura
    
    spring.datasource.url = jdbc:postgresql://${DB_HOST}/${DB_NAME}
    spring.datasource.username=${DB_USER}
    spring.datasource.password=${DB_PASSWORD}
    spring.datasource.driver-class-name=org.postgresql.Driver
    hibernate.dialect=org.hibernate.dialect.HSQLDialect

    spring.jpa.hibernate.ddl-auto = update
    ```
3. Abre el proyecto con tu IDE favorito (IntelliJ recomendado) y ejecuta el archivo 'ChallengeApplication' en /src/main/java/com/literalura/challenge.

## Ejemplo de respuesta JSON dentro de la aplicación: 
```
{
    "titulo": "El Quijote",
    "categoria": "Ficción",
    "lenguaje": "ES",
    "nDescargas": 1000,
    "autor": {
        "nombre": "Miguel de Cervantes",
        "anioNacimiento": 1547,
        "anioMuerte": 1616
    }
}

```

# Capturas de la aplicación
![inicio](https://github.com/BogdanRivera/LiterAlura-Challenge/assets/121648408/38b66819-9cbe-40db-a61f-501a5b89ee55)
![estadística](https://github.com/BogdanRivera/LiterAlura-Challenge/assets/121648408/1b7e8d8f-2721-4890-99c5-8f891d314663)
![librosRegistrados](https://github.com/BogdanRivera/LiterAlura-Challenge/assets/121648408/ca83967c-d022-4967-8b56-290423f19bc3)


# Contribución 
Las contribuciones son bienvenidas. Si tienes duda puedes contactarme directamente a mi correo: bogdanrivera@gmail.com

   
