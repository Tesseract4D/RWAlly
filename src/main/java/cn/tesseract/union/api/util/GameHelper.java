package cn.tesseract.union.api.util;

import android.app.Activity;

import java.lang.reflect.Field;
import java.util.Map;

public class GameHelper {
    public static Activity getActivity() {
        try {
            Class ac = Class.forName("android.app.ActivityThread");
            Class acr = Class.forName("android.app.ActivityThread$ActivityClientRecord");

            Object t = ac.getDeclaredMethod("currentActivityThread").invoke(null);
            Field m = t.getClass().getDeclaredField("mActivities");
            m.setAccessible(true);
            Field n = acr.getDeclaredField("paused");
            n.setAccessible(true);
            Field p = acr.getDeclaredField("activity");
            p.setAccessible(true);

            for (Object o : ((Map) m.get(t)).values()) {
                if (!(boolean) n.get(o)) {
                    return (Activity) p.get(o);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
