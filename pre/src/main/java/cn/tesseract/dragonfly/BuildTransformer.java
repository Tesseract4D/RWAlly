package cn.tesseract.dragonfly;

import cn.tesseract.dragonfly.asm.Accessor;
import org.apache.commons.io.FileUtils;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class BuildTransformer {
    public static File dir = new File(System.getProperty("user.dir"));

    public static void main(String[] args) throws IOException {
        File old = new File(dir, "libs/bak/classes_transformed.jar"),
                inject = new File(dir, "build/libs/rwally-1.0-SNAPSHOT-all.jar"),
                transformed = new File(dir, "libs/classes.jar"),
                apk = new File(dir, "libs/base.apk");

        apk.delete();
        transformed.delete();
        FileUtils.copyFile(new File(dir, "libs/bak/base.apk"), apk);

        DragonflyTransformer transformer = new DragonflyTransformer();
        transformer.registerNodeTransformer("com.corrodinggames.rts.ally.gameFramework.j.class_1101", node -> {
            for (MethodNode method : node.methods) {
                if (method.name.equals("a") && method.desc.equals("(Lcom/corrodinggames/rts/ally/gameFramework/j/class_1033;)V")) {
                    for (int i = 0; i < method.instructions.size(); i++) {
                        if (method.instructions.get(i) instanceof MethodInsnNode insn) {
                            if (insn.name.equals("d") && insn.desc.equals("(Lcom/corrodinggames/rts/ally/gameFramework/j/class_1054;)V")) {
                                insn.name = "onJoin";
                            }
                        }
                    }
                }
            }
        });

        try (JarOutputStream newJar = new JarOutputStream(Files.newOutputStream(transformed.toPath()))) {
            ZipInputStream oldJar = new ZipInputStream(Files.newInputStream(old.toPath()));
            ZipInputStream injectJar = new ZipInputStream(Files.newInputStream(inject.toPath()));

            boolean transforming = false;
            ZipEntry entry;
            while ((entry = injectJar.getNextEntry()) != null || ((transforming = true) && (entry = oldJar.getNextEntry()) != null)) {
                String name = entry.getName();
                newJar.putNextEntry(new JarEntry(name));
                byte[] data = readEntryBytes(transforming ? oldJar : injectJar);
                if (name.endsWith(".class")) {
                    String className = name.substring(0, name.length() - 6);
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
        } catch (IOException e) {
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
}
