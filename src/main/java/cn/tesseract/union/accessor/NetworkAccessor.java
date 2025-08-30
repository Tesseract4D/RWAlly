package cn.tesseract.union.accessor;

import cn.tesseract.union.api.rusted.RustedNetwork;
import cn.tesseract.union.asm.Accessor;
import com.corrodinggames.rts.union.game.Player;
import com.corrodinggames.rts.union.gameFramework.j.NetworkEngine;
import com.corrodinggames.rts.union.gameFramework.j.class_1037;

@Accessor.Target(NetworkEngine.class)
public interface NetworkAccessor {
    void invoke_method_2741(class_1037 class_1037Var, Player PlayerVar, String str, String str2);

    boolean invoke_method_2767(class_1037 class_1037Var, Player PlayerVar, String str, String str2);

    void invoke_method_2750(String str, class_1037 class_1037Var);

    void invoke_method_2740(class_1037 class_1037Var, int i, String str, String str2);

    RustedNetwork wrapper_instance();
}