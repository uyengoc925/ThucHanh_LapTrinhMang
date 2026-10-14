package UDP;

import java.net.*;
import java.io.*;
import java.util.*;

public class UDP_Data_DoanTangLienTiepDaiNhat {
    public static void main(String[] args) throws Exception {
        DatagramSocket s = new DatagramSocket();
        InetAddress ip = InetAddress.getByName("36.50.135.242");
        int port = 2207;

        String msg = ";B23DCCN752;EE29C059";
        byte[] b = msg.getBytes();

        s.send(new DatagramPacket(b, b.length, ip, port));

        b = new byte[2048];
        DatagramPacket p = new DatagramPacket(b, b.length);
        s.receive(p);

        String str = new String(p.getData(), 0, p.getLength());

        String[] x = str.split(";", 2);
        String requestId = x[0];

        String[] tmp = x[1].split(",");
        int[] a = new int[tmp.length];

        for (int i = 0; i < a.length; i++) {
            a[i] = Integer.parseInt(tmp[i]);
        }

        int start = 0;
        int bestStart = 0;
        int bestLen = 1;

        for (int i = 1; i < a.length; i++) {
            if (a[i] <= a[i - 1]) {
                start = i;
            }

            int len = i - start + 1;

            if (len > bestLen) {
                bestLen = len;
                bestStart = start;
            }
        }

        String segment = "";
        int sum = 0;

        for (int i = bestStart; i < bestStart + bestLen; i++) {
            segment += a[i] + ",";
            sum += a[i];
        }

        segment = segment.substring(0, segment.length() - 1);

        String ans = requestId
                + ";segment=" + segment
                + ";length=" + bestLen
                + ";sum=" + sum;

        b = ans.getBytes();
        s.send(new DatagramPacket(b, b.length, ip, port));

        s.close();
    }
}
