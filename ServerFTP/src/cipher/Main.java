package cipher;

//Created by Heradocles and Mendez.

import java.net.*;
import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException{

        String help = "La lista de comandos es la siguiente: \n\n Put ( put 'direccion del archivo') Esto enviara un archivo al servidor. \n Get (get 'direccion del archivo) Esto recibira un archivo del servidor. \n Quit, para salir de la aplicacion \n Delete (delete 'nombre del archivo), elimina un archivo del servidor \n List (list), se usa para ver la lista de archivos disponibles en el servidor \n";

        //Usuarios registrados.
        String[][] Accounts={{"menor","beta"},{"mayor","papi"},{"diablon","bigcola"}};

        String user;

        //Para el servidor.

        Socket ElSocket = null;
        ServerSocket Server;
        ObjectOutputStream salida = null;
        ObjectInputStream entrada = null;

        String Uservalido="no";


        try {

            System.out.println("\n>Iniciando servidor.");

            Server=new ServerSocket(9000);

            System.out.println("\n>Esperando conexcion de un cliente.");
            ElSocket=Server.accept();
            System.out.println("\n>Conexcion con cliente exitosa.");
            System.out.println("\n>Esperando informacion del usuario.");

            salida=new ObjectOutputStream(ElSocket.getOutputStream());
            entrada=new ObjectInputStream(ElSocket.getInputStream());

            while (Uservalido=="no") {

                //Recibir user del cliente.

                user = (String) entrada.readObject();

                System.out.println(""+user);

                String partesUP[] = user.split("#");

                System.out.println(""+partesUP[0]);
                System.out.println(""+partesUP[1]);

                for (int i = 0; i < Accounts.length; i++) {
                    if (partesUP[0] == Accounts[i][0]) {
                        if (partesUP[1] == Accounts[i][1]){
                            Uservalido = "yes";}
                    }
                }

                salida.writeObject(""+Uservalido);

                System.out.println(""+Uservalido);
            }

        } catch (Exception e ) {
            System.out.println(e);
        }finally {
            entrada.close();
            salida.close();
        }
    }
}

