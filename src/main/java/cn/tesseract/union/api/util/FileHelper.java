package cn.tesseract.union.api.util;

import com.corrodinggames.rts.union.gameFramework.e.FileManager;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileHelper {
    public static InputStream getInputStream(String path) {
        return FileManager.openAssetStream("/SD/rustedWarfare/" + path);
    }

    public static OutputStream getOutputStream(String path) {
        return FileManager.method_2164(new File(FileManager.method_2178("/SD/rustedWarfare/" + path)), true);
    }

    public static boolean exists(String path) {
        return FileManager.fileExists("/SD/rustedWarfare/" + path);
    }

    public static boolean dirExists(String path) {
        return FileManager.dirExists("/SD/rustedWarfare/" + path);
    }

    public static boolean mkdir(String path) {
        return FileManager.mkdir("/SD/rustedWarfare/" + path);
    }

    public static boolean renameFile(String from, String to,String dir) {
        return FileManager.renameFile(from, dir, to);
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
        return FileManager.listDir("/SD/rustedWarfare/" + path, false);
    }

    public static void log(String log) {
        PrintWriter writer = new PrintWriter(FileManager.method_2164(new File(FileManager.method_2178("/SD/rustedWarfare/union.log")), true));
        writer.write("\r\n" + log + "\n (at " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + "\n");
        writer.close();
    }
}