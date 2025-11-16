package cn.tesseract.union.hook;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.tesseract.union.api.rusted.RustedGame;
import cn.tesseract.union.api.util.FileHelper;
import cn.tesseract.union.asm.Hook;
import cn.tesseract.union.asm.HookPriority;
import cn.tesseract.union.asm.ReturnCondition;
import com.corrodinggames.rts.union.R$drawable;
import com.corrodinggames.rts.union.R$id;
import com.corrodinggames.rts.union.appFramework.*;
import com.corrodinggames.rts.union.gameFramework.GameEngine;
import com.corrodinggames.rts.union.gameFramework.e.FileManager;

public class ScriptManageHook {
    @Hook(returnCondition = ReturnCondition.ON_TRUE)
    public static boolean setup(ReplaySelectActivity c, boolean b) {
        if (c.getIntent().getBooleanExtra("manage", false)) {
            GameEngine.method_3037(c);
            if (!class_84.method_137(c)) {
                c.finish();
            } else {
                View var3 = c.findViewById(R$id.levelButtonBack);
                var3.setOnClickListener((View.OnClickListener) new class_197(c));
                LinearLayout var6 = (LinearLayout) c.findViewById(R$id.replayHolder);
                var6.removeAllViews();
                if (!class_84.method_137(c)) {
                    c.finish();
                } else {
                    String[] list = FileHelper.listFiles("scripts");

                    for (int i = 0; i < list.length; ++i) {
                        String name = list[i];
                        if (!name.endsWith(".js") && !name.endsWith(".js.disabled") || name.equals(".disabled"))
                            continue;
                        Button btn = new Button(c.getBaseContext());
                        btn.setId(i);
                        btn.setTag(name);
                        name = FileManager.method_2194(name);
                        btn.setBackgroundResource(R$drawable.btn_dropdown);
                        btn.setText(name);
                        btn.setTextColor(-1);
                        c.registerForContextMenu(btn);
                        btn.setOnClickListener((View.OnClickListener) new class_199(c));
                        btn.setTypeface(Typeface.DEFAULT_BOLD);
                        btn.setPadding(0, 16, 0, 16);
                        var6.addView(btn);
                        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(btn.getLayoutParams());
                        ll.setMargins(0, 2, 0, 2);
                        btn.setLayoutParams(ll);
                    }

                    TextView var7 = (TextView) c.findViewById(R$id.LevelTextTop);
                    var7.setText("管理脚本");
                    if (c.progressDialog != null && c.progressDialog.isShowing()) c.dismissDialog(0);
                }
            }
            return true;
        }
        return false;
    }

    @Hook(returnCondition = ReturnCondition.ON_TRUE, priority = HookPriority.HIGH)
    public static boolean onCreateContextMenu(ReplaySelectActivity c, ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        return c.getIntent().getBooleanExtra("manage", false);
    }


    @Hook(returnCondition = ReturnCondition.ON_TRUE)
    public static boolean loadReplay(ReplaySelectActivity c, Context context, String string) {
        if (c.getIntent().getBooleanExtra("manage", false)) {
            boolean disabled = string.endsWith("disabled");

            new AlertDialog.Builder(c)
                    .setTitle(string)
                    .setMessage("")
                    .setPositiveButton(disabled ? "启用" : "禁用", (di, index) -> {
                        String name = string;
                        int i = name.indexOf('/');
                        if (i != -1) {
                            name = name.substring(i + 1);
                        }
                        FileHelper.renameFile(name, disabled ? name.substring(0, name.length() - 9) : name + ".disabled", "scripts/");
                        c.refresh();
                        RustedGame.get().toast("脚本设置将在重启后生效");
                    })
                    .setNegativeButton("确定", (DialogInterface.OnClickListener) new class_97())
                    .show();
            return true;
        }
        return false;
    }
}
