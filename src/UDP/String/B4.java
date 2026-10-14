package UDP.String;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class B4 {
    public static void main(String [] args) throws IOException{
        String msv = "B23DCCN912";
        String qcode = "Eo1p16Yz";
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

            String msgB = new String(in.getData(), 0, in.getLength(), StandardCharsets.UTF_8);
            System.out.println("Received from server: " + msgB);
            String [] word = msgB.split(";");
            String requestID = word[0];
            String data = word[1].toLowerCase();
            String [] arrData = data.split(" ");
            String send = requestID + ";";

            for(String i: arrData){
                StringBuilder tmp = new StringBuilder();
                i.trim();
                tmp.append(i.substring(0,1).toUpperCase()).append(i.substring(1)).append(" ");
                send += tmp.toString();
            }
            send.trim();

            byte[] buffC = send.getBytes(StandardCharsets.UTF_8);
            DatagramPacket out = new DatagramPacket(buffC, buffC.length, host, port);
            socket.send(out);
            System.out.println("Sent to server: " + send);





        }
    }
}
/*
CHUẨN HÓA CHUỖI
viết hoa chữ cái đầu tiên trong từ
VD:
Input: JIJReaa RJerSIkt uwxXTrtF DsWMF
| Expected: Jijreaa Rjersikt Uwxxtrtf Dswmf
 */
