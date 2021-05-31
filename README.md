# TP Redes Sockets :computer:

## Integrantes : Chiaradia, Velozo, Ortega, Nuñez

  INTEGRANTES| :two_men_holding_hands: :couple:
------------ | -------------
Ignacio Chiaradia | Leonardo Velozo
Silvania Ortega | Carlos Nuñez

***

> PARTE TEORICA



- **5. ¿Cómo funciona el modelo cliente-servidor con TCP/IP Sockets?**

El cliente servidor funciona cuando los usuarios invocan la parte cliente de la aplicación, que construye una solicitud para ese servicio y se la envía al servidor de la aplicación que usa TCP/IP como transporte.
El servidor recibe una solicitud, realiza el servicio requerido y devuelve los resultados en forma de una respuesta

- **6. ¿Cuales son las causas comunes por la que la conexión entre cliente/servidor falle?**

Las causas comunes por la que la conexión entre cliente/servidor falle son: 
Error 2200: El cliente no recibió una respuesta del servidor en un tiempo determinado (timeout); esto sucede solo si un límite de tiempo fue especificado. 
Error 2300: El servidor cerró la conexión.
Error 2310: El servidor se ha caído mientras intentaba procesar el Handshake Request (conexión con el cliente). La conexión se cerró.
Error 2315: El servidor recibió el Handshake Request (conexión con el cliente) y devolvió una respuesta del tipo non-IIOP que el cliente no puede procesar (El cliente recibe una respuesta en un lenguaje que no entiende).


> PARTE PRACTICA

