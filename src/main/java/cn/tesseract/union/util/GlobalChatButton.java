package cn.tesseract.union.util;

import com.corrodinggames.rts.union.game.units.a.class_346;
import com.corrodinggames.rts.union.game.units.a.class_349;
import com.corrodinggames.rts.union.game.units.Unit;
import com.corrodinggames.rts.union.gameFramework.GameEngine;
import com.corrodinggames.rts.union.gameFramework.class_780;

public class GlobalChatButton extends class_346 {
    public GlobalChatButton() {
        super("c__cut_chat");
    }

    public String method_588() {
        return "发送全局聊天";
    }

    public String method_600() {
        return "全局聊天";
    }

    public final class_780 method_583() {
        return GameEngine.get().field_6348.field_4308;
    }

    public final boolean method_607(Unit ce, boolean boolean2) {
        GameEngine.get().field_6347.field_5599.method_2371(13);
        return true;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof class_349 c) {
            return Float.compare(method_636() - c.method_636(), 0);
        }
        return 0;
    }
}