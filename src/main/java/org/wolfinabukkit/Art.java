package org.wolfinabukkit;

import java.util.HashMap;

import org.apache.commons.lang.Validate;
import com.google.common.collect.Maps;

// the paintings
public enum Art {
    KEBAB(0, 1, 1),
    AZTEC(1, 1, 1),
    ALBAN(2, 1, 1),
    AZTEC2(3, 1, 1),
    BOMB(4, 1, 1),
    PLANT(5, 1, 1),
    WASTELAND(6, 1, 1),
    POOL(7, 2, 1),
    COURBET(8, 2, 1),
    SEA(9, 2, 1),
    SUNSET(10, 2, 1),
    CREEBET(11, 2, 1),
    WANDERER(12, 1, 2),
    GRAHAM(13, 1, 2),
    MATCH(14, 2, 2),
    BUST(15, 2, 2),
    STAGE(16, 2, 2),
    VOID(17, 2, 2),
    SKULL_AND_ROSES(18, 2, 2),
    WITHER(19, 2, 2),
    FIGHTERS(20, 4, 2),
    POINTER(21, 4, 4),
    PIGSCENE(22, 4, 4),
    BURNINGSKULL(23, 4, 4),
    SKELETON(24, 4, 3),
    DONKEYKONG(25, 4, 3);

    // private stuff
    private int id;
    private int width;
    private int height;
    private static final HashMap<String, Art> NAME_MAP = Maps.newHashMap();
    private static final HashMap<Integer, Art> ID_MAP = Maps.newHashMap();

    static {
        for (Art pixely : values()) {
            // yea... when we remove the deprecated you lose to buddy
            ID_MAP.put(pixely.id, pixely);
            NAME_MAP.put(pixely.toString().toLowerCase().replace("_", ""), pixely);
        }
    }

    private Art(int id, int width, int height) {
        this.id = id;
        this.width = width;
        this.height = height;
    }

    // public stuff
    public int getBlockWidth() {
        return this.width;
    }

    public int getBlockHeight() {
        return this.height;
    }

    public static Art getByName(String which) {
        Validate.notNull(which, "You are looking for a painting without telling me which one?");
        return NAME_MAP.get(which.toLowerCase().replace("_", ""));
    }

    // yea... if you didnt use these that would be great
    @Deprecated
    public int getId() {
        return this.id;
    }

    @Deprecated
    public static Art getById(int id) {
        return ID_MAP.get(id);
    }
}
