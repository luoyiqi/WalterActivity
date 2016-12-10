package com.lingyi.library;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.lang.reflect.Field;

/**
 * Created by lingyi on 16/12/10.
 * Copyright © 1994-2016 lingyi™ Inc. All rights reserved.
 */

public class WalterUtils {

    private View mCenterView;

    private Context mContext;

    private View nextView;

    private Bitmap nextBitmap;

    private View startView;

    private WalterUtils(){}

    public static WalterUtils getInstance(){
        return SingleHolder.instance;
    }

    private static class SingleHolder {
        public static WalterUtils instance = new WalterUtils();
    }

    public View doAnim(Activity activity){
        int [] loaction = new int[2];
        this.mCenterView.getLocationOnScreen(loaction);
        final RelativeLayout parent = new RelativeLayout(mContext);
        int  centerx = (int) (loaction[0] + (mCenterView.getWidth()/2.0f));
        int centery = (int) (loaction[1] + (mCenterView.getHeight()/2.0f));
        WalterView walterView = new WalterView(mContext, centerx, centery, nextBitmap, new WalterView.onDrawFinish() {
            @Override
            public void onFinish() {
                parent.setVisibility(View.GONE);
            }
        });

        walterView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        parent.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        parent.setPadding(0,getStatusBarHeight(mContext),0,0);
        parent.addView(walterView);
        Bitmap bitmap = getViewBitmap(startView,getScrrenWidth(mContext),getScrrenHeight(mContext));
        Drawable drawable = new BitmapDrawable(mContext.getResources(),bitmap);
        parent.setBackground(drawable);
        ((ViewGroup)(activity.getWindow().getDecorView())).addView(parent);
        return nextView;
    }
    private Bitmap getViewBitmap(View comBitmap, int width, int height) {
        Bitmap bitmap = null;
        if (comBitmap != null) {
            comBitmap.clearFocus();
            comBitmap.setPressed(false);

            boolean willNotCache = comBitmap.willNotCacheDrawing();
            comBitmap.setWillNotCacheDrawing(false);

            // Reset the drawing cache background color to fully transparent
            // for the duration of this operation
            int color = comBitmap.getDrawingCacheBackgroundColor();
            comBitmap.setDrawingCacheBackgroundColor(0);
            float alpha = comBitmap.getAlpha();
            comBitmap.setAlpha(1.0f);

            if (color != 0) {
                comBitmap.destroyDrawingCache();
            }

            int widthSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
            int heightSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
            comBitmap.measure(widthSpec, heightSpec);
            comBitmap.layout(0, 0, width, height);

            comBitmap.buildDrawingCache();
            Bitmap cacheBitmap = comBitmap.getDrawingCache();

            bitmap = Bitmap.createBitmap(cacheBitmap);
            // Restore the view
            comBitmap.setAlpha(alpha);
            comBitmap.destroyDrawingCache();
            comBitmap.setWillNotCacheDrawing(willNotCache);
            comBitmap.setDrawingCacheBackgroundColor(color);
        }
        return bitmap;
    }
    public void startActivityDoAnim(View centerView,int nextViewId,Context context,Class clazz){
        this.mContext = context;
        this.mCenterView = centerView;
        this.nextView = View.inflate(context,nextViewId,null);
        Intent intent = new Intent(context,clazz);
        nextBitmap = getViewBitmap(this.nextView,getScrrenWidth(mContext),getScrrenHeight(mContext));
        context.startActivity(intent);
        ((Activity)context).overridePendingTransition(0,0);
        startView =  ((Activity)context).getWindow().getDecorView();
    }

    public  int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return sbar;
    }

    private  int getScrrenWidth(Context context){
        return  context.getResources().getDisplayMetrics().widthPixels;
    }

    private  int getScrrenHeight(Context context){
        return  context.getResources().getDisplayMetrics().heightPixels;
    }

}
