package com.corrodinggames.rts.union.gameFramework.j;

import com.corrodinggames.rts.union.gameFramework.class_1061;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.SocketException;

public class class_1038 implements Runnable {
    Boolean field_6191;
    final class_1037 field_6192;

    public class_1038(class_1037 c) {
        this.field_6192 = c;
        this.field_6191 = Boolean.TRUE;
    }

    public class_1038(class_1037 c, byte byte2) {
        this(c);
    }

    @Override
    public final void run() {
        String message;
        class_1061.method_2981();
        Thread.currentThread().setName("ReceiveWorker-" + this.field_6192.method_2905());
        try {
            method_2909();
        } catch (EOFException e) {
            class_1061.method_3010(this.field_6192.method_2903("network:ReceiveWorker: EOF reading packet"), e);
        } catch (IOException e2) {
            if (!this.field_6192.field_6166) {
                e2.printStackTrace();
            }
            if (class_1061.field_6309 && (e2 instanceof SocketException) && !this.field_6192.field_6166) {
                class_1061 class_1061VarMethod_3076 = class_1061.method_3076();
                if (!class_1061VarMethod_3076.field_6352.field_5851 && class_1061VarMethod_3076.field_6352.field_5898 && (message = e2.getMessage()) != null && message.contains("EBADF")) {
                    class_1061VarMethod_3076.method_3056("Warning: This disconnect likely due to iOS removing sockets of background apps. Avoid minimising the game in multiplayer. Note: Games can be rejoined.");
                }
            }
            this.field_6192.method_2901("network:ReceiveWorker: " + e2.getMessage());
        } catch (OutOfMemoryError e3) {
            class_1061.method_3034(e3);
            this.field_6192.method_2901("network:ReceiveWorker OutOfMemoryError: " + e3.getMessage());
        }
        this.field_6192.method_2896(true, false);
    }

    private void method_2909() throws IOException {
        int i;
        DataInputStream dataInputStream = new DataInputStream(this.field_6192.field_6169.getInputStream());
        while (this.field_6191 && !this.field_6192.field_6166 && !this.field_6192.field_6169.isClosed()) {
            int i2 = dataInputStream.readInt();
            int i3 = dataInputStream.readInt();
            if (i2 > 20000000) {
                this.field_6192.method_2899("readData(): new packet of type:" + i3 + " has size of:" + i2);
            }
            if (i2 > 10000) {
                if (!this.field_6192.field_6165.field_5851) {
                    i = 50000000;
                } else {
                    i = 1000000;
                }
                int i4 = this.field_6192.field_6181 ? i : 10000;
                if (i2 > i4) {
                    this.field_6192.method_2899("Requested packet too large rejecting (max:" + i4 + ")");
                    return;
                }
            }
            if (i2 < 0) {
                this.field_6192.method_2899("Requested packet negative size:" + i2 + " rejecting");
                return;
            }
            class_1032 class_1032Var = new class_1032(i3);
            class_1032Var.field_6125 = new byte[i2];
            this.field_6192.field_6164 = 0;
            this.field_6192.field_6163 = i2;
            class_1032Var.field_6123 = this.field_6192;
            int i5 = 0;
            while (i5 < i2 && !this.field_6192.field_6166) {
                int i6 = dataInputStream.read(class_1032Var.field_6125, i5, i2 - i5);
                if (i6 == -1) {
                    this.field_6192.method_2899("we got to the end of the stream?!?");
                    return;
                }
                i5 += i6;
                this.field_6192.field_6158++;
                this.field_6192.field_6164 = i5;
            }
            this.field_6192.field_6163 = 0;
            this.field_6192.field_6164 = 0;
            if (!this.field_6192.field_6166) {
                if (class_1032Var.field_6124 > 100) {
                    this.field_6192.field_6165.method_2737(class_1032Var);
                    //this.field_6192.field_6165.field_5874.field_6015 = 0;
                } else {
                    this.field_6192.field_6165.field_5889.add(class_1032Var);
                }
            }
        }
    }
}