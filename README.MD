# Se utilizo:
* Java 8
* Maven 3.6.1
* Eclipse STS (Spring Tool Suite) 4.
* Spring Boot 1.5.6
* Se puede usar otro eclipse, por ejemplo tambien lo probe con eclipse "201903" que es una de las ultimas versiones.

# Ambiente
* Tener configurado java, mvn, y eclipse.
* Descargar el proyecto de github.
* Descomprimir el proyecto "callcenter-master.zip" en el workspace y dejarlo con el nombre de "callcenter"

#### Desde eclipse importar el proyecto como "Existing Maven Projects"
##### De ser necesario se puede Correr por consola (siempre estando parado dentro del proyecto):
* mvn clean
* mvn eclipse:clean
* mvn eclipse:eclipse
* mvn compile
* Hacer un F5 "refresh" sobre el projecto.

#### Pruebas
##### Una vez teniendo el proyecto listo en eclipse, podemos:
* ejecutar el main CallCenter.java (modo run/debug), esto me va a ejecutar el programa.
* ejecutar los test desde la clase Tests.java (modo run/debug en JUnit).
* O correr por consola mvn install (ejecuta los test) ó mvn install -DskipTests dependiendo si quiero ejecutar o no los tests.
* * Esto me va a generar el JAR dentro de la carpeta "target"
* * Y asi vamos a poder ejecutar el programa desde la consola con java -jar target/callcenter.-0.0.1-SNAPSHOT.jar


## Solución

Para no tener que andar creando hilos de forma manualmente con Thread, Semaforos, etc. 
Se utilizo la interfaz ExecutorService y PriorityBlockingQueue para:
* Manejar adecuadamente la concurrencia que ya me brinda java.
* Tener una correcta priorizacion en el orden de atencion de los empleados, de acuerdo a su tipo.
* Y que las llamadas queden en espera hasta que algun empleado se libere y pueda atenderla.
* Podria decirse que con esto estan cubiertos, tanto el caso base principal que se pide, como tambien todos los puntos extras/plus.

#### CallCenter.java

Es el main que inicia el Call Center.

### Aclaraciones
La codificación se realizo con un mix tanto en español como en ingles.

Cabe aclarar que por defecto:
* La cantidad de empleados que se crean siempre es 10. 7 OPERADORES, 2 SUPERVISORES y 1 DIRECTOR.
* La cantidad de recursos/telefonos que se crean es 10. Pero se puede elegir otra cantidad en particular, segun se necesite '*'.
* La cantidad de llamadas minimas que se crean es 10. Pero se puede elegir otra cantidad en particular, segun se necesite '*'.
* La duracion de las llamadas es aleatoria entre 5 y 10. Pero se puede elegir otra duración en particular, segun se necesite '*'.
* '* Como se puede ver en los diferentes test'.

### Test Creados

test10Llamados : Representa el caso de ejemplo minimo solicitado en la consigna.

testCallsQtyResourcesQtyRamdon: Representa el mismo funcionamiento que CallCenter.java.

test20Llamados: Representa el caso en el que entran más de 10 llamadas.
Se atienden las primeras 10 llamadas, y las otras 10 quedan encoladas para ser atendidas a medida que se van liberando empleados.

testLlamadaSinAtender: Representa el caso en el cual una llamada nunca es atendida/completada satisfactoriamente, ya sea por que nunca se libero un empleado o porque la llamada quedo fuera del rango horario de atención del Call Center.

testPriorizacionDeEmpleados: lleva a cabo la validacion de la prioridad en el orden de atencion de los empleados.


##### Diagrama

![alt text](https://github.com/marcelomr7/callcenter/raw/master/src/main/resources/Class.png)
