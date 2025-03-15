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
        return values()[inner.ordinal()];
    }

    public static class_717 getInnerAction(Actions action) {
        return class_717.values()[action.ordinal()];
    }
}
