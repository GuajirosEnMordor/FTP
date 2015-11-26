package cipher;

//Created by Heradocles and Mendez.

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Console {

    public static void main(String[] args) throws IOException {

        //Variables y demas.

        Scanner reader = new Scanner(System.in);
        String ip;
        InetAddress iplocal;

        String uservalido = "no";

        String Directory;
        String DDirectory;
        String help;
        String respuesta;

        String user;

        String comandoftp;
        String direccionarchivo;

        ServerSocket Server = null;
        Socket Cliente = null;
        ObjectOutputStream salida = null;
        ObjectInputStream entrada = null;

        System.out.println(">Bienvenido. ");
        System.out.print("\n>Por favor indique su IP o el nombre de su maquina. \n>");
        ip = reader.nextLine();


        try {
            Cliente = new Socket("" + ip, 9000);
            salida = new ObjectOutputStream(Cliente.getOutputStream());
            entrada = new ObjectInputStream(Cliente.getInputStream());

            System.out.println("\n>Espere, por favor.");
            System.out.println(">Conexion establecida.");

//Verificacion.

            while (uservalido.equals("no")) {
                //Pedir clave y usuario, y separarlo.
                System.out.print("\n>Indique su usuario y clave (usuario#clave): \n>");
                user = reader.nextLine();

                salida.writeObject(user);

                uservalido = (String) entrada.readObject();

                if (uservalido.equals("no")) {
                    System.out.println("\n>Usuario invalido, intente de nuevo.");
                }
            }
            iplocal = Cliente.getInetAddress();
            salida.writeObject(iplocal);

            System.out.println("\n>Usuario valido, por favor espere.");

            while (true) {
                System.out.print("\nftp>Esperando un comando.\nftp>");
                comandoftp = reader.nextLine();

                switch (comandoftp) {

                    case "get":
                        //Hacer el get
                        System.out.println("\nftp>El archivo deseado ha sido descargado.");
                        break;

                    case "put":
                        //hacer el put
                        System.out.println("\nftp>El archivo deseado ha sido subido al servidor.");
                        break;

                    case "list":
                        System.out.print("\nftp>Por favor indique la direccion de la carpeta. \nconexion>");
                        Directory = reader.nextLine();

                        System.out.printf("\nftp>La lista de archivos es la siguinte:\n ");

                        File aDirectory = new File(Directory);

                        String[] filesInDir = aDirectory.list();

                        for (int i = 0; i < filesInDir.length; i++) {
                            System.out.println("\nftp> " + filesInDir[i]);
                        }
                        break;

                    case "delete":

                        salida.writeObject(comandoftp);

                        System.out.print("\nftp>Por favor indique el archivo(La direccion del mismo) que desea eliminar. \n>");
                        DDirectory = reader.nextLine();
                        salida.writeObject(DDirectory);

                        respuesta=(String)entrada.readObject();
                        System.out.println(""+respuesta);

                        break;

                    case "quit":

                        salida.writeObject(comandoftp);

                        System.out.println("\nftp>Cerrando las conexiones, espere un momento.");
                        Cliente.close();
                        entrada.close();
                        salida.close();

                        System.out.println("ftp>Hasta luego, gracias por usar nuestro intento de servidor FTP.");
                        System.exit(0);
                        break;

                    case "help":

                        salida.writeObject(comandoftp);
                        help = (String) entrada.readObject();

                        System.out.println(""+help);


                        break;

                    default:
                    case "Ups":
                        System.out.println("\nftp>El comando no se encuentra disponible. Intente de nuevo.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}





