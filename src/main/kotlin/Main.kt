import kotlin.concurrent.thread

class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            if (args.isEmpty() || args[0] == "server") {
                handleServer()

            } else {
                //llamo al handleClient
                 handleClient()
            }
        }

        private fun handleServer() {
            //inicia servidor
            val server = Server(3000)
            thread{server.run()}

            //espera por comandos (?)
            while (true){
                //lee user input
                when(readLine()){
                    //muestra los clientes conectados
                    "clients"->{
                        val clients = server.getOnlineClients()

                        if(clients.isEmpty()){
                            println("No clients")
                        }else{
                            println("Clients (${clients.size}):")
                            clients.forEach { println(it) }
                        }
                    }
                    "stop"->{
                        if(!server.stopped) {
                            server.stop()
                        }else{
                            println("Server already stopped")
                        }
                    }
                    "start"->{
                        if(server.stopped){
                            thread{server.run()}
                        }else{
                            println("Server already running")
                        }

                    }
                    "info"->{
                        val threadSet: Set<Thread> = Thread.getAllStackTraces().keys
                        println("Threads (${threadSet.size}):")
                        threadSet.forEach{println(it)}
                    }
                    else->println("Not a valid command")
                }
            }
        }

        private fun handleClient(){

            var client: Client? = null

            //mensajito para el user
            val usageMsg = "Usage:\n connect [hostname] [port], x to exit"
            println(usageMsg)

            //regex que matchea start + ip/hostname + port
            val regex = Regex("^connect (.+) ([0-9]{1,5})$", RegexOption.IGNORE_CASE)

            //bucle infinito yey
            while(true){
                val userInput = readLine()!!

                if(userInput == "x") {//se cierra la conexión si mandamos x
                    client?.close()
                }else if(client?.stopped == false && !userInput.startsWith(":")){
                    client.send(userInput)
                }else{
                    //buscamos si lo que ingresó el usuario es válido
                    val matches = regex.find(userInput)
                    //si es valido iniciamos el cliente
                    if(matches?.groupValues?.size == 3){
                        //crea cliente
                        client = Client(matches.groupValues[1], matches.groupValues[2].toInt())
                        //lo inicia en su propio thread
                        thread{client.run()}
                    }else{

                        //si no es válido le ponemos el mensajito
                        println(usageMsg)
                    }

                }


            }
        }
    }
}