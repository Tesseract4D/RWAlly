package cn.tesseract.union.util;

import android.app.Activity;
import com.corrodinggames.rts.union.appFramework.MultiplayerBattleroomActivity;
import com.corrodinggames.rts.union.game.b.class_299;
import com.corrodinggames.rts.union.game.class_324;
import com.corrodinggames.rts.union.game.units.class_426;
import com.corrodinggames.rts.union.gameFramework.class_1061;
import com.corrodinggames.rts.union.gameFramework.class_773;

import java.lang.reflect.Field;
import java.util.Map;

public class GameHelper {
    public static final class_1061 game = class_1061.method_3076();

    public static void toast(String s) {
        game.method_3056(s);
    }

    public static class_299 getMap() {
        return game.field_6339;
    }

    public static class_426 getUnitById(long id) {
        return class_773.method_1767(id, true);
    }

    public static boolean isUserPlayer(class_324 player) {
        return game.field_6373 == player;
    }

    public static String getCurrentMapName() {
        return game.field_6352.field_5874.field_6013;
    }

    public static void setCurrentMapName(String name) {
        game.field_6352.field_5874.field_6013 = name;
    }

    public static void updateUI() {
        MultiplayerBattleroomActivity.updateUI();
    }

    public static void startGame() {
        MultiplayerBattleroomActivity.lastLoaded.runOnUiThread(() -> MultiplayerBattleroomActivity.lastLoaded.startNetButton.performClick());
    }

    public static Activity getActivity() {
        try {
            Class ac = Class.forName("android.app.ActivityThread");
            Class acr = Class.forName("android.app.ActivityThread$ActivityClientRecord");

            Object t = ac.getDeclaredMethod("currentActivityThread").invoke(null);
            Field m = t.getClass().getDeclaredField("mActivities");
            m.setAccessible(true);
            Field n = acr.getDeclaredField("paused");
            n.setAccessible(true);
            Field p = acr.getDeclaredField("activity");
            p.setAccessible(true);

            for (Object o : ((Map) m.get(t)).values()) {
                if (!(boolean) n.get(o)) {
                    return (Activity) p.get(o);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}