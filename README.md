# Prueba tecnica: Spring boot, JPA & GIT

Objetivo técnico de la prueba es validar los conocimientos en github, spring boot y conexiones
REST.

Objetivo de negocio es poder exponer un API para poder recupera la información del clima por
ciudad y guardar los resultados para poder consultar el número de hits de las últimas 10
ciudades.

## Cumplimientos

* Crear un API que exponga un endpoint para poder consultar el clima de una ciudad, este
endpoint solo aceptará el nombre de la ciudad para fines prácticos. El API deberá consumir el
API de OpenWeather y regresar la información.

* SI el API de OpenWeather se encuentra abajo debera regresar el último estatus consultando,
si no sea consultado deberá regresar una respuesta ( se deja a consideración que el
desarrollador proponga la respuesta)

* Exponer otro endpoint para poder regresar las úlitmas 10 ciudades y el número de consultas
que se han realizados

## Descripcion Arquitectura

Para almacenar ek historial de las consultas se utiliza Postgresql, por otro lado
la cache de las consultas correctas, que pueden ser enviadas en caso de no recuperar la informacion,
desde openweathermap se basa en redis.

## Requisitos: Ambiente

El ambiente de ejecucion, depende de los siguientes elementos:

* Postgresql
* Redis
* Java

Para simplificar el proyecto, se puede ejecutar utilizando *Docker* junto a *Docker Compose*,
para no requerir la instalacion manual de todo el ambiente.

## Configuraciones.

Las configuraciones, se cumplen en 2 sentidos, el primero corresponde al servicio, para poder
cumplir lo de responder desde cache, se introdujo una variable de ambiente, para definir el tiempo
de timeout de la consulta al api, la cual es:

* TIMEOUT: para poder ver el comportamiento de respuesta desde cache, utilizar valores bajos, los cuales
son en milisegundos.

* API_KEY: para pasar el api key de openweathermap, incluye uno por default

El segundo grupo corresponde a la configuracion de las bases de datos, por parte de las variables de entorno
se tiene para redis:

* REDIS_SERVER: para indicar el host del servidor.

para el caso de la DB SQL se utiliza el archivo application.properties que se encuentra dentro de resources del
proyecto Java.

> NOTA: para iniciar el proyecto con docker, no requiere realizar ninguna accion, ya se tienen valores por defecto
para todos los datos indicados.

## Iniciar el servicio

Para simplificar la puesta en marcha del proyecto, se indica solo lo requerido para poner en marcha el servicio
utilizando docker.

En la terminal ejecutar la siguiente instruccion, dentro de la carpeta del repositorio:

```
$ docker-compose up --remove-orphans
```

Esto inicia la compilacion de la imagen y a su vez, el inicio de las DB.

![inicio](https://raw.githubusercontent.com/3l3n01/spring-boot-jpa/main/images/inicio.png)

Si no ocurre algun inesperado, se mostrara el inicio de la app de spring boot

![spring](https://raw.githubusercontent.com/3l3n01/spring-boot-jpa/main/images/inicializacion.png)

se puede validar que todo funciona de forma correcta al consultar los conetedores en ejecucion.

```
$ docker ps
```
![contenedores](https://raw.githubusercontent.com/3l3n01/spring-boot-jpa/main/images/contenedores.png)


## Endpoint

Se exponen dos direcciones, en las cuales son las siguientes.

* [GET] /api/weather/city: La cual recibe por query param, el name de la ciudad de la cual se quiere consultar el clima
* [GET] /api/weather/city/top: Consulta el top 10 de las ciudades mas consultadas, a si como la cantidad de veces que se han realizado las busquedas.

> NOTA: 
Hibernate en su configuracion de DDL, esta como "CREATE", por lo que cada que ponga en marcha el proyecto, el contenido de la base de datos (el historial de consultas)
sera eliminado, por lo que puede tener como respuesta al consultar el TOP un arreglo vacio, si no hace consultas previas.


## OpenApi (Swagger)

El prouyecto implemento en lo mas basico la documentacion de openApi (swagger) y a su vez, se incluye el entorno visual para poder ver esta informacion
desde la siguiente direccion: http://localhost:8080/api/swagger-ui.html

![api](https://raw.githubusercontent.com/3l3n01/spring-boot-jpa/main/images/swagger.png)

