import kotlin.concurrent.thread

class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            if (args.isEmpty() || args[0] == "server") {
                handleServer()
                //handleClient()
            } else {
                //TODO acá va el cliente
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
    }
}