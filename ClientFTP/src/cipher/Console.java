package cipher;

//Created by Heradocles and Mendez

import java.util.Scanner;

public class Console {

        public static void menu(){
        //Variables y demas.
        Scanner reader=new Scanner(System.in);
        String ip;
        String user;
        String clave;
        String comandoftp;
        String direccionarchivo;



        System.out.println(">Bienvenido. ");
        System.out.print("\n>Por favor indique su IP o el nombre de su maquina. \n>");
        ip=reader.nextLine();

        System.out.println("\n>Espere, por favor.");
        //Verificar conexcion.

        System.out.println(">Conexion establecida.");
        System.out.print("\n>Usuario: \n>");
        user=reader.nextLine();
        //Verificar usuario.

        System.out.print("\n>El usuario ingresado, es valido, por favor, ingrese su clave: \n>");
        clave=reader.nextLine();
        //Verificar clave.

        System.out.println("\n>Su clave es valida. ");

        while (true){
            System.out.print("\nftp>Esperando un comando.\nftp>");
            comandoftp=reader.nextLine();

            switch (comandoftp){

                case "get":
                    //Hacer el get
                    System.out.println("\nftp>El archivo deseado ha sido descargado.");
                    break;

                case "put":
                    //hacer el put
                    System.out.println("\nftp>El archivo deseado ha sido subido al servidor.");
                    break;

                case "list":
                    //hacer el list
                    System.out.printf("\nftp>La lista de archivos es la siguinte: ");
                    break;

                case "delete":
                    //comprobar si el archivo existe
                    //hacer el delete

                    System.out.println("\nftp>El archivo no se encuentra disponible.");
                    System.out.println("\nftp>El archivo no deseado, ha sido borrado.");
                    break;

                case "quit":
                    System.out.println("\nftp>Cerrando las conexciones, espere un momento.");
                    System.out.println("ftp>Hasta luego, gracias por usar nuestro intento de servidor FTP.");
                    System.exit(0);
                    break;

                case "help":
                    //traer help del servidor, pero por ahora... Te jodiste.

                    String help = "La lista de comandos es la siguiente: \n\n >Put ( put 'direccion del archivo') Esto enviara un archivo al servidor. \n >Get (get 'direccion del archivo) Esto recibira un archivo del servidor. \n >Quit, para salir de la aplicacion. \n >Delete (delete 'nombre del archivo), elimina un archivo del servidor. \n >List (list), se usa para ver la lista de archivos disponibles en el servidor.";
                    System.out.println("\nftp>"+help);
                    break;

                default:
                case "Ups":
                    System.out.println("\nftp>El comando no se encuentra disponible. Intente de nuevo.");
            }
        }
    }
}
