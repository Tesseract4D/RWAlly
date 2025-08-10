package cn.tesseract.union.util;

import com.corrodinggames.rts.union.game.units.Unit;
import com.corrodinggames.rts.union.game.units.a.class_346;
import com.corrodinggames.rts.union.game.units.a.class_349;

public class RangeType2Button extends class_346 {
    public static int type = 0;

    public RangeType2Button() {
        super("c__cut_range2");
    }

    public String method_588() {
        return "切换范围显示类型";
    }

    public String method_600() {
        return getTypeName() + "范围";
    }

    public final boolean method_607(Unit ce, boolean boolean2) {
        if (++type == 3) type = 0;
        return true;
    }

    public String getTypeName() {
        if (type == 1) return "建筑";
        else if (type == 2) return "单位";
        else return "全部";
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof class_349 c) {
            return Float.compare(method_636() - c.method_636(), 0);
        }
        return 0;
    }
}