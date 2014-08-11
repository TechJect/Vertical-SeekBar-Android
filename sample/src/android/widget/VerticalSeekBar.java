package android.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;

import org.jetbrains.annotations.NotNull;

public class VerticalSeekBar extends SeekBar {

    private OnSeekBarChangeListener myListener;

    public VerticalSeekBar(Context context) {
        super(context);
    }
    public VerticalSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public VerticalSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(h, w, oldh, oldw); //switch width, height
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec); //switch width, height
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }

    @Override
    public void setOnSeekBarChangeListener(OnSeekBarChangeListener mListener){
        this.myListener = mListener;
    }

    protected void onDraw(@NotNull Canvas c) {
        c.rotate(-90);
        c.translate(-getHeight(), 0);

        super.onDraw(c);
    }

    @Override
    public boolean onTouchEvent(@NotNull MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(myListener != null)
                    myListener.onStartTrackingTouch(this);
                break;
            case MotionEvent.ACTION_MOVE:
                int max = getMax();
                float eventY = event.getY();
                int height = getHeight();
                int calculation = getMax() - (int) (getMax() * event.getY() / getHeight());
                if(calculation > max)
                    calculation = max;
                else if(calculation < 0)
                    calculation = 0;
                setProgress(calculation);
                onSizeChanged(getWidth(), getHeight(), 0, 0);
                if(myListener != null)
                    myListener.onProgressChanged(this,calculation, false);
                break;
            case MotionEvent.ACTION_UP:
                if(myListener != null)
                    myListener.onStopTrackingTouch(this);
                break;

            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }
}