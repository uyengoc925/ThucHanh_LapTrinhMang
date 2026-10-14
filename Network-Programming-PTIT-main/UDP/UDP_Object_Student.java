package UDP;

import java.net.*;
import java.io.*;

public class UDP_Object_Student {
    public static void main(String[] args) throws Exception {
        DatagramSocket s = new DatagramSocket();
        InetAddress ip = InetAddress.getByName("36.50.135.242");
        int port = 2209;

        String msg = ";B23DCCN752;vN1ZPn6z";
        byte[] b = msg.getBytes();

        s.send(new DatagramPacket(b, b.length, ip, port));

        // Nhận dữ liệu
        b = new byte[4096];
        DatagramPacket p = new DatagramPacket(b, b.length);
        s.receive(p);

        // 8 byte đầu là requestId
        String requestId = new String(b, 0, 8);

        // Các byte sau là object Student
        ByteArrayInputStream bis =
                new ByteArrayInputStream(b, 8, p.getLength() - 8);

        ObjectInputStream ois = new ObjectInputStream(bis);
        Student st = (Student) ois.readObject();

        // Chuẩn hóa tên
        String[] a = st.name.trim().toLowerCase().split("\\s+");
        String name = "";

        for (String x : a) {
            name += Character.toUpperCase(x.charAt(0))
                    + x.substring(1) + " ";
        }

        st.name = name.trim();

        // Tạo email
        String email = a[a.length - 1];

        for (int i = 0; i < a.length - 1; i++) {
            email += a[i].charAt(0);
        }

        st.email = email + "@ptit.edu.vn";

        // Chuyển Student thành byte[]
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);

        oos.writeObject(st);
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
