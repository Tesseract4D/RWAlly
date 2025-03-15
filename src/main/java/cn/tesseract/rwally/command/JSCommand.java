package cn.tesseract.rwally.command;

import cn.tesseract.rwally.hook.RhinoHook;
import cn.tesseract.rwally.util.RWHelper;
import com.corrodinggames.rts.ally.gameFramework.j.class_1054;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

public class JSCommand extends CommandBase {
    private final Function func;
    private final String owner;

    public JSCommand(int args, boolean requireOp, String description, Function func, String owner) {
        super(args, requireOp, description);
        this.func = func;
        this.owner = owner;
    }

    @Override
    public String processCommand(class_1054 sender, String[] args) {
        Scriptable scope = RhinoHook.getScope(owner);
        String result = null;
        try {
            result = String.valueOf(func.call(RhinoHook.CONTEXT, scope, scope, args));
        } catch (Throwable e) {
            RWHelper.sendSysMessage("在执行 " + owner + " 时发生错误: " + e);
        }
        return result;
    }
}
