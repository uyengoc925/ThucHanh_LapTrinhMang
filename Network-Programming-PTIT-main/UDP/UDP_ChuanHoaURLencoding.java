package UDP;

import java.io.*;
import java.net.*;
import java.util.*;


public class UDP_ChuanHoaURLencoding {
    public static void main(String[] args) throws Exception {
        DatagramSocket s = new DatagramSocket();
        InetAddress ip = InetAddress.getByName("36.50.135.242");
        int port = 2208;

        String msg = ";B23DCCN752;XbYdNZ3A";
        byte[] b = msg.getBytes();

        s.send(new DatagramPacket(b, b.length, ip, port));

        b = new byte[2048];
        DatagramPacket p = new DatagramPacket(b, b.length);
        s.receive(p);

        String str = new String(p.getData(), 0, p.getLength());

        String[] x = str.split(";", 2);
        String requestId = x[0];
        String data = x[1];

        TreeMap<String, String> map = new TreeMap<>();

        for (String item : data.split("&")) {
            String[] kv = item.split("=", 2);

            String key = URLDecoder.decode(kv[0], "UTF-8");
            String value = URLDecoder.decode(kv[1], "UTF-8");

            map.put(key, value);
        }

        String ans = requestId + ";";

        for (Map.Entry<String, String> e : map.entrySet()) {
            ans += e.getKey() + "=" + e.getValue() + ";";
        }

        ans = ans.substring(0, ans.length() - 1);

        b = ans.getBytes();
        s.send(new DatagramPacket(b, b.length, ip, port));

        s.close();
    }
}
