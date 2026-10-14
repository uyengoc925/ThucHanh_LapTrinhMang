package UDP;

import java.io.*;
import java.net.*;

public class UDP_DataType_TongChuSo {
    public static void main(String[] args) throws Exception {
        DatagramSocket s = new DatagramSocket();
        InetAddress ip = InetAddress.getByName("localhost");
        int port = 2207;

        String msg = ";B23DCCN123;A1F3D5B7";
        byte[] b = msg.getBytes();

        s.send(new DatagramPacket(b, b.length, ip, port));

        b = new byte[1024];
        DatagramPacket p = new DatagramPacket(b, b.length);
        s.receive(p);

        String str = new String(p.getData(), 0, p.getLength());

        String[] x = str.split(";");
        String requestId = x[0];
        String num = x[1];

        int sum = 0;

        for (char c : num.toCharArray()) {
            if (Character.isDigit(c)) {
                sum += c - '0';
            }
        }

        String ans = requestId + ";" + sum;

        b = ans.getBytes();
        s.send(new DatagramPacket(b, b.length, ip, port));

        s.close();
    }
}
