package com.anenn.passcode;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.EditText;

import java.lang.ref.WeakReference;

import anenn.com.passcode.R;

/**
 * Created by anenn <anennzxq@gmail.com> on 5/18/16.
 */
public class PassCodeView extends EditText {

    private static final int MSG_FILL_PASS_CODE = 10;

    private static final int STROKE_COLOR = R.color.passCode_stroke_color;
    private static final int CIRCLE_COLOR = R.color.passCode_circle_color;
    private static final int STROKE_WIDTH = 2;
    private static final int CIRCLE_RADIUS = 5;
    private static final int PASS_CODE_LENGTH = 4;

    // Custom Attributes
    private int strokeColor;
    private int strokeWidth;
    private int circleColor;
    private int circleRadius;
    private int passCodeLength;
    private boolean autoCleanPassCode;
    private boolean isEditable;

    private Paint mPassCodePaint;
    private int mCurrentPassCodeLength;
    private boolean isAcceptInput = true;

    private WeakHandler mWeakHandler;
    private OnPassCodeFillListener onPassCodeFillListener;

    public PassCodeView(Context context) {
        this(context, null);
    }

    public PassCodeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PassCodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // init custom attributes
        initAttributes(context, attrs, defStyleAttr);
        // init handler
        mWeakHandler = new WeakHandler(this);
        // init Paint
        mPassCodePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        // set itself status
        setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
        setInputType(InputType.TYPE_CLASS_NUMBER);

        if (isEditable)
            requestAllFocus();
    }

    private void initAttributes(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = null;
        try {
            typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PassCodeView, defStyleAttr, 0);
            strokeColor = typedArray.getColor(R.styleable.PassCodeView_strokeColor, ContextCompat.getColor(context, STROKE_COLOR));
            strokeWidth = typedArray.getDimensionPixelSize(R.styleable.PassCodeView_strokeWidth, getDimension(STROKE_WIDTH));
            circleColor = typedArray.getColor(R.styleable.PassCodeView_circleColor, ContextCompat.getColor(context, CIRCLE_COLOR));
            circleRadius = typedArray.getDimensionPixelSize(R.styleable.PassCodeView_circleRadius, getDimension(CIRCLE_RADIUS));
            passCodeLength = typedArray.getInteger(R.styleable.PassCodeView_passCodeLength, PASS_CODE_LENGTH);
            autoCleanPassCode = typedArray.getBoolean(R.styleable.PassCodeView_autoClean, true);
            isEditable = typedArray.getBoolean(R.styleable.PassCodeView_editable, true);
        } finally {
            if (typedArray != null) {
                typedArray.recycle();
            }
        }
    }

    /**
     * dp to px
     *
     * @param defaultSize
     * @return
     */
    private int getDimension(int defaultSize) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, defaultSize,
                getResources().getDisplayMetrics());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        int circleX = width / passCodeLength / 2;
        int circleY = height / 2;

        // draw stroke
        mPassCodePaint.setColor(strokeColor);
        mPassCodePaint.setStrokeWidth(strokeWidth);
        mPassCodePaint.setStyle(Paint.Style.STROKE);
        for (int i = 0; i < passCodeLength; i++) {
            int centreX = circleX + i * (width / passCodeLength);
            canvas.drawCircle(centreX, circleY, circleRadius, mPassCodePaint);
        }

        // draw circle
        mPassCodePaint.setColor(circleColor);
        mPassCodePaint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < mCurrentPassCodeLength; i++) {
            int centreX = circleX + i * (width / passCodeLength);
            canvas.drawCircle(centreX, circleY, circleRadius, mPassCodePaint);
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);

        if (passCodeLength > 0) {
            if (mCurrentPassCodeLength <= passCodeLength) {
                mCurrentPassCodeLength = text.length();

                invalidate();

                if (mCurrentPassCodeLength == passCodeLength) {
                    clearAllFocus();
                    isAcceptInput = false;
                    Message message = mWeakHandler.obtainMessage(MSG_FILL_PASS_CODE, text);
                    mWeakHandler.sendMessageDelayed(message, 200);
                }
            }
        }
    }

    public void clearAllFocus() {
        clearFocus();
        setFocusable(false);
        setFocusableInTouchMode(false);
    }

    public void requestAllFocus() {
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();
    }

    public void setStrokeColor(int color) {
        strokeColor = color;
        invalidate();
    }

    public void setCircleColor(int color) {
        circleColor = color;
        invalidate();
    }

    public void setStrokeWidth(int width) {
        strokeWidth = width;
        invalidate();
    }

    public void setCircleRadius(int radius) {
        circleRadius = radius;
        invalidate();
    }

    public void setPassCodeLength(int length) {
        passCodeLength = length;
        invalidate();
    }

    public void setPassCode(String passCode) {
        if (passCode != null && !passCode.matches("^\\d+$"))
            throw new IllegalArgumentException("The format of pass code is invalid");
        setText(passCode);
    }

    public String getPassCode() {
        if (getText().length() > passCodeLength) {
            return getText().toString().substring(0, passCodeLength);
        } else {
            return getText().toString().substring(0, getText().length());
        }
    }

    public void appendPassCode(String passCode) {
        if (isAcceptInput) {
            if (passCode != null)
                setText(getPassCode().concat(passCode));
        }
    }

    public void delPassCode() {
        if (mCurrentPassCodeLength > 0) {
            String passCode = getPassCode();
            setText(passCode.length() > 0 ? passCode.substring(0, passCode.length() - 1) : null);
        }
    }

    public void setOnPassCodeFillListener(OnPassCodeFillListener onPassCodeFillListener) {
        this.onPassCodeFillListener = onPassCodeFillListener;
    }

    public OnPassCodeFillListener getOnPassCodeFillListener() {
        return onPassCodeFillListener;
    }

    public void release() {
        if (mWeakHandler != null) {
            mWeakHandler.removeMessages(MSG_FILL_PASS_CODE);
            mWeakHandler = null;
        }
    }

    private static class WeakHandler extends Handler {

        private WeakReference<PassCodeView> reference;

        public WeakHandler(PassCodeView passCodeView) {
            reference = new WeakReference<>(passCodeView);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_FILL_PASS_CODE:
                    PassCodeView passCodeView = reference.get();
                    OnPassCodeFillListener listener = passCodeView.onPassCodeFillListener;
                    if (listener != null) {
                        listener.passCodeFill(String.valueOf(msg.obj));
                    }
                    if (passCodeView.autoCleanPassCode) {
                        passCodeView.setText(null);
                        if (passCodeView.isEditable)
                            passCodeView.requestAllFocus();
                        passCodeView.isAcceptInput = true;
                    }
                    break;
            }
        }
    }
}
