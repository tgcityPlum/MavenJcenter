package com.tgcity.function.calendar;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import org.joda.time.LocalDate;

/**
 * Created by necer on 2020/3/27.
 */
public class WhiteBackground implements CalendarBackground {


    @Override
    public Drawable getBackgroundDrawable(LocalDate localDate, int totalDistance, int currentDistance) {
        return new Drawable() {
            @Override
            public void draw(@NonNull Canvas canvas) {
                canvas.drawColor(Color.WHITE);
            }

            @Override
            public void setAlpha(int alpha) {

            }

            @Override
            public void setColorFilter(@Nullable ColorFilter colorFilter) {

            }

            @SuppressLint("WrongConstant")
            @Override
            public int getOpacity() {
                return 0;
            }
        };
    }
}
