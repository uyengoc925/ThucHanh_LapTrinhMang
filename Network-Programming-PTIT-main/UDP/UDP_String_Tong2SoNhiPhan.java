package UDP;

import java.net.*;
import java.io.*;

public class UDP_String_Tong2SoNhiPhan {
    public static void main(String[] args) throws Exception {
        DatagramSocket s = new DatagramSocket();
        InetAddress ip = InetAddress.getByName("36.50.135.242");
        int port = 2208;

        String msg = ";B23DCCN752;XbYdNZ3";
        byte[] b = msg.getBytes();

        s.send(new DatagramPacket(b, b.length, ip, port));

        b = new byte[1024];
        DatagramPacket p = new DatagramPacket(b, b.length);
        s.receive(p);

        String str = new String(p.getData(), 0, p.getLength());

        String[] x = str.split(";");
        String requestId = x[0];

        String[] a = x[1].split(",");

        long b1 = Long.parseLong(a[0], 2);
        long b2 = Long.parseLong(a[1], 2);

        long sum = b1 + b2;

        String ans = requestId + ";" + sum;

        b = ans.getBytes();
        s.send(new DatagramPacket(b, b.length, ip, port));

        s.close();
    }
}
