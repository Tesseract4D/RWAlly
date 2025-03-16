package cn.tesseract.rwally.hook;

import cn.tesseract.dragonfly.asm.Hook;
import cn.tesseract.dragonfly.asm.ReturnCondition;
import com.corrodinggames.rts.ally.gameFramework.j.class_1041;
import com.corrodinggames.rts.ally.gameFramework.j.class_1072;
import com.corrodinggames.rts.ally.gameFramework.j.class_1084;

import java.io.BufferedReader;

public class FakeRoomHook {
    private static final class_1041 PLACEHOLDER = new class_1041();
    private static final int[] BLACKLISTED_IP_HASHES = new int[]{
            1333734922, 1901825672
    };
    private static final int[] WHITELISTED_IP_HASHES = new int[]{
            1558803033, 51307167, 1719447496, 1004215222, 1110668765, -1397351055, 1710786090, 647074725, -932325753, -1156334048, -1336393795, -326242510, -1478815263, 2109978129, -608941469, 2064176083, -633785615, 1583998294, -2035919147, 1355783838, 1455771462, 1003469514, 1087551817, 2053215667, -1202636475, -694548742, 239883009, 618977602, 1921409598, -362576151, 1131160678, -1822537988, 1571125486, 1513338195, -1028366841, -1692623504, 654980747, 2017984921, -392483869, -514209665, 1034345357, 1645612428, 1808105386, -904997790, 1407428753, 194272152, 325303431, -1183171865, 1049904379, 697982227, 2083720148, -1881870705, -2063452009, -1884134403
    };

    public static boolean isBlacklisted(String[] data) {
        int match = 0;
        for (String d : data) {
            for (int h : BLACKLISTED_IP_HASHES)
                if (d.hashCode() == h)
                    match++;
        }
        return match >= 2;
    }

    public static boolean isWhitelisted(String[] data) {
        for (String d : data)
            for (int h : WHITELISTED_IP_HASHES)
                if (d.hashCode() == h) return true;
        return false;
    }

    @Hook(injector = "line:1065")
    public static void a(class_1072 c, BufferedReader reader, int i, @Hook.LocalVariable(6) String[] data) {
        if (isBlacklisted(data) && !isWhitelisted(data)) {
            data[0] = null;
            data[18] = null;
        }
    }

    @Hook(injector = "line:850", returnCondition = ReturnCondition.ALWAYS)
    public static class_1041 b(class_1084 c, String str) {
        return PLACEHOLDER;
    }
}
