# Prueba - Ejercicio-BCI

---

### Resumen
En este microservicio se disponibiliza 4 endpoint para la creacion, consulta, actualizacion y borrado de usuarios.
Los datos son persistentes en una base de datos h2 la cual se mantiene en memoria.
Para el desarrollo se usó  Java 1.8 y springboot 2.7.15 y construido con gradle.
Además se utilizó groovy con el framework Spock para la creacion de test
---

### Requisitos
- Gradle
- JDK 1.8

---

## Comandos para ejecutar proyecto
1. Abrir una terminal y ubicarse en raíz de proyecto para ejecutar el siguiente comando:


       gradle build

2. Una vez completado el paso anterior, ubicarse en carpeta \build\libs y ejecutar el siguiente comando:


       java -jar EjercicioBCI-1.0.0-SNAPSHOT.JAR

3. Se levantará la aplicación en el siguiente path:


       http://localhost:8080

---

### Endpoints


<b>  1.POST /users/sign-up </b>

Este endpoint permite crear un usuario nuevo.
La estructura del body de la peticion debe ser la siguiente:



         {
            "name": "Alejandro Yañez",
            "email": "alejandro.yanezj@utem.cl",
            "password": "Password1234",
            "phones": [
                {
                    "number": "54360080",
                    "citycode": "9",
                    "contrycode":"56"
                }
            ]
        }

Notas:

      1. Los elementos name, email y password son requeridos.
      2. El elemento phones es opcional pero en caso de enviar un telefono todos sus campos son obligatorios.
      3. En caso de tener problema con la validacion del formato para email o contraseña leer el apartado de validaciones para comprobar o configurar la validacion.

<b>  2.GET /users/login </b>

Este endpoint permite obtener un usuario ya existente a partir del email y la contraseña que se usaron para el registro.
La estructura del body de la peticion debe ser la siguiente:

        {
            "email":"alejandro.yanezj@utem.cl",
            "password":"Password1234"
        }


<b>  3.PUT /users/update</b>

Este endpoint permite actualizar el usuario. 
Para realizar modificaciones es necesario enviar el siguiente header:

    "Authorization":"Bearer token"

El token del usuario puede ser obtenido tanto en el registro como en el login.

La estructura del body es:

    {
        "id":"358ac550-2b33-4110-91a0-d7a0648a7041",
        "name": "Alejandro Yañez Jaramillo",
        "isActive": true,
        "email":"alejandro.yanezj@utem.cl",
        "phones":[{
                "number": "54360080",
                "citycode": "9",
                "contrycode": "56"
        }]
    }

Notas:

    1. El id es un campo obligatorio y debe coincidir con el token enviado. 
    2. Ningun campo es obligatorio pero en caso de ser enviado, no puede venir en blanco o nulo.
    3. Cuando se envian telefonos, se agregan todos los telefonos que el usuario no haya tenido registrados
       y se borran todos los telefonos que tenga registrados pero que no sean enviados.
    
<b>  4.DELETE /users/delete </b>

Este endpoint permite eliminar un usuario a partir de su id.
Para eliminar un usuario es necesario enviar el siguiente header:

    "Authorization":"Bearer token"

El token del usuario puede ser obtenido tanto en el registro como en el login.

La estructura del body es:

    {
        "id":"3258ccd9-aa30-4308-a680-74a4dfbc244d"
    }

---

### Validaciones
Para la validacion de email y contraseña se utilizan Regex, los cuales pueden ser modificados en el archivo
application.yml en la ruta /src/main/resources/ del proyecto y se encuentran bajo el siguiente nombre

validation.regex.email

validation.regex.password

Una vez realizado el cambio es necesario correr el build del proyecto nuevamente para que los cambios sean aplicados



---

### H2
Cuando el proyecto está levantado se puede visualizar la base de datos en la siguiente ruta:


      http://localhost:8080/h2-console


las credenciales son:

    - database name: testdb
    - username: user
    - password: password