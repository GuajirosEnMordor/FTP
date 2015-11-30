package cipher;


import java.io.*;
import java.net.*;
import java.util.Scanner;

public class FTP {

    //Scan.

    public Scanner reader = new Scanner(System.in);

    //Variables.

    public String ip;
    public String sesion = "no";
    public String respuesta;
    public String Archivo;

    //Server/Socket.

    public ServerSocket Server = null;
    public Socket Cliente = null;

    //Flujo de datos.

    public ObjectOutputStream salida = null;
    public ObjectInputStream entrada = null;
    public InputStream entradafile = null;
    public OutputStream salidafile = null;

    void conexion () throws IOException {

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


            InetAddress addr=Cliente.getInetAddress();
            respuesta=addr.getHostAddress();
            salida.writeObject(respuesta);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void put () throws IOException{

        try{

            System.out.print("\nftp>Por favor indique el archivo(con su formato) que desea subir al servidor. \n>");

            Archivo = reader.nextLine();

            String carpeta = "PON LA DIRECCION AQUI"+Archivo;
            File Folder = new File(carpeta);

            //Validamos.

            if(Folder.exists()) {

                salida.writeObject(respuesta);
                salida.writeObject(Archivo);


                Cliente = new Socket("localhost", 9001);
                File f = new File("PON LA DIRECCION AQUI" + Archivo);
                byte[] bytes = new byte[999999];
                entradafile = new FileInputStream(f);
                salidafile = Cliente.getOutputStream();
                int count;
                while ((count = entradafile.read(bytes)) > 0) {
                    salidafile.write(bytes, 0, count);
                }

                System.out.println("\nftp>El archivo deseado ha sido subido al servidor.");

            } else {
                System.out.println("\nftp>El Archivo no se encuentra disponible o no existe");
            }
        }catch (Exception e) {

            e.printStackTrace();

        } finally {
            entradafile.close();
            salidafile.close();
            Cliente.close();
        }
    }

    public void get () throws IOException {

        try {

            salida.writeObject(respuesta);

            System.out.print("\nftp>Por favor indique el archivo(con su formato) que desea recibir del servidor.. \n>");
            Archivo = reader.nextLine();
            salida.writeObject(Archivo);

            respuesta = (String) entrada.readObject();

            //Validamos.

            if (respuesta.equals("si")) {

                Server = new ServerSocket(9002);
                Cliente = Server.accept();
                entradafile = Cliente.getInputStream();
                salidafile = new FileOutputStream("PON LA DIRECCION AQUI" + Archivo);
                byte[] bytes = new byte[999999];
                int count;
                while ((count = entradafile.read(bytes)) > 0) {
                    salidafile.write(bytes, 0, count);
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
            entradafile.close();
            salidafile.close();
        }
    }

    public void list (){
        try {

            salida.writeObject(respuesta);

            System.out.print("\nftp>Esperando informacion del servidor.\n");

            String[] files = (String[]) entrada.readObject();

            //Se valida el String Array para ver que respuesta imprimir.

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

    public void delete () throws IOException{

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

    public void quit () throws IOException{

        try {

            //Procedemos a cerrar todos los canales abiertos.

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

    public void help () {
        try {

            salida.writeObject(respuesta);
            respuesta = (String) entrada.readObject();

            System.out.println(""+respuesta);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

}

