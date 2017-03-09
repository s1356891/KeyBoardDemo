package com.workspace.bin.keyboarddemo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

/**
 * 加强版的EditText, DrawableRight的单击和长按事件
 * 要实现响应点击,先设置setDrawableListener,setDrawableLongListener
 * @author luffy
 *
 */
public class XEditText extends EditText {

	private DrawableLeftListener mLeftListener ;
	private DrawableRightListener mRightListener ;
	private DrawableRightLongListener mRightLongListener ;

	final int DRAWABLE_LEFT = 0;
    final int DRAWABLE_TOP = 1;
    final int DRAWABLE_RIGHT = 2;
    final int DRAWABLE_BOTTOM = 3;
    private long time;
    private long currentTimeMillis;

    @SuppressLint("NewApi")
	public XEditText(Context context, AttributeSet attrs, int defStyleAttr,
			int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	public XEditText(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public XEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public XEditText(Context context) {
		super(context);
	}

	public void setDrawableLeftListener(DrawableLeftListener listener) {
		this.mLeftListener = listener;
	}

	public void setDrawableRightListener(DrawableRightListener listener,DrawableRightLongListener longListener) {
		this.mRightListener = listener;
        this.mRightLongListener = longListener;
    }

	public interface DrawableLeftListener {
		public void onDrawableLeftClick(View view) ;
	}

	public interface DrawableRightListener {
		public void onDrawableRightClick(View view) ;
	}
    public interface DrawableRightLongListener {
        public void onDrawableRightLongClick(View view) ;
    }

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_UP:
            currentTimeMillis = System.currentTimeMillis();
            Drawable drawableRight = getCompoundDrawables()[DRAWABLE_RIGHT] ;
            if (currentTimeMillis - time > 500&&mRightLongListener!=null) {
                if (drawableRight != null && event.getRawX() >= (getRight() - drawableRight.getBounds().width())) {
                    mRightLongListener.onDrawableRightLongClick(this); ;
                    return true ;
                }
            }
            if (mRightListener != null) {
				if (drawableRight != null && event.getRawX() >= (getRight() - drawableRight.getBounds().width())) {
					mRightListener.onDrawableRightClick(this) ;
	        		return true ;
				}
			}

			if (mLeftListener != null) {
				Drawable drawableLeft = getCompoundDrawables()[DRAWABLE_LEFT] ;
				if (drawableLeft != null && event.getRawX() <= (getLeft() + drawableLeft.getBounds().width())) {
					mLeftListener.onDrawableLeftClick(this) ;
	        		return true ;
				}
			}
			break;
			case MotionEvent.ACTION_DOWN:
                time = System.currentTimeMillis();
				break;
		}

		return super.onTouchEvent(event);
	}
}