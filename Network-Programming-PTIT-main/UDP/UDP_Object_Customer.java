package UDP;

import java.net.*;
import java.io.*;

public class UDP_Object_Customer {
    public static void main(String[] args) throws Exception {
        DatagramSocket s = new DatagramSocket();
        InetAddress ip = InetAddress.getByName("36.50.135.242");
        int port = 2209;

        String msg = ";B23DCCN752;yr90aDjc";
        byte[] b = msg.getBytes();

        s.send(new DatagramPacket(b, b.length, ip, port));

        b = new byte[4096];
        DatagramPacket p = new DatagramPacket(b, b.length);
        s.receive(p);

        String requestId = new String(b, 0, 8);

        ByteArrayInputStream bis =
                new ByteArrayInputStream(b, 8, p.getLength() - 8);
        ObjectInputStream ois = new ObjectInputStream(bis);

        Customer c = (Customer) ois.readObject();

        // Xử lý tên
        String[] a = c.name.trim().toLowerCase().split("\\s+");

        String name = a[a.length - 1].toUpperCase() + ", ";

        for (int i = 0; i < a.length - 1; i++) {
            name += Character.toUpperCase(a[i].charAt(0))
                    + a[i].substring(1) + " ";
        }

        c.name = name.trim();

        // Xử lý ngày sinh: mm-dd-yyyy -> dd/mm/yyyy
        String[] date = c.dayOfBirth.split("-");
        c.dayOfBirth = date[1] + "/" + date[0] + "/" + date[2];

        // Tạo username
        String user = "";

        for (int i = 0; i < a.length - 1; i++) {
            user += a[i].charAt(0);
        }

        user += a[a.length - 1];
        c.userName = user;

        // Chuyển object thành byte[]
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);

        oos.writeObject(c);
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
