package TCP;

import java.io.*;
import java.net.Socket;

public class TPCCharacterStream1 {
    public static void main(String[] args) throws IOException {
        String serverHost = "36.50.135.242";
        int serverPort = 2208;

        String studentCode = "B23DCCN912";
        String qCode = "aQfZPOd5";

        try {
            // Bước 1: Kết nối Server và khởi tạo Streams
            Socket socket = new Socket(serverHost, serverPort);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            if (socket != null) {
                System.out.println("Connect successful");
                System.out.println(socket);
            }

            // Gửi chuỗi "studentCode;qCode" tới Server
            out.write(studentCode + ";" + qCode);
            out.newLine();
            out.flush();

            // Nhận dữ liệu trả về từ Server
            String data = in.readLine();
            System.out.println("Received:");
            System.out.println(data);

            // Xử lý lọc các tên miền kết thúc bằng ".edu"
            if (data != null) {
                String[] domains = data.split("\\s*,\\s*");
                StringBuilder result = new StringBuilder();

                for (String domain : domains) {
                    if (domain.endsWith(".edu")) {
                        if (result.length() > 0) {
                            result.append(", ");
                        }
                        result.append(domain);
                    }
                }

                // Gửi kết quả xử lý lại cho Server
                out.write(result.toString());
                out.newLine();
                out.flush();

                System.out.println("Sent:");
                System.out.println(result);
            }

            // Đóng kết nối
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
