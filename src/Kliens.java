import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Kliens {

    public static void main(String[] args) {
        try{
            Socket kapcsolat= new Socket("localhost",8080);
            DataInputStream szervertol = new DataInputStream(kapcsolat.getInputStream());
            DataOutputStream szervernek = new DataOutputStream(kapcsolat.getOutputStream());
            Scanner sc= new Scanner(System.in);
            while(true){
                System.out.println("kérek egy várost: ");

                String varos = sc.nextLine();

                int menu;

                do{
                    System.out.println("Válasszon az alábbi lehetőségek közül: ");
                    System.out.println("1: Mai napi minimum\n2: Mai napi maximum\n3: Holnapi minimum\n4: Holnapi maximum\n 5: A város adatainak kilistázása\n6: kilépés");
                    menu= sc.nextInt();
                    szervernek.writeUTF(varos);
                    szervernek.flush();
                    szervernek.writeInt(menu);
                    szervernek.flush();
                    System.out.println(szervertol.readUTF());
                }
                while(menu != 6);

            }


        }
        catch (IOException ex){
            System.out.println(ex);
        }
    }
}