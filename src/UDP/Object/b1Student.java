package UDP.Object;

import java.io.*;
import java.net.*;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

import UDP.Student;
public class b1Student {
    public static String chuanTen(String name){
        String [] word =  name.trim().toLowerCase().split(" ");
        StringBuilder sb = new StringBuilder();
        for(String i : word) {
            if (i.length() > 0) {
                sb.append(Character.toUpperCase(i.charAt(0))).append(i.substring(1)).append(" ");
            }
        }
        return sb.toString().trim();

    }
    public static String taoEmail(String name){
        String[] word = name.trim().toLowerCase().split(" ");
        StringBuilder sb = new StringBuilder();
        sb.append(word[word.length - 1]);
        for(int i = 0; i < word.length - 1; i++){
            sb.append(word[i].charAt(0));
        }
        sb.append("@ptit.edu.vn");
        return sb.toString();
    }

    public static void main(String [] args) throws IOException {
        String msv = "B23DCCN912";
        String qcode = "w3GlDvUT";
        String frist =";" +  msv + ";" + qcode ;
        String ip = "36.50.135.242";
        int port = 2209;

        try(DatagramSocket socket = new DatagramSocket()){
            InetAddress host = InetAddress.getByName(ip);
            // a
            byte[] buffA = frist.getBytes(StandardCharsets.UTF_8);
            DatagramPacket buff = new DatagramPacket(buffA, buffA.length, host, port);
            socket.send(buff);
            // b
            byte[] buffB = new byte[4096];
            DatagramPacket in = new DatagramPacket(buffB, buffB.length, host, port);
            socket.receive(in);
            // c
           String requestId = new String(in.getData(), 0, 8, StandardCharsets.UTF_8);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(in.getData(), 8, in.getLength() - 8);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            Student student = (Student) objectInputStream.readObject();

            System.out.println(student);

            student.name = chuanTen(student.name);
            student.email = taoEmail(student.name);


            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(student);
            objectOutputStream.flush();
            byte[] studentBytes = outputStream.toByteArray();
            byte[] requestIdBytes = requestId.getBytes(StandardCharsets.UTF_8);
            byte[] sendBytes = new byte[8 + studentBytes.length];
            // teen byte, index bat dau, byte den, index bat dau, so ky tu copy
            System.arraycopy(requestIdBytes, 0, sendBytes,  0, 8);
            System.arraycopy(studentBytes,0, sendBytes, 8, studentBytes.length);


            DatagramPacket out = new DatagramPacket(sendBytes, sendBytes.length, host, port);
            socket.send(out);
            System.out.println(student);





        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}



