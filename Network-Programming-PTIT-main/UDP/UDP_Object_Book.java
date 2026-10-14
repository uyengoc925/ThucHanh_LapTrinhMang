package UDP;

import java.net.*;
import java.io.*;

public class UDP_Object_Book {
    public static void main(String[] args) throws Exception {
        DatagramSocket s = new DatagramSocket();
        InetAddress ip = InetAddress.getByName("36.50.135.242");
        int port = 2209;

        String msg = ";B23DCCN752;eQkvAeId";
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

        Book book = (Book) ois.readObject();

        // Chuẩn hóa title
        String[] a = book.title.toLowerCase().trim().split("\\s+");
        String title = "";

        for (String x : a) {
            title += Character.toUpperCase(x.charAt(0))
                    + x.substring(1) + " ";
        }

        book.title = title.trim();

        // Chuẩn hóa author: HỌ, Tên
        a = book.author.toLowerCase().trim().split("\\s+");

        String author = a[0].toUpperCase() + ", ";

        for (int i = 1; i < a.length; i++) {
            author += Character.toUpperCase(a[i].charAt(0))
                    + a[i].substring(1) + " ";
        }

        book.author = author.trim();

        // Chuẩn hóa ISBN
        String isbn = book.isbn.replaceAll("[^0-9]", "");

        book.isbn = isbn.substring(0, 3) + "-"
                + isbn.substring(3, 4) + "-"
                + isbn.substring(4, 6) + "-"
                + isbn.substring(6, 12) + "-"
                + isbn.substring(12, 13);

        // yyyy-mm-dd -> mm/yyyy
        String[] date = book.publishDate.split("-");
        book.publishDate = date[1] + "/" + date[0];

        // Chuyển object thành byte[]
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);

        oos.writeObject(book);
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
