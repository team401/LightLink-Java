package org.lightlink;

import edu.wpi.first.wpilibj.I2C;

public class LightLink {
    public class Color {
        public static final int RED = 0x01;
        public static final int ORANGE = 0x02;
        public static final int YELLOW = 0x03;
        public static final int GREEN = 0x04;
        public static final int BLUE = 0x05;
        public static final int VIOLET = 0x06;
        public static final int WHITE = 0x07;
        public static final int BLACK = 0x08;
    }

    public class Action {
        public static final int SOLID = 0x01;
        public static final int BLINK = 0x02;
        public static final int SIGNAL = 0x03;
        public static final int RACE = 0x04;
        public static final int BOUNCE = 0x05;
        public static final int SPLIT = 0x06;
        public static final int BREATHE = 0x07;
        public static final int RAINBOW = 0x08;
    }

    public class Speed {
        public static final int SLOW = 0x01;
        public static final int FAST = 0x02;
    }

    private static final Object LL_LOCK = new Object();
    private static final int DEFAULT_ADDRESS = 0x42;
    private static final I2C.Port DEFAULT_PORT = I2C.Port.kMXP;
    private static final int DEFAULT_STRIP = 0x00;
    private static final int DEFAULT_SPEED = Speed.SLOW;
    private static final int DEFAULT_COLOR = Color.BLACK;
    private I2C i2c;

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

    public LightLink() {
        i2c = new I2C(DEFAULT_PORT, DEFAULT_ADDRESS);
    }

    public LightLink(I2C.Port port) {
        i2c = new I2C(port, DEFAULT_ADDRESS);
    }

    public LightLink(int address) {
        address = bound(address);
        i2c = new I2C(DEFAULT_PORT, address);
    }

    public LightLink(I2C.Port port, int address) {
        address = bound(address);
        i2c = new I2C(port, address);
    }

    private static byte[] buildCommand(int strip, int color, int action, int speed) {
        //SPEC v2
        strip = bound0(strip);
        color = bound(color);
        action = bound(action);
        speed = bound(speed);
        byte[] array = new byte[6];
        array[0] = (byte) 0x00;         //START
        array[1] = (byte) (strip + 1);  //STRIP
        array[2] = (byte) color;        //COLOR
        array[3] = (byte) action;       //ACTION
        array[4] = (byte) speed;        //SPEED
        array[5] = (byte) 0xFF;         //FINISH
        return array;
    }

    public void set(int color, int action, int speed, int strip) {
        synchronized (LL_LOCK) {
            i2c.writeBulk(buildCommand(strip, color, action, speed));
        }
    }

    public void set(int color, int action, int speed) {
        set(color, action, speed, DEFAULT_STRIP);
    }

    public void off(int strip) {
        set(DEFAULT_COLOR, Action.SOLID, DEFAULT_SPEED, strip);
    }

    public void off() {
        off(DEFAULT_STRIP);
    }

    public void solid(int color, int strip) {
        set(color, Action.SOLID, DEFAULT_SPEED, strip);
    }

    public void solid(int color) {
        solid(color, DEFAULT_STRIP);
    }

    public void blink(int color, int speed, int strip) {
        set(color, Action.BLINK, speed, strip);
    }

    public void blink(int color, int speed) {
        blink(color, speed, DEFAULT_STRIP);
    }

    public void blink(int color) {
        blink(color, DEFAULT_SPEED, DEFAULT_STRIP);
    }

    public void signal(int color, int strip) {
        set(color, Action.SIGNAL, DEFAULT_SPEED, strip);
    }

    public void signal(int color) {
        signal(color, DEFAULT_STRIP);
    }

    public void race(int color, int speed, int strip) {
        set(color, Action.RACE, speed, strip);
    }

    public void race(int color, int speed) {
        race(color, speed, DEFAULT_STRIP);
    }

    public void race(int color) {
        race(color, DEFAULT_SPEED);
    }

    public void bounce(int color, int speed, int strip) {
        set(color, Action.BOUNCE, speed, strip);
    }

    public void bounce(int color, int speed) {
        bounce(color, speed, DEFAULT_STRIP);
    }

    public void bounce(int color) {
        bounce(color, DEFAULT_SPEED);
    }

    public void split(int color, int speed, int strip) {
        set(color, Action.SPLIT, speed, strip);
    }

    public void split(int color, int speed) {
        split(color, speed, DEFAULT_STRIP);
    }

    public void split(int color) {
        split(color, DEFAULT_SPEED);
    }

    public void breathe(int color, int speed, int strip) {
        set(color, Action.BREATHE, speed, strip);
    }

    public void breathe(int color, int speed) {
        breathe(color, speed, DEFAULT_STRIP);
    }

    public void breathe(int color) {
        breathe(color, DEFAULT_SPEED);
    }

    public void rainbow(int speed, int strip) {
        set(DEFAULT_COLOR, Action.RAINBOW, speed, strip);
    }

    public void rainbow(int speed) {
        rainbow(speed, DEFAULT_STRIP);
    }

    public void rainbow() {
        rainbow(DEFAULT_SPEED);
    }
}
