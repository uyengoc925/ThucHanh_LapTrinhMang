package UDP.datatype;

import javax.print.DocFlavor;
import java.io.IOException;
import java.net.*;
import java.lang.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
// SAP XEP LAI CHUOI THEO THU TU
public class b22 {

    public static void main(String [] args) throws IOException {
        String frist = ";B23DCCN912;aKZwZxWk";
        String host = "36.50.135.242";
        int port = 2207;

        DatagramSocket socket = new DatagramSocket();
        InetAddress ip = InetAddress.getByName(host);
        socket.setSoTimeout(5000);

        byte[] buffA = frist.getBytes(StandardCharsets.UTF_8);
        DatagramPacket out = new DatagramPacket(buffA, buffA.length, ip, port);
        socket.send(out);

        byte[]buffB = new byte[4096];
        DatagramPacket in = new DatagramPacket(buffB, buffB.length, ip, port);
        socket.receive(in);
        String request = new String(in.getData(), 0, in.getLength(), StandardCharsets.UTF_8);
        String [] w = request.split(";");
        String reuqestId = w[0];
        String[] chuoi = w[1].split(",");
        StringBuilder res = new StringBuilder(reuqestId + ";");
        int len = chuoi.length;
       String [] r = new String[len+5];
        for(String s: chuoi){
            String [] part = s.split(":");
            String value = part[0];
            int so = Integer.parseInt(part[1]);
            r[so] = value;
        }
        for(int i =1; i<=len; i++){
            res.append(r[i]);
        }

        byte[] out2 = res.toString().getBytes(StandardCharsets.UTF_8);
        DatagramPacket o = new DatagramPacket(out2, out2.length, ip, port);
        socket.send(o);
        socket.close();



    }
}
/*
sắp xếp chuỗi về đúng vị trí
input: "veM3xgA1g:4,IPFfgEanY:5,aWXlSzDwe:2,PHupvPc:3,PR3gH8ahN:6,UEEKHLIt:7,M6dpWTE:1"
output: "M6dpWTEaWXlSzDwePHupvPcveM3xgA1gIPFfgEanYPR3gH8ahNUEEKHLIt"
 */