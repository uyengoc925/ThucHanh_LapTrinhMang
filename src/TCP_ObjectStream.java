import TCP.Customer;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCP_ObjectStream {
    public static void main(String[] args) {
        String host = "36.50.135.242";
        int port = 2209;
        String request = "B23DCCN912;i9OaO7T9";


        try (Socket socket = new Socket(host, port);
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())
        ) {
            socket.setSoTimeout(5000);
            out.writeObject(request);
            out.flush();

            Customer customer = (Customer) in.readObject();
            System.out.println("Customer ID: " + customer.getId());
            // name + dob + username
            System.out.println(customer.getName() + "  " + customer.getDayOfBirth () + "  " + customer.getUsername());

            // chuan hoa ten & username
            String username = "";

            String originalName = customer.getName().trim();
            String[] word = originalName.split("\\s+");
            String ten = word[word.length - 1].toUpperCase();
            String ho = "";
            for (int i = 0; i < word.length - 1; i++) {
                ho += word[i].substring(0, 1).toUpperCase() + word[i].substring(1).toLowerCase() + " ";
                username += word[i].substring(0, 1).toLowerCase();
            }
            username += ten.toLowerCase();
            customer.setUsername(username);
            String nameChuan = ten + ", " + ho;
            customer.setName(nameChuan);
            // chuan hoa dob
            String dob = customer.getDayOfBirth ();
            String[] dobArr = dob.split("-"); // mm -- dd -- yyyy;
            String dobChuan = dobArr[1] + "/" + dobArr[0] + "/" + dobArr[2];
            customer.setDayOfBirth (dobChuan);
            System.out.println(customer.getName() + "  " + customer.getDayOfBirth () + "  " + customer.getUsername());

            // send back to server
            out.writeObject(customer);
            out.flush();


        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
}
