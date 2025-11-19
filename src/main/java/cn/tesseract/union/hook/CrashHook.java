package cn.tesseract.union.hook;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import cn.tesseract.union.asm.Hook;
import cn.tesseract.union.util.GameHelper;
import com.corrodinggames.rts.union.gameFramework.class_1061;

public class CrashHook {
    @Hook
    public static void method_3044(class_1061 c, String string1, String string2) {
        ClipboardManager clipboard = (ClipboardManager) GameHelper.getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copied Text", string2);
        clipboard.setPrimaryClip(clip);
    }
}
