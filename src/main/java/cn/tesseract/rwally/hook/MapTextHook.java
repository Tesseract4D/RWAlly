package cn.tesseract.rwally.hook;

import cn.tesseract.dragonfly.asm.Hook;
import cn.tesseract.rwally.accessor.MapTextAccessor;
import cn.tesseract.rwally.util.RWHelper;
import com.corrodinggames.rts.ally.gameFramework.n.class_1262;
import com.corrodinggames.rts.ally.gameFramework.n.class_1296;

public class MapTextHook {
    @Hook(injector = "simple:equals")
    public static void a(class_1296 c, float f, @Hook.LocalVariable(13) class_1262 mt) {
        float size = ((MapTextAccessor) mt).get_baseSize();
        if (size == 0)
            ((MapTextAccessor) mt).set_baseSize(mt.B.getTextSize());
        size *= RWHelper.getGameEngine().cU;
        if (size != mt.B.getTextSize())
            mt.B.setTextSize(size);
    }
}