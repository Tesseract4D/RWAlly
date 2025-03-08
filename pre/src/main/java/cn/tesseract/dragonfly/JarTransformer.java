package cn.tesseract.dragonfly;

import org.objectweb.asm.tree.MethodNode;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class JarTransformer {
    public static File dir = new File(System.getProperty("user.dir"));

    public static void main(String[] args) {
        File jar = new File(dir, "libs/bak/classes.jar"),
                transformed = new File(dir, "libs/bak/classes_transformed.jar");

        transformed.delete();

        DragonflyTransformer transformer = new DragonflyTransformer();


        try (JarOutputStream newJar = new JarOutputStream(Files.newOutputStream(transformed.toPath()))) {
            ZipInputStream oldJar = new ZipInputStream(Files.newInputStream(jar.toPath()));

            ZipEntry entry;
            while ((entry = oldJar.getNextEntry()) != null) {
                String name = entry.getName();
                newJar.putNextEntry(new JarEntry(name));
                byte[] data = readEntryBytes(oldJar);
                if (name.endsWith(".class")) {
                    String className = name.substring(0, name.length() - 6);

                    transformer.registerNodeTransformer(className, node -> {
                        node.access = ~(~node.access | Modifier.FINAL | Modifier.PRIVATE | Modifier.PROTECTED) | Modifier.PUBLIC;
                        for (MethodNode method : node.methods)
                            if (method.name.equals("<init>"))
                                method.access = ~(~method.access | Modifier.PRIVATE | Modifier.PROTECTED) | Modifier.PUBLIC;
                    });

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
