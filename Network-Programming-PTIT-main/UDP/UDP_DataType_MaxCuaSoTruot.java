package UDP;

import java.net.*;
import java.io.*;

public class UDP_DataType_MaxCuaSoTruot {
    public static void main(String[] args) throws Exception {
        DatagramSocket s = new DatagramSocket();
        InetAddress ip = InetAddress.getByName("36.50.135.242");
        int port = 2207;

        String msg = ";B23DCCN752;ylrhZ6UM";
        byte[] b = msg.getBytes();

        s.send(new DatagramPacket(b, b.length, ip, port));

        b = new byte[2048];
        DatagramPacket p = new DatagramPacket(b, b.length);
        s.receive(p);

        String str = new String(p.getData(), 0, p.getLength());

        String[] x = str.split(";");
        String requestId = x[0];
        int n = Integer.parseInt(x[1]);
        int k = Integer.parseInt(x[2]);

        String[] tmp = x[3].split(",");
        int[] a = new int[n];

        for (int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(tmp[i]);
        }

        String ans = requestId + ";";

        for (int i = 0; i <= n - k; i++) {
            int mx = a[i];

            for (int j = i; j < i + k; j++) {
                mx = Math.max(mx, a[j]);
            }

            ans += mx + ",";
        }

        ans = ans.substring(0, ans.length() - 1);

        b = ans.getBytes();
        s.send(new DatagramPacket(b, b.length, ip, port));

        s.close();
    }
}
