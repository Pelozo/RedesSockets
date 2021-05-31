# TP Redes Sockets :computer:

## Integrantes : Chiaradia, Velozo, Ortega, Nuñez

  INTEGRANTES| :two_men_holding_hands: :couple:
------------ | -------------
Ignacio Chiaradia | Leonardo Velozo
Silvania Ortega | Carlos Nuñez

***

> PARTE TEORICA

- **3. ¿Que es un socket?

Un socket es conocido como un tipo de software que actúa como un punto final que funciona estableciendo un enlace de comunicación de red bidireccional entre el extremo del servidor y el programa receptor del cliente.


- **4. ¿A qué capa del modelo TPC/IP pertenecen los sockets? ¿Porque? 

Pertenece a la capa de Transporte, ya que usa protocolos de esta capa para su funcionamiento. Por ejemplo, las propiedades de un socket dependen de las características del protocolo en el que se implementan. El protocolo más utilizado es la Transmisión Control Protocol. Cuando se implementan con el protocolo TCP, los sockets tienen las siguientes propiedades: Son orientados a la conexión ; Se garantiza la transmisión de todos los octetos sin errores ni omisiones; Se garantiza que todo octeto llegará a su destino en el mismo orden en que se ha transmitido.
Pero tambien se usa el el protocolo UDP, el cual es un protocolo no orientado a la conexión, sin garantía de entrega. En ningún caso se garantiza que llegue o que lleguen todos los mensajes en el mismo orden que se mandaron. Esto lo hace adecuado para el envío de mensajes frecuentes pero no demasiado importantes, como por ejemplo, un streaming de audio.




- **5. ¿Cómo funciona el modelo cliente-servidor con TCP/IP Sockets?**

El cliente servidor funciona cuando los usuarios invocan la parte cliente de la aplicación, que construye una solicitud para ese servicio y se la envía al servidor de la aplicación que usa TCP/IP como transporte.
El servidor recibe una solicitud, realiza el servicio requerido y devuelve los resultados en forma de una respuesta

- **6. ¿Cuales son las causas comunes por la que la conexión entre cliente/servidor falle?**

Las causas comunes por la que la conexión entre cliente/servidor falle son: 
Error 2200: El cliente no recibió una respuesta del servidor en un tiempo determinado (timeout); esto sucede solo si un límite de tiempo fue especificado. 
Error 2300: El servidor cerró la conexión.
Error 2310: El servidor se ha caído mientras intentaba procesar el Handshake Request (conexión con el cliente). La conexión se cerró.
Error 2315: El servidor recibió el Handshake Request (conexión con el cliente) y devolvió una respuesta del tipo non-IIOP que el cliente no puede procesar (El cliente recibe una respuesta en un lenguaje que no entiende).

- **7. Diferencias entre sockets UDP y TCP 


La principal diferencia entre ambos es que el UDP necesita que le entreguemos paquetes de datos que el usuario debe construir, mientras el TCP admite bloques de datos (cuyo tamaño puede ir desde 1 bytes hasta muchos K bytes, dependiendo de la implementación) que serán empaquetados de forma transparente antes de ser transmitidos. Existe además otra diferencia importante. Tanto los paquetes de datos UDP como los segmentos TCP (este es el nombre que reciben los paquetes TCP) pueden perderse (muy rara vez llegan al destino correcto con errores). 
Si un paquete se pierde el UDP no hace nada. Por el contrario, si un segmento se pierde el TCP lo retransmitirá, y este proceso durará hasta que el segmento ha sido correctamente entregado al host receptor, o se produzca un número máximo de retransmisiones.  Finalmente, en aplicaciones en tiempo real es necesario también tener en cuenta una cosa. En el UDP controlamos qué datos viajan en cada paquete. En el TCP esto no es posible porque el empaquetamiento es automático. De hecho, el TCP espera un tiempo prudencial a tener bastantes datos que transmitir antes de enviar un segmento ya que esto ahorra ancho de banda. Si es importante que los datos tarden el mínimo tiempo posible en llegar al receptor el UDP es la mejor opción. En este sentido se dice que el UDP tiene una menor latencia que el TCP.



> PARTE PRACTICA


