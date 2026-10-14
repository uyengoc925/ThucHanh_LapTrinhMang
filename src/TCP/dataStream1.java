package TCP;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class dataStream1 {
    public static void main(String args[]){
        String serverHost = "36.50.135.242";
        int serverPort = 2207;

        String studentCode = "B23DCCN912";
        String qCode = "LbXYWL8S";

        Socket socket = null;
        DataInputStream dis = null;
        DataOutputStream dos = null;

        try{
            // start connect
            socket = new Socket(serverHost, serverPort);
            // timeout  = 5s
            socket.setSoTimeout(5000);
            //create 2 byte data
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            // send studentCode;qCode
            String request = studentCode +";" + qCode;
            dos.writeUTF(request);
            dos.flush();
            // revice 2 interger
            int a = dis.readInt();
            int b = dis.readInt();

            int ab = a*b;
            int sum = a+b;

            // send to server
            dos.writeInt(sum);
            dos.writeInt(ab);
            dos.flush( );

        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally {
            // d. Đóng các luồng và kết nối
            try {
                if (dos != null) dos.close();
                if (dis != null) dis.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }
}
