package TCP;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;
public class TPCCharacterStream2 {

    public static void main(String[] args) throws IOException {
        String serverHost = "36.50.135.242";
        int serverPort = 2208;

        String studentCode = "B23DCCN912";
        String qCode = "MMNyh8Uz";

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

            // nhan du lieu tu server
            String respone = in.readLine();
            System.out.println("nhan tu server " + respone);

            if(respone != null && !respone.isEmpty()){
                Map<Character, Integer> charCountMap = new LinkedHashMap<>();
                for(char ch : respone.toCharArray() ){
                    if(Character.isLetterOrDigit(ch)){
                        charCountMap.put(ch, charCountMap.getOrDefault(ch, 0) + 1);
                    }
                }
                StringBuilder result = new StringBuilder();
                for (Map.Entry<Character, Integer> entry : charCountMap.entrySet()) {
                    if(entry.getValue() > 1){
                        result.append(entry.getKey()).append(":").append(entry.getValue()).append(",");

                    }

                }
                // gui ket qua len server
                out.write(result.toString());
                out.newLine();
                out.flush();
                System.out.println("send: "+ result.toString() );

            }
            // d. Đóng kết nối
            socket.close();







        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
