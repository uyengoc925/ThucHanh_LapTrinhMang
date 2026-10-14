package TCP;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ByteStream2 {
    public static void main(String[] args) {
        String serverHost = "36.50.135.242";
        int serverPort = 2206;

        // Thay mã sinh viên và mã câu hỏi tương ứng
        String studentCode = "B23DCCN912";
        String qCode = "CTZi6jdh";

        try {
            // Khởi tạo kết nối Socket và cài đặt Timeout 5s
            Socket socket = new Socket(serverHost, serverPort);
            socket.setSoTimeout(5000);

            // Lấy luồng byte dữ liệu
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            // a. Gửi chuỗi "studentCode;qCode"
            String request = studentCode + ";" + qCode + "\n";
            out.write(request.getBytes());
            out.flush();

            // b. Nhận chuỗi từ server qua InputStream
            byte[] buffer = new byte[1024];
            int bytesRead = in.read(buffer);
            if (bytesRead == -1) {
                socket.close();
                return;
            }

            String response = new String(buffer, 0, bytesRead).trim();
            System.out.println("received: " + response);

            // c. Xử lý tìm khoảng cách nhỏ nhất
            if (!response.isEmpty()) {
                String[] parts = response.split(",");
                int[] numbers = new int[parts.length];
                List<Integer> uniqueNumbers = new ArrayList<>();
                for (int i = 0; i < parts.length; i++) {
                    numbers[i] = Integer.parseInt(parts[i].trim());
                    if(!uniqueNumbers.contains(numbers[i])){
                        uniqueNumbers.add(numbers[i]);
                    }
                }

                Collections.sort(uniqueNumbers, Collections.reverseOrder());
                int second = uniqueNumbers.get(1);

                int index = -1;
                for(int i =0; i<numbers.length; i++){
                    if(numbers[i] == second){
                        index = i;
                        break;
                    }
                }


               // Định dạng kết quả gửi lại server: "khoảng cách nhỏ nhất,số thứ nhất,số thứ hai"
                String result = second + "," + index + "\n";
                out.write(result.getBytes());
                out.flush();
                System.out.println("send: " + result.trim());
            }

            // d. Đóng kết nối
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
