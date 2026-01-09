package cn.tesseract.union.hook;

import cn.tesseract.union.asm.Hook;
import cn.tesseract.union.asm.ReturnCondition;
import com.corrodinggames.rts.union.gameFramework.j.class_1001;

public class UUIDHook {
    public static String forceUUID = "";

    @Hook(returnCondition = ReturnCondition.ON_NOT_NULL)
    public static String method_2717(class_1001 c) {
        return (forceUUID == null || forceUUID.isEmpty()) ? null : forceUUID;
    }
}
