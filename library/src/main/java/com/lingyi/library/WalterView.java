package com.lingyi.library;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;
import android.widget.Scroller;

/**
 * Created by lingyi on 16/12/10.
 * Copyright © 1994-2016 lingyi™ Inc. All rights reserved.
 */

public class WalterView extends View {

    private Paint mPaint;

    private int mCenterx;

    private int mCentery;

    private float radius = 0;

    private Bitmap mViewBitmap;

    private int maxRadius = 0;

    private Path mPath;

    private Scroller mScroller;

    private onDrawFinish listener;


    public WalterView(Context context, int centerx, int centery, Bitmap mViewBitmap, onDrawFinish listener) {
        super(context);
        this.mCenterx = centerx;
        this.mCentery = centery;
        this.mViewBitmap = mViewBitmap;
        mPaint = new Paint();
        int scrrenWidth = getScrrenWidth();
        int scrrenHeight = getScrrenHeight();
        int maxY = Math.max(Math.abs(mCentery),Math.abs(scrrenHeight - mCentery));
        int maxX = Math.max(Math.abs(mCenterx),Math.abs(scrrenWidth - mCenterx));
        maxRadius = (int)Math.sqrt((double) (maxX * maxX + maxY * maxY));
        mPath = new Path();
        radius = 0;
        mPath.addCircle(mCenterx,mCentery,radius,Path.Direction.CW);
        this.listener = listener;
        mScroller = new Scroller(context);
        mScroller.startScroll((int)radius,0,(int)(maxRadius-radius),0,1200);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.addCircle(mCenterx,mCentery,radius,Path.Direction.CW);
        canvas.clipPath(mPath);
        canvas.drawBitmap(mViewBitmap,0,0,mPaint);
    }


    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mScroller.computeScrollOffset()){
            radius = mScroller.getCurrX();
            invalidate();
        }else{
            if(listener != null){
                listener.onFinish();
            }
        }
    }
    public interface onDrawFinish{
        void onFinish();
    }

    private  int getScrrenWidth(){
        return  getResources().getDisplayMetrics().widthPixels;
    }

    private  int getScrrenHeight(){
        return  getResources().getDisplayMetrics().heightPixels;
    }
}
