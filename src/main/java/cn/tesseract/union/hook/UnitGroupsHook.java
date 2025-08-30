package cn.tesseract.union.hook;

import android.graphics.Color;
import cn.tesseract.union.accessor.InGameGuiAccessor;
import cn.tesseract.union.asm.Hook;
import cn.tesseract.union.asm.ReturnCondition;
import com.corrodinggames.rts.union.game.units.Unit;
import com.corrodinggames.rts.union.gameFramework.class_773;
import com.corrodinggames.rts.union.gameFramework.class_907;
import com.corrodinggames.rts.union.gameFramework.f.class_908;
import com.corrodinggames.rts.union.gameFramework.f.class_945;
import com.corrodinggames.rts.union.gameFramework.f.class_961;

import java.util.ArrayList;

public class UnitGroupsHook {
    public static final int EXTRA_SLOTS = 4;
    public static final float DELAY = 25;

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void method_2383(class_908 c, float var1) {
        float var2 = c.field_5209.field_6416;
        int var6 = (int) (c.field_5209.field_6388 - var2 * 30.0F);
        int var4 = (int) (c.field_5209.field_6418 - c.field_5209.field_6423 + 10.0F);
        int width = (int) (c.field_5209.field_6423 - 20.0F) / 3;
        int height = width - 5;
        var4 -= width * EXTRA_SLOTS;

        int var5;
        for (int var3 = 0; var3 < c.field_5178.size(); var4 = var5) {
            class_945 var13 = (class_945) c.field_5178.get(var3);
            if (var13.field_5392) {
                if (!var13.field_5385.isEmpty()) {
                    ArrayList<Unit> var14 = new ArrayList<>();

                    for (Unit var15 : (ArrayList<Unit>) var13.field_5385) {
                        var15 = class_773.method_1767(var15.id, true);
                        if (var15 != null && !var15.field_1925) {
                            var14.add(var15);
                        }
                    }

                    var13.field_5385 = var14;
                }

                var13.field_5392 = false;
            }

            var13.method_2450();
            if (c.field_5209.field_6345.keyboardSupport && var3 < c.field_5209.field_6348.field_4283.length) {
                if (c.field_5209.field_6348.field_4285[var3].method_1807()) {
                    var13.field_5385.clear();
                    var13.method_2449();
                }

                if (c.field_5209.field_6348.field_4284[var3].method_1807()) {
                    c.field_5182.method_2554();
                    var13.method_2448();
                }

                if (c.field_5209.field_6348.field_4283[var3].method_1807()) {
                    c.field_5182.method_2554();
                    c.field_5182.method_2562();
                    var13.method_2448();
                }
            }

            var5 = var4;
            if (c.field_5209.field_6345.showUnitGroups) {
                if (var3 < (EXTRA_SLOTS + 3)) {
                    String var17;
                    if (var13.field_5385.isEmpty()) {
                        if (c.field_5182.field_5543) {
                            var17 = "空";
                        } else {
                            var17 = "(" + (var3 + 1) + ")";
                        }
                    } else {
                        var17 = "" + var13.field_5385.size();
                    }

                    var13.field_5388 = class_907.method_2242(var13.field_5388, 0.01F * var1);
                    var13.field_5389 = class_907.method_2242(var13.field_5389, 0.01F * var1);
                    var13.field_5390 = class_907.method_2242(var13.field_5390, 0.01F * var1);
                    var5 = Color.argb(50, (int) (100.0F + var13.field_5390 * 100.0F), (int) (100.0F + var13.field_5389 * 100.0F), (int) (100.0F + var13.field_5388 * 100.0F));
                    class_961 var18 = c.field_5182;
                    int var9 = (int) (31.0F * c.field_5209.field_6416);
                    boolean var11 = var18.method_2507(var4, var6, height, var9, var17, true, var5, var18.field_5479, false, null);
                    boolean var16;
                    if (var11 && c.field_5182.field_5506 == null && !c.field_5182.field_5470) {
                        var13.field_5386 += var1;
                        c.field_5182.method_2491();
                        c.field_5216.reset();
                        c.field_5216.setColor(Color.argb(120, 200, 0, 0));
                        InGameGuiAccessor accessor = (InGameGuiAccessor) c;
                        if (var13.field_5386 < DELAY) {
                            var2 = var13.field_5386 / DELAY;
                            c.field_5216.setColor(Color.argb((int) (150.0F + 40.0F * var2), 0, 200, 0));
                            accessor.invoke_method_2358(var4, var6, height, "选择", "(长按...)", c.field_5216, var2);
                        } else if (var13.field_5386 < DELAY * 2) {
                            var2 = (var13.field_5386 - DELAY) / DELAY;
                            c.field_5216.setColor(Color.argb((int) (150.0F + 40.0F * var2), 200, 0, 0));
                            accessor.invoke_method_2358(var4, var6, height, "添加", "(长按...)", c.field_5216, var2);
                        } else {
                            accessor.invoke_method_2358(var4, var6, height, "替换", "", c.field_5216, 0.0F);
                            var2 = 1.0F;
                        }

                        var5 = (int) (31.0F * c.field_5209.field_6416);
                        c.field_5226.set(var4, (int) ((float) (var6 + var5) - (float) var5 * var2), var4 + height, var5 + var6);
                        c.field_5209.field_6342.method_3278(c.field_5226, c.field_5216);
                        var16 = true;
                    } else {
                        var16 = false;
                    }

                    if (!var16) {
                        if (var13.field_5386 != 0.0f && !c.field_5182.field_5459) {
                            if (var13.field_5386 > DELAY * 2) {
                                var13.field_5385.clear();
                                var13.method_2449();
                                var13.field_5390 = 1.0f;
                            } else if (var13.field_5386 > DELAY) {
                                var13.method_2449();
                                c.field_5182.method_2554();
                                c.field_5182.method_2562();
                                var13.method_2448();
                                var13.field_5389 = 1.0f;
                            } else if (!var13.field_5385.isEmpty()) {
                                c.field_5182.method_2554();
                                c.field_5182.method_2562();
                                var13.method_2448();
                                var13.field_5388 = 1.0f;
                            } else {
                                var13.field_5385.clear();
                                var13.method_2449();
                                var13.field_5389 = 1.0f;
                            }
                        }


                        if (!var16) {
                            var13.field_5386 = 0.0F;
                        }
                    }

                    var5 = var4 + width;
                }
            }

            ++var3;
        }
    }
}