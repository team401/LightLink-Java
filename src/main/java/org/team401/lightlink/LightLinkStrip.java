package org.team401.lightlink;

import edu.wpi.first.wpilibj.I2C;

public class LightLinkStrip {
    public static class Color {
        private Color() {}

        public static final int RED = 0x01;
        public static final int ORANGE = 0x02;
        public static final int YELLOW = 0x03;
        public static final int GREEN = 0x04;
        public static final int BLUE = 0x05;
        public static final int VIOLET = 0x06;
        public static final int WHITE = 0x07;
        public static final int BLACK = 0x08;
    }

    public static class Action {
        private Action() {}

        public static final int SOLID = 0x01;
        public static final int BLINK = 0x02;
        public static final int SIGNAL = 0x03;
        public static final int RACE = 0x04;
        public static final int BOUNCE = 0x05;
        public static final int SPLIT = 0x06;
        public static final int BREATHE = 0x07;
        public static final int RAINBOW = 0x08;
    }

    public static class Speed {
        private Speed() {}

        public static final int SLOW = 0x01;
        public static final int FAST = 0x02;
    }

    private static final Object LL_LOCK = new Object();
    private static final int DEFAULT_ADDRESS = 0x42;
    private static final I2C.Port DEFAULT_PORT = I2C.Port.kMXP;
    private static final int DEFAULT_SPEED = Speed.SLOW;
    private static final int DEFAULT_COLOR = Color.BLACK;
    private I2C i2c;
    private byte stripId;

    private static int bound(int i) {
        if (i > 254) return 254;
        if (i < 1) return 1;
        return i;
    }

    private static int bound0(int i) {
        if (i > 254) return 254;
        if (i < 0) return 0;
        return i;
    }

    public LightLinkStrip(int stripId) {
        this.stripId = (byte) (bound0(stripId) + 1);
        i2c = new I2C(DEFAULT_PORT, DEFAULT_ADDRESS);
    }

    public LightLinkStrip(int stripId, I2C.Port port) {
        this.stripId = (byte) (bound0(stripId) + 1);
        i2c = new I2C(port, DEFAULT_ADDRESS);
    }

    public LightLinkStrip(int stripId, int address) {
        this.stripId = (byte) (bound0(stripId) + 1);
        address = bound(address);
        i2c = new I2C(DEFAULT_PORT, address);
    }

    public LightLinkStrip(int stripId, I2C.Port port, int address) {
        this.stripId = (byte) (bound0(stripId) + 1);
        address = bound(address);
        i2c = new I2C(port, address);
    }

    private byte[] array = new byte[6];

    private byte[] buildCommand(int color, int action, int speed) {
        //SPEC v2
        color = bound(color);
        action = bound(action);
        speed = bound(speed);
        array[0] = (byte) 0x00;         //START
        array[1] = stripId;             //STRIP
        array[2] = (byte) color;        //COLOR
        array[3] = (byte) action;       //ACTION
        array[4] = (byte) speed;        //SPEED
        array[5] = (byte) 0xFF;         //FINISH
        return array;
    }

    public void set(int color, int action, int speed) {
        synchronized (LL_LOCK) {
            i2c.writeBulk(buildCommand(color, action, speed));
        }
    }

    public void off() {
        set(DEFAULT_COLOR, Action.SOLID, DEFAULT_SPEED);
    }

    public void solid(int color) {
        set(color, Action.SOLID, DEFAULT_SPEED);
    }

    public void blink(int color, int speed) {
        set(color, Action.BLINK, speed);
    }

    public void blink(int color) {
        blink(color, DEFAULT_SPEED);
    }

    public void signal(int color) {
        set(color, Action.SIGNAL, DEFAULT_SPEED);
    }

    public void race(int color, int speed) {
        set(color, Action.RACE, speed);
    }

    public void race(int color) {
        race(color, DEFAULT_SPEED);
    }

    public void bounce(int color, int speed) {
        set(color, Action.BOUNCE, speed);
    }

    public void bounce(int color) {
        bounce(color, DEFAULT_SPEED);
    }

    public void split(int color, int speed) {
        set(color, Action.SPLIT, speed);
    }

    public void split(int color) {
        split(color, DEFAULT_SPEED);
    }

    public void breathe(int color, int speed) {
        set(color, Action.BREATHE, speed);
    }

    public void breathe(int color) {
        breathe(color, DEFAULT_SPEED);
    }

    public void rainbow(int speed) {
        set(DEFAULT_COLOR, Action.RAINBOW, speed);
    }

    public void rainbow() {
        rainbow(DEFAULT_SPEED);
    }
}
