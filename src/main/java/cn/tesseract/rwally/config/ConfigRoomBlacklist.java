package cn.tesseract.rwally.config;

import java.util.HashSet;
import java.util.Set;

public class ConfigRoomBlacklist extends Config<RoomBlacklist> {
    public Set<String> blacklistedIds = new HashSet<>();

    public ConfigRoomBlacklist(String path) {
        super(path, new RoomBlacklist());
    }

    public void read() {
        super.read();
        blacklistedIds.clear();
        for (String id : instance.ids) {
            int i = id.lastIndexOf('|');
            if (i == -1 || id.length() - 1 == i) continue;
            blacklistedIds.add(id.substring(i + 1));
        }
    }

    public void blacklist(String user, String map, String uuid) {
        if (!blacklistedIds.contains(uuid)) {
            instance.ids.add(user + "|" + map + "|" + uuid);
            blacklistedIds.add(uuid);
        }
        save();
    }

    public boolean isBlacklisted(String uuid) {
        return blacklistedIds.contains(uuid);
    }
}
