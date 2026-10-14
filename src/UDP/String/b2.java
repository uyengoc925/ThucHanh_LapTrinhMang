package UDP.String;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.math.*;

public class b2 {
    public static long chuyenNP(String a){
        StringBuilder k = new StringBuilder(a);
        k.reverse();

        int n = k.length();
        long res = 0;
        for(int i =0; i<n; i++){
            if(k.charAt(i) == '1'){
                res+= 1L<<i; // dich bit
            }
        }
        return res;
    }
    public static void main(String [] args) throws IOException {
        String msv = "B23DCCN912";
        String qcode = "O0Ejmqjm";
        String frist =";" +  msv + ";" + qcode ;
        String ip = "36.50.135.242";
        int port = 2208;

        try(DatagramSocket socket = new DatagramSocket()){
            InetAddress host = InetAddress.getByName(ip);
            // a
            byte[] buffA = frist.getBytes(StandardCharsets.UTF_8);
            DatagramPacket buff = new DatagramPacket(buffA, buffA.length, host, port);
            socket.send(buff);
            // b
            byte[] buffB = new byte[2048];
            DatagramPacket in = new DatagramPacket(buffB, buffB.length, host, port);
            socket.receive(in);
            // c
            String msgB = new String(in.getData(), 0, in.getLength(), StandardCharsets.UTF_8);
            System.out.println("Received from server: " + msgB);
            String [] word = msgB.split(";");
            String b1 = word[1];
            String b2 = word[2];
            long s1 = chuyenNP(b1);
            long s2 = chuyenNP(b2);
            long sum = s1+ s2;
            String send =word[0] +";" + String.valueOf(sum);

            byte[] buffC = send.getBytes(StandardCharsets.UTF_8);
            DatagramPacket out = new DatagramPacket(buffC, buffC.length, host, port);
            socket.send(out);
            System.out.println("Sent to server: " + send);
        }

    }
}
/*
tính tổng 2 số nhị phân
input: resuqestId;b1;b2 (b1, b2 là số nhị phân)
ouput: reuqestId;sum ( sum = b1+ b2 - số thập phân)
 */
