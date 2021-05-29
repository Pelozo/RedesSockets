import java.io.IOException
import java.io.OutputStream
import java.net.Socket
import java.net.SocketException
import java.nio.charset.Charset
import java.util.*


class Client(private val serverIp: String, private val serverPort: Int): Runnable{

    private lateinit var client: Socket // define el socket que va a usar
    var stopped: Boolean = false //var para saber si el server está corriendo o no
    lateinit var reader: Scanner //input stream con lo que el cliente mande

    lateinit var writer: OutputStream //output stream para mandarle cosas al server

    override fun run() {
        println("Connecting to server $serverIp:$serverPort ...")

        try {
            client = Socket(serverIp, serverPort)
            reader = Scanner(client.getInputStream())
            writer = client.getOutputStream()
            println("Connected to server")
            stopped = false

            while(!stopped && reader.hasNextLine()){
                println("Server: ${reader.nextLine()}")
            }

        }catch(ex: Exception) {
            var cause: Throwable? = ex
            while (cause!!.cause != null && cause.cause !== cause) {
                cause = cause.cause
            }
            stopped = true
            println("Connection error: ${cause.message}")
        }
    }


    private fun write(msg: String){
        try{
            writer.write((msg + System.lineSeparator()).toByteArray(Charset.defaultCharset())) // mandar al servidor un mensaje
            println("You: $msg")
        }catch (err:Exception){
            println("Error al intentar enviar mensaje");
            stopped = true;
        }
    }

    // FUNCIONES EXTERNAS

    fun close(){
        stopped = true
        writer.flush()
        client.close()
        println("Connection closed")
    }

    fun send(msg: String){
        try {
            write(msg)
        }catch(ex: SocketException){
            close()
        }
    }


}