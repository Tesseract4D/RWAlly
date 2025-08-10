package cn.tesseract.union.hook;

import android.R;
import android.widget.Spinner;
import cn.tesseract.union.asm.Hook;
import cn.tesseract.union.asm.ReturnCondition;
import com.corrodinggames.rts.union.appFramework.MultiplayerBattleroomActivity;
import com.corrodinggames.rts.union.appFramework.class_166;
import com.corrodinggames.rts.union.appFramework.class_167;
import com.corrodinggames.rts.union.game.Player;
import com.corrodinggames.rts.union.game.EnumColor;
import com.corrodinggames.rts.union.gameFramework.GameEngine;
import com.corrodinggames.rts.union.gameFramework.h.class_988;
import com.corrodinggames.rts.union.gameFramework.j.NetworkEngine;
import com.corrodinggames.rts.union.gameFramework.m.class_1189;
import com.corrodinggames.rts.union.gameFramework.m.class_1216;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ColorHook {
    public static String[] teamColorNames = new String[]{
            "绿",
            "红",
            "蓝",
            "黄",
            "青",
            "白",
            "黑",
            "粉",
            "橙",
            "紫",
            "橄榄绿",
            "深红",
            "天蓝",
            "金色",
            "暗紫",
            "深青",
            "暗灰",
            "淡粉",
            "玫瑰红",
            "亮黄",
            "青绿",
            "沙棕",
            "淡紫",
            "巧克力色",
            "海军蓝",
            "淡蓝",
            "暗绿",
            "灰蓝",
            "亮粉",
            "火焰红",
            "草绿",
            "紫罗兰",
            "酱色",
            "橙红",
            "薄荷绿",
            "栗色",
            "薄荷蓝",
            "绿松石",
            "古董白",
            "玛瑙色",
            "水鸭绿",
            "鳄梨色",
            "胭脂红",
            "苍白绿",
            "蛋壳色",
            "碳灰色",
            "蒂芙尼蓝",
            "云母色",
            "莓果紫",
            "杏色",
            "蛋黄",
            "鲜红",
            "亮绿",
            "玛瑙红",
            "亮紫",
            "草坪绿",
            "淡黄",
            "勃艮第红",
            "茶色",
            "浅绿",
            "青铜",
            "天空蓝",
            "桃红",
            "亮棕",
            "青绿",
            "奶油色",
            "褐",
            "珊瑚红",
            "朱红",
            "紫红",
            "浅蓝",
            "深紫",
            "暗桃色",
            "金铜色",
            "桃色",
            "天蓝",
            "钴蓝",
            "火山灰色",
            "黄绿",
            "深青绿",
            "橙黄",
            "浅紫",
            "柠檬黄",
            "蓝宝石",
            "姜黄",
            "珊瑚色",
            "罂粟红",
            "葡萄紫",
            "淡紫红",
            "幻影紫",
            "海洋蓝",
            "玫瑰色",
            "古铜色",
            "鲜绿",
            "松绿",
            "月亮灰",
            "乌木色",
            "亮橙",
            "葡萄蓝",
            "杏仁色"
    };
    public static int[] teamColors = new int[]{
            0xff00ff00,
            0xffd02013,
            0xff0463f3,
            0xffffff40,
            0xff00ffff,
            0xffd0f8f7,
            0xff000000,
            0xffff00ea,
            0xffff7f18,
            0xff9368c4,
            0xff6b8e23,
            0xff8b0000,
            0xff87ceeb,
            0xffffd700,
            0xff800080,
            0xff008b8b,
            0xff2f4f4f,
            0xffffb6c1,
            0xffff007f,
            0xfffff200,
            0xff20b2aa,
            0xfff4a300,
            0xffe6a8d7,
            0xffd2691e,
            0xff000080,
            0xffadd8e6,
            0xff006400,
            0xff6699cc,
            0xffff69b4,
            0xfff94c2f,
            0xff7cfc00,
            0xff8a2be2,
            0xff800000,
            0xffff4500,
            0xff98ff98,
            0xff800000,
            0xffb2e0e0,
            0xff40e0d0,
            0xfffaebd7,
            0xff000000,
            0xff00b4b4,
            0xff568203,
            0xff9b111e,
            0xffb4e7c4,
            0xfff1e3c6,
            0xff36454f,
            0xff0abfbc,
            0xffd1c7b7,
            0xff8e358a,
            0xffffc8a3,
            0xffffe135,
            0xffff3f34,
            0xff32cd32,
            0xff9f1d1d,
            0xff9b30b4,
            0xff7cfc00,
            0xfffff5b7,
            0xff9e1b32,
            0xffd2691e,
            0xffb0e57c,
            0xffcd7f32,
            0xff87cefa,
            0xffff6666,
            0xff8e6e53,
            0xff00a699,
            0xfffff1cc,
            0xffa52a2a,
            0xffff6347,
            0xffd84124,
            0xff9b1b30,
            0xffadd8e6,
            0xff6a0dad,
            0xffe9b7a3,
            0xffb87333,
            0xffffcc99,
            0xff009acd,
            0xff0047ab,
            0xff595959,
            0xff9acd32,
            0xff008080,
            0xffffb84d,
            0xffb89df7,
            0xfffff700,
            0xff0f52ba,
            0xfff4a300,
            0xffff7f50,
            0xff9b111e,
            0xff6f2da8,
            0xffff66cc,
            0xff9b30b4,
            0xff1e90ff,
            0xffff007f,
            0xff7f4f24,
            0xff32cd32,
            0xffb5e1a2,
            0xffb6b6b6,
            0xff3d2b1f,
            0xffffa500,
            0xff6a5acd,
            0xfff4c2c2
    };

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void setupPlayerColorDropDown(MultiplayerBattleroomActivity c, Spinner spinner, boolean z, boolean z2, Player class_315Var) {
        String str;
        int i;
        int i2;
        List<class_167> arrayList = new ArrayList<>();
        if (z) {
            arrayList.add(new class_167("-99", class_988.method_2636("menus.settings.option.default", new Object[0]), null));
        }
        for (int i3 = 0; i3 < teamColorNames.length; i3++) {
            boolean z3 = z2 && NetworkEngine.method_2728(i3, class_315Var);
            String h = Player.method_524(i3);
            if (h == null) {
                str = null;
            } else if (h.isEmpty()) {
                str = h.toUpperCase();
            } else {
                str = h.substring(0, 1).toUpperCase(Locale.ROOT) + h.substring(1).toLowerCase(Locale.ROOT);
            }
            if (z3) {
                str = str + " (used)";
                i2 = -7829368;
                i = -99;
            } else {
                i = i3;
                i2 = i3;
            }
            arrayList.add(new class_167(String.valueOf(i), str, Player.method_522(i2)));
        }
        class_166 class_182Var = new class_166(c, arrayList);
        class_182Var.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(class_182Var);
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static String method_524(Player c, int i2) {
        return (i2 < 0 || i2 >= teamColorNames.length) ? "GRAY" : teamColorNames[i2];
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static int method_522(Player c, int i2) {
        if (i2 >= 0 && i2 < teamColors.length) {
            return teamColors[i2];
        }
        return 0xff777777;
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static int method_452(Player c) {
        return c.field_1457 < 0 ? 5 : c.field_1401 == null ? c.field_1457 % teamColors.length : c.field_1402 < 0 ? 5 : c.field_1402;
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static class_1189[] method_479(Player c, class_1189 e, EnumColor q) {
        class_1189[] class_1189VarArr = new class_1189[teamColorNames.length];
        int i = 0;
        if ((GameEngine.field_6304 && !GameEngine.field_6306) || q == EnumColor.disabled) {
            while (i < teamColorNames.length) {
                class_1189VarArr[i] = e;
                i++;
            }
            return class_1189VarArr;
        }
        class_1189[] class_1189VarArrMethod_3204 = e.method_3204(q);
        if (class_1189VarArrMethod_3204 != null) {
            return class_1189VarArrMethod_3204;
        }
        while (i < teamColorNames.length) {
            int iMethod_522 = Player.method_522(i);
            if (i == 0) {
                class_1189VarArr[i] = e;
            } else {
                class_1189VarArr[i] = new class_1216(e, iMethod_522, q, i);
            }
            i++;
        }
        e.method_3205(q, class_1189VarArr);
        return class_1189VarArr;
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static class_1189[] method_495(Player c, class_1189 e, EnumColor q) {
        class_1189[] class_1189VarArr = new class_1189[teamColorNames.length];
        if ((GameEngine.field_6304 && !GameEngine.field_6306) || q == EnumColor.disabled) {
            for (int i = 0; i < teamColorNames.length; i++) {
                class_1189VarArr[i] = e;
            }
            return class_1189VarArr;
        }
        class_1189[] class_1189VarArrMethod_3204 = e.method_3204(q);
        if (class_1189VarArrMethod_3204 != null) {
            return class_1189VarArrMethod_3204;
        }
        int[] iArr = new int[teamColorNames.length];
        for (int i2 = 0; i2 < teamColorNames.length; i2++) {
            iArr[i2] = Player.method_522(i2);
        }
        for (int i3 = 0; i3 < teamColorNames.length; i3++) {
            if (i3 != 0) {
                class_1189 class_1189VarClone = e.clone();
                class_1189VarArr[i3] = class_1189VarClone;
                class_1189VarClone.field_6772 = "color(" + i3 + "):" + e.method_3200();
                class_1189VarArr[i3].method_3214();
            }
        }
        e.method_3214();
        if (q == EnumColor.hueAdd) {
            Player.method_507(e, class_1189VarArr, iArr);
        } else if (q == EnumColor.hueShift) {
            Player.method_496(e, class_1189VarArr, iArr);
        } else {
            Player.method_481(e, class_1189VarArr, iArr);
        }
        for (int i4 = 0; i4 < teamColorNames.length; i4++) {
            class_1189 class_1189Var = class_1189VarArr[i4];
            if (class_1189Var != null) {
                class_1189Var.method_3218();
                class_1189VarArr[i4].method_3219();
            }
        }
        e.method_3219();
        class_1189VarArr[0] = e;
        e.method_3205(q, class_1189VarArr);
        return class_1189VarArr;
    }
}