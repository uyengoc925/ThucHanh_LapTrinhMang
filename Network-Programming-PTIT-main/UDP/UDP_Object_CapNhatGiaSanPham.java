package UDP;

import java.net.*;
import java.io.*;

public class UDP_Object_CapNhatGiaSanPham {
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
        PricedProduct product = (PricedProduct) ois.readObject();

        // Tính finalPrice
        double finalPrice =
                product.getBasePrice()
                        * (1 + product.getTaxRate() / 100.0)
                        * (1 - product.getDiscountRate() / 100.0);

        finalPrice = Math.round(finalPrice * 100.0) / 100.0;

        product.setFinalPrice(finalPrice);

        // Serialize lại object
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