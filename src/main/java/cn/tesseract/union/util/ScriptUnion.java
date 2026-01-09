package cn.tesseract.union.util;

import org.mozilla.javascript.NativeJavaClass;
import org.mozilla.javascript.Scriptable;

import java.util.HashMap;

public class ScriptUnion {
    private static final HashMap<String, Object> shared = new HashMap<>();
    public final Scriptable scope;
    public final String side = "ANDROID";

    public ScriptUnion(Scriptable scope) {
        this.scope = scope;
    }

    public NativeJavaClass type(String s) throws ClassNotFoundException {
        return new NativeJavaClass(scope, Class.forName(s));
    }

    public void setShared(String s, Object o) {
        shared.put(s, o);
    }

    public Object getShared(String s) {
        return shared.get(s);
    }
}
