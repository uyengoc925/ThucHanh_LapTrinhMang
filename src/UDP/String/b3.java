package UDP.String;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class b3 {
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
//            String msgB = "1234;helloWorld;xinchao";
            System.out.println("Received from server: " + msgB);
            String [] w = msgB.split(";");
            String s1 = w[1];
            String s2 = w[2];
            int n1 = s1.length();
            int n2 = s2.length();
            int [] a = new int[n1];
            for(int i =0; i<n1; i++){
                a[i] = 1;
                for(int j = 0; j<n2; j++){
                    if(s1.charAt(i) == s2.charAt(j)){
                        a[i] = 0;
                    }
                }
            }
            String send =w[0] +";" ;

            for(int i =0; i<n1; i++){
                if(a[i]==1){
                    send += s1.charAt(i);
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
str1,str2
. Loại bỏ các ký tự trong chuỗi thứ nhất mà xuất hiện trong chuỗi thứ hai, giữ nguyên thứ tự xuất hiện. Gửi thông điệp là một chuỗi lên server theo định dạng
 "requestId;strOutput", trong đó chuỗi strOutput là chuỗi đã được xử lý ở trên.
 */
