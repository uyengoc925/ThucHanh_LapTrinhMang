package UDP.Object;

import UDP.Customer;
import UDP.Employee;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Flow;

public class b22Employee {
    public static void main(String [] args) throws IOException, ClassNotFoundException {
        String msv = "B23DCCN912";
        String qcode = "R4jKsDCW";
        String frist = ";" + msv + ";" + qcode;
        String ip = "36.50.135.242";
        int port = 2209;
        // a
        DatagramSocket socket = new DatagramSocket();
        InetAddress host = InetAddress.getByName(ip);
        byte[] buffA= frist.getBytes(StandardCharsets.UTF_8);
        socket.send(new DatagramPacket(buffA, buffA.length, host, port));
        // b
        byte[] buffB = new byte[4096];
        DatagramPacket in = new DatagramPacket(buffB, buffB.length, host, port);
        socket.receive(in);
        // byte - index dau tien - so luong ky tu doc
        String requestId = new String(buffB, 0, 8);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffB, 8, in.getLength()-8);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Employee employee = (Employee) objectInputStream.readObject();
        // chuan hoa name
        String tmpName = employee.name;
        String[] w = tmpName.toLowerCase().split("\\s+");
        StringBuilder resName = new StringBuilder();
        for(String string : w){
            resName.append(string.substring(0,1).toUpperCase()).append(string.substring(1)).append(" ");
        }
        employee.name= resName.toString().trim();
        // hireDate
        employee.hireDate = chuyenDate(employee.hireDate);
        // tang luong
        int x = 0;
        char[] chars = employee.hireDate.substring(6).toCharArray();
        for(char c : chars){
            x+= c-'0';
        }
        double tmpSalary = employee.salary;
        tmpSalary = tmpSalary*(100+ x)/100;
        employee.salary = tmpSalary;

        // chuyen thanh object
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
        outputStream.writeObject(employee);
        outputStream.flush();

        byte[] rID = requestId.getBytes(StandardCharsets.UTF_8);
        byte[] obj = byteArrayOutputStream.toByteArray();
        byte[] send = new byte[8 + obj.length];

        System.arraycopy(rID, 0, send, 0, 8);
        System.arraycopy(obj, 0, send, 8, obj.length);
        socket.send(new DatagramPacket(send, send.length, host, port));
        socket.close();






        socket.close();



    }
    public static String chuyenDate(String d){
        String [] w = d.split("\\-");
        String res = w[2] + "/" + w[1] + "/" + w[0];
        return res;
    }
}
