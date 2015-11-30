package cipher;

//Created by Heradocles and Mendez.

import java.lang.String;
import java.net.*;
import java.io.*;

public class Main extends FTP{

    public static void main(String[] args) throws IOException {

        System.out.println("\n>Iniciando servidor.");

        FTP.conexion();

        try{

        while (true) {

            System.out.println("\nftp>Esperando un comando.");

            respuesta = (String) entrada.readObject();
            switch (respuesta) {

                case "put":
                    FTP.put();
                    break;

                case "get":
                    FTP.get();
                    break;

                case "list":
                    FTP.list();
                    break;

                case "delete":
                    FTP.delete();
                    break;

                case "help":
                    FTP.help();
                    break;

                case "quit":
                    FTP.quit();
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


