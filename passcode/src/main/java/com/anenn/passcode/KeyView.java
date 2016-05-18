package com.anenn.passcode;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Copyright Youdar, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * <p>
 * Created by anenn <anennzxq@gmail.com> on 5/18/16.
 */
public class KeyView extends TextView {

    public KeyView(Context context) {
        this(context, null);
    }

    public KeyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KeyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        final int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int width;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize != 0 ? Math.min(widthSize, screenWidth / 3) : screenWidth / 3;
        } else {
            width = screenWidth / 3;
        }

        setMeasuredDimension(width, (int) (width * 0.5));
    }
}
