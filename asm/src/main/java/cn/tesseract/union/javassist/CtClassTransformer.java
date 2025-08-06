package cn.tesseract.union.javassist;

import javassist.ClassPool;
import javassist.CtClass;

public interface CtClassTransformer {
    void transform(ClassPool pool, CtClass ctc);
}
