package cn.tesseract.union.api;

import android.graphics.PointF;
import com.corrodinggames.rts.union.game.class_324;
import com.corrodinggames.rts.union.gameFramework.class_898;

public class Action {
    public static String getId(class_898 c) {
        return c.field_5092.field_1520;
    }

    public static PointF getPoint(class_898 c) {
        return c.field_5093;
    }

    public static class_324 getPlayer(class_898 c) {
        return c.field_5090;
    }
}
