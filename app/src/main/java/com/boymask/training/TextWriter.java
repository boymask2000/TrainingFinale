package com.boymask.training;

import android.graphics.Canvas;
import android.graphics.Paint;

public class TextWriter {
    public static void write(String text, Canvas canvas, int i, int num, Paint mPaint){
        mPaint.setTextSize(180);

        float x;
        x = (canvas.getWidth() / (num + 1)) * (i + 1);
        float y = canvas.getHeight() / 5 * (i+2);
        canvas.drawText(text, x, y, mPaint);
    }
}
