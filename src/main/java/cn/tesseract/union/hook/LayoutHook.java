package cn.tesseract.union.hook;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import cn.tesseract.union.asm.Hook;
import com.corrodinggames.rts.union.R$id;
import com.corrodinggames.rts.union.appFramework.MainMenuActivity;
import com.corrodinggames.rts.union.appFramework.class_97;

public class LayoutHook {
    @Hook
    public static void warnAboutBugs(MainMenuActivity c) {
        Toast.makeText(c, "联盟版 v1.3 洗玻璃呀制作", Toast.LENGTH_SHORT).show();
        Toast.makeText(c, "群号：927263395", Toast.LENGTH_LONG).show();
        ((Button) c.findViewById(R$id.startgameButton)).setWidth(900);
        ((Button) c.findViewById(R$id.menuCustomButton)).setWidth(900);
        ((Button) c.findViewById(R$id.multiplayerButton)).setWidth(900);
        LinearLayout l1 = (LinearLayout) c.findViewById(R$id.settingsButton).getParent();
        l1.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        lp.topMargin = 0;

        ((Button) c.findViewById(R$id.settingsButton)).setWidth(450);
        ((Button) c.findViewById(R$id.modsButton)).setWidth(450);
        ((Button) c.findViewById(R$id.settingsButton)).setLayoutParams(lp);
        ((Button) c.findViewById(R$id.modsButton)).setLayoutParams(lp);

        LinearLayout l2 = new LinearLayout(c);
        ((LinearLayout) l1.getParent()).addView(l2);

        Button b;
        l1.removeView(b = (Button) c.findViewById(R$id.helpButton));
        l2.addView(b, lp);
        b.setWidth(450);

        l1.removeView(b = (Button) c.findViewById(R$id.exitgameButton));
        l2.addView(b, lp);
        b.setOnClickListener(view -> {
            new AlertDialog.Builder(view.getContext())
                    .setTitle("更新日志")
                    .setMessage("""
                            群号：927263395
                            
                            联盟版 v1.3 更新日志:
                            主界面排版调整
                            现在可以在局内调整地图文字的大小以及是否跟随缩放
                            调整巡逻，保护等按钮的大小和顺序
                            
                            联盟版 v1.2 更新日志:
                            更好看的主界面排版
                            游戏内长按保存或回放文件新增提取地图选项，可以从直接从保存文件或回放文件中提取地图文件
                            
                            结盟版 v1.1 更新日志:
                            修复单位残骸也会显示范围的bug
                            减少编队槽到7个
                            修复连续和停止单位的按钮在选中不同单位时位置不同的bug
                            
                            铁锈战争联盟版 v1.0 功能：
                            1. 过滤所有广告房/假房
                            2. 显示单位的攻击范围，并支持游戏内调整开启/关闭/仅友方/仅敌方/仅单位/仅建筑
                            3. 添加连续命令按钮，相当于电脑的Shift键
                            4. 添加停止单位按钮，运输舰不再乱跑
                            5. 允许房主调整0.5-50的经济倍率
                            6. 允许玩家调整1-100的所有位置
                            7. 将颜色增加到100种，每种颜色都不同，玩家位置超过10时自动使用这些新颜色
                            8. 地图上的文字大小随缩放比例变化，即使缩到最小也不会影响视野
                            9. 增加七个编队槽
                            10. 增加全局聊天按钮
                            11. 支持js引擎，适配联盟版的结盟脚本正在开发中
                            """)
                    .setPositiveButton("确定", (DialogInterface.OnClickListener) new class_97())
                    .show();
        });
        b.setVisibility(View.VISIBLE);
        b.setWidth(450);
    }

    @Hook(injector = "exit")
    public static void setButtonText(MainMenuActivity c) {
        ((Button) c.findViewById(R$id.exitgameButton)).setText("更新日志");
    }
}
