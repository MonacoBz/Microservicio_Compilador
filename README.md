# Microservicios

En este proyecto realicé un pequeño compilador, el cual muestra en la web una lista de los tokens y un texto con más información del resultado.

El compilador se basa en una arquitectura de microservicios, realizado en **Java Spring Boot**, está compuesto por 6 microservicios los cuales tienen metricas para poder ver la información de cada uno, los microservicios son:

- **eureka-server**
- **gateway**
- **compilador**
- **lexema**
- **parser**
- **semantica**
## eureka-server

Este microservicio actúa como **Service Discovery**. Su función es monitorear el estado de los demás microservicios, permitiendo saber cuáles están activos y en qué puerto se encuentran.

Los microservicios se registran en Eureka mediante **Feign Client** y envían "latidos" (heartbeats) cada 30 segundos al `eureka-server` para indicar que siguen en funcionamiento. Si un servicio deja de enviar estos latidos, Eureka lo marcará como inactivo.

## gateway

Este microservicio actúa como un **API Gateway**, encargado de manejar todas las solicitudes HTTP entrantes y redirigirlas al microservicio correspondiente según la ruta de la petición.

Se puede pensar en el gateway como un **guarda de seguridad** que controla el acceso a los distintos microservicios, asegurándose de que cada solicitud llegue al servicio adecuado.

## compilador

Este microservicio es el encargado de procesar la lógica principal del compilador. Recibe el **body** de la solicitud HTTP con el código fuente y coordina la ejecución de los demás microservicios (lexema, parser, semántica) para analizar y procesar dicho código.

Finalmente, retorna la respuesta estructurada con la información del análisis solicitado.

<img width="673" height="191" alt="image" src="https://github.com/user-attachments/assets/b8e5aa6a-e87d-40a0-b1d0-6643ee3f53bd" />


## lexema

Este microservicio se encarga de procesar el texto de entrada, fragmentándolo para generar tokens. Los tokens se clasifican según palabras reservadas y otros criterios léxicos.

Al final, retorna una lista con todos los tokens identificados.

## semantico

Este microservicio verifica que el texto no contenga errores semánticos y que la sintaxis sea correcta, utilizando un árbol AST descendente. 

Recibe la lista de tokens y va creando los nodos del árbol según la jerarquía de los operadores.

##parser 

