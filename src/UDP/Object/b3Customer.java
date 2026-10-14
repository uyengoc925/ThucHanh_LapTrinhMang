package UDP.Object;

import UDP.Customer;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class b3Customer {
    public static String chuanTen(String name){
        String [] word = name.split("\\s+");
        StringBuilder res = new StringBuilder();
        int n = word.length;
        res.append(word[n - 1].toUpperCase()).append(", ");
        for (int i = 0; i < n - 1; i++) {
            String tmp = word[i].substring(0, 1).toUpperCase() + word[i].substring(1).toLowerCase();
            res.append(tmp).append(" ");
        }
        return res.toString().trim();
    }
    public static String chuanDob(String dob){
        String [] word = dob.split("\\-");
        String res = word[1] + "/" + word[0] + "/" + word[2];
        return res;
    }
    public static void main(String [] args) throws IOException, ClassNotFoundException {
        String msv = "B23DCCN912";
        String qcode = "R4jKsDCW";
        String frist = ";" + msv + ";" + qcode;
        String ip = "36.50.135.242";
        int port = 2209;
        // a
        DatagramSocket socket = new DatagramSocket();
        InetAddress host = InetAddress.getByName(ip);
        byte[] buffA= frist.getBytes(StandardCharsets.UTF_8);
        socket.send(new DatagramPacket(buffA, buffA.length, host, port));
        // b
        byte[] buffB = new byte[4096];
        DatagramPacket in = new DatagramPacket(buffB, buffB.length, host, port);
        socket.receive(in);
        // byte - index dau tien - so luong ky tu doc
        String requestId = new String(buffB, 0, 8, StandardCharsets.UTF_8);
        ByteArrayInputStream inByte = new ByteArrayInputStream(buffB,8, in.getLength()-8);
        ObjectInputStream o = new ObjectInputStream(inByte);
        Customer cc = (Customer) o.readObject();
        System.out.println(cc);

        // xu ly
        String tmp = cc.name;
        cc.name = chuanTen(cc.name);
        cc.dayOfBirth  = chuanDob(cc.dayOfBirth );
        String [] word = tmp.toLowerCase().split(" ");
        StringBuilder un = new StringBuilder();
        int n = word.length;
        for(int i =0; i<n-1; i++){
            un.append(word[i].substring(0,1));
        }
        un.append(word[n-1]);
        cc.userName   = un.toString().trim();

        System.out.println(cc);
        // gui
        byte[] rId = requestId.getBytes(StandardCharsets.UTF_8) ;
        ByteArrayOutputStream outByte = new ByteArrayOutputStream();
        ObjectOutputStream outputStream = new ObjectOutputStream(outByte);
        outputStream.writeObject(cc);
        outputStream.flush();

        byte[] customer = outByte.toByteArray();
        byte[] send = new byte[8 + customer.length];
        System.arraycopy(rId, 0, send, 0, 8);
        System.arraycopy(customer, 0, send, 8, customer.length);

        socket.send(new DatagramPacket(send, send.length, host, port));

        socket.close();



    }
}
