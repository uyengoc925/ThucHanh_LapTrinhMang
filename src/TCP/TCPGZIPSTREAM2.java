package TCP;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class TCPGZIPSTREAM2 {
    public static void main(String args[]) throws IOException {
        String ip = "36.50.135.242";
        int port = 2210;
        String send1 = "B23DCCN912;OQdqhfFh\n";

       try(Socket socket = new Socket(ip, port)){
           socket.setSoTimeout(5000);
           GZIPOutputStream out = new GZIPOutputStream(socket.getOutputStream(), true);

           out.write(send1.getBytes(StandardCharsets.UTF_8));
           out.flush();
           System.out.println("Sent: " + send1.trim());

           GZIPInputStream in = new GZIPInputStream(socket.getInputStream());
           byte buffer[] = new byte[1024];
           int bytesRead = in.read(buffer);
           String recevie = new String(buffer, 0, bytesRead, StandardCharsets.UTF_8).trim();
           System.out.println("Received: " + recevie);

           // sort characters
           char [] arr = recevie.toCharArray();
           Arrays.sort(arr);
           String result = new String(arr);

           out.write((result.getBytes(StandardCharsets.UTF_8)));
          out.flush();

          System.out.println("Sent: " + result.trim());

          out.close();
          in.close();




       }

    }
}
