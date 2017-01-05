package com.example.administrator.moonlightcalendar.other;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.example.administrator.moonlightcalendar.R;

/**
 * Created by Administrator on 2017/1/5 0005.
 */

public class MyItemDecoration extends RecyclerView.ItemDecoration {

    private int orientation;
    private Context mContext;
    private int mItemSize = 1;
    private Paint mPaint;

    public MyItemDecoration(Context context, int orientation) {
        if(orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL){
            throw new IllegalArgumentException("请传入正确的参数") ;
        }
        this.orientation = orientation;
        mContext = context;
        mItemSize = (int) TypedValue.applyDimension(mItemSize, TypedValue.COMPLEX_UNIT_DIP, context.getResources().getDisplayMetrics());
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG) ;
        mPaint.setColor(context.getResources().getColor(R.color.light_gray));
         /*设置填充*/
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if(orientation == LinearLayoutManager.VERTICAL){
            outRect.set(0,0,0,mItemSize);
        }else {
            outRect.set(0,0,mItemSize,0);
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if(orientation == LinearLayoutManager.VERTICAL){
            drawVertical(c,parent) ;
        }else {
            drawHorizontal(c,parent) ;
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop() ;
        final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom() ;
        final int childSize = parent.getChildCount() ;
        for(int i = 0 ; i < childSize ; i ++){
            final View child = parent.getChildAt( i ) ;
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + layoutParams.rightMargin ;
            final int right = left + mItemSize ;
            c.drawRect(left,top,right,bottom,mPaint);
        }
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft() ;
        final int right = parent.getMeasuredWidth() - parent.getPaddingRight() ;
        final int childSize = parent.getChildCount() ;
        for(int i = 0 ; i < childSize ; i ++){
            final View child = parent.getChildAt( i ) ;
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + layoutParams.bottomMargin ;
            final int bottom = top + mItemSize ;
            c.drawRect(left,top,right,bottom,mPaint);
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }
}
