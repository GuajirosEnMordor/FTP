package cipher;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class FTP {

    //Informacion servidor.

    static public String help = "\nftp>La lista de comandos es la siguiente: \n\n Put ( put 'direccion del archivo') Esto enviara un archivo al servidor. \n Get (get 'direccion del archivo) Esto recibira un archivo del servidor. \n Quit, para salir de la aplicacion \n Delete (delete 'nombre del archivo), elimina un archivo del servidor \n List (list), se usa para ver la lista de archivos disponibles en el servidor \n";
    static public String[][] Accounts = {{"menor", "beta"}, {"mayor", "papi"}, {"diablon", "bigcola"}};

    //Datos.

    static public String ip;
    static public String sesion = "no";
    static public String respuesta;
    static public String archivo;
    static public String comandoftp;

    //Para el servidor.

    static public Socket ElSocket = null;
    static public ServerSocket ElServer = null;

    //Flujo de datos.

    static public ObjectOutputStream salida = null;
    static public ObjectInputStream entrada = null;
    static public InputStream entradafile=null;
    static public OutputStream salidafile=null;

    public static void conexion (){

        try {
            ElServer = new ServerSocket(9000);

            System.out.println("\n>Esperando conexion de un cliente.");
            ElSocket = ElServer.accept();
            System.out.println("\n>Conexion con cliente exitosa.");
            System.out.println("\n>Esperando informacion del usuario.");

            salida = new ObjectOutputStream(ElSocket.getOutputStream());
            entrada = new ObjectInputStream(ElSocket.getInputStream());


        //Validar usuario.

        while (sesion.equals("no")) {

            //Recibir user del cliente.

            respuesta = (String) entrada.readObject();

            String partesUP[] = respuesta.split("#");

            for (int i = 0; i < Accounts.length; i++) {
                if (partesUP[0].equals(Accounts[i][0])) {
                    if (partesUP[1].equals(Accounts[i][1])) {
                        sesion = "yes";
                        System.out.println("\n>Usuario valido.");
                    }
                }
            }



            if (sesion.equals("no")){
                System.out.println("\n>Se ha detectado un intento de conexion con datos invalidos, se esperan nuevos datos para intentar una nueva conexion.");
            }

            salida.writeObject("" + sesion);
        }

        ip = (String) entrada.readObject();
        System.out.println("\n>IP del cliente es: " + ip);

    }catch (Exception e) {
            e.printStackTrace();}
    }


    public static void put() throws IOException{

        try{

        System.out.println("\nftp>El usuario ha seleccionado put.");
        archivo=(String)entrada.readObject();


            ElServer=new ServerSocket(9001);
            ElSocket=ElServer.accept();
            entradafile=ElSocket.getInputStream();
            salidafile=new FileOutputStream("C:\\Test\\Server\\"+archivo);
            byte[] bytes=new byte[999999];
            int count;
            while((count=entradafile.read(bytes))>0){
                salidafile.write(bytes,0,count);
                System.out.println("\nftp>Tarea completada.");
            }


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            ElServer.close();
            ElSocket.close();
            entradafile.close();
            salidafile.close();
        }
    }

    public static void get() throws IOException{

        try{
        System.out.println("\nftp>El usuario ha selecionado get.");

        archivo=(String)entrada.readObject();

        String carpeta = "C:\\Test\\Server\\"+archivo;
        File Folder = new File(carpeta);
        if(Folder.exists()) {

            respuesta="si";
            salida.writeObject(respuesta);


                ElSocket = new Socket("" + ip, 9002);
                File f = new File("C:\\Test\\Server\\" + archivo);
                byte[] bytes = new byte[999999];
                entradafile = new FileInputStream(f);
                salidafile = ElSocket.getOutputStream();
                int count;
                while ((count = entradafile.read(bytes)) > 0) {
                    salidafile.write(bytes, 0, count);
                }
                System.out.println("\nftp>Tarea completada.");

            }else{
                System.out.println("\nftp>El Archivo no se encuentra disponible o no existe.\n");
                respuesta="no";
                salida.writeObject(respuesta);
            }
        }catch (Exception e) {

            e.printStackTrace();

        } finally {
            entradafile.close();
            salidafile.close();
            ElSocket.close();
        }
    }

    public static void list(){

        try{

        System.out.println("\nftp>El Cliente ha seleccionado list.");
        System.out.println("\nftp>Recolectando informacion.");

        String path="C:\\";
        File directorio=new File(path);
        String [] files=directorio.list();

        System.out.println("\nftp>Enviando informacion al cliente.");

        salida.writeObject(files);
    }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void delete(){

        try{


        System.out.println("\nftp>El cliente ha seleccionado delete.");

        respuesta=(String)entrada.readObject();

        System.out.println("\nftp>Se desea eliminar el siguiente archivo: \nftp>"+respuesta);


            File file = new File("C:\\Test\\Server\\"+respuesta);

            if (file.delete()) {
                System.out.println(file.getName() + "\nftp>El archivo no deseado, ha sido borrado.");
                respuesta="\nftp>El archivo no deseado, ha sido borrado.";
                salida.writeObject(respuesta);

            } else {
                System.out.println("\nftp>El archivo no se encuentra disponible.");
                respuesta="\nftp>El archivo no se encuentra disponible.";
                salida.writeObject(respuesta);
            }


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void help(){

        try {

            System.out.println("\nftp>El cliente, ha seleccionado help.");

            System.out.println("\nftp>Enviando informacion al usuario.");
            salida.writeObject(help);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void quit() throws IOException{

        System.out.println("\nftp>El cliente ha seleccionado salir.");
        System.out.println("\nftp>Cerrando las conexiones, espere. ");

        entrada.close();
        salida.close();
        ElSocket.close();
        ElServer.close();

        System.out.println("\nftp>Gracias por usar este intento de servidor ftp.");
        System.exit(0);

    }

}