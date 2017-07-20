package com.h.deleteeditview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

/**
 * Created by h on 2017/7/20.
 * <p>
 * 一个带有delete按钮的editview
 */

public class DeleteEditView extends EditText {
    Context context;
    Paint paint;
    Bitmap bitmap;
    boolean show = false;
    float coordX, coordY;

    public DeleteEditView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public DeleteEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        paint = new Paint();
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_delete_input);
        bitmap = Bitmap.createScaledBitmap(bitmap, 50, 50, true);
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(bitmapShader);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setFilterBitmap(true);
        paint.setDither(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //setPadding(0, 0, bitmap.getWidth(), 0);
        coordX = canvas.getWidth() - bitmap.getWidth();
        coordY = (canvas.getHeight() - getPaddingBottom() - getPaddingTop()) / 2 - bitmap.getHeight() / 2 + getPaddingTop();

        setPadding(getPaddingLeft(), getPaddingTop(), bitmap.getWidth(), getPaddingBottom());
        //Log.e("gggggg", getPaddingLeft() + "  " + getPaddingTop() + "  " + getPaddingRight() + "  " + getPaddingBottom());
        if (show) {
            canvas.drawBitmap(bitmap, coordX, coordY, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (isRange(x, y) && show) {
            setText("");
        }
        return super.onTouchEvent(event);
    }

    //xy 是否在范围内
    private boolean isRange(float x, float y) {
        if (x > coordX && x < (coordX + bitmap.getWidth()) &&
                y > coordY && y < (coordY + bitmap.getHeight())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if (getText().length() > 0) {
            show = true;
        } else {
            show = false;
        }
    }
}
