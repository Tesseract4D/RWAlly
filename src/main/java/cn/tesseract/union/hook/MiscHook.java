package cn.tesseract.union.hook;

import android.os.Bundle;
import android.widget.Toast;
import cn.tesseract.union.asm.Hook;
import com.corrodinggames.rts.union.appFramework.MainMenuActivity;

public class MiscHook {
    @Hook
    public static void onCreate(MainMenuActivity c, Bundle bundle) {
        Toast.makeText(c, "联盟版 v1.1 洗玻璃呀制作", Toast.LENGTH_LONG).show();
    }
}
