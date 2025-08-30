package cn.tesseract.union.hook;

import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import cn.tesseract.union.api.util.FileHelper;
import cn.tesseract.union.asm.Hook;
import cn.tesseract.union.api.util.GameHelper;
import com.corrodinggames.rts.union.appFramework.LoadLevelActivity;
import com.corrodinggames.rts.union.appFramework.ReplaySelectActivity;
import com.corrodinggames.rts.union.gameFramework.GameEngine;
import com.corrodinggames.rts.union.gameFramework.class_775;
import com.corrodinggames.rts.union.gameFramework.j.class_1044;

import java.io.IOException;

public class ExtractMapHook {
    private static String map = "";
    private static boolean extracting = false;
    private static boolean extracted = false;

    @Hook(injector = "line:1347")
    public static void method_1780(class_775 c, class_1044 j, boolean boolean2, boolean boolean3) throws IOException {
        if (extracting) {
            FileHelper.write(FileHelper.getOutputStream("maps/" + map), j.method_2929());
            extracted = true;
        }
    }

    @Hook(targetMethod = "method_1780", injector = "line:1358")
    public static void method_1780$2(class_775 c, class_1044 j, boolean boolean2, boolean boolean3) {
        if (extracting)
            GameEngine.get().method_3056(extracted ? "成功提取地图文件" : "未提取到地图，该保存/回放可能使用的是本地地图");
        extracting = false;
        extracted = false;
    }

    @Hook
    public static void onCreateContextMenu(LoadLevelActivity c, ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.add(4, view.getId(), 0, "提取地图");
    }

    @Hook
    public static void onContextItemSelected(LoadLevelActivity c, MenuItem menuItem) {
        String str = c.levels[menuItem.getItemId()];
        if (menuItem.getGroupId() == 4) {
            map = str.replaceAll(" \\[v1.15] \\(.*\\)\\.rwsave$", ".tmx");
            extracting = true;
            c.loadLevel(GameHelper.getActivity(), str);
        }
    }

    @Hook
    public static void onCreateContextMenu(ReplaySelectActivity c, ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.add(4, view.getId(), 0, "提取地图");
    }

    @Hook
    public static void onContextItemSelected(ReplaySelectActivity c, MenuItem menuItem) {
        String str = c.replays[menuItem.getItemId()];
        if (menuItem.getGroupId() == 4) {
            map = str.replaceAll(" \\[v1.15] \\(.*\\)\\.replay$", ".tmx");
            extracting = true;
            c.loadReplay(GameHelper.getActivity(), str);
        }
    }
}
