import java.io.OutputStream
import java.net.ServerSocket
import java.net.Socket
import java.net.SocketException
import java.nio.charset.Charset
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.logging.Logger
import kotlin.concurrent.thread

class Server(private val serverPort: Int): Runnable{

    //var para tener el server socket
    private lateinit var server: ServerSocket
    //var para lista de usuarios conectados
    private val clients = mutableListOf<ClientHandler>()

    //var para saber si el server está corriendo o no
    var stopped: Boolean = false

    override fun run() {
        //crea el server, escuchando al puerto que se le pasó por param
        server = ServerSocket(serverPort)
        //imprime mensaje donde dice el puerto en el cual está corriendo
        println("Server running on port ${server.localPort}")

        //mantiene el server vivo
        stopped = false

        while (!stopped){
            try {
                //acepta un nuevo cliente
                val client = server.accept()
                //imprime mensaje con la ip del cliente
                println("Client connected: ${client.inetAddress.hostAddress}")
                //crea el handler del cliente
                val clientHandler = ClientHandler(client)
                //agrega el handler a la lista de clientes
                clients.add(clientHandler)
                //corre el handler en su propio thread
                thread {
                    clientHandler.run()
                }
            }catch(e: SocketException){
                println("Connection closed")
            }
        }
    }

    //devuelve lista de ips de los clientes conectados
    fun getOnlineClients():List<String>{
        return clients.filter {!it.stopped}.map {it.name}
    }

    fun stop(){
        //stop clients
        for(client in clients){
            if(!client.stopped)
                client.stop()
        }
        //clear client list
        clients.clear()
        //stop runnable
        stopped = true
        //close socket server
        server.close()
        println("Server stopped")
    }


    class ClientHandler(private val client: Socket){
        //input stream con lo que el cliente mande
        private val reader: Scanner = Scanner(client.getInputStream())
        //output stream para mandarle cosas al cliente
        private val writer: OutputStream = client.getOutputStream()

        var stopped: Boolean = false

        //name = ip
        val name = client.inetAddress.hostAddress

        fun run() {
            // Mensaje de bienvenida
            write("Hey bud. Try typing hello, ping or something. Type \"x\" to leave")
            val help="Comandos validos: ping, kotlin, js, port, hora y fecha, hello"
            write(help);
            //se queda esperando mientras esté corriendo
            while (!stopped) {
                //pueden pasar varias cosas raras, asi que por las dudas lo ponemos en un try.
                try {
                    //lee el input del usuario
                    val text = reader.nextLine()
                    //mostrar mensaje en consola
                    println("${client.inetAddress.hostName} ($name) : $text")

                    //actuar según mensaje
                    when(text.toLowerCase()){
                        //salir
                        "x", "exit", "bye" ->{
                            write("k, bye")
                            shutdown()
                        }
                        //responder ping
                        "ping"->write("pong")

                        //saludar
                        "hello","hi", "hey", "hola", "holis", "ciao", "oi"->write("Hey!")

                        "kotlin", "js" ->write("Kotlin > JS")

                        "port"->write(client.localPort.toString())

                        "hora y fecha"->{
                            val current = LocalDateTime.now()

                            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
                            val formatted = current.format(formatter)

                            write("la fecha y hora actual querido es: $formatted")
                        }
                        "help", "ayuda", "h", "sos"->write(help)
                        //idk
                        else->write("Not a valid command my dude")
                    }

                } catch (ex: Exception) {
                    shutdown()
                }

            }
        }

        private fun write(message: String) {
            writer.write((message + System.lineSeparator()).toByteArray(Charset.defaultCharset()))
            println("Server to $name: $message")
        }


        private fun shutdown() {
            stopped = true
            client.close()
        }

        fun stop(){
            write("server is closing connection")
            shutdown()
        }

    }

}