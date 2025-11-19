package cn.tesseract.union.util;

import com.corrodinggames.rts.union.game.class_324;

public class PlayerHelper {
    public static int getMaxTeamId() {
        return class_324.field_1448;
    }

    public static class_324 get(int i) {
        return class_324.method_526(i);
    }

    public static class_324 get(String i) {
        try {
            return class_324.method_526(Integer.parseInt(i) + 1);
        } catch (NumberFormatException e) {
            for (var p : players()) if (p != null && p.field_1468.startsWith(i)) return p;
        }
        return null;
    }

    public static class_324[] players() {
        return class_324.field_1455;
    }
}
