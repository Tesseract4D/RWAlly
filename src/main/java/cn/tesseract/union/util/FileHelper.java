package cn.tesseract.union.util;

import com.corrodinggames.rts.union.gameFramework.e.class_899;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileHelper {
    public static InputStream getInputStream(String path) {
        return class_899.method_2188("/SD/rustedWarfare/" + path);
    }

    public static OutputStream getOutputStream(String path) {
        return class_899.method_2164(new File(class_899.method_2178("/SD/rustedWarfare/" + path)), true);
    }

    public static boolean exists(String path) {
        return class_899.method_2186("/SD/rustedWarfare/" + path);
    }

    public static boolean dirExists(String path) {
        return class_899.method_2180("/SD/rustedWarfare/" + path);
    }

    public static boolean mkdir(String path) {
        return class_899.method_2190("/SD/rustedWarfare/" + path);
    }

    public static String read(InputStream is) throws IOException {
        if (is == null) {
            return "";
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        while (true) {
            String line = reader.readLine();
            if (line != null) {
                if (sb.length() == 0) {
                    sb.append("\n");
                }
                sb.append(line);
            } else {
                return sb.toString();
            }
        }
    }

    public static void write(OutputStream os, byte[] content) throws IOException {
        os.write(content);
        os.close();
    }

    public static void write(OutputStream os, String content) throws IOException {
        write(os, content.getBytes());
    }

    public static String[] listFiles(String path) {
        return class_899.method_2168("/SD/rustedWarfare/" + path, false);
    }

    public static void log(String log) {
        PrintWriter writer = new PrintWriter(class_899.method_2164(new File(class_899.method_2178("/SD/rustedWarfare/union.log")), true));
        writer.write("\r\n" + log + "\n (at " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + "\n");
        writer.close();
    }
}