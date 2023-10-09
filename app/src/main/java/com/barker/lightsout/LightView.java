package com.barker.lightsout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;

public class LightView extends SurfaceView {
    private int w;
    private int h;
    private final Paint darkPaint;
    private final Paint lightPaint;
    private final Paint backPaint;
    private final Paint winPaint;
    private final Paint nopePaint;
    private final Paint blackPaint;
    private MainModel model;

    /**
     * Constructor for the view
     * @param context context of the view
     * @param attrs attributes for the view
     */
    public LightView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);

        w = 0;
        h = 0;

        darkPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        darkPaint.setColor(0xFF504040);
        darkPaint.setStyle(Paint.Style.FILL);

        lightPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        lightPaint.setColor(0xFFD0E0E0);
        lightPaint.setStyle(Paint.Style.FILL);

        backPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backPaint.setColor(0xFF100505);
        backPaint.setStyle(Paint.Style.FILL);

        winPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        winPaint.setColor(0xFF008010);
        winPaint.setStyle(Paint.Style.FILL);

        nopePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        nopePaint.setColor(0xFF804010);
        nopePaint.setStyle(Paint.Style.FILL);

        blackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        blackPaint.setColor(0x80100505);
        blackPaint.setStyle(Paint.Style.FILL);
    }

    /**
     * setSize
     * sets the size of the view for its calculations
     * @param w width
     * @param h height
     */
    public void setSize(int w, int h) {
        this.w = w;
        this.h = h;
        invalidate();
    }

    /**
     * getWide
     * get the width (w) value
     * @return the width
     */
    public int getWide() {
        return w;
    }

    /**
     * getTall
     * get the height (h) value
     * @return the height
     */
    public int getTall() {
        return h;
    }

    /**
     * setModel
     * sets the model class for the view to use
     * @param model the model class
     */
    public void setModel(MainModel model) {
        this.model = model;
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (model == null) {
            return;
        }

        // background / "solved" indicator
        if (model.getSolved()) {
            canvas.drawRect(0, 0, w, h, winPaint);
        } else {
            canvas.drawRect(0, 0, w, h, nopePaint);
        }

        int sizeBase = (Math.min(w, h) * 7) / 8;
        float SB5 = sizeBase / 5f;
        float baseLeft = w / 2f - sizeBase / 2f;
        float baseTop = h / 2f - sizeBase / 2f;
        float baseRight = w / 2f + sizeBase / 2f;
        float baseBottom = h / 2f + sizeBase / 2f;

        //shadow and backfill
        float SB30 = sizeBase / 30f;
        canvas.drawRect(baseLeft + SB30, baseTop + SB30, baseRight + SB30,
                baseBottom + SB30, blackPaint);
        canvas.drawRect(baseLeft, baseTop, baseRight, baseBottom, backPaint);

        //draw each cube
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Paint paint;
                if (model.getState(i, j)) {
                    paint = lightPaint;
                } else {
                    paint = darkPaint;
                }

                canvas.drawRect(baseLeft + SB5 * i, baseTop + SB5 * j,
                        baseLeft + SB5 * (i + 1), baseTop + SB5 * (j + 1), paint);
            }
        }
    }
}