import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import TCP.Laptop;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
// OBJECT STREAM 2
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
       String host ="36.50.135.242";
       int port = 2209;
       String request = "B23DCCN912;wXMeywhB";
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
       Socket socket = null;

       try{
           socket = new Socket(host, port);
           socket.setSoTimeout(5000);

           oos = new ObjectOutputStream(socket.getOutputStream());
           ois = new ObjectInputStream(socket.getInputStream());

           oos.writeObject(request);
           oos.flush();

           // revice object from server
           Laptop laptop = (Laptop) ois.readObject();
           System.out.println("Laptop ID: " + laptop.getId());
           System.out.println(laptop.getName() + "  " + laptop.getQuantity());

           laptop.setName(fixName(laptop.getName()));
           // FIX INFOMATION
           laptop.setQuantity(fixQuantity(laptop.getQuantity()));

           System.out.println(laptop.getName() + "  " + laptop.getQuantity());

           oos.writeObject(laptop);
           oos.flush();

       } catch(IOException | ClassNotFoundException e){
           e.printStackTrace();
       }finally{
           try{
               if(socket != null) socket.close();
               if (ois  != null) ois.close();
               if(oos != null) oos.close();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }

    }

//    public static void main(String[] args) {
//        String a = "lenovo thinkpad T520 hello";
//        String b = fixName(a);
//        System.out.println(b);
//    }

    private static String fixName(String name){
        if(name == null || name.isEmpty()) return name;

        String[] words = name.trim().split("\\s+");
        String res = new String();
        int n = words.length;
        for (int i = 0; i<=n/2; i++){
            String temp = words[i];
            words[i] = words[n-i-1];
            words[n-i-1] = temp;
        }
        return String.join(" ", words);
    }
    public static int fixQuantity(int quantity){
        String quantityStr = String.valueOf(quantity);
        StringBuilder fixedQuantityStr = new StringBuilder(quantityStr).reverse();
        String res = fixedQuantityStr.toString();
        return Integer.parseInt(res);
    }
}

