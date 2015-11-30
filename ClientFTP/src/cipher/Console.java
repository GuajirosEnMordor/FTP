package cipher;

import java.io.*;

public class Console extends FTP{

    public static void main(String[] args) throws IOException {

        //Instanciacion de clase.
        FTP ftp=new FTP();
        ftp.conexion();

        while (true) {
            System.out.print("\nftp>Esperando un comando.\nftp>");
            ftp.respuesta = ftp.reader.nextLine();

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

                case "quit":
                    ftp.quit();
                    break;

                case "help":
                    ftp.help();
                    break;

                default:
                    System.out.println("\nftp>El comando no se encuentra disponible. Intente de nuevo.");
                    break;
            }
        }
    }
}
