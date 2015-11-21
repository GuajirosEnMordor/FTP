package cipher;

//Created by Heradocles and Mendez

import java.io.File;
import java.util.Scanner;

public class Console {

        public static void menu(){
        //Variables y demas.
        Scanner reader=new Scanner(System.in);
        String ip;
        String session="no";
        String Directory;
        String DDirectory;

        //Para la validacion en servidor.
        String[][] Accounts={{"menor","beta"},{"mayor","papi"},{"diablon","bigcola"}};

        //Para introducir en el cliente.
        String user;

        String comandoftp;
        String direccionarchivo;

        System.out.println(">Bienvenido. ");
        System.out.print("\n>Por favor indique su IP o el nombre de su maquina. \n>");
            ip=reader.nextLine();


        System.out.println("\n>Espere, por favor.");
        //Verificar conexcion.

        System.out.println(">Conexion establecida.");

            if(session.equals("no")){
                //Pedir clave y usuario, y separarlo.
        System.out.print("\n>Indique su usuario y clave (usuario#clave): \n>");
            user=reader.nextLine();
            String partesUP[]=user.split("#");

            //Validacion.

                for(int i=0;i<Accounts.length;i++) {
                    if(partesUP[0]==Accounts[i][0]){
                    if(partesUP[1]==Accounts[i][1])
                        session="yes";
                    if(session.equals("no")){
                        System.out.println("Usuario invalido.");
                        }
                    }
                }
            }

        System.out.println("\n>Usuario valido, por favor espere.");

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
                    System.out.print("\nftp>Por favor indique la direccion de la carpeta. \nftp>");
                    Directory=reader.nextLine();

                    System.out.printf("\nftp>La lista de archivos es la siguinte:\n ");

                    File aDirectory = new File(Directory);

                    String[] filesInDir = aDirectory.list();

                    for ( int i=0; i<filesInDir.length; i++ ) {
                        System.out.println("\nftp> " + filesInDir[i]);
                    }
                    break;

                case "delete":
                    try{
                        System.out.print("\n>Por favor indique el archivo que desea eliminar. \n>");
                        DDirectory=reader.nextLine();

                        File file = new File(DDirectory);

                        if(file.delete()){
                            System.out.println(file.getName() + "\nftp>El archivo no deseado, ha sido borrado.");
                        }else{
                            System.out.println("\nftp>El archivo no se encuentra disponible.");
                        }

                    }catch(Exception e){

                        e.printStackTrace();

                    }
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
