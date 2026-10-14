package UDP;

import java.io.*;
import java.net.*;

public class UDP_Object_CapNhatLuongNhanVien {
    public static void main(String[] args) throws Exception {
        DatagramSocket s = new DatagramSocket();
        InetAddress ip = InetAddress.getByName("36.50.135.242");
        int port = 2209;

        String msg = ";B23DCCN752;EE29C059";
        byte[] b = msg.getBytes();

        s.send(new DatagramPacket(b, b.length, ip, port));

        // Nhận
        b = new byte[4096];
        DatagramPacket p = new DatagramPacket(b, b.length);
        s.receive(p);

        String requestId = new String(b, 0, 8);

        ByteArrayInputStream bis =
                new ByteArrayInputStream(b, 8, p.getLength() - 8);

        ObjectInputStream ois = new ObjectInputStream(bis);
        Employee e = (Employee) ois.readObject();

        // Chuẩn hóa tên
        String[] a = e.getName().toLowerCase().trim().split("\\s+");
        String name = "";

        for (String x : a) {
            name += Character.toUpperCase(x.charAt(0))
                    + x.substring(1) + " ";
        }

        e.setName(name.trim());

        // Tăng lương 8%
        double salary = e.getSalary() * 1.08;
        salary = Math.round(salary * 100.0) / 100.0;

        e.setSalary(salary);

        // Serialize object
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
