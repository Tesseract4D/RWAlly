package cn.tesseract.union.api.rusted;

import cn.tesseract.union.accessor.PlayerAccessor;
import com.corrodinggames.rts.union.game.Player;

import java.util.HashMap;
import java.util.Map;

public class RustedPlayer extends RustedWrapper<Player> {
    public final Map<String, Object> data = new HashMap<>();

    public RustedPlayer(Player instance) {
        super(instance);
    }

    public static RustedPlayer warp(Player inner) {
        return inner == null ? null : ((PlayerAccessor) inner).get_wrapper();
    }

    public static int getMaxTeamId() {
        return Player.maxTeamId;
    }

    public static RustedPlayer getPlayer(int i) {
        return warp(Player.method_526(i));
    }

    public static void setMaxPlayer(int n) {
        Player.setMaxTeamId(n, true);
    }

    public String getName() {
        return inner.name;
    }

    public void setName(String name) {
        inner.name = name;
    }

    public int getIndex() {
        return inner.teamId;
    }

    public void setIndex(int index) {
        inner.teamId = index;
    }

    public int getTeam() {
        return inner.team;
    }

    public void setTeam(int team) {
        inner.team = team;
    }

    public void setColor(int color) {
        inner.color = color;
        inner.colour = color;
    }

    public boolean isControlledByAI() {
        return inner.isControlledByAI;
    }

    public String getUUID() {
        return inner.uuid;
    }

    public void setUUID(String uuid) {
        inner.uuid = uuid;
    }

    public int getLastPing() {
        return inner.getLastPing();
    }

    public int getIncome() {
        return inner.getIncome();
    }

    public boolean isDefeated() {
        return inner.teamDefeatedTech;
    }

    public boolean isWipedOut() {
        return inner.teamWipedOut;
    }

    public boolean isAlive() {
        return !inner.teamWipedOut && !inner.teamDefeatedTech;
    }
}
