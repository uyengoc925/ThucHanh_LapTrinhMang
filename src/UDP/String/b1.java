package UDP.String;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class b1 {

    public static void main(String [] args) throws IOException {
        String msv = "B23DCCN912";
        String qcode = "O0Ejmqjm";
        String frist =";" +  msv + ";" + qcode ;
        String ip = "36.50.135.242";
        int port = 2208;

        try(DatagramSocket socket = new DatagramSocket()){
            InetAddress host = InetAddress.getByName(ip);
            // a
            byte[] buffA = frist.getBytes(StandardCharsets.UTF_8);
            DatagramPacket buff = new DatagramPacket(buffA, buffA.length, host, port);
            socket.send(buff);
            // b
            byte[] buffB = new byte[2048];
            DatagramPacket in = new DatagramPacket(buffB, buffB.length, host, port);
            socket.receive(in);
            // c
            String msgB = new String(in.getData(), 0, in.getLength(), StandardCharsets.UTF_8);
            System.out.println("Received from server: " + msgB);
            String [] word = msgB.split(";");
            String requestID = word[0];
            String data = word[1].trim();
            char [] arr = data.toCharArray();
            Map<Character, Integer> map = new HashMap<>();
            for(char i : arr){
                if(map.containsKey(i)){
                    map.put(i, map.get(i) + 1);
                } else {
                    map.put(i, 1);
                }
            }
            int n = arr.length;

            Character maxKey = null;
            int maxValue = 0;
            String send = requestID + ";";
            for(int i =0; i<n; i++){
                if(map.get(arr[i]) > maxValue){
                    maxValue = map.get(arr[i]);
                    maxKey = arr[i];
                }
            }
            send += maxKey + ":";
            for(int i=0; i<n; i++){
                if(arr[i] == maxKey){
                    send += i+1 +"," ;


                }
            }



            byte[] buffC = send.getBytes(StandardCharsets.UTF_8);
            DatagramPacket out = new DatagramPacket(buffC, buffC.length, host, port);
            socket.send(out);
            System.out.println("Sent to server: " + send);





        }

    }
}
/*
Tìm kiếm ký tự xuất hiện nhiều nhất trong chuỗi và gửi lên server theo định dạng
 “requestId;ký tự xuất hiện nhiều nhất: các vị trí xuất hiện ký tự đó”.
  ví dụ: “requestId;8:4,9,”
 */
