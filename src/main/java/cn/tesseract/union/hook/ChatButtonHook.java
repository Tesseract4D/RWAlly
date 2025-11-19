package cn.tesseract.union.hook;

import cn.tesseract.union.accessor.SideBarAccessor;
import cn.tesseract.union.asm.Hook;
import cn.tesseract.union.asm.ReturnCondition;
import cn.tesseract.union.button.*;
import com.corrodinggames.rts.union.game.units.a.class_336;
import com.corrodinggames.rts.union.game.units.a.class_339;
import com.corrodinggames.rts.union.game.units.class_426;
import com.corrodinggames.rts.union.gameFramework.f.class_908;
import com.corrodinggames.rts.union.gameFramework.f.class_961;

import java.util.ArrayList;

public class ChatButtonHook {
    @Hook(targetMethod = "<init>", injector = "exit")
    public static void init(class_961 c) {
        SideBarAccessor a = (SideBarAccessor) c;
        a.set_chatButton(new GlobalChatButton());
        a.set_rangeTypeButton(new RangeTypeButton());
        a.set_rangeTypeButton2(new RangeType2Button());
        a.set_shiftButton(new ShiftButton());
        a.set_stopButton(new StopButton());
        a.set_mapTextButton(new MapTextButton());
    }

    @Hook(injector = "simple:add,1")
    public static void method_2365(class_908 c, class_426 class_415Var, ArrayList arrayList) {
        SideBarAccessor a = (SideBarAccessor) c.field_5182;
        c.field_5199.add(a.get_chatButton());
        c.field_5199.add(a.get_mapTextButton());
        c.field_5199.add(a.get_rangeTypeButton());
        c.field_5199.add(a.get_rangeTypeButton2());
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static float method_624(class_336 c) {
        return 1f;
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static float method_624(class_339 c) {
        return 1f;
    }
}