package UDP;

import java.net.*;

public class UDP_String_ChuanHoaChuoi {
    public static void main(String[] args) throws Exception {
        DatagramSocket s = new DatagramSocket();
        InetAddress ip = InetAddress.getByName("36.50.135.242");
        int port = 2208;

        String msg = ";B23DCCN752;9TaDPwPY";
        byte[] b = msg.getBytes();

        s.send(new DatagramPacket(b, b.length, ip, port));

        b = new byte[1024];
        DatagramPacket p = new DatagramPacket(b, b.length);
        s.receive(p);

        String str = new String(p.getData(), 0, p.getLength());

        String[] x = str.split(";", 2);
        String requestId = x[0];
        String data = x[1];

        String[] a = data.trim().split("\\s+");
        String ans = "";

        for (String word : a) {
            word = word.toLowerCase();
            ans += Character.toUpperCase(word.charAt(0))
                    + word.substring(1) + " ";
        }

        ans = ans.trim();

        String res = requestId + ";" + ans;

        b = res.getBytes();
        s.send(new DatagramPacket(b, b.length, ip, port));

        s.close();
    }
}
