package UDP;

import java.net.*;
import java.io.*;
import java.util.*;

public class UDP_String_KiTuXuatHienNhieuNhat {
    public static void main(String[] args) throws Exception {
        DatagramSocket s = new DatagramSocket();
        InetAddress ip = InetAddress.getByName("36.50.135.242");
        int port = 2208;

        String msg = ";B23DCCN752;qIEEHVC3";
        byte[] b = msg.getBytes();

        s.send(new DatagramPacket(b, b.length, ip, port));

        b = new byte[1024];
        DatagramPacket p = new DatagramPacket(b, b.length);
        s.receive(p);

        String str = new String(p.getData(), 0, p.getLength());

        String[] x = str.split(";");
        String requestId = x[0];
        String data = x[1];

        int[] cnt = new int[256];

        for (char c : data.toCharArray()) {
            cnt[c]++;
        }

        char cmax = data.charAt(0);

        for (char c : data.toCharArray()) {
            if (cnt[c] > cnt[cmax]) {
                cmax = c;
            }
        }

        String ans = requestId + ";" + cmax + ":";

        for (int i = 0; i < data.length(); i++) {
            if (data.charAt(i) == cmax) {
                int id = i + 1;
                ans += id + ",";
            }
        }

        b = ans.getBytes();
        s.send(new DatagramPacket(b, b.length, ip, port));

        s.close();
    }
}
