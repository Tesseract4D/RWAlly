package cn.tesseract.union.hook;

import android.content.Context;
import cn.tesseract.union.accessor.*;
import cn.tesseract.union.api.rusted.*;
import cn.tesseract.union.asm.Hook;
import com.corrodinggames.rts.union.game.Player;
import com.corrodinggames.rts.union.game.units.Unit;
import com.corrodinggames.rts.union.gameFramework.GameEngine;
import com.corrodinggames.rts.union.gameFramework.j.NetworkEngine;
import com.corrodinggames.rts.union.gameFramework.j.class_1037;

import java.net.Socket;

public class WrapperHook {
    @Hook
    public static void init(NetworkEngine c) {
        ((NetworkAccessor) c).set_wrapper(new RustedNetwork(c));
    }

    @Hook
    public static void init(Player c) {
        ((PlayerAccessor) c).set_wrapper(new RustedPlayer(c));
    }

    @Hook
    public static void init(Unit c) {
        ((UnitAccessor) c).set_wrapper(new RustedUnit(c));
    }

    @Hook
    public static void init(GameEngine c, Context context) {
        ((GameAccessor) c).set_wrapper(new RustedGame(c));
    }

    @Hook
    public static void init(class_1037 c, NetworkEngine ae, Socket socket) {
        ((ConnectionAccessor) c).set_wrapper(new RustedConnection(c));
    }
}
