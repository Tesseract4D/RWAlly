package cn.tesseract.rwally.util;


import com.corrodinggames.rts.ally.game.units.class_717;

public enum Actions {
    MOVE,
    ATTACK,
    BUILD,
    REPAIR,
    LOADINTO,
    UNLOADAT,
    RECLAIM,
    ATTACKMOVE,
    LOADUP,
    PATROL,
    GUARD,
    GUARDAT,
    TOUCHTARGET,
    FOLLOW,
    TRIGGERACTION,
    TRIGGERACTIONWHENINRANGE,
    SETPASSIVETARGET;

    public static Actions getAction(class_717 inner) {
        return switch (inner) {
            case a -> MOVE;
            case b -> ATTACK;
            case c -> BUILD;
            case d -> REPAIR;
            case e -> LOADINTO;
            case f -> UNLOADAT;
            case g -> RECLAIM;
            case h -> ATTACKMOVE;
            case i -> LOADUP;
            case j -> PATROL;
            case k -> GUARD;
            case l -> GUARDAT;
            case m -> TOUCHTARGET;
            case n -> FOLLOW;
            case o -> TRIGGERACTION;
            case p -> TRIGGERACTIONWHENINRANGE;
            case q -> SETPASSIVETARGET;
        };
    }

    public static class_717 getInnerAction(Actions action) {
        return switch (action) {
            case MOVE -> class_717.a;
            case ATTACK -> class_717.b;
            case BUILD -> class_717.c;
            case REPAIR -> class_717.d;
            case LOADINTO -> class_717.e;
            case UNLOADAT -> class_717.f;
            case RECLAIM -> class_717.g;
            case ATTACKMOVE -> class_717.h;
            case LOADUP -> class_717.i;
            case PATROL -> class_717.j;
            case GUARD -> class_717.k;
            case GUARDAT -> class_717.l;
            case TOUCHTARGET -> class_717.m;
            case FOLLOW -> class_717.n;
            case TRIGGERACTION -> class_717.o;
            case TRIGGERACTIONWHENINRANGE -> class_717.p;
            case SETPASSIVETARGET -> class_717.q;
        };
    }
}
