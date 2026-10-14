package UDP;

import java.net.*;
import java.io.*;
import java.util.*;

public class UDP_DataType_Min2Max2 {
    public static void main(String[] args) throws Exception {
        DatagramSocket s = new DatagramSocket();
        InetAddress ip = InetAddress.getByName("36.50.135.242");
        int port = 2207;

        String msg = ";B23DCCN752;99D9F604";
        byte[] b = msg.getBytes();

        s.send(new DatagramPacket(b, b.length, ip, port));

        b = new byte[1024];
        DatagramPacket p = new DatagramPacket(b, b.length);
        s.receive(p);

        String str = new String(p.getData(), 0, p.getLength());

        String[] x = str.split(";");
        String requestId = x[0];

        String[] a = x[1].split(",");
        ArrayList<Integer> list = new ArrayList<>();

        for (String v : a) {
            list.add(Integer.parseInt(v));
        }

        Collections.sort(list);

        int secondMin = list.get(1);
        int secondMax = list.get(list.size() - 2);

        String ans = requestId + ";" + secondMax + "," + secondMin;

        b = ans.getBytes();
        s.send(new DatagramPacket(b, b.length, ip, port));

        s.close();
    }
}
