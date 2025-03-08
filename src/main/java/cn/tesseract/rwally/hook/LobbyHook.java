package cn.tesseract.rwally.hook;

import android.R;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import cn.tesseract.dragonfly.asm.Hook;
import cn.tesseract.dragonfly.asm.ReturnCondition;
import cn.tesseract.rwally.Reference;
import com.corrodinggames.rts.ally.appFramework.*;
import com.corrodinggames.rts.ally.game.units.custom.logicBooleans.VariableScope;
import com.corrodinggames.rts.ally.gameFramework.class_340;
import com.corrodinggames.rts.ally.gameFramework.class_998;
import com.corrodinggames.rts.ally.gameFramework.h.class_993;
import com.corrodinggames.rts.ally.gameFramework.j.class_1041;
import com.corrodinggames.rts.ally.gameFramework.utility.class_1331;

import java.util.Iterator;

public class LobbyHook {
    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void run(class_190 c) {
        String str;
        String str2;
        class_340 t = class_340.t();
        class_340.a("lobby", "refreshServerListRunnable");
        String a2 = class_993.a("menus.lobby.gameState.battleroom", new Object[0]);
        String a3 = class_993.a("menus.lobby.gameState.ingame", new Object[0]);
        String a4 = class_993.a("menus.lobby.gameState.chat", new Object[0]);
        class_1331 class_1331Var = c.a.activityRecycledTextViews;
        for (int childCount = c.a.gameListTable.getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = c.a.gameListTable.getChildAt(childCount);
            if (childAt.getId() == -1) {
                if (childAt instanceof TableRow tableRow) {
                    for (int childCount2 = tableRow.getChildCount() - 1; childCount2 >= 0; childCount2--) {
                        View childAt2 = tableRow.getChildAt(childCount2);
                        if (MultiplayerLobbyActivity.normalServerCell.equals(childAt2.getTag()) && (childAt2 instanceof TextView textView)) {
                            tableRow.removeView(textView);
                            class_1331Var.add(textView);
                        }
                    }
                }
                c.a.gameListTable.removeView(childAt);
            }
        }
        class_340.d("recycledTextViews: " + class_1331Var.size());
        Iterator<class_1041> it = MultiplayerLobbyActivity.getSortedDiscoveredServers().iterator();
        int i = 0;
        while (it.hasNext()) {
            class_1041 room = it.next();
            if (Reference.roomBlacklist.isBlacklisted(room.b))
                continue;
            i++;
            if (c.a.showLimitedRows && i > 35) {
                TableRow tableRow2 = new TableRow(c.a);
                MultiplayerLobbyActivity.addCell(room, tableRow2, "...", class_1331Var);
                MultiplayerLobbyActivity.addCell(room, tableRow2, "...", class_1331Var);
                MultiplayerLobbyActivity.addCell(room, tableRow2, "...", class_1331Var);
                MultiplayerLobbyActivity.addCell(room, tableRow2, "...", class_1331Var);
                MultiplayerLobbyActivity.addCell(room, tableRow2, "...", class_1331Var);
                MultiplayerLobbyActivity.addCell(room, tableRow2, "...", class_1331Var);
                c.a.gameListTable.addView(tableRow2);
                ViewGroup.LayoutParams layoutParams = tableRow2.getLayoutParams();
                layoutParams.width = -1;
                tableRow2.setLayoutParams(layoutParams);
                break;
            }
            TableRow tableRow3 = new TableRow(c.a);
            tableRow3.setClickable(true);
            tableRow3.setBackgroundResource(R.drawable.list_selector_background);
            tableRow3.setOnClickListener((View.OnClickListener) new class_185(c, room));
            MultiplayerLobbyActivity.addCell(room, tableRow3, room.s.replace("battleroom", a2).replace("ingame", a3).replace("chat", a4), class_1331Var);
            MultiplayerLobbyActivity.addCell(room, tableRow3, class_998.a(room.n, 15), class_1331Var);
            if (room.t.equals("?")) {
                str = "?";
            } else {
                str = room.t + "\\" + room.u;
            }
            MultiplayerLobbyActivity.addCell(room, tableRow3, class_998.a(str, 15), class_1331Var);
            String a5 = class_998.a(LevelSelectActivity.convertLevelFileNameForDisplay(room.q), 40);
            String str3 = a5;
            if (a5 == null) {
                str3 = VariableScope.nullOrMissingString;
            }
            MultiplayerLobbyActivity.addCell(room, tableRow3, str3, class_1331Var);
            if ("ANY".equalsIgnoreCase(room.k)) {
                str2 = room.k;
            } else {
                str2 = "v" + class_998.a(room.k, 8);
            }
            MultiplayerLobbyActivity.addCell(room, tableRow3, str2, class_1331Var);
            String str4 = "N";
            if (room.h) {
                if (room.m) {
                    str4 = "P";
                } else {
                    str4 = "Y";
                }
            }
            if (room.a) {
                str4 = "L";
            }
            MultiplayerLobbyActivity.addCell(room, tableRow3, str4, class_1331Var);
            c.a.gameListTable.addView(tableRow3);
            ViewGroup.LayoutParams layoutParams2 = tableRow3.getLayoutParams();
            layoutParams2.width = -1;
            tableRow3.setLayoutParams(layoutParams2);
        }
        if (t.bU.bk.isEmpty()) {
            TableRow tableRow4 = new TableRow(c.a);
            tableRow4.setBackgroundResource(R.drawable.list_selector_background);
            String str5 = "没有房间 :(";
            if (t.bU.bj != null) {
                str5 = "出错: " + t.bU.bj;
            }
            TextView addCell = MultiplayerLobbyActivity.addCell(null, tableRow4, str5, class_1331Var);
            TableRow.LayoutParams layoutParams3 = (TableRow.LayoutParams) addCell.getLayoutParams();
            layoutParams3.span = 6;
            addCell.setLayoutParams(layoutParams3);
            addCell.setGravity(Gravity.CENTER);
            c.a.gameListTable.addView(tableRow4);
            ViewGroup.LayoutParams layoutParams4 = tableRow4.getLayoutParams();
            layoutParams4.width = -1;
            tableRow4.setLayoutParams(layoutParams4);
        }
        class_340.a("大厅", "刷新完毕");
        c.a.refreshButton.setText(c.a.textRefreshButton);
        c.a.refreshButton.setEnabled(true);
    }


    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void onClick(class_185 c, View view) {
        class_1041 room = c.a;
        String link = room.e;
        StringBuilder message = new StringBuilder();

        if (link != null) {
            String var2 = room.f;
            message.append(var2 != null ? var2.replace("\\n", "\n") : "");
            message.append("\n").append(message).append("地址: ").append(link).append("\n");
        } else {
            if (room.a) {
                message.append("局域网: ").append(room.d).append(":").append(room.g).append("\n");
            }

            message.append("用户: ").append(room.n).append("\n")
                    .append("地图: ").append(LevelSelectActivity.convertLevelFileNameForDisplay(room.q)).append("\n")
                    .append("房间ID: ").append(room.b).append("\n");

            if (room.m) {
                message.append("需要密码\n");
            }

            if (!room.h && !room.a) {
                message.append("端口: 不开放(可能无法加入)\n");
            }

            String versionInfo = "ANY".equalsIgnoreCase(room.k) ? "版本: " + room.k
                    : "版本: v" + room.k + (room.b() ? "" : " (游戏版本不同!)");

            message.append(versionInfo).append("\n");

            if (room.z != null && !room.z.isEmpty()) {
                message.append("需要模组: ").append(room.z).append("\n");
            }
        }

        AlertDialog.Builder var17 = new AlertDialog.Builder(c.b.a)
                .setIcon(R.drawable.ic_dialog_info)
                .setTitle(c.a.a() ? "打开链接?" : "加入服务器?")
                .setMessage(message.toString())
                .setNegativeButton("取消", (DialogInterface.OnClickListener) new class_181(c))
                .setNeutralButton("加入黑名单", (d, i) -> {
                    Reference.roomBlacklist.blacklist(c.a.n, c.a.q, c.a.b);
                    MultiplayerLobbyActivity.refreshServerList();
                    Toast.makeText(c.b.a, "已加入黑名单", Toast.LENGTH_SHORT).show();
                });

        if (c.a.a()) {
            var17.setPositiveButton("打开", (DialogInterface.OnClickListener) new class_179(c));
        } else if (!c.a.a) {
            var17.setPositiveButton("加入", (DialogInterface.OnClickListener) new class_180(c));
        } else {
            var17.setPositiveButton("通过局域网加入", (DialogInterface.OnClickListener) new class_186(c));
        }

        var17.show();
    }
}
