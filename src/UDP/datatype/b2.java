package UDP.datatype;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class b2 {

    public static void main(String []args ) throws IOException {

        String msv = "B23DCCN912";
        String qcode = "iiIKWkeM";
        String frist =";" +  msv + ";" + qcode + "\n";
        String ip = "36.50.135.242";
        int port = 2207;


        try(DatagramSocket socket = new DatagramSocket()){
            InetAddress host = InetAddress.getByName(ip);
            byte[] msgA = frist.getBytes(StandardCharsets.UTF_8);
            DatagramPacket packetA = new DatagramPacket(msgA, msgA.length, host, port);
            socket.send(packetA);

            // nhan b
            byte[] buff = new byte[2048];
            DatagramPacket packetB = new DatagramPacket(buff, buff.length, host, port);
            socket.receive(packetB);
            String revice = new String(packetB.getData(), 0, packetB.getLength(), StandardCharsets.UTF_8);
            System.out.println("Received from server: " + revice);
            String [] word = revice.split(";");

            String  requestID = word[0];
            String  data = word[1];
            String [] arrData = data.split(",");
            //
            List<Integer> arr = new ArrayList<>();
            for(String i : arrData){
                arr.add(Integer.parseInt(i.trim()));
            }
            Collections.sort(arr);
            int MAX = arr.get(arr.size() - 1);
            int MIN = arr.get(0);
            String send = requestID + ";" + MAX + "," + MIN ;
            byte[] msgc =send.getBytes(StandardCharsets.UTF_8);
            DatagramPacket packetC = new DatagramPacket(msgc, msgc.length, host, port);
            socket.send(packetC);


        }

    }
}
