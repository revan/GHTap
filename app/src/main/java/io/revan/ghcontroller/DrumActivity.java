package io.revan.ghcontroller;

import android.os.Bundle;

import io.revan.KeyPressEvent;

public class DrumActivity extends AbstractControllerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_drums);
        super.onCreate(savedInstanceState);

        instrument = KeyPressEvent.Instrument.DRUMS;
    }
}
