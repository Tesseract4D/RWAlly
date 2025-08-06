package cn.tesseract.union.hook;

import cn.tesseract.union.asm.Hook;
import cn.tesseract.union.asm.ReturnCondition;
import com.corrodinggames.rts.union.appFramework.MultiplayerLobbyActivity;
import com.corrodinggames.rts.union.appFramework.class_173;
import com.corrodinggames.rts.union.gameFramework.class_1061;
import com.corrodinggames.rts.union.gameFramework.j.class_1040;
import com.corrodinggames.rts.union.gameFramework.j.class_1047;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LobbyHook {
    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static ArrayList getSortedDiscoveredServers(MultiplayerLobbyActivity c) {
        ArrayList list = new ArrayList();
        synchronized (class_1047.field_6255) {
            for (class_1040 o : (ConcurrentLinkedQueue<class_1040>) class_1061.method_3076().field_6352.field_5947) {
                String version = o.field_6210;
                if (version.equals("1.15-RN") || version.equals("1.15-RN-MOD")
                        || (version.contains("1.15") && !o.field_6203.equals("127.0.0.1") && !o.field_6216.isEmpty()))
                    list.add(o);
            }
            Collections.sort(list, new class_173());
        }
        return list;
    }
}
