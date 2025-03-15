package cn.tesseract.rwally.util;

import com.corrodinggames.rts.ally.gameFramework.e.class_916;

import java.io.*;

public class FileHelper {
    public static InputStream getInputStream(String path) {
        return class_916.k("/SD/rustedWarfare/" + path);
    }

    public static OutputStream getOutputStream(String path) {
        return class_916.l("/SD/rustedWarfare/" + path);
    }

    public static boolean exists(String path) {
        return class_916.i("/SD/rustedWarfare/" + path);
    }

    public static boolean dirExists(String path) {
        return class_916.f("/SD/rustedWarfare/" + path);
    }

    public static boolean mkdir(String path) {
        return class_916.m("/SD/rustedWarfare/" + path);
    }

    public static boolean renameFile(String from, String to) {
        return class_916.a(from, "/SD/rustedWarfare/", to);
    }

    public static String read(String path) throws IOException {
        InputStream is = getInputStream(path);
        if (is == null) return "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            if (sb.length() == 0) sb.append("\n");
            sb.append(line);
        }
        return sb.toString();
    }

    public static void write(String path, String content) throws IOException {
        OutputStream os = getOutputStream(path);
        os.write(content.getBytes());
        os.close();
    }

    public static String[] listFiles(String path) {
        return class_916.a("/SD/rustedWarfare/" + path, false);
    }
}
