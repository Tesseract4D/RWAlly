package cn.tesseract.union;

import cn.tesseract.union.asm.Accessor;
import cn.tesseract.union.javassist.CtClassTransformer;
import javassist.*;
import org.apache.commons.io.FileUtils;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class BuildTransformer {
    public static File dir = new File(System.getProperty("user.dir"));
    public static File old = new File(dir, "libs/bak/classes_transformed.jar"), inject = new File(dir, "build/libs/rwally-1.0-SNAPSHOT-all.jar"), transformed = new File(dir, "libs/classes.jar"), apk = new File(dir, "libs/base.apk");

    public static final HashMap<String, List<CtClassTransformer>> ctcTransformers = new HashMap<>();

    public static void main(String[] args) throws IOException {
        apk.delete();
        transformed.delete();

        FileUtils.copyFile(new File(dir, "libs/bak/base.apk"), apk);

        DragonflyTransformer transformer = new DragonflyTransformer();
        HashSet<String> entries = new HashSet<>();

        /*transformer.registerNodeTransformer("com.corrodinggames.rts.union.gameFramework.class_775", node -> {
            for (MethodNode method : node.methods) {
                for (int i = 0; i < method.instructions.size(); i++) {
                    if (method.instructions.get(i) instanceof MethodInsnNode insn) {
                        if(insn.name.equals("method_946")){
                            method.instructions.remove(insn.getPrevious());
                            method.instructions.remove(insn);
                        }
                    }
                }
            }
        });*/

        transformer.registerNodeTransformer("com.corrodinggames.rts.union.gameFramework.class_898", node -> {
            for (MethodNode method : node.methods) {
                if (method.name.equals("method_2156")) for (int i = 0; i < method.instructions.size(); i++) {
                    if (method.instructions.get(i) instanceof MethodInsnNode insn) {
                        if (insn.name.equals("method_1018")) {
                            insn.name = "onCommandSpawn";
                        }
                    }
                }
            }
        });

        transformer.registerNodeTransformer("com.corrodinggames.rts.union.gameFramework.j.class_1001", node -> {
            for (MethodNode method : node.methods) {
                for (int i = 0; i < method.instructions.size(); i++) {
                    if (method.instructions.get(i) instanceof MethodInsnNode insn) {
                        if (insn.name.equals("method_2765")) {
                            insn.name = "onPlayerJoin";
                        }
                    }
                }
            }
        });

        registerCtClassTransformer("com.corrodinggames.rts.union.gameFramework.m.class_1192", (pool, ctc) -> {
            try {
                ctc.getDeclaredMethod("method_3199").setBody("""
                        {
                        try {
                            $1.restore();
                        } catch (IllegalStateException ignored) {
                        }
                        }
                        """);
            } catch (NotFoundException | CannotCompileException e) {
                throw new RuntimeException(e);
            }
        });

        registerCtClassTransformer("com.corrodinggames.rts.union.gameFramework.f.class_908", (pool, ctc) -> {
            try {
                CtMethod ctm = ctc.getDeclaredMethod("method_2365");
                ctm.addLocalVariable("index", pool.get(int.class.getName()));
                ctm.addLocalVariable("shift", pool.get("cn.tesseract.union.button.ShiftButton"));
                ctm.insertAt(1389, """
                        {
                            index=$0.field_5199.indexOf($0.field_5182.field_5605);
                            $0.field_5199.set(index,((cn.tesseract.union.accessor.SideBarAccessor)$0.field_5182).get_stopButton());
                            $0.field_5199.add(index,((cn.tesseract.union.accessor.SideBarAccessor)$0.field_5182).get_shiftButton());
                            $0.field_5199.add(index,$0.field_5182.field_5605);
                        }
                        """);
                ctm.insertAfter("""
                        {
                            shift=((cn.tesseract.union.accessor.SideBarAccessor)$0.field_5182).get_shiftButton();
                            if(!($0.field_5199.contains(shift)||shift.disabled))
                                shift.method_607(null,false);
                        }
                        """);
            } catch (NotFoundException | CannotCompileException e) {
                throw new RuntimeException(e);
            }
        });

        try (JarOutputStream newJar = new JarOutputStream(Files.newOutputStream(transformed.toPath()))) {
            ZipInputStream oldJar = new ZipInputStream(Files.newInputStream(old.toPath()));
            ZipInputStream injectJar = new ZipInputStream(Files.newInputStream(inject.toPath()));

            ClassPool pool = ClassPool.getDefault();
            pool.appendClassPath(old.getAbsolutePath());
            pool.appendClassPath(inject.getAbsolutePath());
            pool.appendClassPath(new File(dir, "libs/android.jar").getAbsolutePath());

            boolean transforming = false;
            ZipEntry entry;
            while ((entry = injectJar.getNextEntry()) != null || ((transforming = true) && (entry = oldJar.getNextEntry()) != null)) {
                String name = entry.getName();

                if (entries.contains(name)) {
                    System.out.println("Replace " + name);
                    continue;
                }
                newJar.putNextEntry(new JarEntry(name));
                entries.add(name);
                byte[] data = readEntryBytes(transforming ? oldJar : injectJar);
                if (name.endsWith(".class")) {
                    String className = name.substring(0, name.length() - 6).replace('/', '.');

                    List<CtClassTransformer> transformers = ctcTransformers.get(className);

                    if (transformers != null) {
                        CtClass ctClass = pool.get(className);
                        Iterator<CtClassTransformer> it = transformers.iterator();
                        while (it.hasNext()) {
                            it.next().transform(pool, ctClass);
                            it.remove();
                        }
                        System.out.println("Transforming CtClass: " + className);
                        data = ctClass.toBytecode();
                    }

                    if (className.endsWith("Hook")) {
                        transformer.logger.debug("Parsing hooks container " + className);
                        transformer.registerHookContainer(data);
                    } else if (className.endsWith("Accessor")) {
                        transformer.logger.debug("Parsing accessor " + className);
                        Accessor accessor = new Accessor(data);
                        transformer.registerNodeTransformer(accessor.target, accessor);
                    }

                    data = transformer.transform(className, data);
                }
                newJar.write(data);

                newJar.closeEntry();
            }
        } catch (IOException | NotFoundException | CannotCompileException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] readEntryBytes(ZipInputStream jar) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        while ((bytesRead = jar.read(buffer)) != -1) {
            bytes.write(buffer, 0, bytesRead);
        }

        return bytes.toByteArray();
    }


    public static void registerCtClassTransformer(String className, CtClassTransformer transformer) {
        List<CtClassTransformer> list = ctcTransformers.computeIfAbsent(className.replace('/', '.'), k -> new ArrayList<>());
        list.add(transformer);
    }
}
