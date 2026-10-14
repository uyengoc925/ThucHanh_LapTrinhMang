package UDP;

import java.net.*;
import java.io.*;
import java.util.*;

public class UDP_Data_Median {
    public static void main(String[] args) throws Exception {
        DatagramSocket s = new DatagramSocket();
        InetAddress ip = InetAddress.getByName("36.50.135.242");
        int port = 2207;

        String msg = ";B23DCCN752;EE29C059";
        byte[] b = msg.getBytes();

        s.send(new DatagramPacket(b, b.length, ip, port));

        b = new byte[2048];
        DatagramPacket p = new DatagramPacket(b, b.length);
        s.receive(p);

        String str = new String(p.getData(), 0, p.getLength());

        String[] x = str.split(";", 2);
        String requestId = x[0];

        String[] tmp = x[1].split(",");
        int[] a = new int[tmp.length];

        for (int i = 0; i < a.length; i++) {
            a[i] = Integer.parseInt(tmp[i]);
        }

        Arrays.sort(a);

        String answer;

        if (a.length % 2 == 1) {
            answer = String.valueOf(a[a.length / 2]);
        } else {
            double median = (a[a.length / 2 - 1] + a[a.length / 2]) / 2.0;
            answer = String.format(Locale.US, "%.2f", median);
        }

        String ans = requestId + ";" + answer;

        b = ans.getBytes();
        s.send(new DatagramPacket(b, b.length, ip, port));

        s.close();
    }
}
