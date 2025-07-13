package cn.tesseract.union.javassist;

import javassist.CtClass;

public interface CtClassTransformer {
    void transform(CtClass ctc);
}
