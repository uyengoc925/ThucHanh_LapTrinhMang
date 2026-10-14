package UDP.datatype;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class b5 {
    public static void main(String[] args) throws IOException {
        String first = ";B23DCCN912;iiIKWkeM";
        String host = "36.50.135.242";   // ⚠️ điền IP server thật
        int port = 2207;

        DatagramSocket socket = new DatagramSocket();
        InetAddress ip = InetAddress.getByName(host);

        // a. Gửi ;studentCode;qCode
        byte[] buffA = first.getBytes(StandardCharsets.UTF_8);
        socket.send(new DatagramPacket(buffA, buffA.length, ip, port));

        // b. Nhận "requestId;num"
        byte[] buffB = new byte[8192];
        DatagramPacket in = new DatagramPacket(buffB, buffB.length);
        socket.receive(in);
        String request = new String(in.getData(), 0, in.getLength(), StandardCharsets.UTF_8);

        String[] w = request.split(";");
        String[] num = w[1].split(",");

        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (String ch : num) {
            int tmp = Integer.parseInt(ch);
            if(tmp > max){
                max = tmp;
            }
            if(tmp < min){
                min = tmp;
            }
        }

        String res = w[0] + ";" + max + "," + min;
        byte[] c = res.getBytes(StandardCharsets.UTF_8);
        socket.send(new DatagramPacket(c, c.length, ip, port));

        // d. Đóng socket
        socket.close();
    }
}
/*
b.Nhận thông điệp là một chuỗi từ server theo định dạng
“requestId;a1,a2,...,a50” với requestId là chuỗi ngẫu nhiên duy nhất; a1 -> a50 là 50 số nguyên ngẫu nhiên
c. Thực hiện tìm giá trị lớn nhất và giá trị nhỏ nhất thông điệp trong a1 -> a50 và gửi thông điệp lên lên server theo định dạng “requestId;max,min”

 */
