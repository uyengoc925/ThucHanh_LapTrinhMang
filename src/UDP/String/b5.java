package UDP.String;

import java.awt.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.PortUnreachableException;
import java.nio.charset.StandardCharsets;

public class b5 {
    public static void main(String [] args) throws IOException {
        String msv = "B23DCCN912";
        String qcode = "Eo1p16Yz";
        String frist = ";" + msv + ";" + qcode;
        String ip = "36.50.135.242";
        int port = 2208;
        DatagramSocket socket = new DatagramSocket();
        InetAddress host = InetAddress.getByName(ip);

        byte[] buffA = frist.getBytes(StandardCharsets.UTF_8);
        DatagramPacket out = new DatagramPacket(buffA,  buffA.length, host, port);
        socket.send(out);

        byte[] buffB = new byte[4096];
        DatagramPacket in = new DatagramPacket(buffB, buffB.length);
        socket.receive(in);
        String msgB = new String(in.getData(), 0, in.getLength());
//        String msgB = "1234;hello123434--;";
        String [] w  = msgB.split(";");
        String s = w[1];
        int n = s.length();
        int [] a = new int[n];
        int [] cnt = new int[256];
        for(int i =0; i<n; i++){
            char c = s.charAt(i);

            if((c>='a' && c<='z') || ( c>='A' && c<='Z')){
                cnt[c]++;
                a[i] = 1; // a[i] = 1 thif laf chu cai
            }
        }
        for(int i =0; i<n; i++){
            char c = s.charAt(i);
            if(cnt[c] !=1) a[i] =0;

        }
        StringBuilder res = new StringBuilder();
        res.append(w[0]).append(";");
        for(int i =0; i<n; i++){
            if(a[i]==1){
                res.append(s.charAt(i));
            }
        }
        System.out.println(res.toString());
        byte[] out1 = res.toString().trim().getBytes(StandardCharsets.UTF_8);
        DatagramPacket o = new DatagramPacket(out1, out1.length, host, port);
        socket.send(o);

    }
}
/*
 loiaj bỏ ký tự đặc việt, số, ký tự trùng, giữ nguyên thứ tự xuất hiện.
 */
