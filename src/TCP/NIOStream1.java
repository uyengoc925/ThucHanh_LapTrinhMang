package TCP;

import javax.print.DocFlavor;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;


import static sun.security.jgss.GSSToken.readFully;

public class NIOStream1 {

    static final String HOST = "36.50.135.242";
    static final int PORT = 2211;
    static final int TIMEOUT_MS = 5000;

    // ==== Các hàm hỗ trợ framing ====

   static void sendFrame(SocketChannel ch, String payload) throws IOException{
       byte[] data = payload.getBytes(StandardCharsets.UTF_8);
       ByteBuffer buffer = ByteBuffer.allocate(4 + data.length);
       buffer.putInt(data.length);
       buffer.put(data);
       buffer.flip();
       while (buffer.hasRemaining()) {
       ch.write(buffer);
       }

   }

   static String readFrame(SocketChannel ch) throws  IOException{
       ByteBuffer lenbuf = ByteBuffer.allocate(4);
       readFully(ch, lenbuf);
       lenbuf.flip();
       int len = lenbuf.getInt();

       ByteBuffer databuf = ByteBuffer.allocate(len);
       readFully(ch, databuf);
       databuf.flip();

       return new String(databuf.array(), 0, len, StandardCharsets.UTF_8);
   }

   static void readFully(SocketChannel ch, ByteBuffer buffer) throws IOException {
       while (buffer.hasRemaining()) {
           int bytesRead = ch.read(buffer);
           if (bytesRead == -1) {
               throw new IOException("End of stream reached");
           }
       }
   }

   public static void main(String [] args){
      try(SocketChannel ch = SocketChannel.open()){
          ch.connect(new InetSocketAddress(HOST, PORT));
          ch.socket().setSoTimeout(TIMEOUT_MS);
          String msg1 = "B23DCCN912;VQ6i7gCS\n";
          sendFrame(ch, msg1);

          StringBuilder http = new StringBuilder();
          for(int i =0; i<3; i++){
              String frame = readFrame(ch);
              System.out.println("Received frame: " + frame);
              http.append(frame);
          }

          String httpFull = http.toString();
          System.out.println("full http: " + httpFull);

          String [] lines = httpFull.split("\r\n");
          String []request  = lines[0].split(" ");
          String method = request[0];
          String path = request[1];

          String host = "";
          for(String line:lines){
              if(line.toLowerCase().startsWith("host:")){
                  host = line.substring(5).trim();
              }
          }

          String msg2 = method + ";" + path + ";" + host + "\n";
          sendFrame(ch, msg2);

      }catch (IOException e){
          e.printStackTrace();}
   }



}