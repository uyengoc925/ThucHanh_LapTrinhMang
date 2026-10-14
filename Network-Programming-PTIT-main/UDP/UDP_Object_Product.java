package UDP;

import java.net.*;
import java.io.*;

public class UDP_Object_Product {
    public static void main(String[] args) throws Exception {
        DatagramSocket s = new DatagramSocket();
        InetAddress ip = InetAddress.getByName("36.50.135.242");
        int port = 2209;

        String msg = ";B23DCCN752;EE29C059";
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

        Product product = (Product) ois.readObject();

        // Đổi lại từ đầu và từ cuối
        String[] a = product.name.split("\\s+");

        String temp = a[0];
        a[0] = a[a.length - 1];
        a[a.length - 1] = temp;

        product.name = String.join(" ", a);

        // Đảo ngược quantity
        int quantity = product.quantity;
        int rev = 0;

        while (quantity > 0) {
            rev = rev * 10 + quantity % 10;
            quantity /= 10;
        }

        product.quantity = rev;

        // Chuyển object thành byte[]
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);

        oos.writeObject(product);
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
