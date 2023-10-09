package com.barker.lightsout;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.ViewTreeObserver;
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
    MainController controller = null;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LightView view = findViewById(R.id.lightView);
        FloatingActionButton button = findViewById(R.id.reset);
        FloatingActionButton increase = findViewById(R.id.increase);
        FloatingActionButton decrease = findViewById(R.id.decrease);
        LinearLayout layout = findViewById(R.id.layout);

        if (controller == null) {
            controller = new MainController(new MainModel(), view, layout, increase, decrease);
        }

        view.setOnTouchListener(controller);
        button.setOnClickListener(controller);
        increase.setOnClickListener(controller);
        decrease.setOnClickListener(controller);

        //for getting the size of the layout
        ViewTreeObserver vto = layout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(controller);
    }
}