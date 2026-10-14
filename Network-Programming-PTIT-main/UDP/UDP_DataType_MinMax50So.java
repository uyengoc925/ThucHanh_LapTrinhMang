package UDP;

import java.net.*;

public class UDP_DataType_MinMax50So {
    public static void main(String[] args) throws Exception {
        DatagramSocket s = new DatagramSocket();
        InetAddress ip = InetAddress.getByName("36.50.135.242");
        int port = 2207;

        String msg = ";B23DCCN752;6d3FsZwH";
        byte[] b = msg.getBytes();

        s.send(new DatagramPacket(b, b.length, ip, port));

        b = new byte[1024];
        DatagramPacket p = new DatagramPacket(b, b.length);
        s.receive(p);

        String str = new String(p.getData(), 0, p.getLength());

        String[] x = str.split(";");
        String requestId = x[0];

        String[] a = x[1].split(",");

        int mx = Integer.MIN_VALUE;
        int mn = Integer.MAX_VALUE;

        for (String v : a) {
            int num = Integer.parseInt(v);

            mx = Math.max(mx, num);
            mn = Math.min(mn, num);
        }

        String ans = requestId + ";" + mx + "," + mn;

        b = ans.getBytes();
        s.send(new DatagramPacket(b, b.length, ip, port));

        s.close();
    }
}
