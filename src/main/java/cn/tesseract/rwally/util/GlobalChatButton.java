package cn.tesseract.rwally.util;

import com.corrodinggames.rts.ally.game.units.a.class_351;
import com.corrodinggames.rts.ally.game.units.a.class_371;
import com.corrodinggames.rts.ally.game.units.class_415;
import com.corrodinggames.rts.ally.gameFramework.class_340;
import com.corrodinggames.rts.ally.gameFramework.class_797;

public class GlobalChatButton extends class_351 {
    public GlobalChatButton() {
        super("c__cut_chat");
    }

    @Override
    public String a() {
        return "发送全局聊天";
    }

    @Override
    public String b() {
        return "全局聊天";
    }


    @Override
    public final boolean c(class_415 class_415Var, boolean z) {
        class_340.t().bP.g.b(13);
        return true;
    }

    @Override
    public final class_797 Q() {
        return class_340.t().bQ.t;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof class_371 c) {
            float s = s() - c.s();
            if (s < 0.0f) {
               return  -1;
            } else if (s > 0.0f) {
                return  1;
            }
        }
        return 0;
    }
}
