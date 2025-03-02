package cn.tesseract.rwally.hook;

import cn.tesseract.dragonfly.asm.Hook;
import cn.tesseract.dragonfly.asm.ReturnCondition;
import cn.tesseract.rwally.util.Actions;
import cn.tesseract.rwally.Reference;
import com.corrodinggames.rts.ally.gameFramework.class_945;
import com.corrodinggames.rts.ally.gameFramework.j.class_1101;

public class BanUnitHook {
    @Hook(returnCondition = ReturnCondition.ON_TRUE)
    public static boolean a(class_1101 c, class_945 command) {
        String unit = command.k.b;
        while (unit.startsWith("u_") || unit.startsWith("c_"))
            unit = unit.substring(2);
        if (Reference.bannedUnits.contains(unit))
            return true;

        if (command.j != null && Actions.getAction(command.j.a) == Actions.BUILD && Reference.bannedUnits.contains(command.j.b.i()))
            return true;

        return false;
    }
}
