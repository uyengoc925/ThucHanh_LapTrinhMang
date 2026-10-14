
package UDP.Object;

import UDP.Product;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class b4Product {
    public static void main(String[] args)
            throws IOException, ClassNotFoundException {

        String msv = "B23DCCN912";
        String qcode = "YOUR_QCODE";
        String first = ";" + msv + ";" + qcode;

        String ip = "36.50.135.242";
        int port = 809;

        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress host = InetAddress.getByName(ip);

            // a. Gui ma sinh vien va ma cau hoi
            byte[] buffA = first.getBytes(StandardCharsets.UTF_8);
            DatagramPacket out = new DatagramPacket(
                    buffA, buffA.length, host, port
            );
            socket.send(out);

            // b. Nhan requestId + object Product
            byte[] buffB = new byte[4096];
            DatagramPacket in = new DatagramPacket(
                    buffB, buffB.length
            );
            socket.receive(in);
            String requestId = new String(buffB, 0, 8, StandardCharsets.UTF_8);
            ByteArrayInputStream byteArrayInputStream =new ByteArrayInputStream(buffB, 8, in.getLength() - 8 );

            ObjectInputStream objectInputStream =new ObjectInputStream(byteArrayInputStream);

            Product product =(Product) objectInputStream.readObject();

            // c1. Doi tu dau tien va tu cuoi cung
            String[] words = product.name.trim().split("\\s+");

            if (words.length > 1) {
                String temp = words[0];
                words[0] = words[words.length - 1];
                words[words.length - 1] = temp;
            }

            product.name = String.join(" ", words);

            // c2. Dao nguoc so luong
            String quantity = String.valueOf(product.quantity);
            String reversed = new StringBuilder(quantity)
                    .reverse().toString();

            product.quantity = Integer.parseInt(reversed);

            // c3. Chuyen object thanh byte[]
            ByteArrayOutputStream byteArrayOutputStream =
                    new ByteArrayOutputStream();

            ObjectOutputStream objectOutputStream =
                    new ObjectOutputStream(byteArrayOutputStream);

            objectOutputStream.writeObject(product);
            objectOutputStream.flush();

            byte[] rID = requestId.getBytes(StandardCharsets.UTF_8);
            byte[] obj = byteArrayOutputStream.toByteArray();

            byte[] send = new byte[8 + obj.length];

            System.arraycopy(rID, 0, send, 0, 8);
            System.arraycopy(obj, 0, send, 8, obj.length);

            // d. Gui lai server
            DatagramPacket result = new DatagramPacket(
                    send, send.length, host, port
            );
            socket.send(result);

            objectInputStream.close();
            objectOutputStream.close();
        }
    }
}
