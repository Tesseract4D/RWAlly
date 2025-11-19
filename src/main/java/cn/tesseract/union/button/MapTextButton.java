package cn.tesseract.union.button;

import com.corrodinggames.rts.union.game.units.a.class_346;
import com.corrodinggames.rts.union.game.units.a.class_349;
import com.corrodinggames.rts.union.game.units.class_426;
import com.corrodinggames.rts.union.gameFramework.class_1061;

public class MapTextButton extends class_346 {
    public static boolean type = true;
    public static float scale = 1;

    public MapTextButton() {
        super("c__cut_mt");
    }

    public String method_588() {
        return "";
    }

    public String method_600() {
        return "地图文字\n" + (type ? "跟随缩放" : "尺寸固定");
    }

    public final boolean method_607(class_426 ce, boolean boolean2) {
        type = !type;
        scale = 1 / class_1061.method_3076().field_6404;
        return true;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof class_349 c) {
            return Float.compare(method_636() - c.method_636(), 0);
        }
        return 0;
    }
}