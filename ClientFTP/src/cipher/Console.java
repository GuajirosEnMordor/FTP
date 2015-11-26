package cipher;

//Created by Heradocles and Mendez.

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Console {

    public static void main(String[] args) throws IOException {

        //Variables y demas.

        Scanner reader = new Scanner(System.in);
        String ip;
        String iplocal;

        String uservalido = "no";

        String DDirectory;
        String help;
        String respuesta;

        String Archivo;

        String user;

        String comandoftp;


        ServerSocket Server = null;
        Socket Cliente = null;

        Socket Cliente1=null;

        ObjectOutputStream salida = null;
        ObjectInputStream entrada = null;
        InputStream entry=null;
        OutputStream exit=null;



        System.out.println(">Bienvenido. ");
        System.out.print("\n>Por favor indique la IP a donde se quiere conectar.. \n>");
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


            System.out.println("\n>Usuario valido, por favor espere.");

            System.out.print("\n>Indique SU ip. \n>");
            iplocal=reader.nextLine();
            salida.writeObject(iplocal);


            while (true) {
                System.out.print("\nftp>Esperando un comando.\nftp>");
                comandoftp = reader.nextLine();

                switch (comandoftp) {

                    case "put":

                        System.out.print("\nftp>Por favor indique el archivo(con su formato) que desea subir al servidor. \n>");

                        Archivo = reader.nextLine();

                        String carpeta = "D:\\Programacion\\Proyectos ST\\FTP\\Archivos cliente\\"+Archivo;
                        File Folder = new File(carpeta);

                        if(Folder.exists()) {

                            salida.writeObject(comandoftp);
                            salida.writeObject(Archivo);

                            try {
                                Cliente1 = new Socket("localhost", 9001);
                                File f = new File("D:\\Programacion\\Proyectos ST\\FTP\\Archivos cliente\\" + Archivo);
                                byte[] bytes = new byte[999999];
                                entry = new FileInputStream(f);
                                exit = Cliente1.getOutputStream();
                                int count;
                                while ((count = entry.read(bytes)) > 0) {
                                    exit.write(bytes, 0, count);
                                }

                                System.out.println("\nftp>El archivo deseado ha sido subido al servidor.");

                            } catch (Exception e) {

                                e.printStackTrace();

                            } finally {
                                entry.close();
                                exit.close();
                                Cliente1.close();
                            }
                        }else {
                            System.out.println("\nftp>El Archivo no se encuentra disponible o no existe");
                        }

                        break;

                    case "get":

                        salida.writeObject(comandoftp);

                        System.out.print("\nftp>Por favor indique el archivo(con su formato) que desea recibir del servidor.. \n>");
                        Archivo = reader.nextLine();
                        salida.writeObject(Archivo);

                        respuesta=(String)entrada.readObject();

                        if (respuesta.equals("si")) {

                            try {
                                Server = new ServerSocket(9002);
                                Cliente1 = Server.accept();
                                entry = Cliente1.getInputStream();
                                exit = new FileOutputStream("D:\\Programacion\\Proyectos ST\\FTP\\Archivos cliente\\" + Archivo);
                                byte[] bytes = new byte[999999];
                                int count;
                                while ((count = entry.read(bytes)) > 0) {
                                    exit.write(bytes, 0, count);
                                    System.out.println("\nftp>El Archivo deseado ha sido recibido del servidor.");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                Cliente1.close();
                                Server.close();
                                entry.close();
                                exit.close();
                            }

                        }else{
                            System.out.println("\nftp>El archivo deseado no se encuentra disponible o no existe.");
                        }

                        break;

                    case "list":

                        salida.writeObject(comandoftp);

                        System.out.print("\nftp>Esperando informacion del servidor.\n");

                        String [] files=(String[])entrada.readObject();

                        if (files.length == 0) {
                            System.out.println("\nftp>El servidor no tiene archivos disponibles en este momento.");
                        } else {

                            System.out.println("\nftp>La lista de archivos es la siguiente: \n");
                            for (String aFile : files) {
                                System.out.println("ftp>"+aFile);
                            }
                        }

                        break;

                    case "delete":

                        salida.writeObject(comandoftp);

                        System.out.print("\nftp>Por favor indique el archivo(Con su formato) que desea eliminar. \n>");
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

                        System.out.println("\nftp>Hasta luego, gracias por usar nuestro intento de servidor FTP.");
                        System.exit(0);
                        break;

                    case "help":

                        salida.writeObject(comandoftp);
                        help = (String) entrada.readObject();

                        System.out.println(""+help);


                        break;

                    default:
                           System.out.println("\nftp>El comando no se encuentra disponible. Intente de nuevo.");
                           break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}





