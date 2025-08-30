package cn.tesseract.union.hook;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import cn.tesseract.union.asm.Hook;
import cn.tesseract.union.api.util.GameHelper;
import com.corrodinggames.rts.union.gameFramework.GameEngine;

public class CrashHook {
    @Hook
    public static void method_3044(GameEngine c, String string1, String string2) {
        ClipboardManager clipboard = (ClipboardManager) GameHelper.getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copied Text", string2);
        clipboard.setPrimaryClip(clip);
    }
}
