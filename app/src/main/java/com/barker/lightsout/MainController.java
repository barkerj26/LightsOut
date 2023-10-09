package com.barker.lightsout;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * MainController
 * Handles behavior for clicking on and resetting the board
 */
public class MainController implements View.OnClickListener, View.OnTouchListener,
        ViewTreeObserver.OnGlobalLayoutListener {
    private final MainModel model;
    private final LightView lightView;
    private final LinearLayout layout;
    private final FloatingActionButton increase;
    private final FloatingActionButton decrease;

    /**
     * Constructor for the controller
     *
     * @param model the model class
     * @param view the view class
     */
    public MainController(MainModel model, LightView view, LinearLayout layout,
                          FloatingActionButton increase, FloatingActionButton decrease) {
        this.model = model;
        this.lightView = view;
        this.layout = layout;
        this.increase = increase;
        this.decrease = decrease;
        view.setModel(model);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.reset) {
            model.randomizeStates();
        } else if (id == R.id.increase) {
            int newSquares = Math.min(model.getSquares() + 1, 10);

            decrease.show();
            if (newSquares == 10) {
                increase.hide();
            }

            model.setSquares(newSquares);
        } else if (id == R.id.decrease) {
            int newSquares = Math.max(model.getSquares() - 1, 3);

            increase.show();
            if (newSquares == 3) {
                decrease.hide();
            }

            model.setSquares(newSquares);
        }
        lightView.invalidate();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        int w = lightView.getWide();
        int h = lightView.getTall();

        int sizeBase = (Math.min(w, h) * 7) / 8;
        float baseLeft = w / 2f - sizeBase / 2f;
        float baseTop = h / 2f - sizeBase / 2f;
        float baseRight = w / 2f + sizeBase / 2f;
        float baseBottom = h / 2f + sizeBase / 2f;

        if (x < baseLeft || y < baseTop || x > baseRight || y > baseBottom) {
            return false;
        }

        int row = (int) ((x - baseLeft) / (sizeBase / (float) model.getSquares()));
        int col = (int) ((y - baseTop) / (sizeBase / (float) model.getSquares()));
        model.pressState(row, col);
        model.checkSolved();
        lightView.invalidate();

        return false;
    }

    @Override
    public void onGlobalLayout() {
        lightView.setSize(layout.getMeasuredWidth(), layout.getMeasuredHeight());
    }
}
