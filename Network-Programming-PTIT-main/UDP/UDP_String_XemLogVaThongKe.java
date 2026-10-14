package UDP;

import java.io.*;
import java.net.*;

public class UDP_String_XemLogVaThongKe {
    public static void main(String[] args) throws Exception {
        DatagramSocket s = new DatagramSocket();
        InetAddress ip = InetAddress.getByName("36.50.135.242");
        int port = 2208;

        String msg = ";B23DCCN752;XbYdNZ3A";
        byte[] b = msg.getBytes();

        s.send(new DatagramPacket(b, b.length, ip, port));

        b = new byte[4096];
        DatagramPacket p = new DatagramPacket(b, b.length);
        s.receive(p);

        String str = new String(p.getData(), 0, p.getLength());

        String[] x = str.split(";", 2);
        String requestId = x[0];
        String data = x[1];

        String[] logs = data.split("\\|\\|");

        int error = 0, info = 0, warn = 0;
        String maskedLog = "";

        for (String log : logs) {
            if (log.startsWith("ERROR")) error++;
            else if (log.startsWith("INFO")) info++;
            else if (log.startsWith("WARN")) warn++;

            log = log.replaceAll(
                    "[\\w.+-]+@[\\w.-]+",
                    "[EMAIL]"
            );

            log = log.replaceAll(
                    "\\b\\d{10,11}\\b",
                    "[PHONE]"
            );

            log = log.replaceAll(
                    "token=\\S+",
                    "token=[TOKEN]"
            );

            maskedLog += log + "||";
        }

        if (maskedLog.endsWith("||")) {
            maskedLog = maskedLog.substring(0, maskedLog.length() - 2);
        }

        String ans = requestId + ";" + maskedLog
                + "##ERROR=" + error
                + ";INFO=" + info
                + ";WARN=" + warn;

        b = ans.getBytes();
        s.send(new DatagramPacket(b, b.length, ip, port));

        s.close();
    }
}
