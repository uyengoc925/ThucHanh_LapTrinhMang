package UDP;

import java.net.*;
import java.io.*;

public class UDP_String_LoaiBoKyTuTrongChuoi {
    public static void main(String[] args) throws Exception {
        DatagramSocket s = new DatagramSocket();
        InetAddress ip = InetAddress.getByName("36.50.135.242");
        int port = 2208;

        String msg = ";B23DCCN752;06D6800D";
        byte[] b = msg.getBytes();

        s.send(new DatagramPacket(b, b.length, ip, port));

        b = new byte[1024];
        DatagramPacket p = new DatagramPacket(b, b.length);
        s.receive(p);

        String str = new String(p.getData(), 0, p.getLength());

        String[] x = str.split(";", 2);
        String requestId = x[0];
        String input = x[1];

        boolean[] check = new boolean[256];
        String output = "";

        for (char c : input.toCharArray()) {
            if (Character.isLetter(c) && !check[c]) {
                output += c;
                check[c] = true;
            }
        }

        String ans = requestId + ";" + output;

        b = ans.getBytes();
        s.send(new DatagramPacket(b, b.length, ip, port));

        s.close();
    }
}
