package TCP;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;


public class ByteStream1 {
    public static void main(String[] args) {
        String serverHost = "36.50.135.242";
        int serverPort = 2206;

        // Thay mã sinh viên và mã câu hỏi tương ứng
        String studentCode = "B23DCCN912";
        String qCode = "fA5i8sDs";

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
                for (int i = 0; i < parts.length; i++) {
                    numbers[i] = Integer.parseInt(parts[i].trim());
                }

                // Sắp xếp mảng để dễ dàng tìm khoảng cách giữa các phần tử kề nhau
                Arrays.sort(numbers);

                int minDiff = Integer.MAX_VALUE;
                int num1 = 0;
                int num2 = 0;

                for (int i = 0; i < numbers.length - 1; i++) {
                    int diff = numbers[i + 1] - numbers[i];

                    // Cập nhật khoảng cách nhỏ nhất và ưu tiên cặp số lớn hơn nếu khoảng cách bằng nhau
                    if (diff <= minDiff) {
                        minDiff = diff;
                        num1 = numbers[i];
                        num2 = numbers[i + 1];
                    }
                }

                // Định dạng kết quả gửi lại server: "khoảng cách nhỏ nhất,số thứ nhất,số thứ hai"
                String result = minDiff + "," + num1 + "," + num2 + "\n";
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
