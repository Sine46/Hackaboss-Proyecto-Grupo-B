# API de Gestión de Pedidos

Backend REST desarrollado con Spring Boot para la gestión de pedidos, productos y stock en un sistema tipo restaurante / TPV.

### Descripción

Este proyecto simula un sistema real de gestión de pedidos donde se aplican reglas de negocio como:

- Control de stock en tiempo real.
- Cálculo automático del precio total
- Gestión de estados del pedido
- Validaciones de consistencia
### Tecnologías utilizadas:
- Java 17+
- Spring Boot
- Spring Data JPA (Hibernate)
- MySQL
- Maven
- Swagger / OpenAPI
- Postman

### Arquitectura
El proyecto sigue una arquitectura en capas: controller → service → repository → database
#### Principios aplicados
- Separación de responsabilidades
- Uso de DTOs (no exposición de entidades)
- Mappers para conversión de datos
- Manejo global de excepciones
- Uso de transacciones (@Transactional) 
### Funcionalidades
#### - Gestión de pedidos
- Crear pedidos
- Añadir productos a pedidos existentes
- Cambiar estado del pedido
- Obtener detalle de pedidos
#### - Gestión de productos
- Listar productos
- Consultar productos individuales
- Validación de productos activos
###  Lógica de negocio

- El precio total se calcula siempre en backend
- El stock se actualiza automáticamente
- No se pueden modificar pedidos finalizados
- Validación de stock antes de añadir productos
- Evita duplicados en líneas de pedido

### API Docs (Swagger)

Documentación interactiva disponible en:
http://localhost:8080/doc/swagger-ui.html

### Configuración básica
Para la configuración, dentro de application.properties: 
```
spring.datasource.url=jdbc:mysql://localhost:3306/pedidos_db
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_PASSWORD
```
Asi se selecciona la database que se quiere utilizar y el usuario y contraseña para acceder a esta.
My SQL tiene que estar activo y la database tiene que estar creada para que funcione el proyecto adecuadamente.
Para probar datos se adjunta un script de SQL(data.sql) en /resources para inicializar las bases de datos con datos validados y una colección de postman para probar los distintos endpoints.
### Autores
Proyecto desarrollado como parte del curso de Backend con enfoque profesional
- Cristina Senra Sanmiguel (Sine46)
- Laurentiu Patrunjel Constantin (ripergnd)
- Javier Cervera Centenero (Jacercen)
