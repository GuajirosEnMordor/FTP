package cipher;

import java.lang.String;
import java.net.*;
import java.io.*;

public class FTPconnections {

    public static void Conecctions() throws IOException{

            String help = "\nftp>La lista de comandos es la siguiente: \n\n Put ( put 'direccion del archivo') Esto enviara un archivo al servidor. \n Get (get 'direccion del archivo) Esto recibira un archivo del servidor. \n Quit, para salir de la aplicacion \n Delete (delete 'nombre del archivo), elimina un archivo del servidor \n List (list), se usa para ver la lista de archivos disponibles en el servidor \n";

            //Usuarios registrados.
            String[][] Accounts = {{"menor", "beta"}, {"mayor", "papi"}, {"diablon", "bigcola"}};

            String user;
            String comandocliente;
            String respuesta;
            String enviar;
            String archivo;


            Socket ElSocket = null;
            ServerSocket Server = null;
            ObjectOutputStream salida = null;
            ObjectInputStream entrada = null;

            Socket ElSocket1 = null;
            ServerSocket Server1 = null;

            InputStream entradafile = null;
            OutputStream salidafile = null;

            String Uservalido = "no";

            String ipcliente;

            try {

                System.out.println("\n>Iniciando servidor.");

                Server = new ServerSocket(9000);

                System.out.println("\n>Esperando conexion de un cliente.");
                ElSocket = Server.accept();
                System.out.println("\n>Conexion con cliente exitosa.");
                System.out.println("\n>Esperando informacion del usuario.");

                salida = new ObjectOutputStream(ElSocket.getOutputStream());
                entrada = new ObjectInputStream(ElSocket.getInputStream());

                while (Uservalido.equals("no")) {

                    //Recibir user del cliente.


                    user = (String) entrada.readObject();

                    String partesUP[] = user.split("#");

                    for (int i = 0; i < Accounts.length; i++) {
                        if (partesUP[0].equals(Accounts[i][0])) {
                            if (partesUP[1].equals(Accounts[i][1])) {
                                Uservalido = "yes";
                                System.out.println("\n>Usuario valido.");
                            }
                        }
                    }

                    if (Uservalido.equals("no")) {
                        System.out.println("\n>Se ha detectado un intento de conexion con datos invalidos, se esperan nuevos datos para intentar una nueva conexion.");
                    }

                    salida.writeObject("" + Uservalido);
                }

                ipcliente = (String) entrada.readObject();
                System.out.println("\n>IP del cliente es: " + ipcliente);

               Comandos.Funciones();

            } catch (Exception e) {
                e.printStackTrace();
            }

    }
}

