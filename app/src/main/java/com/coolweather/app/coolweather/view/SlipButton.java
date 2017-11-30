package com.coolweather.app.coolweather.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import com.coolweather.app.coolweather.Listener.OnChangedListener;
import com.coolweather.app.coolweather.R;

/**
 * Created by dongdong on 2017/9/28.
 */

public class SlipButton extends View implements View.OnTouchListener {

    private float DownX, NowX;// 按下时的x,当前的x
    private float btn_on_left = 0;
    private float btn_off_left = 0;
    private boolean NowChoose = false;// 记录当前按钮是否打开,true为打开,flase为关闭
    private boolean isChecked;
    private boolean OnSlip = false;// 记录用户是否在滑动的变量
    private boolean isChgLsnOn = false;

    private OnChangedListener ChgLsn;
    private Bitmap bg_on;
    private Bitmap bg_off;
    private Bitmap slip_btn;

    public SlipButton(Context context) {
        super(context);

        init();
    }

    public SlipButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() { // 初始化
        bg_on = BitmapFactory.decodeResource(getResources(),
                R.drawable.toggle_on);
        bg_off = BitmapFactory.decodeResource(getResources(),
                R.drawable.toggle_off);
        slip_btn = BitmapFactory.decodeResource(getResources(),
                R.drawable.slip_btn);

        btn_off_left = bg_off.getWidth() - slip_btn.getWidth();

        setOnTouchListener(this); // 设置监听器,也可以直接复写OnTouchEvent
    }

    @Override
    protected void onDraw(Canvas canvas) {// 绘图函数
        super.onDraw(canvas);

        Matrix matrix = new Matrix();
        Paint paint = new Paint();
        float x;

        if (NowX < (bg_on.getWidth() / 2)) { // 滑动到前半段与后半段的背景不同,在此做判断
            x = NowX - slip_btn.getWidth() / 2;
            canvas.drawBitmap(bg_off, matrix, paint);// 画出关闭时的背景
        } else {
            x = bg_on.getWidth() - slip_btn.getWidth() / 2;
            canvas.drawBitmap(bg_on, matrix, paint);// 画出打开时的背景
        }
        if (OnSlip) {// 是否是在滑动状态,
            if (NowX >= bg_on.getWidth()) {// 是否划出指定范围,不能让游标跑到外头,必须做这个判断
                x = bg_on.getWidth() - slip_btn.getWidth() / 2;// 减去游标1/2的长度...
            } else if (NowX < 0) {
                x = 0;
            } else {
                x = NowX - slip_btn.getWidth() / 2;
            }
        } else {// 非滑动状态
            if (NowChoose) {// 根据现在的开关状态设置画游标的位置
                x = btn_off_left;
                canvas.drawBitmap(bg_on, matrix, paint);// 初始状态为true时应该画出打开状态图片
            } else {
                x = btn_on_left;
            }
        }
        if (isChecked) {
            canvas.drawBitmap(bg_on, matrix, paint);
            x = btn_off_left;
            isChecked = !isChecked;
        }

        if (x < 0) {// 对游标位置进行异常判断...
            x = 0;
        } else if (x > bg_on.getWidth() - slip_btn.getWidth()) {
            x = bg_on.getWidth() - slip_btn.getWidth();
        }
        canvas.drawBitmap(slip_btn, x, 0, paint);// 画出游标.

    }


    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {// 根据动作来执行代码
            case MotionEvent.ACTION_DOWN:// 按下
                if (event.getX() > bg_on.getWidth()
                        || event.getY() > bg_on.getHeight()) {
                    return false;
                }
                OnSlip = true;
                DownX = event.getX();
                NowX = DownX;
                break;

            case MotionEvent.ACTION_MOVE:// 滑动
                Log.d("David", "event.getX = " + event.getX());
                Log.d("David", "event.getY = " + event.getY());
                NowX = event.getX();
                boolean LastChoose = NowChoose;

                if (NowX >= (bg_on.getWidth() / 2)) {
                    NowChoose = true;
                } else {
                    NowChoose = false;
                }

                if (isChgLsnOn && (LastChoose != NowChoose)) { // 如果设置了监听器,就调用其方法..
                    ChgLsn.OnChanged(NowChoose);
                }
                break;

            case MotionEvent.ACTION_UP:// 松开
                OnSlip = false;
                break;
            default:
        }
        invalidate();// 重画控件
        return true;
    }

    public void SetOnChangedListener(OnChangedListener l){//设置监听器,当状态修改的时候
        isChgLsnOn = true;
        ChgLsn = l;
    }
    public void setCheck(boolean isChecked) {
        this.isChecked = isChecked;
        NowChoose = isChecked;
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int measuredHeight = measureHeight(heightMeasureSpec);
        int measuredWidth = measureWidth(widthMeasureSpec);
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    private int measureHeight(int measureSpec) {

        /*int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        // Default size if no limits are specified.

        int result = 500;
        if (specMode == MeasureSpec.AT_MOST){
            // Calculate the ideal size of your
            // control within this maximum size.
            // If your control fills the available
            // space return the outer bound.

            result = specSize;
        } else if (specMode == MeasureSpec.EXACTLY){
            // If your control can fit within these bounds return that value.
            result = specSize;
        }*/
        return bg_on.getHeight();
    }

    private int measureWidth(int measureSpec) {

        /*int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        // Default size if no limits are specified.
        int result = 500;
        if (specMode == MeasureSpec.AT_MOST){
            // Calculate the ideal size of your control
            // within this maximum size.
            // If your control fills the available space
            // return the outer bound.
            result = specSize;
        } else if (specMode == MeasureSpec.EXACTLY){
            // If your control can fit within these bounds return that value.
            result = specSize;
        }*/
        return bg_on.getWidth();
    }


}
