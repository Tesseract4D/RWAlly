package cn.tesseract.rwally.hook;

import android.graphics.Color;
import cn.tesseract.dragonfly.asm.Hook;
import cn.tesseract.dragonfly.asm.ReturnCondition;
import cn.tesseract.rwally.accessor.InGameGuiAccessor;
import com.corrodinggames.rts.ally.game.units.class_415;
import com.corrodinggames.rts.ally.gameFramework.class_328;
import com.corrodinggames.rts.ally.gameFramework.class_998;
import com.corrodinggames.rts.ally.gameFramework.f.class_1001;
import com.corrodinggames.rts.ally.gameFramework.f.class_1002;
import com.corrodinggames.rts.ally.gameFramework.f.class_960;

import java.util.ArrayList;

public class UnitGroupsHook {
    public static int EXTRA_SLOTS = 7;

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void f(class_1002 c, float var1) {
        float var2 = c.b.cg;
        int var6 = (int) (c.b.cE - var2 * 30.0F);
        int var4 = (int) (c.b.ci - c.b.cn + 10.0F);
        int width = (int) (c.b.cn - 20.0F) / 3;
        int height = width - 5;
        var4 -= width * EXTRA_SLOTS;

        int var5;
        for (int var3 = 0; var3 < c.aA.size(); var4 = var5) {
            class_960 var13 = (class_960) c.aA.get(var3);
            if (var13.h) {
                if (!var13.a.isEmpty()) {
                    ArrayList<class_415> var14 = new ArrayList<>();

                    for (Object o : var13.a) {
                        class_415 var15 = (class_415) o;
                        var15 = class_328.a(var15.ej, true);
                        if (var15 != null && !var15.bX) {
                            var14.add(var15);
                        }
                    }

                    var13.a = var14;
                }

                var13.h = false;
            }

            var13.c();
            if (c.b.bN.keyboardSupport && var3 < c.b.bQ.ai.length) {
                if (c.b.bQ.ak[var3].a()) {
                    var13.a.clear();
                    var13.b();
                }

                if (c.b.bQ.aj[var3].a()) {
                    c.a.e();
                    var13.a();
                }

                if (c.b.bQ.ai[var3].a()) {
                    c.a.e();
                    c.a.h();
                    var13.a();
                }
            }

            var5 = var4;
            if (c.b.bN.showUnitGroups) {
                if (var3 < (EXTRA_SLOTS + 3)) {
                    String var17;
                    if (var13.a.isEmpty()) {
                        if (c.a.bN) {
                            var17 = "空";
                        } else {
                            var17 = "(" + (var3 + 1) + ")";
                        }
                    } else {
                        var17 = "" + var13.a.size();
                    }

                    var13.d = class_998.a(var13.d, 0.01F * var1);
                    var13.e = class_998.a(var13.e, 0.01F * var1);
                    var13.f = class_998.a(var13.f, 0.01F * var1);
                    var5 = Color.argb(50, (int) (100.0F + var13.f * 100.0F), (int) (100.0F + var13.e * 100.0F), (int) (100.0F + var13.d * 100.0F));
                    class_1001 var18 = c.a;
                    int var9 = (int) (31.0F * c.b.cg);
                    boolean var11 = var18.a(var4, var6, height, var9, var17, true, var5, var18.aC, false, null);
                    boolean var16;
                    if (var11 && c.a.ac == null && !c.a.T) {
                        var13.b += var1;
                        c.a.a();
                        c.i.reset();
                        c.i.setColor(Color.argb(120, 200, 0, 0));
                        InGameGuiAccessor accessor = (InGameGuiAccessor) c;
                        if (var13.b < 50.0F) {
                            var2 = var13.b / 50.0F;
                            c.i.setColor(Color.argb((int) (150.0F + 40.0F * var2), 0, 200, 0));
                            accessor.invoke_a(var4, var6, height, "选择", "(长按...)", c.i, var2);
                        } else if (var13.b < 100.0F) {
                            var2 = (var13.b - 50.0F) / 50.0F;
                            c.i.setColor(Color.argb((int) (150.0F + 40.0F * var2), 200, 0, 0));
                            accessor.invoke_a(var4, var6, height, "添加", "(长按...)", c.i, var2);
                        } else {
                            accessor.invoke_a(var4, var6, height, "替换", "", c.i, 0.0F);
                            var2 = 1.0F;
                        }

                        var5 = (int) (31.0F * c.b.cg);
                        c.s.set(var4, (int) ((float) (var6 + var5) - (float) var5 * var2), var4 + height, var5 + var6);
                        c.b.bL.b(c.s, c.i);
                        var16 = true;
                    } else {
                        var16 = false;
                    }

                    if (!var16) {
                        if (var13.b != 0.0F && !c.a.I) {
                            if (var13.b > 100.0F) {
                                var13.a.clear();
                                var13.b();
                                var13.f = 1.0F;
                            } else if (var13.b > 50.0F) {
                                var13.b();
                                c.a.e();
                                c.a.h();
                                var13.a();
                                var13.e = 1.0F;
                            } else if (!var13.a.isEmpty()) {
                                c.a.e();
                                c.a.h();
                                var13.a();
                                var13.d = 1.0F;
                            } else {
                                var13.a.clear();
                                var13.b();
                                var13.e = 1.0F;
                            }
                        }

                        if (!var16) {
                            var13.b = 0.0F;
                        }
                    }

                    var5 = var4 + width;
                }
            }

            ++var3;
        }
    }
}
