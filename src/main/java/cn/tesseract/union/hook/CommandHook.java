package cn.tesseract.union.hook;

import cn.tesseract.union.asm.Hook;
import cn.tesseract.union.asm.ReturnCondition;
import com.corrodinggames.rts.union.game.Player;
import com.corrodinggames.rts.union.gameFramework.j.NetworkEngine;
import com.corrodinggames.rts.union.gameFramework.j.class_1037;

public class CommandHook {


    @Hook(returnCondition = ReturnCondition.ON_TRUE)
    public static boolean method_2767(NetworkEngine c, class_1037 conn, Player player, String name, String message) {
        boolean qc;
        String command = message.trim();
        if (!command.startsWith("-qc ")) {
            qc = false;
        } else {
            command = command.substring(4).trim();
            qc = true;
        }

        String arg;
        String[] args;

        if ((command.startsWith("-") || command.startsWith(".") || command.startsWith("_")) && command.length() >= 2) {
            String s = command.substring(1).trim();
            int i = s.indexOf(" ");
            if (i == -1) {
                i = s.length();
            }
            command = s.substring(0, i).toLowerCase();
            if (s.length() >= (i = i + 1)) {
                arg = s.substring(i).trim();
                args = s.split(" ");
            }
        } else {
            return false;
        }



        return false;
    }
}
