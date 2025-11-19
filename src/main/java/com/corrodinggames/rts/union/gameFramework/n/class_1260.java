package com.corrodinggames.rts.union.gameFramework.n;

import android.graphics.Paint;
import android.graphics.Typeface;
import cn.tesseract.union.util.NetHelper;
import com.corrodinggames.rts.game.units.custom.logicBooleans.VariableScope;
import com.corrodinggames.rts.union.game.b.class_298;
import com.corrodinggames.rts.union.game.b.class_305;
import com.corrodinggames.rts.union.game.class_324;
import com.corrodinggames.rts.union.game.units.custom.class_548;
import com.corrodinggames.rts.union.gameFramework.class_1061;
import com.corrodinggames.rts.union.gameFramework.n.a.class_1257;
import com.corrodinggames.rts.union.gameFramework.n.a.class_1258;

import java.util.Iterator;

public class class_1260 {
    public static class_1255 method_3436(class_1273 p, class_298 a2) throws class_305 {
        try {
            class_1061 gameEngine = class_1061.method_3076();
            String str = a2.field_859;
            if (str == null) {
                str = "NULL";
            }
            String id = a2.method_288("id");
            if (id != null && !id.equals(VariableScope.nullOrMissingString)) {
                str = id;
            }
            String strTrim = str.trim();
            String str2 = a2.field_861;
            if (str2 != null) {
                class_1261 class_1261VarMethod_3438 = class_1261.method_3438(str2);
                if (class_1261VarMethod_3438 == null) {
                    class_1273.method_3444("Error: Unknown type:" + str2 + " found on " + strTrim);
                    return null;
                }
                class_1255 trigger = new class_1255();
                trigger.field_7036 = a2;
                trigger.field_7023 = class_1261VarMethod_3438;
                trigger.field_7018 = strTrim;
                Iterator it = p.field_7084.iterator();
                int i = 0;
                while (it.hasNext()) {
                    if (((class_1255) it.next()).field_7018.equalsIgnoreCase(trigger.field_7018)) {
                        i++;
                    }
                }
                trigger.field_7019 = trigger.field_7018;
                if (i != 0) {
                    trigger.field_7019 += "_" + i;
                }
                trigger.field_7017 = a2.field_859;
                Integer numMethod_3423 = trigger.method_3423("team");
                if (numMethod_3423 != null) {
                    trigger.field_7041 = class_324.method_526(numMethod_3423);
                    if (trigger.field_7041 == null) {
                        trigger.method_3427("Cannot find team:".concat(String.valueOf(numMethod_3423)));
                        return null;
                    }
                }
                trigger.field_7034 = trigger.method_3420("delay", trigger.field_7034);
                trigger.field_7032 = trigger.method_3420("repeatDelay", trigger.field_7032);
                trigger.field_7031 = trigger.method_3415("repeatCount", trigger.field_7031);
                trigger.field_7033 = trigger.method_3420("resetActivationAfter", trigger.field_7033);
                trigger.field_7024 = trigger.method_3424("allToActivate");
                trigger.field_7020.field_7062 = trigger.field_7024;
                trigger.field_7035 = trigger.method_3420("warmup", trigger.field_7035);
                trigger.field_7014 = trigger.method_3426("globalMessage");
                trigger.field_7039 = trigger.method_3422("textOffsetX");
                trigger.field_7040 = trigger.method_3422("textOffsetY");
                if (trigger.field_7023 == class_1261.field_7069 || trigger.field_7023 == class_1261.field_7063) {
                    trigger.field_7042 = trigger.method_3426("text");
                }
                if (trigger.field_7023 == class_1261.field_7069) {
                    p.field_7103 = true;
                    trigger.field_7015 = new Paint();
                    trigger.field_7015.setAntiAlias(true);
                    trigger.field_7015.setTextAlign(Paint.Align.CENTER);
                    trigger.field_7015.setTypeface(Typeface.create(Typeface.SANS_SERIF, 1));
                    trigger.field_7015.setColor(trigger.method_3425("textColor"));
                    gameEngine.method_3029(trigger.field_7015, trigger.method_3415("textSize", 20));
                    if (trigger.field_7015.getAlpha() == 0) {
                        trigger.method_3427("Text has an alpha of 0");
                    }
                    String strMethod_3419 = trigger.method_3419("style");
                    if (strMethod_3419 != null && !strMethod_3419.equals(VariableScope.nullOrMissingString)) {
                        if (strMethod_3419.equalsIgnoreCase("arrow")) {
                            trigger.field_7016 = true;
                        } else {
                            trigger.method_3427("Unknown style: ".concat(String.valueOf(strMethod_3419)));
                        }
                    }
                }
                if (trigger.field_7023 == class_1261.field_7067) {
                    trigger.field_7038 = class_548.method_1260(null, trigger.method_3419("spawnUnits"), "<unitAdd>", "spawnUnits", false);
                    if (trigger.field_7041 == null) {
                        trigger.method_3427("No team set");
                    }
                }
                if (trigger.field_7023 == class_1261.field_7066) {
                    trigger.method_3414("addTeamTags");
                    trigger.method_3414("removeTeamTags");
                }
                if (trigger.field_7023 == class_1261.field_7065) {
                    trigger.method_3414("add");
                    trigger.method_3414("set");
                }
                if (trigger.field_7023 == class_1261.field_7071) {
                    trigger.method_3413(class_1258.method_3433(trigger));
                }
                if (trigger.field_7023 == class_1261.field_7072) {
                    trigger.method_3413(class_1257.method_3432(trigger));
                }
                trigger.method_3414("comment");
                trigger.method_3414("team");
                trigger.method_3414("globalMessage");
                trigger.method_3414("globalMessage_delayPerChar");
                trigger.method_3414("globalMessage_textColor");
                trigger.method_3414("debugMessage");
                trigger.method_3414("showOnMap");
                trigger.method_3414("text");
                trigger.method_3414("target");
                trigger.method_3414("onlyIfEmpty");
                if (trigger.field_7023 == class_1261.field_7064) {
                    trigger.method_3414("unload");
                }
                if (trigger.field_7023 == class_1261.field_7068) {
                    trigger.method_3414("onlyIfEmpty");
                }
                return trigger;
            }
            class_1273.method_3444("Error: no type field set for: ".concat(strTrim));
            return null;
        } catch (RuntimeException e2) {
            throw new class_305("Error while reading: " + ("(Map trigger: " + a2.field_859 + ", type:" + a2.field_861 + ")"), e2);
        }
    }
}