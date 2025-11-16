package cn.tesseract.union.hook;

import android.R;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import cn.tesseract.union.asm.Hook;
import cn.tesseract.union.asm.ReturnCondition;
import com.corrodinggames.rts.union.appFramework.MultiplayerBattleroomActivity;
import com.corrodinggames.rts.union.appFramework.class_166;
import com.corrodinggames.rts.union.appFramework.class_167;
import com.corrodinggames.rts.union.game.Player;
import com.corrodinggames.rts.union.gameFramework.h.class_988;

import java.util.ArrayList;
import java.util.List;

public class SpawnPositionHook {
    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void setupSpawnPositionDropDown(MultiplayerBattleroomActivity c, Spinner spinner, boolean z) {
        List<class_167> arrayList = new ArrayList<>();
        if (z) {
            arrayList.add(new class_167("-99", class_988.method_2636("menus.settings.option.default"), null));
        }
        arrayList.add(new class_167("-3", "观战", -1));
        for (int i = 0; i < 100; i++) {
            arrayList.add(new class_167(String.valueOf(i), (i + 1) + " 号 " + (i % 2 == 0 ? "A" : "B") + " 队 ", Player.method_522(i % 10)));
        }
        arrayList.add(new class_167("-3", "观战", -1));
        class_166 class_182Var = new class_166(c, arrayList);
        class_182Var.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(class_182Var);
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void setupTeamAllyDropDown(MultiplayerBattleroomActivity c, Spinner spinner, boolean z) {
        ArrayList<class_167> arrayList = new ArrayList<>();
        if (z) {
            arrayList.add(new class_167("0", "自动", -1));
        }
        for (int i = 0; i < 100; i++) {
            arrayList.add(new class_167(String.valueOf(i + 1), Player.method_467(i) + " 队 ", Player.method_522(i)));
        }
        class_166 class_182Var = new class_166(c, arrayList);
        class_182Var.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(class_182Var);
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static String method_467(Player c, int t) {
        return t == -3 ? "S" : String.valueOf(t + 1);
    }
}