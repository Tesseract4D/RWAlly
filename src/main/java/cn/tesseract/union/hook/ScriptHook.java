package cn.tesseract.union.hook;

import cn.tesseract.union.asm.Hook;
import cn.tesseract.union.util.ScriptManager;
import com.corrodinggames.rts.union.gameFramework.class_1061;

public class ScriptHook {
    @Hook
    public static void method_3038(class_1061 c, String str) {
        if ("Init completed".equals(str)) {
            ScriptManager.reload();
        }
    }
}