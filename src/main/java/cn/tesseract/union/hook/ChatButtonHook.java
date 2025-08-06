package cn.tesseract.union.hook;

import cn.tesseract.union.accessor.SideBarAccessor;
import cn.tesseract.union.asm.Hook;
import cn.tesseract.union.util.GlobalChatButton;
import cn.tesseract.union.util.ShiftButton;
import cn.tesseract.union.util.StopButton;
import com.corrodinggames.rts.union.game.units.class_426;
import com.corrodinggames.rts.union.gameFramework.f.class_908;
import com.corrodinggames.rts.union.gameFramework.f.class_961;

import java.util.ArrayList;

public class ChatButtonHook {
    @Hook(targetMethod = "<init>", injector = "exit")
    public static void init(class_961 c) {
        ((SideBarAccessor) c).set_chatButton(new GlobalChatButton());
        ((SideBarAccessor) c).set_shiftButton(new ShiftButton());
        ((SideBarAccessor) c).set_stopButton(new StopButton());
    }

    @Hook(injector = "simple:add,1")
    public static void method_2365(class_908 c, class_426 class_415Var, ArrayList arrayList) {
        c.field_5199.add(((SideBarAccessor) c.field_5182).get_chatButton());
    }
}