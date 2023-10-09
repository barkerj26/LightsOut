package com.barker.lightsout;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * Lights out
 * 5x5 button game
 *
 * @author Jaden Barker
 * 8 October 2023
 * CS 301
 */
public class MainActivity extends AppCompatActivity {
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LightView view = findViewById(R.id.lightView);
        FloatingActionButton button = findViewById(R.id.reset);
        MainController controller = new MainController(new MainModel(), view);
        view.setOnTouchListener(controller);
        button.setOnClickListener(controller);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        LightView view = findViewById(R.id.lightView);
        LinearLayout layout = findViewById(R.id.layout);

        view.setSize(layout.getWidth(), layout.getHeight());
    }
}