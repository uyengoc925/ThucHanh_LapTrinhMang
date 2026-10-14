package UDP.String;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class A93 {
    public static void main(String[] args) throws IOException {
        DatagramSocket client = new DatagramSocket();
        String str = "";

        DatagramPacket dpreq = new DatagramPacket(str.getBytes(), str.getBytes().length, InetAddress.getByName("localhost"), 1108); //203.162.10.109
        client.send(dpreq);


        byte[] buf = new byte[1024];
        DatagramPacket dpReq = new DatagramPacket(buf, buf.length);
        client.receive(dpReq);

        String Receive = new String(dpReq.getData()).trim();
        String[] data = Receive.split(";"); // data[0] = requestID, data[1] = chuỗi ký tự, data[2] = ký tự phân tách
        data[2] = "["+data[2]+"]";//ky tu phan tách
        System.out.println("requestID: " + data[2]);
        String[] token = data[1].split(data[2]);// tách chuỗi ký tự theo ký tự phân tách
        // input: "abc,def,ghi" và ký tự phân tách là "," => output: ["abc","def","ghi"]
        // output: "requestID;abc;def;ghi"
        String kq ="";
        for(String x : token)       kq += x;

        String kqSendSer = "requestID;" + kq;
        byte[] send = kqSendSer.getBytes();
        DatagramPacket sendSer = new DatagramPacket(send,send.length, InetAddress.getByName("localhost"), 1108);
        client.send(sendSer);

        client.close();
    }
}
