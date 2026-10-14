package UDP;

import java.net.*;
import java.io.*;

public class UDP_String_KhongCheNhayCam {
    public static void main(String[] args) throws Exception {
        DatagramSocket s = new DatagramSocket();
        InetAddress ip = InetAddress.getByName("36.50.135.242");
        int port = 2208;

        String msg = ";B23DCCN752;EE29C059";
        byte[] b = msg.getBytes();

        s.send(new DatagramPacket(b, b.length, ip, port));

        b = new byte[2048];
        DatagramPacket p = new DatagramPacket(b, b.length);
        s.receive(p);

        String str = new String(p.getData(), 0, p.getLength());

        String[] x = str.split(";", 2);
        String requestId = x[0];
        String data = x[1];

        data = data.replaceAll(
                "[\\w.+-]+@[\\w.-]+",
                "[EMAIL]"
        );

        data = data.replaceAll(
                "\\b\\d{10,11}\\b",
                "[PHONE]"
        );

        data = data.replaceAll(
                "token=\\S+",
                "token=[TOKEN]"
        );

        String ans = requestId + ";" + data;

        b = ans.getBytes();
        s.send(new DatagramPacket(b, b.length, ip, port));

        s.close();
    }
}
