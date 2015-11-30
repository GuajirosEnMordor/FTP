package cipher;

import java.lang.String;
import java.net.*;
import java.io.*;

public class Comandos extends FTPconnections {

    public static void Funciones() throws IOException{

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

            ipcliente = (String) entrada.readObject();
            System.out.println("\n>IP del cliente es: " + ipcliente);

            while (true) {

                System.out.println("\nftp>Esperando un comando.");

                comandocliente = (String) entrada.readObject();
                switch (comandocliente) {

                    case "put":

                        System.out.println("\nftp>El usuario ha seleccionado put.");
                        archivo = (String) entrada.readObject();

                        try {
                            Server1 = new ServerSocket(9001);
                            ElSocket1 = Server1.accept();
                            entradafile = ElSocket1.getInputStream();
                            salidafile = new FileOutputStream("D:\\Programacion\\Proyectos ST\\FTP\\Archivos servidor\\" + archivo);
                            byte[] bytes = new byte[999999];
                            int count;
                            while ((count = entradafile.read(bytes)) > 0) {
                                salidafile.write(bytes, 0, count);
                                System.out.println("\nftp>Tarea completada.");
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            Server1.close();
                            ElSocket1.close();
                            entradafile.close();
                            salidafile.close();
                        }

                        break;

                    case "get":

                        System.out.println("\nftp>El usuario ha selecionado get.");

                        archivo = (String) entrada.readObject();

                        String carpeta = "D:\\Programacion\\Proyectos ST\\FTP\\Archivos cliente\\" + archivo;
                        File Folder = new File(carpeta);
                        if (Folder.exists()) {

                            respuesta = "si";
                            salida.writeObject(respuesta);

                            try {
                                ElSocket1 = new Socket("" + ipcliente, 9002);
                                File f = new File("D:\\Programacion\\Proyectos ST\\FTP\\Archivos servidor\\" + archivo);
                                byte[] bytes = new byte[999999];
                                entradafile = new FileInputStream(f);
                                salidafile = ElSocket1.getOutputStream();
                                int count;
                                while ((count = entradafile.read(bytes)) > 0) {
                                    salidafile.write(bytes, 0, count);
                                }
                                System.out.println("\nftp>Tarea completada.");

                            } catch (Exception e) {

                                e.printStackTrace();

                            } finally {
                                entradafile.close();
                                salidafile.close();
                                ElSocket1.close();
                            }
                        } else {
                            System.out.println("\nftp>El Archivo no se encuentra disponible o no existe.\n");
                            respuesta = "no";
                            salida.writeObject(respuesta);
                        }

                        break;

                    case "list":

                        System.out.println("\nftp>El Cliente ha seleccionado list.");
                        System.out.println("\nftp>Recolectando informacion.");

                        String path = "D:\\Programacion\\Proyectos ST\\FTP\\Archivos servidor";
                        File directorio = new File(path);
                        String[] files = directorio.list();

                        System.out.println("\nftp>Enviando informacion al cliente.");

                        salida.writeObject(files);

                        break;

                    case "delete":

                        System.out.println("\nftp>El cliente ha seleccionado delete.");

                        respuesta = (String) entrada.readObject();

                        System.out.println("\nftp>Se desea eliminar el siguiente archivo: \nftp>" + respuesta);

                        try {
                            File file = new File("D:\\Programacion\\Proyectos ST\\FTP\\Archivos servidor\\" + respuesta);

                            if (file.delete()) {
                                System.out.println(file.getName() + "\nftp>El archivo no deseado, ha sido borrado.");
                                enviar = "\nftp>El archivo no deseado, ha sido borrado.";
                                salida.writeObject(enviar);

                            } else {
                                System.out.println("\nftp>El archivo no se encuentra disponible.");
                                enviar = "\nftp>El archivo no se encuentra disponible.";
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

                    default:
                        System.out.println("\nftp>El comando que el cliente trato de ejecutar no esta disponible.");
                        break;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
