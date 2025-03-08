package cn.tesseract.rwally.hook;

import android.widget.Spinner;
import cn.tesseract.dragonfly.asm.Hook;
import cn.tesseract.dragonfly.asm.ReturnCondition;
import com.corrodinggames.rts.ally.appFramework.MultiplayerBattleroomActivity;
import com.corrodinggames.rts.ally.appFramework.class_169;
import com.corrodinggames.rts.ally.appFramework.class_182;
import com.corrodinggames.rts.ally.game.class_315;
import com.corrodinggames.rts.ally.gameFramework.h.class_993;

import java.util.ArrayList;
import java.util.List;

public class SpawnPositionHook {

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void setupSpawnPositionDropDown(MultiplayerBattleroomActivity c, Spinner spinner, boolean z) {
        List<class_169> arrayList = new ArrayList<>();
        if (z) {
            arrayList.add(new class_169("-99", class_993.a("menus.settings.option.default", new Object[0]), null));
        }
        arrayList.add(new class_169("-3", "观战", -1));

        for (int i = 0; i < 100; i++) {
            arrayList.add(new class_169(String.valueOf(i), (i + 1) + " 号 " + (i % 2 == 0 ? "A" : "B") + " 队 ", class_315.g(i % 10)));
        }

        class_182 class_182Var = new class_182(c, arrayList);
        class_182Var.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(class_182Var);
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void setupTeamAllyDropDown(MultiplayerBattleroomActivity c, Spinner spinner, boolean z) {
        ArrayList<class_169> arrayList = new ArrayList<>();
        if (z) {
            arrayList.add(new class_169("0", "自动", -1));
        }
        for (int i = 0; i < 100; i++) {
            arrayList.add(new class_169(String.valueOf(i + 1), class_315.a(i) + " 队 ", class_315.g(i)));
        }
        class_182 class_182Var = new class_182(c, arrayList);
        class_182Var.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(class_182Var);
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static String a(class_315 c, int t) {
        return t == -3 ? "S" : String.valueOf(t + 1);
    }
}
