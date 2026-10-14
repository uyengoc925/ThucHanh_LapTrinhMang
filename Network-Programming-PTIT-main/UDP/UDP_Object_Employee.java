package UDP;

import java.net.*;
import java.io.*;

public class UDP_Object_Employee {
    public static void main(String[] args) throws Exception {
        DatagramSocket s = new DatagramSocket();
        InetAddress ip = InetAddress.getByName("36.50.135.242");
        int port = 2209;

        String msg = ";B23DCCN752;ITleSdqV";
        byte[] b = msg.getBytes();

        s.send(new DatagramPacket(b, b.length, ip, port));

        // Nhận dữ liệu
        b = new byte[4096];
        DatagramPacket p = new DatagramPacket(b, b.length);
        s.receive(p);

        String requestId = new String(b, 0, 8);

        ByteArrayInputStream bis =
                new ByteArrayInputStream(b, 8, p.getLength() - 8);
        ObjectInputStream ois = new ObjectInputStream(bis);

        Employee e = (Employee) ois.readObject();

        // Chuẩn hóa name
        String[] a = e.getName().toLowerCase().trim().split("\\s+");
        String name = "";

        for (String x : a) {
            name += Character.toUpperCase(x.charAt(0))
                    + x.substring(1) + " ";
        }

        e.setName(name.trim());

        // Lấy năm từ hireDate
        String[] date = e.getHireDate().split("-");
        String year = date[0];

        int percent = 0;

        for (char c : year.toCharArray()) {
            percent += c - '0';
        }

        // Tăng salary x%
        double salary = e.getSalary() * (1 + percent / 100.0);
        salary = Math.round(salary * 100.0) / 100.0;

        e.setSalary(salary);

        // yyyy-mm-dd -> dd/mm/yyyy
        e.setHireDate(date[2] + "/" + date[1] + "/" + date[0]);

        // Serialize lại object
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);

        oos.writeObject(e);
        oos.flush();

        byte[] obj = bos.toByteArray();
        byte[] req = requestId.getBytes();

        byte[] send = new byte[8 + obj.length];

        System.arraycopy(req, 0, send, 0, 8);
        System.arraycopy(obj, 0, send, 8, obj.length);

        s.send(new DatagramPacket(send, send.length, ip, port));

        s.close();
    }
}
