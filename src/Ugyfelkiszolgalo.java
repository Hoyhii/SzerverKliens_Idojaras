import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Ugyfelkiszolgalo implements Runnable{

    private HashMap<String, Idojaras > elorejelzes;
    Socket kapcsolat;

    public Ugyfelkiszolgalo(Socket kapcsolat){
        elorejelzes = new HashMap<>();
        this.kapcsolat = kapcsolat;
        Beolvas();
    }


    @Override
    public void run() {
        try{
            DataInputStream ugyfeltol = new DataInputStream(kapcsolat.getInputStream());
            DataOutputStream ugyfelnek = new DataOutputStream(kapcsolat.getOutputStream());
            while (true){
                int menu;
                String varos;
              do{
                    varos = ugyfeltol.readUTF();
                    menu=ugyfeltol.readInt();
                    switch (menu){
                        case 1: ugyfelnek.writeUTF("Mai napi min hőmérséklet a kiválasztott városban: "+napiMin(varos)); break;
                        case 2: ugyfelnek.writeUTF("Mai napi max hőmérséklet a kiválasztott városban: "+napiMax(varos)); break;
                        case 3: ugyfelnek.writeUTF("Holnapi min hőmérséklet a kiválasztott városban: "+holnapMin(varos)); break;
                        case 4: ugyfelnek.writeUTF("Holnapi max hőmérséklet a kiválasztott városban: "+holnapMax(varos)); break;
                        case 5: ugyfelnek.writeUTF(varosAdatok(varos));break;
                        case 6: ugyfelnek.writeUTF("Ön a kilépést választotta");
                    }
                }
                while(menu != 6);

            }

        }
        catch (IOException ex){
            System.out.println(ex);
        }
    }

    public void Beolvas(){


        try {
            BufferedReader br = new BufferedReader(new FileReader("weather.txt"));
            br.readLine();
            String sor = br.readLine();
            while(sor != null){
                Idojaras i = new Idojaras(sor);
                elorejelzes.put(i.getMegye(), i);
                sor = br.readLine();


            }
            for (Map.Entry<String, Idojaras> entry:elorejelzes.entrySet()){
                System.out.println(entry.getValue());
            }

        }catch (FileNotFoundException ex){
            System.out.println(ex);
        }catch (IOException e){
            System.out.println(e);
        }
    }

    private String napiMin(String varos){
        Idojaras i = elorejelzes.get(varos);
        return ""+i.getMai().getMin();
    }
    private String napiMax(String varos){
        Idojaras i = elorejelzes.get(varos);
        return ""+i.getMai().getMax();
    }
    private String holnapMin(String varos){
        Idojaras i = elorejelzes.get(varos);
        return ""+i.getHolnapi().getMin();
    }
    private String holnapMax(String varos){
        Idojaras i = elorejelzes.get(varos);
        return ""+i.getHolnapi().getMax();
    }
    private String varosAdatok(String varos){
        Idojaras i = elorejelzes.get(varos);
        return ""+i.toString();
    }
}
