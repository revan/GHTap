package io.revan;

import java.io.Serializable;

public class KeyPressEvent implements Serializable {
    public enum Instrument {
        GUITAR, DRUMS
    }
    public enum KeyState {
        UP, DOWN
    }
    public enum Button {
        GREEN, RED, YELLOW, BLUE, ORANGE, STRUM_UP, STRUM_DOWN, PLUS, MINUS, BAR
    }

    public Instrument instrument;
    public Button button;
    public KeyState keyState;

    public KeyPressEvent(Instrument instrument, Button button, KeyState keyState) {
        this.instrument = instrument;
        this.button = button;
        this.keyState = keyState;
    }

    @Override
    public String toString() {
        return instrument + "|" + button + "|" + keyState;
    }

    public static KeyPressEvent fromString(String s) {
        String[] fields = s.split("\\|");
        return new KeyPressEvent(
                Instrument.valueOf(fields[0]),
                Button.valueOf(fields[1]),
                KeyState.valueOf(fields[2]));
    }
}
