package cn.tesseract.union.hook;

import cn.tesseract.union.accessor.MapTriggerAccessor;
import cn.tesseract.union.asm.Hook;
import cn.tesseract.union.util.MapTextButton;
import com.corrodinggames.rts.union.gameFramework.GameEngine;
import com.corrodinggames.rts.union.gameFramework.n.class_1255;
import com.corrodinggames.rts.union.gameFramework.n.class_1273;

public class MapTextHook {

    @Hook(injector = "simple:equals")
    public static void method_3440(class_1273 c, float f, @Hook.LocalVariable(13) class_1255 mt) {
        float size = ((MapTriggerAccessor) mt).get_baseSize();
        if (size == 0) ((MapTriggerAccessor) mt).set_baseSize(mt.field_7015.getTextSize());
        float size2 = MapTextButton.type ? size * GameEngine.get().field_6404 * MapTextButton.scale : size;
        if (size2 != mt.field_7015.getTextSize()) {
            mt.field_7015.setTextSize(size2);
        }
    }
}