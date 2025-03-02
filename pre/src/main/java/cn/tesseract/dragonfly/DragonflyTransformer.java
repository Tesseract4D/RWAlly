package cn.tesseract.dragonfly;

import cn.tesseract.dragonfly.asm.ClassMetadataReader;
import cn.tesseract.dragonfly.asm.HookClassTransformer;
import cn.tesseract.dragonfly.asm.NodeTransformer;
import cn.tesseract.dragonfly.asm.SafeClassWriter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class DragonflyTransformer extends HookClassTransformer {
    public static final HashMap<String, List<NodeTransformer>> transformerMap = new HashMap<>();
    public static boolean dumpTransformedClass = true;

    static {
        registerNodeTransformer("net/minecraft/DedicatedServer", node -> {
            for (MethodNode method : node.methods)
                if (method.name.equals("<clinit>")) for (int i = 0; i < method.instructions.size(); i++) {
                    AbstractInsnNode ainsn = method.instructions.get(i);
                    if (ainsn instanceof FieldInsnNode) {
                        FieldInsnNode insn = (FieldInsnNode) ainsn;
                        if (insn.name.equals("disconnection_penalty_enabled"))
                            method.instructions.set(method.instructions.get(i - 1), new InsnNode(Opcodes.ICONST_0));
                    }
                }
        });

        registerNodeTransformer("net/minecraft/Minecraft", node -> {
            for (MethodNode method : node.methods) {
                if (method.name.equals("inDevMode")) {
                    method.instructions.clear();

                    method.instructions.add(new InsnNode(Opcodes.ICONST_1));
                    method.instructions.add(new InsnNode(Opcodes.IRETURN));
                }
            }
        });

        registerNodeTransformer("net/minecraft/server/MinecraftServer", node -> {
            for (MethodNode method : node.methods) {
                if (method.name.equals("setTreacheryDetected")) {
                    method.instructions.clear();

                    method.instructions.add(new InsnNode(Opcodes.RETURN));
                }
            }
        });

        registerNodeTransformer("net/minecraft/BlockGrass", node -> {
            boolean flag = false;
            for (MethodNode method : node.methods)
                if (method.name.equals("updateTick")) for (int i = 0; i < method.instructions.size(); i++) {
                    AbstractInsnNode ainsn = method.instructions.get(i);
                    if (ainsn instanceof FieldInsnNode) {
                        FieldInsnNode insn = (FieldInsnNode) ainsn;
                        if (insn.name.equals("setBlock")) {
                            if (flag) insn.name = "growGrass";
                            flag = true;
                        }
                    }
                }
        });

        registerNodeTransformer("net/minecraft/ServerCommandManager", node -> {
            Map<String, String> commandReplace = new HashMap<>();
            commandReplace.put("net/minecraft/CommandServerTp", "cn/tesseract/dragonfly/command/CommandTp");
            commandReplace.put("net/minecraft/CommandTime", "cn/tesseract/dragonfly/command/CommandTime");
            commandReplace.put("net/minecraft/CommandGameRule", "cn/tesseract/dragonfly/command/CommandGameRule");
            commandReplace.put("net/minecraft/CommandServerOp", "cn/tesseract/dragonfly/command/CommandOp");
            commandReplace.put("net/minecraft/CommandServerDeop", "cn/tesseract/dragonfly/command/CommandDeOp");

            for (MethodNode method : node.methods)
                if (method.name.equals("<init>")) for (int i = 0; i < method.instructions.size(); i++) {
                    AbstractInsnNode ainsn = method.instructions.get(i);
                    if (ainsn instanceof TypeInsnNode) {
                        TypeInsnNode insn = (TypeInsnNode) ainsn;
                        if (commandReplace.containsKey(insn.desc))
                            ((MethodInsnNode) method.instructions.get(i + 2)).owner = insn.desc = commandReplace.get(insn.desc);
                    }
                }
        });

        registerNodeTransformer("net/minecraft/Block", node -> {
            Map<String, String> blockReplace = new HashMap<>();
            blockReplace.put("net/minecraft/BlockPressurePlate", "cn/tesseract/dragonfly/block/BlockPebble");
            blockReplace.put("net/minecraft/BlockCocoa", "cn/tesseract/dragonfly/block/BlockCocoa2");

            for (MethodNode method : node.methods) {
                if (method.name.equals("<clinit>")) {
                    for (int i = 0; i < method.instructions.size(); i++) {
                        AbstractInsnNode ainsn = method.instructions.get(i);
                        if (method.instructions.get(i) instanceof TypeInsnNode) {
                            TypeInsnNode insn = (TypeInsnNode) ainsn;
                            if (blockReplace.containsKey(insn.desc)) insn.desc = blockReplace.get(insn.desc);
                        } else if (method.instructions.get(i) instanceof MethodInsnNode) {
                            MethodInsnNode insn = (MethodInsnNode) ainsn;
                            if (blockReplace.containsKey(insn.owner)) insn.owner = blockReplace.get(insn.owner);
                        }
                    }
                }
            }
        });

        registerNodeTransformer("net/minecraft/BlockLeaves", node -> {
            int counter = 0;
            for (MethodNode method : node.methods)
                if (method.name.equals("updateTick")) for (int i = 0; i < method.instructions.size(); i++) {
                    AbstractInsnNode insn = method.instructions.get(i);
                    if (insn.getOpcode() == Opcodes.ICONST_4) {
                        if (counter == 1 || counter == 2) {
                            method.instructions.set(insn, new IntInsnNode(Opcodes.BIPUSH, 7));
                        }
                        counter++;
                    }
                }
        });

        registerNodeTransformer("net/minecraft/BlockDeadBush", node -> {
            for (MethodNode method : node.methods)
                if (method.name.equals("dropBlockAsEntityItem")) for (int i = 0; i < method.instructions.size(); i++) {
                    AbstractInsnNode ainsn = method.instructions.get(i);
                    if (ainsn instanceof LdcInsnNode) {
                        LdcInsnNode insn = (LdcInsnNode) ainsn;
                        if (insn.cst.equals(0.05F)) insn.cst = 0.1F;
                    }
                }
        });

        registerNodeTransformer("net/minecraft/BlockGravel", node -> {
            for (MethodNode method : node.methods)
                if (method.name.equals("dropBlockAsEntityItem")) for (int i = 0; i < method.instructions.size(); i++) {
                    AbstractInsnNode ainsn = method.instructions.get(i);
                    if (ainsn instanceof IntInsnNode) {
                        IntInsnNode insn = (IntInsnNode) ainsn;
                        if (insn.operand == 12) insn.operand = 10;
                    }
                }
        });

        registerNodeTransformer("net/minecraft/ServerPlayer", node -> {
            for (MethodNode method : node.methods)
                if (method.name.equals("onDeath")) for (int i = 0; i < method.instructions.size(); i++) {
                    AbstractInsnNode ainsn = method.instructions.get(i);
                    if (ainsn instanceof IntInsnNode) {
                        IntInsnNode insn = (IntInsnNode) ainsn;
                        if (insn.operand == -40) insn.operand = 0;
                    }
                }
        });

        registerNodeTransformer("net/minecraft/WorldGenPumpkin", node -> {
            for (MethodNode method : node.methods)
                if (method.name.equals("generate")) for (int i = 0; i < method.instructions.size(); i++) {
                    AbstractInsnNode ainsn = method.instructions.get(i);
                    if (ainsn instanceof IntInsnNode) {
                        IntInsnNode insn = (IntInsnNode) ainsn;
                        if (insn.operand == 64) insn.operand = 32;
                    }
                }
        });

        registerNodeTransformer("net/minecraft/PlayerManager", node -> {
            for (MethodNode method : node.methods)
                if (method.name.equals("updateMountedMovingPlayer"))
                    for (int i = 0; i < method.instructions.size(); i++) {
                        AbstractInsnNode ainsn = method.instructions.get(i);
                        if (ainsn instanceof LdcInsnNode) {
                            LdcInsnNode insn = (LdcInsnNode) ainsn;
                            if (insn.cst.equals(10L)) insn.cst = 200L;
                        }
                    }
        });

        registerNodeTransformer("net/minecraft/AnvilChunkLoader", node -> {
            for (MethodNode method : node.methods)
                if (method.name.equals("writeChunkToNBT"))
                    for (int i = 0; i < method.instructions.size(); i++) {
                        AbstractInsnNode ainsn = method.instructions.get(i);
                        if (ainsn instanceof MethodInsnNode) {
                            MethodInsnNode insn = (MethodInsnNode) ainsn;
                            if (insn.owner.equals("net/minecraft/Minecraft") && insn.name.equals("inDevMode"))
                                insn.owner = "cn/tesseract/dragonfly/hook/LogHook";
                        }
                    }
        });

        registerNodeTransformer("net/minecraft/WorldType", node -> {
            for (MethodNode method : node.methods) {
                if (method.name.equals("<init>")) {
                    method.access = Opcodes.ACC_PUBLIC;
                }
            }
        });

        registerNodeTransformer("net/minecraft/Block", node -> {
            for (MethodNode method : node.methods) {
                if (method.name.equals("setBlockBoundsForCurrentThread")) {
                    method.access = Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL;
                }
            }
        });

        registerNodeTransformer("net/minecraft/FoodStats", node -> {
            for (FieldNode field : node.fields) {
                if (field.name.equals("player")) {
                    field.access = Opcodes.ACC_PUBLIC;
                }
            }
        });

        registerNodeTransformer("net/minecraft/EntityPlayer", node -> {
            for (FieldNode field : node.fields) {
                if (field.name.equals("username")) {
                    field.access = Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL;
                }
            }
        });

        registerNodeTransformer("net/minecraft/ServerPlayer", node -> {
            node.fields.add(new FieldNode(Opcodes.ACC_PUBLIC, "latest_home_day", "I", null, null));
        });

        registerNodeTransformer("net/minecraft/EntityItem", node -> {
            node.fields.add(new FieldNode(Opcodes.ACC_PUBLIC, "exploded", "Z", null, null));
        });

        registerNodeTransformer("net/minecraft/StringTranslate", node -> {
            for (FieldNode field : node.fields) {
                if (field.name.equals("languageList")) {
                    field.access = Opcodes.ACC_PUBLIC;
                }
            }
        });

        registerNodeTransformer("net/minecraft/ChunkPosition", node -> {
            for (FieldNode field : node.fields) {
                if (field.name.equals("x") || field.name.equals("y") || field.name.equals("z")) {
                    field.access = Opcodes.ACC_PUBLIC;
                }
            }
        });

        registerNodeTransformer("net/minecraft/DedicatedPlayerList", node -> {
            for (FieldNode field : node.fields) {
                if (field.name.equals("opsList") || field.name.equals("whiteList")) {
                    field.access = Opcodes.ACC_PUBLIC;
                }
            }
        });

        registerNodeTransformer("net/minecraft/BlockTallGrass", node -> {
            node.access = node.access ^ Opcodes.ACC_FINAL;
        });

        registerNodeTransformer("net/minecraft/BlockCactus", node -> {
            node.access = node.access ^ Opcodes.ACC_FINAL;
        });

        registerNodeTransformer("net/minecraft/CaveNetworkGenerator", node -> {
            node.access = node.access ^ Opcodes.ACC_FINAL;
        });
    }

    @Override
    public byte[] transform(String className, byte[] classBytes) {
        classBytes = super.transform(className, classBytes);
        List<NodeTransformer> transformers = transformerMap.get(className.replace('/', '.'));

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

    public static void registerNodeTransformer(String className, NodeTransformer transformer) {
        List<NodeTransformer> list = transformerMap.computeIfAbsent(className.replace('/', '.'), k -> new ArrayList<>());
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
        ClassVisitor cw = new ClassVisitor(Opcodes.ASM5, new ClassWriter(cr, 0)) {
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
