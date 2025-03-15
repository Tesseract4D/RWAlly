package cn.tesseract.rwally.util;

import com.corrodinggames.rts.ally.gameFramework.e.class_916;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogHelper {
    public static void log(String log) {
        PrintWriter writer = new PrintWriter(class_916.a(new File(class_916.e("/SD/rustedWarfare/rwally.log")), true));
        writer.write("\r\n" + log + "\n (at " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + "\n");
        writer.close();
    }
}
