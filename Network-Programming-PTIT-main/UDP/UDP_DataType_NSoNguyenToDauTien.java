package UDP;

import java.net.*;
import java.io.*;

public class UDP_DataType_NSoNguyenToDauTien {
    public static void main(String[] args) throws Exception {
        DatagramSocket s = new DatagramSocket();
        InetAddress ip = InetAddress.getByName("localhost");
        int port = 2207;

        String msg = ";B23DCCN123;F3E8B2D4";
        byte[] b = msg.getBytes();

        s.send(new DatagramPacket(b, b.length, ip, port));

        b = new byte[1024];
        DatagramPacket p = new DatagramPacket(b, b.length);
        s.receive(p);

        String str = new String(p.getData(), 0, p.getLength());

        String[] x = str.split(";");
        String requestId = x[0];
        int n = Integer.parseInt(x[1]);

        String ans = requestId + ";";
        int cnt = 0, num = 2;

        while (cnt < n) {
            if (isPrime(num)) {
                ans += num + ",";
                cnt++;
            }
            num++;
        }

        ans = ans.substring(0, ans.length() - 1);

        b = ans.getBytes();
        s.send(new DatagramPacket(b, b.length, ip, port));

        s.close();
    }

    static boolean isPrime(int n) {
        if (n < 2) return false;

        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0)
                return false;
        }

        return true;
    }
}
