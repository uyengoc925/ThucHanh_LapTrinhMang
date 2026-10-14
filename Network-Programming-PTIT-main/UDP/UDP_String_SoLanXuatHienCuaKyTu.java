package UDP;

import java.net.*;
import java.io.*;

public class UDP_String_SoLanXuatHienCuaKyTu {
    public static void main(String[] args) throws Exception {
        DatagramSocket s = new DatagramSocket();
        InetAddress ip = InetAddress.getByName("36.50.135.242");
        int port = 2208;

        String msg = ";B23DCCN752;9F8C2D3A";
        byte[] b = msg.getBytes();

        s.send(new DatagramPacket(b, b.length, ip, port));

        b = new byte[1024];
        DatagramPacket p = new DatagramPacket(b, b.length);
        s.receive(p);

        String str = new String(p.getData(), 0, p.getLength());

        String[] x = str.split(";", 2);
        String requestId = x[0];
        String data = x[1];

        int[] cnt = new int[256];

        for (char c : data.toCharArray()) {
            cnt[c]++;
        }

        boolean[] check = new boolean[256];
        String output = "";

        for (char c : data.toCharArray()) {
            if (!check[c]) {
                output += cnt[c] + "" + c;
                check[c] = true;
            }
        }

        String ans = requestId + ";" + output;

        b = ans.getBytes();
        s.send(new DatagramPacket(b, b.length, ip, port));

        s.close();
    }
}
