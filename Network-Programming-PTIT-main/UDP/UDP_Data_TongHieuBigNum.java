package UDP;

import java.net.*;
import java.io.*;
import java.util.*;
import java.math.*;

public class UDP_Data_TongHieuBigNum {
    public static void main(String[] args) throws Exception {
        DatagramSocket s = new DatagramSocket();
        InetAddress ip = InetAddress.getByName("36.50.135.242");
        int port = 2207;

        String msg = ";B23DCCN752;A1F3D5B7";
        byte[] b = msg.getBytes();

        s.send(new DatagramPacket(b, b.length, ip, port));

        b = new byte[1024];
        DatagramPacket p = new DatagramPacket(b, b.length);
        s.receive(p);

        String str = new String(p.getData(), 0, p.getLength());

        String[] x = str.split(";");
        String requestId = x[0];

        BigInteger num1 = new BigInteger(x[1]);
        BigInteger num2 = new BigInteger(x[2]);

        BigInteger sum = num1.add(num2);
        BigInteger diff = num1.subtract(num2);

        String ans = requestId + ";" + sum + "," + diff;

        b = ans.getBytes();
        s.send(new DatagramPacket(b, b.length, ip, port));

        s.close();
    }
}
