package cn.tesseract.union;

import cn.tesseract.union.asm.ClassMetadataReader;
import cn.tesseract.union.asm.HookClassTransformer;
import cn.tesseract.union.asm.NodeTransformer;
import cn.tesseract.union.asm.SafeClassWriter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class DragonflyTransformer extends HookClassTransformer {
    public final HashMap<String, List<NodeTransformer>> nodeTransformers = new HashMap<>();
    public static boolean dumpTransformedClass = true;

    @Override
    public byte[] transform(String className, byte[] classBytes) {
        className = className.replace('/', '.');
        classBytes = super.transform(className, classBytes);
        List<NodeTransformer> transformers = nodeTransformers.get(className);

        if (transformers != null) {
            ClassNode classNode = new ClassNode();
            ClassReader classReader = new ClassReader(classBytes);

            classReader.accept(classNode, ClassReader.EXPAND_FRAMES);

            Iterator<NodeTransformer> it = transformers.iterator();
            while (it.hasNext()) {
                it.next().transform(classNode);
                it.remove();
            }

            ClassWriter classWriter = new SafeClassWriter(new ClassMetadataReader(), ClassWriter.COMPUTE_MAXS);
            classNode.accept(classWriter);

            classBytes = classWriter.toByteArray();
        }
        return classBytes;
    }

    public void registerNodeTransformer(String className, NodeTransformer transformer) {
        List<NodeTransformer> list = nodeTransformers.computeIfAbsent(className.replace('/', '.'), k -> new ArrayList<>());
        list.add(transformer);
    }

    public static byte[] getClassData(String className) {
        String classResourceName = '/' + className.replace('.', '/') + ".class";
        try {
            return IOUtils.toByteArray(DragonflyTransformer.class.getResourceAsStream(classResourceName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void dumpClassFile(byte[] bytes) {
        final String[] className = new String[1];
        ClassReader cr = new ClassReader(bytes);
        ClassVisitor cw = new ClassVisitor(Opcodes.ASM9, new ClassWriter(cr, 0)) {
            @Override
            public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                className[0] = name;
                super.visit(version, access, name, signature, superName, interfaces);
            }
        };
        cr.accept(cw, 0);
        String name = className[0].substring(className[0].lastIndexOf('/') + 1);
        File file = new File(System.getProperty("user.dir") + File.separator + "classes" + File.separator + name + ".class");
        try {
            FileUtils.writeByteArrayToFile(file, bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
