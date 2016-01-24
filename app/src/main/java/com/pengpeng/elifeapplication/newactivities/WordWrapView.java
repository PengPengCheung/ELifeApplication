package com.pengpeng.elifeapplication.newactivities;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by pengpeng on 16-1-21.
 */
public class WordWrapView extends ViewGroup {

    private static final int PADDING_HOR = 10;//水平方向padding
    private static final int PADDING_VERTICAL = 10;//垂直方向padding
    private static final int SIDE_MARGIN = 5;//左右间距
    private static final int TEXT_MARGIN = 5;


    public WordWrapView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WordWrapView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public WordWrapView(Context context) {
        super(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int actualWidth = r - l;
        int x = SIDE_MARGIN;// 横坐标开始
        int y = 0;//纵坐标开始
        int rows = 1;
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            view.setBackgroundColor(Color.GREEN);
            int width = view.getMeasuredWidth();
//            Log.i("onLayoutgetWidth", String.valueOf(width));
            int height = view.getMeasuredHeight();
            Log.i("onLayoutgetHeight", String.valueOf(height));
            x += width + TEXT_MARGIN;
            if (x > actualWidth) {
                x = width + SIDE_MARGIN;
                rows++;
            }
            y = rows * (height + TEXT_MARGIN);
            if (i == 0) {
                view.layout(x - width - TEXT_MARGIN, y - height, x - TEXT_MARGIN, y);
            } else {
                view.layout(x - width, y - height, x, y);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int x = 0;//横坐标
        int y = 0;//纵坐标
        int rows = 1;//总行数
        int specWidth = MeasureSpec.getSize(widthMeasureSpec);
        int actualWidth = specWidth - SIDE_MARGIN * 2;//实际宽度
        int childCount = getChildCount();
        for (int index = 0; index < childCount; index++) {
            View child = getChildAt(index);
//            child.setPadding(PADDING_HOR, PADDING_VERTICAL, PADDING_HOR, PADDING_VERTICAL);
            child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
            int width = child.getMeasuredWidth();
//            Log.i("onMeasuregetWidth", String.valueOf(width));
            int height = child.getMeasuredHeight();
            Log.i("onMeasuregetHeight", String.valueOf(height));
            x += width + TEXT_MARGIN;
            if (x > actualWidth) {//换行
                x = width;
                rows++;
            }
            y = rows * (height + TEXT_MARGIN);
        }
        setMeasuredDimension(actualWidth, y);//这里指整段布局所占的宽度和高度，而不是某个单独的textview或者edittext，y从布局的顶格开始，
    }

}


//public class WordWarpView extends ViewGroup {
//    private final static String TAG = "MyViewGroup";
//
//    private final static int VIEW_MARGIN=2;
//
//    public WordWarpView(Context context) {
//        super(context);
//    }
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        Log.d(TAG, "widthMeasureSpec = " + widthMeasureSpec + " heightMeasureSpec" + heightMeasureSpec);
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        for (int index = 0; index < getChildCount(); index++) {
//            final View child = getChildAt(index);
//            // measure
//            child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
//        }
//
//
//    }
//
//    @Override
//    protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
//        Log.d(TAG, "changed = "+arg0+" left = "+arg1+" top = "+arg2+" right = "+arg3+" botom = "+arg4);
//        final int count = getChildCount();
//        int row=0;// which row lay you view relative to parent
//        int lengthX=arg1;    // right position of child relative to parent
//        int lengthY=arg2;    // bottom position of child relative to parent
//        for(int i=0;i<count;i++){
//
//            final View child = this.getChildAt(i);
//            int width = child.getMeasuredWidth();
//            int height = child.getMeasuredHeight();
//            lengthX+=width+VIEW_MARGIN;
//            lengthY=row*(height+VIEW_MARGIN)+VIEW_MARGIN+height+arg2;
//            //if it can't drawing on a same line , skip to next line
//            if(lengthX>arg3){
//                lengthX=width+VIEW_MARGIN+arg1;
//                row++;
//                lengthY=row*(height+VIEW_MARGIN)+VIEW_MARGIN+height+arg2;
//
//            }
//
//            child.layout(lengthX-width, lengthY-height, lengthX, lengthY);
//        }
//
//    }
//
//}
