package com.corrodinggames.rts.union.gameFramework.j;

import cn.tesseract.union.accessor.ConnectionAccessor;
import cn.tesseract.union.accessor.PacketAccessor;
import cn.tesseract.union.accessor.PlayerAccessor;
import cn.tesseract.union.api.event.Event;
import cn.tesseract.union.api.event.PacketEvent;
import cn.tesseract.union.api.event.PlayerJoinEvent;
import cn.tesseract.union.api.util.ScriptManager;
import com.corrodinggames.rts.union.gameFramework.GameEngine;

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
        GameEngine.method_2981();
        Thread.currentThread().setName("ReceiveWorker-" + this.field_6192.method_2905());
        try {
            method_2909();
        } catch (EOFException e) {
            GameEngine.method_3010(this.field_6192.method_2903("network:ReceiveWorker: EOF reading packet"), e);
        } catch (IOException e2) {
            if (!this.field_6192.field_6166) {
                e2.printStackTrace();
            }
            if (GameEngine.field_6309 && (e2 instanceof SocketException) && !this.field_6192.field_6166) {
                GameEngine game = GameEngine.get();
                if (!game.field_6352.field_5851 && game.field_6352.field_5898 && (message = e2.getMessage()) != null && message.contains("EBADF")) {
                    game.method_3056("Warning: This disconnect likely due to iOS removing sockets of background apps. Avoid minimising the game in multiplayer. Note: Games can be rejoined.");
                }
            }
            this.field_6192.method_2901("network:ReceiveWorker: " + e2.getMessage());
        } catch (OutOfMemoryError e3) {
            GameEngine.method_3034(e3);
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
            class_1032 packet = new class_1032(i3);
            packet.field_6125 = new byte[i2];
            this.field_6192.field_6164 = 0;
            this.field_6192.field_6163 = i2;
            packet.field_6123 = this.field_6192;
            int i5 = 0;
            while (i5 < i2 && !this.field_6192.field_6166) {
                int i6 = dataInputStream.read(packet.field_6125, i5, i2 - i5);
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
                if (packet.field_6124 > 100) {
                    if (packet.field_6124 == 110 && packet.field_6123.field_6142 != null) {
                        ScriptManager.call("onJoin", new PlayerJoinEvent(((PlayerAccessor) packet.field_6123.field_6142).get_wrapper(), ((ConnectionAccessor) packet.field_6123).get_wrapper()));
                    }
                    Event event = new PacketEvent(((PacketAccessor) packet).get_wrapper());
                    ScriptManager.call("onPacket", event);
                    if (!event.isCanceled()) this.field_6192.field_6165.method_2737(packet);
                    //this.field_6192.field_6165.field_5874.field_6015 = 0;
                } else {
                    this.field_6192.field_6165.field_5889.add(packet);
                }
            }
        }
    }
}