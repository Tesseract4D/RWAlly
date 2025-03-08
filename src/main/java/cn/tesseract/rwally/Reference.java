package cn.tesseract.rwally;

import cn.tesseract.rwally.util.Config;
import cn.tesseract.rwally.util.RoomBlacklist;
import com.corrodinggames.rts.ally.gameFramework.e.class_916;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class Reference {
    public static final String version = "2.0";
    public static final File dir = new File(class_916.e("/SD/rustedWarfare"));
    public static final Config<RoomBlacklist> roomBlacklist = new Config<>("roomBlacklist.json", new RoomBlacklist());
    public static final Set<String> bannedUnits = new HashSet<>();
}
