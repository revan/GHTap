package io.revan.ghcontroller;

import android.os.Bundle;
import android.widget.Button;

import io.revan.KeyPressEvent;

public class GuitarActivity extends AbstractControllerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_guitar);
        super.onCreate(savedInstanceState);

        setupButton((Button) findViewById(R.id.button_strum2), KeyPressEvent.Button.STRUM_DOWN);
        instrument = KeyPressEvent.Instrument.GUITAR;
    }
}
