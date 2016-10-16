package io.revan.ghcontroller;

import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

import io.revan.KeyPressEvent;

/**
 * Created by Revan on 10/16/2016.
 */

public abstract class AbstractControllerActivity extends AppCompatActivity {
    protected KeyPressEvent.Instrument instrument;

    private Vibrator vibrator;

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://192.168.0.108:9003");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(KeyPressEvent.Button button, KeyPressEvent.KeyState keyState) {
        Log.d("ghc", "SENDING MESSAGE: " + button + " " + keyState);
        mSocket.emit("press", new KeyPressEvent(instrument, button, keyState).toString());
    }

    protected void setupButton(final Button button, final KeyPressEvent.Button message) {
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        vibrator.vibrate(25);
                        sendMessage(message, KeyPressEvent.KeyState.DOWN);
                        return true;
                    case MotionEvent.ACTION_UP:
                        sendMessage(message, KeyPressEvent.KeyState.UP);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        setupButton((Button) findViewById(R.id.button1), KeyPressEvent.Button.GREEN);
        setupButton((Button) findViewById(R.id.button2), KeyPressEvent.Button.RED);
        setupButton((Button) findViewById(R.id.button3), KeyPressEvent.Button.YELLOW);
        setupButton((Button) findViewById(R.id.button4), KeyPressEvent.Button.BLUE);
        setupButton((Button) findViewById(R.id.button5), KeyPressEvent.Button.ORANGE);

        setupButton((Button) findViewById(R.id.button_strum1), KeyPressEvent.Button.STRUM_UP);

        mSocket.connect();
    }


    /** Sets full screen. */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }
}
