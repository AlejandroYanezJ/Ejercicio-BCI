# Prueba - Ejercicio-BCI

---

### Resumen
En este microservicio se disponibiliza un endpoint para la creacion y persistencia de usuarios en una base de datos h2.
Fue desarrollado en Java 1.8 y springboot 2.7.15 y construido con gradle. 
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

### Endpoint

Esta aplicacion cuenta con un unico endpoint el cual sirve para crear usuarios

<b>  METHOD POST /users/sign-up </b>
    
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
      3. En caso de tener problema con la validacion del formato para email o contraseña leer el siguiente punto para comprobar o configurar la validacion.
---

### Configuracion validaciones
Para la validacion de email y contraseña se utilizan Regex, los cuales pueden ser modificados en el archivo
/src/main/resources/application.yml del proyecto y se encuentran bajo el siguiente nombre

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
