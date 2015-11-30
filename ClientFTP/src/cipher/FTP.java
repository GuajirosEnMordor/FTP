package cipher;


import java.io.*;
import java.net.*;
import java.util.Scanner;

public class FTP {

    static public Scanner reader = new Scanner(System.in);

    static public String ip;
    static public String sesion = "no";
    static public String respuesta;
    static public String Archivo;

    static public ServerSocket Server = null;
    static public Socket Cliente = null;

    static public ObjectOutputStream salida = null;
    static public ObjectInputStream entrada = null;
    static public InputStream entry = null;
    static public OutputStream exit = null;

    public static void conexion () throws IOException {

        //Programa

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

            while (sesion.equals("no")) {
                //Pedir clave y usuario, y separarlo.
                System.out.print("\n>Indique su usuario y clave (usuario#clave): \n>");
                respuesta = reader.nextLine();

                salida.writeObject(respuesta);

                sesion = (String) entrada.readObject();

                if (sesion.equals("no")) {
                    System.out.println("\n>Usuario invalido, intente de nuevo.");
                }
            }


            System.out.println("\n>Usuario valido, por favor espere.");

            System.out.print("\n>Indique SU ip. \n>");
            respuesta = reader.nextLine();
            salida.writeObject(respuesta);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void put () throws IOException{

        try{

            System.out.print("\nftp>Por favor indique el archivo(con su formato) que desea subir al servidor. \n>");

            Archivo = reader.nextLine();

            String carpeta = "C:\\Test\\Client\\"+Archivo;
            File Folder = new File(carpeta);

            if(Folder.exists()) {

                salida.writeObject(respuesta);
                salida.writeObject(Archivo);


                Cliente = new Socket("localhost", 9001);
                File f = new File("C:\\Test\\Client\\" + Archivo);
                byte[] bytes = new byte[999999];
                entry = new FileInputStream(f);
                exit = Cliente.getOutputStream();
                int count;
                while ((count = entry.read(bytes)) > 0) {
                    exit.write(bytes, 0, count);
                }

                System.out.println("\nftp>El archivo deseado ha sido subido al servidor.");

            } else {
                System.out.println("\nftp>El Archivo no se encuentra disponible o no existe");
            }
        }catch (Exception e) {

            e.printStackTrace();

        } finally {
            entry.close();
            exit.close();
            Cliente.close();
        }
    }

    public static void get () throws IOException {

        try {

            salida.writeObject(respuesta);

            System.out.print("\nftp>Por favor indique el archivo(con su formato) que desea recibir del servidor.. \n>");
            Archivo = reader.nextLine();
            salida.writeObject(Archivo);

            respuesta = (String) entrada.readObject();

            if (respuesta.equals("si")) {


                Server = new ServerSocket(9002);
                Cliente = Server.accept();
                entry = Cliente.getInputStream();
                exit = new FileOutputStream("C:\\Test\\Client\\" + Archivo);
                byte[] bytes = new byte[999999];
                int count;
                while ((count = entry.read(bytes)) > 0) {
                    exit.write(bytes, 0, count);
                    System.out.println("\nftp>El Archivo deseado ha sido recibido del servidor.");
                }
            } else {
                System.out.println("\nftp>El archivo deseado no se encuentra disponible o no existe.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Cliente.close();
            Server.close();
            entry.close();
            exit.close();
        }
    }

    public static void list (){
        try {

            salida.writeObject(respuesta);

            System.out.print("\nftp>Esperando informacion del servidor.\n");

            String[] files = (String[]) entrada.readObject();

            if (files.length == 0) {
                System.out.println("\nftp>El servidor no tiene archivos disponibles en este momento.");
            } else {

                System.out.println("\nftp>La lista de archivos es la siguiente: \n");
                for (String aFile : files) {
                    System.out.println("ftp>" + aFile);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void delete () throws IOException{

        try {

            salida.writeObject(respuesta);

            System.out.print("\nftp>Por favor indique el archivo(Con su formato) que desea eliminar. \n>");
            Archivo = reader.nextLine();
            salida.writeObject(Archivo);

            respuesta = (String) entrada.readObject();
            System.out.println("" + respuesta);

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public static void quit () throws IOException{

        try {

            salida.writeObject(respuesta);

            System.out.println("\nftp>Cerrando las conexiones, espere un momento.");
            Cliente.close();
            entrada.close();
            salida.close();

            System.out.println("\nftp>Hasta luego, gracias por usar nuestro intento de servidor FTP.");
            System.exit(0);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void help () {
        try {

            salida.writeObject(respuesta);
            respuesta = (String) entrada.readObject();

            System.out.println(""+respuesta);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
