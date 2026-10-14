package UDP.datatype;
import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
public class b4 {
    public static void main(String[] args) throws IOException {
        String first = ";B23DCCN912;waDaDIy6";
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
        String[] s = new String(in.getData()).split(";");
        // s[0] là reuqestID, s[1] là n, s[2] là mảng a

        String result = s[0]+";";
        int n = Integer.parseInt(s[1].trim());
        String[] t = s[2].trim().split(",");
        int len = t.length;
        int[] a = new int[len];
        for(int i = 0; i <len;i++){
            a[i] = Integer.parseInt(t[i]);
        }
        // tìm các số từ 1 đến n mà không có trong mảng a
        for(int i=1; i<=n;i++){
            int flag = 1;
            for(int j = 0;j <len;j++){
                if(a[j] == i)  flag = 0;
            }
            if(flag==1) result += i +",";
        }
        result = result.substring(0,result.length()-1);

        byte[] rs = result.getBytes();
        DatagramPacket dpack = new DatagramPacket(rs,rs.length,ip,2207);
        socket.send(dpack);

        socket.close();
    }
}
/*
a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ;studentCode;qCode.
Ví dụ: ;B15DCCN001;73457A17
b. Nhận thông điệp là một chuỗi từ server theo định dạng requestId;n;A1,A2,...An , với
requestId là chuỗi ngẫu nhiên duy nhất
n là một số ngẫu nhiên nhỏ hơn 100.
A1, A2 ... Am (m <= n) là các giá trị ngẫu nhiên nhỏ hơn hoặc bằng n và có thể trùng nhau.
Ví dụ: requestId;10;2,3,5,6,5
c. Tìm kiếm các giá trị còn thiếu và gửi lên server theo định dạng requestId;B1,B2,...,Bm
Ví dụ: reqqestId;1,4,7,8,9,10
d. Đóng socket và kết thúc chương trình.
 */