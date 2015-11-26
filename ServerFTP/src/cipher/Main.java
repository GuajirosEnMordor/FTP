package cipher;

//Created by Heradocles and Mendez.

import com.sun.org.apache.xpath.internal.operations.*;

import java.lang.String;
import java.net.*;
import java.io.*;
import java.security.interfaces.ECKey;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {

        String help = "\n>La lista de comandos es la siguiente: \n\n Put ( put 'direccion del archivo') Esto enviara un archivo al servidor. \n Get (get 'direccion del archivo) Esto recibira un archivo del servidor. \n Quit, para salir de la aplicacion \n Delete (delete 'nombre del archivo), elimina un archivo del servidor \n List (list), se usa para ver la lista de archivos disponibles en el servidor \n";

        //Usuarios registrados.
        String[][] Accounts = {{"menor", "beta"}, {"mayor", "papi"}, {"diablon", "bigcola"}};

        String user;
        String comandocliente;
        String respuesta;
        String enviar;

        //Para el servidor.

        Socket ElSocket = null;
        ServerSocket Server = null;
        ObjectOutputStream salida = null;
        ObjectInputStream entrada = null;

        String Uservalido = "no";

        InetAddress ipcliente;


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

                if (Uservalido.equals("no")){
                    System.out.println("\n>Se ha detectado un intento de conexion con datos invalidos, se esperan nuevos datos para intentar una nueva conexion.");
                }

                salida.writeObject("" + Uservalido);
            }

            ipcliente = (InetAddress) entrada.readObject();
            System.out.println("\n>IP del cliente es: " + ipcliente);


            while (true) {

                System.out.println("\nftp>Esperando un comando.");

                comandocliente = (String) entrada.readObject();
                switch (comandocliente) {

                    case "list":

                        System.out.println("\nftp>El Cliente ha seleccionado list.");
                        System.out.println("\nftp>Recolectando informacion.");

                        String path="D:\\Programacion\\Proyectos ST\\FTP\\Archivos servidor";
                        File directorio=new File(path);
                        String [] files=directorio.list();

                        System.out.println("\nftp>Enviando informacion al cliente.");

                        salida.writeObject(files);

                        break;

                    case"delete":

                        System.out.println("\nftp>El cliente ha seleccionado delete.");

                        respuesta=(String)entrada.readObject();

                        System.out.println("\nftp>Se desea eliminar el siguiente archivo: \nftp>"+respuesta);

                        try {
                            File file = new File(respuesta);

                            if (file.delete()) {

                                System.out.println(file.getName() + "\nftp>El archivo no deseado, ha sido borrado.");
                                enviar="\nftp>El archivo no deseado, ha sido borrado.";
                                salida.writeObject(enviar);

                            } else {
                                System.out.println("\nftp>El archivo no se encuentra disponible.");
                                enviar="\nftp>El archivo no se encuentra disponible.";
                                salida.writeObject(enviar);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        break;

                    case "help":

                        System.out.println("\nftp>El cliente, ha seleccionado help.");

                        System.out.println("\nftp>Enviando informacion al usuario.");
                        salida.writeObject(help);
                        break;

                    case "quit":

                        System.out.println("\nftp>El cliente ha seleccionado salir.");
                        System.out.println("\nftp>Cerrando las conexiones, espere. ");

                        entrada.close();
                        salida.close();
                        ElSocket.close();
                        Server.close();

                        System.out.println("\nftp>Gracias por usar este intento de servidor ftp.");
                        System.exit(0);

                        break;

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

