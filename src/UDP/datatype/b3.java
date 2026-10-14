package UDP.datatype;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class b3 {
    public static void main(String[] args) throws IOException {
        String first = ";B23DCCN912;qcode";
        String host = "36.50.135.242";   // ⚠️ điền IP server thật
        int port = 2207;

        DatagramSocket socket = new DatagramSocket();
        InetAddress ip = InetAddress.getByName(host);

        // a. Gửi ;studentCode;qCode
        byte[] buffA = first.getBytes(StandardCharsets.UTF_8);
        socket.send(new DatagramPacket(buffA, buffA.length, ip, port));

        // b. Nhận "requestId;num"
        byte[] buffB = new byte[8192];
        DatagramPacket in = new DatagramPacket(buffB, buffB.length);  // ✅ không truyền ip/port
        socket.receive(in);
        String request = new String(in.getData(), 0, in.getLength(), StandardCharsets.UTF_8);

        String[] w = request.split(";");
        String requestId = w[0];
        String num = w[1];

        // c. Tính tổng các chữ số
        int sum = 0;
        for (char ch : num.toCharArray()) {
            if (ch >= '0' && ch <= '9') {   // an toàn nếu có ký tự khác
                sum += (ch - '0');
            }
        }

        String res = requestId + ";" + sum;
        byte[] c = res.getBytes(StandardCharsets.UTF_8);
        socket.send(new DatagramPacket(c, c.length, ip, port));

        // d. Đóng socket
        socket.close();  // ✅ BẮT BUỘC theo đề
    }
}
/*
a.Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B15DCCN011;A1F3D5B7".
b. Nhận thông điệp là một chuỗi từ server theo định dạng "requestId;num", với: requestId là chuỗi ngẫu nhiên duy nhất; num là một số nguyên lớn.
c. Tính tổng các chữ số trong num và gửi lại tổng này về server theo định dạng "requestId;sumDigits".
d. Đóng socket và kết thúc chương trình.

 */
