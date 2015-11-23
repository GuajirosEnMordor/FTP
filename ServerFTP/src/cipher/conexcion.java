package cipher;

//Created by Heradocles and Mendez.

import java.net.*;
import java.io.*;
import java.util.*;

public class conexcion {

    public static void main(String[] args) {

        String help = "La lista de comandos es la siguiente: \n\n Put ( put 'direccion del archivo') Esto enviara un archivo al servidor. \n Get (get 'direccion del archivo) Esto recibira un archivo del servidor. \n Quit, para salir de la aplicacion \n Delete (delete 'nombre del archivo), elimina un archivo del servidor \n List (list), se usa para ver la lista de archivos disponibles en el servidor \n";

        Socket SocketCliente = null;
        ServerSocket Server;

        try {

            System.out.println("\n>Iniciando servidor.");
            Server=new ServerSocket(9000);
            System.out.println("\n>Esperando conexcion de un cliente.");
            SocketCliente=Server.accept();
            System.out.println("Conexcion con cliente exitosa.");

        } catch (IOException e) {
            System.out.println(e);
        }
    }
}

