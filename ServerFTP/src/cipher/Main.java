package cipher;

import java.io.*;

public class Main extends FTP{

    public static void main(String[] args) throws IOException {

        System.out.println("\n>Iniciando servidor.");

        //Instanciaciond de clase.
        FTP ftp=new FTP();
        ftp.conexion();



        try{

            while (true) {

                System.out.println("\nftp>Esperando un comando.");

                ftp.respuesta = (String) ftp.entrada.readObject();
                switch (ftp.respuesta) {

                    case "put":
                        ftp.put();
                        break;

                    case "get":
                        ftp.get();
                        break;

                    case "list":
                        ftp.list();
                        break;

                    case "delete":
                        ftp.delete();
                        break;

                    case "help":
                        ftp.help();
                        break;

                    case "quit":
                        ftp.quit();
                        break;

                    default:
                        System.out.println("\nftp>El comando que el cliente trato de ejecutar no esta disponible.");
                        break;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();}

    }
}
