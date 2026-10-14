package UDP;

import java.net.*;
import java.io.*;

public class UDP_String_LoaiBoKyTuTrong2Chuoi {
    public static void main(String[] args) throws Exception {
        DatagramSocket s = new DatagramSocket();
        InetAddress ip = InetAddress.getByName("36.50.135.242");
        int port = 2208;

        String msg = ";B23DCCN752;B34D51E0";
        byte[] b = msg.getBytes();

        s.send(new DatagramPacket(b, b.length, ip, port));

        b = new byte[1024];
        DatagramPacket p = new DatagramPacket(b, b.length);
        s.receive(p);

        String str = new String(p.getData(), 0, p.getLength());

        String[] x = str.split(";", 3);
        String requestId = x[0];
        String str1 = x[1];
        String str2 = x[2];

        String output = "";

        for (char c : str1.toCharArray()) {
            if (str2.indexOf(c) == -1) {
                output += c;
            }
        }

        String ans = requestId + ";" + output;

        b = ans.getBytes();
        s.send(new DatagramPacket(b, b.length, ip, port));

        s.close();
    }
}
