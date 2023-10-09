package com.barker.lightsout;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

/**
 * MainController
 * Handles behavior for clicking on and resetting the board
 */
public class MainController implements View.OnClickListener, View.OnTouchListener,
        ViewTreeObserver.OnGlobalLayoutListener {
    MainModel model;
    LightView lightView;
    LinearLayout layout;

    /**
     * Constructor for the controller
     *
     * @param model the model class
     * @param view the view class
     */
    public MainController(MainModel model, LightView view, LinearLayout layout) {
        this.model = model;
        this.lightView = view;
        this.layout = layout;
        view.setModel(model);
    }

    @Override
    public void onClick(View view) {
        model.randomizeStates();
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

        int row = (int) ((x - baseLeft) / (sizeBase / 5f));
        int col = (int) ((y - baseTop) / (sizeBase / 5f));
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
