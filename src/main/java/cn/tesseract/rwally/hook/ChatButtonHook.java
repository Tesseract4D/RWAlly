package cn.tesseract.rwally.hook;

import cn.tesseract.dragonfly.asm.Hook;
import cn.tesseract.rwally.accessor.SideBarAccessor;
import cn.tesseract.rwally.util.GlobalChatButton;
import com.corrodinggames.rts.ally.game.units.class_415;
import com.corrodinggames.rts.ally.gameFramework.f.class_1001;
import com.corrodinggames.rts.ally.gameFramework.f.class_1002;

import java.util.ArrayList;

public class ChatButtonHook {
    @Hook(targetMethod = "<init>", injector = "exit")
    public static void init(class_1001 c) {
        ((SideBarAccessor) c).set_chat(new GlobalChatButton());
    }

    @Hook(injector = "simple:add,1")
    public static void a(class_1002 c, class_415 class_415Var, ArrayList arrayList) {
        c.aq.add(((SideBarAccessor) c.a).get_chat());
    }
}
