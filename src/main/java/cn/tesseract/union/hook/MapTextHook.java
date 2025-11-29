package cn.tesseract.union.hook;

import cn.tesseract.union.accessor.MapTriggerAccessor;
import cn.tesseract.union.asm.Hook;
import cn.tesseract.union.button.MapTextButton;
import cn.tesseract.union.util.NetHelper;
import com.corrodinggames.rts.union.game.class_317;
import com.corrodinggames.rts.union.game.class_324;
import com.corrodinggames.rts.union.game.units.class_426;
import com.corrodinggames.rts.union.game.units.custom.class_471;
import com.corrodinggames.rts.union.game.units.custom.class_527;
import com.corrodinggames.rts.union.gameFramework.class_1061;
import com.corrodinggames.rts.union.gameFramework.class_898;
import com.corrodinggames.rts.union.gameFramework.n.class_1255;
import com.corrodinggames.rts.union.gameFramework.n.class_1273;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class MapTextHook {
    public static final ConcurrentHashMap<Long, class_1255> mappings = new ConcurrentHashMap<>();

    @Hook(injector = "simple:equals")
    public static void method_3440(class_1273 c, float f, @Hook.LocalVariable(13) class_1255 mt) {
        float size = ((MapTriggerAccessor) mt).get_baseSize();
        if (size == 0) ((MapTriggerAccessor) mt).set_baseSize(mt.field_7015.getTextSize());
        float size2 = MapTextButton.type ? size * class_1061.method_3076().field_6404 * MapTextButton.scale : size;
        if (size2 != mt.field_7015.getTextSize()) {
            mt.field_7015.setTextSize(size2);
        }
    }

    @Hook(injector = "exit")
    public static void method_3014(class_317 c, boolean boolean1, boolean boolean2, int type) {
        mappings.clear();
        for (class_1255 mt : (ArrayList<class_1255>) class_1061.method_3076().field_6411.field_7084) {
            var comment = mt.method_3419("comment");
            var prefix = "unit:";
            if (comment != null && comment.startsWith(prefix)) {
                var unit = comment.substring(prefix.length()).split(",");
                if (unit.length != 0) {
                    ((MapTriggerAccessor) mt).set_boundUnit(unit[0]);
                    if (NetHelper.isHost() && type == 2) {
                        var team = -2;
                        if (unit.length > 1) try {
                            team = Integer.parseInt(unit[1]);
                        } catch (NumberFormatException ignored) {
                        }
                        var player = class_324.method_526(team);
                        if (player == null || player.field_1464 == -3) player = class_324.method_526(-2);
                        NetHelper.spawnUnit(player, unit[0], mt.method_3411(), mt.method_3417(), 0);
                    }
                }
            }
        }
    }

    @Hook
    public static void method_912(class_426 c, class_471 af, class_426 ce) {
        if (NetHelper.isHost() && af == class_471.field_2400 && mappings.containsKey(ce.field_4220)) {
            for (class_898 action : (ArrayList<class_898>) class_1061.method_3076().field_6412.field_4801) {
                var mt = mappings.get(ce.field_4220);
                if (action.field_5103 == 5 && (float) mt.method_3411() == action.field_5091.field_3931 && (float) mt.method_3417() == action.field_5091.field_3932) {
                    action.field_5090 = c.field_1927;
                }
            }
        }
    }

    @Hook
    public static void method_961(class_426 ce) {
        if (NetHelper.isHost() && mappings.containsKey(ce.field_4220)) {
            var mt = mappings.get(ce.field_4220);
            NetHelper.spawnUnit(class_324.method_526(-2), ce.method_1059().method_1660(), mt.method_3411(), mt.method_3417(), 0);
        }
    }

    @Hook(createMethod = true)
    public static void onCommandSpawn(class_426 c) {
        c.method_1018();
    }

    private static int lastSec = -1;

    @Hook(targetMethod = "method_425", injector = "exit")
    public static void onTick(class_317 c, float f) {
        int current = class_1061.method_3076().field_6379 / 1000;
        if (lastSec != current) {
            lastSec = current;
            if (lastSec % 5 == 0) {
                for (var unit : (AbstractList<class_426>) class_426.method_964()) {
                    var name = unit.method_1059().method_1660();
                    for (class_1255 mt : (ArrayList<class_1255>) class_1061.method_3076().field_6411.field_7084) {
                        if (name.equals(((MapTriggerAccessor) mt).get_boundUnit()) && (float) mt.method_3411() == unit.field_4227 && (float) mt.method_3417() == unit.field_4228) {
                            for (var entry : mappings.entrySet())
                                if (entry.getValue() == mt) mappings.remove(entry.getKey());
                            mappings.put(unit.field_4220, mt);
                            setTriggerText(mt, unit.field_1927);
                            break;
                        }
                    }
                }
            }
        }
    }

    public static void setTriggerText(class_1255 mt, class_324 p) {
        if (mt.field_7042 == null) return;
        String text = mt.field_7042.method_1223();
        int i = text.indexOf(' ');
        if (i != -1) text = text.substring(0, i);
        mt.field_7042 = class_527.method_1221(text + " " + (p.field_1468 == null ? "?" : p.field_1468));
        mt.field_7015.setColor(ColorHook.teamColors[p.method_452()]);
    }
}