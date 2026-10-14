package TCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class DataStream2 {
    public static void main(String [] args) throws IOException {

        String host = "36.50.135.242";
        int port = 2207;

        String msv = "B23DCCN912";
        String qcode = "S4cntmQ8";
        String frist = msv + ";" + qcode + "\n";

        try(Socket socket = new Socket(host, port)){
            socket.setSoTimeout(5000);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            dos.writeUTF(frist);
            dos.flush();

            String recive = dis.readUTF().trim();
            int s = dis.readInt();
            int len = recive.length();
            char [] a = recive.toCharArray();
            char [] b = new char[len];
            for(int i =0; i<len; i++){
                if(a[i] >='a' && a[i] <='z'){
                    int tmp = a[i] -'a';
                    b[i] = (char) ((tmp+ s + 26)%26 + 'a');
                }
                else if(a[i] >='A' && a[i] <='Z'){
                    int tmp = a[i] -'A';
                    b[i] = (char) ((tmp+ s + 26)%26 + 'A');
                }
                else{
                    b[i] = a[i];
                }
            }
            String result = new String(b);
            dos.writeUTF(result);
            dos.flush();

            dis.close();
            dos.close();

        }

    }
}
