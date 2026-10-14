package UDP.Object;

import UDP.Book;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.time.chrono.IsoChronology;

public class b5Book {
    public static String Title(String t){
        String[] w = t.trim().toLowerCase().split("\\s+");
        StringBuilder res = new StringBuilder();
        for(String ii : w){
            res.append(ii.substring(0,1).toUpperCase()).append(ii.substring(1)).append(" ");
        }
        return res.toString().trim();
    }

    public static String Author (String a){
        a = Title(a);
        String[] w = a.split("\\s+");
        StringBuilder res = new StringBuilder();
        int n = w.length;
        for(int i =0; i<n-1; i++){
            res.append(w[i]);
            if(i==n-2){
                res.append(", ");
            }
            else{
                res.append(" ");
            }
        }
        res.append(w[n-1]);
        return res.toString().trim();

    }
// yyyy-mm-dd -> mm/yyyy
    public static String PublishDate(String d) {
        String[] w = d.split("-");
        return w[1] + "/" + w[0];
    }

    public static String chuanhoaISBN(String s){
        //Ví dụ: Input: 9783161484100. Ouput: 978-3-16-148410-0
        return String.format("%s-%s-%s-%s-%s", s.substring(0, 3), s.substring(3, 4), s.substring(4, 6), s.substring(6, 12), s.substring(12));
    }

    public static void main(String[] args)throws IOException, ClassNotFoundException {
        String msv = "B23DCCN912";
        String qcode = "YOUR_QCODE";
        String first = ";" + msv + ";" + qcode;
        String ip = "36.50.135.242";
        int port = 809;
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress host = InetAddress.getByName(ip);
            // a. Gui ma sinh vien va ma cau hoi
            byte[] buffA = first.getBytes(StandardCharsets.UTF_8);
            socket.send(new DatagramPacket(buffA, buffA.length, host, port));
            // b. Nhan requestId + object Book
            byte[] buffB = new byte[4096];
            DatagramPacket in = new DatagramPacket(buffB, buffB.length);
            socket.receive(in);

            String requestId = new String(buffB, 0, 8, StandardCharsets.UTF_8);
            ByteArrayInputStream bais = new ByteArrayInputStream(buffB, 8, in.getLength() - 8);
            ObjectInputStream ois = new ObjectInputStream(bais);
            Book book = (Book) ois.readObject();
            // c1. Chuan hoa title
            book.title = b5Book.Title(book.title);
            // c2. Chuan hoa author
            book.author = b5Book.Author(book.author);
            // c3. Chuan hoa ISBN
            // Can xac dinh quy tac tu vi du de bai truoc khi xu ly.
            book.isbn = chuanhoaISBN(book.isbn);
            // c4. Chuyen publishDate yyyy-mm-dd -> mm/yyyy
            book.publishDate = b5Book.PublishDate(book.publishDate);
            // d. Serialize lai object
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(book);
            oos.flush();
            byte[] rID = requestId.getBytes(StandardCharsets.UTF_8);
            byte[] obj = baos.toByteArray();
            byte[] send = new byte[8 + obj.length];
            System.arraycopy(rID, 0, send, 0, 8);
            System.arraycopy(obj, 0, send, 8, obj.length);
            socket.send(new DatagramPacket(
                    send, send.length, host, port
            ));

            ois.close();
            oos.close();
        }
    }


}


