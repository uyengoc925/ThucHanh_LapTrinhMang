package UDP;

import java.net.*;
import java.io.*;

public class UDP_DataType_MaCaesar {
    public static void main(String[] args) throws Exception {
        DatagramSocket s = new DatagramSocket();
        InetAddress ip = InetAddress.getByName("localhost");
        int port = 2207;

        String msg = ";B23DCCN123;825EE3A7";
        byte[] b = msg.getBytes();

        s.send(new DatagramPacket(b, b.length, ip, port));

        b = new byte[1024];
        DatagramPacket p = new DatagramPacket(b, b.length);
        s.receive(p);

        String str = new String(p.getData(), 0, p.getLength());

        String[] x = str.split(";");
        String requestId = x[0];
        String encode = x[1];
        int k = Integer.parseInt(x[2]);

        String decode = "";

        for (char c : encode.toCharArray()) {
            if (c >= 'A' && c <= 'Z')
                c = (char) ((c - 'A' - k + 26) % 26 + 'A');

            else if (c >= 'a' && c <= 'z')
                c = (char) ((c - 'a' - k + 26) % 26 + 'a');

            decode += c;
        }

        String ans = requestId + ";" + decode;

        b = ans.getBytes();
        s.send(new DatagramPacket(b, b.length, ip, port));

        s.close();
    }
}
