package UDP;

import java.io.*;
import java.net.*;
import java.util.*;

public class UDP_Data_PhanVi90 {
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

        double sum = 0;

        for (int i = 0; i < a.length; i++) {
            a[i] = Integer.parseInt(tmp[i]);
            sum += a[i];
        }

        // Trung bình dãy ban đầu
        double avg = sum / a.length;

        int aboveAvg = 0;
        for (int v : a) {
            if (v > avg) {
                aboveAvg++;
            }
        }

        // Sắp xếp và lấy p90
        Arrays.sort(a);

        int pos = (int) Math.ceil(a.length * 0.9) - 1;
        int p90 = a[pos];

        String ans = requestId
                + ";p90=" + p90
                + ";aboveAvg=" + aboveAvg;

        b = ans.getBytes();
        s.send(new DatagramPacket(b, b.length, ip, port));

        s.close();
    }
}
