package UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDP_DataType_GiaTriConThieu_KhongGiaTriTrungNhau {
    public static void main(String[] args) throws Exception{
        DatagramSocket s = new DatagramSocket();
        InetAddress ip = InetAddress.getByName("36.50.135.242");
        int port = 2207;

        String msg = ";B23DCCN752;vMy8FoQI";
        byte[] b = msg.getBytes();

        s.send(new DatagramPacket(b, b.length, ip, port));

        b = new byte[1024];
        DatagramPacket p = new DatagramPacket(b, b.length);
        s.receive(p);

        String str = new String(p.getData(), 0, p.getLength());

        String[] x = str.split(";");
        String requestId = x[0];
        int n = Integer.parseInt(x[1]);

        boolean[] check = new boolean[n + 1];

        for (String v : x[2].split(",")) {
            check[Integer.parseInt(v)] = true;
        }

        String ans = requestId + ";";

        for (int i = 1; i <= n; i++) {
            if (!check[i]) {
                ans += i + ",";
            }
        }

        if (ans.endsWith(",")) {
            ans = ans.substring(0, ans.length() - 1);
        }

        b = ans.getBytes();
        s.send(new DatagramPacket(b, b.length, ip, port));

        s.close();
    }
}