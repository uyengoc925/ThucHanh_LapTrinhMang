package TCP;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class TCPGZIPSTREAM1 {

    public static void main(String[] args) throws IOException {
        String ip = "36.50.135.242";
        String qcode = "tYrpFLvn";
        String msv = "B23DCCN912";
        int port = 2210;

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(ip, port));
            socket.setSoTimeout(5000);

            GZIPOutputStream out = new GZIPOutputStream(socket.getOutputStream(), true);

            String send1 = msv + ";" + qcode + "\n";
            out.write(send1.getBytes(StandardCharsets.UTF_8));
            out.flush();
            System.out.println("Sent: " + send1.trim());

            GZIPInputStream in = new GZIPInputStream(socket.getInputStream());
            byte buffer[] = new byte[1024];
            int bytesRead = in.read(buffer);
            String receive = new String (buffer, 0, bytesRead, StandardCharsets.UTF_8).trim();
            System.out.println("Received: " + receive);

            String reverse = new StringBuilder(receive).reverse().toString();
            String base64 = Base64.getEncoder().encodeToString(reverse.getBytes(StandardCharsets.UTF_8));

            String send2 = reverse + "|" + base64 + "\n";
            out.write(send2.getBytes(StandardCharsets.UTF_8));
            out.flush();
            System.out.println("Sent: " + send2.trim());

            out.close();
            in.close();

        } catch (SocketTimeoutException e) {
            System.out.println("Hết thời gian chờ server.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}