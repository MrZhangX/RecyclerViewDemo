package com.example.a10850.recyclerviewdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/***
 * 创建时间：2019/6/3 
 * 创建人：
 * 功能描述：
 */
public class DividerTestDecoration extends RecyclerView.ItemDecoration {

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    private static final String TAG = "DividerItem";
    private static final int[] ATTRS = new int[]{16843284};
    private Drawable mDivider;
    private int mOrientation;
    private final Rect mBounds = new Rect();

    public DividerTestDecoration(Context context, int orientation) {
        TypedArray a = context.obtainStyledAttributes(ATTRS);
        this.mDivider = a.getDrawable(0);
        if (this.mDivider == null) {
            Log.w("DividerItem", "@android:attr/listDivider was not set in the theme used for this DividerItemDecoration. Please set that attribute all call setDrawable()");
        }

        a.recycle();
        this.setOrientation(orientation);
    }

    public void setOrientation(int orientation) {
        if (orientation != 0 && orientation != 1) {
            throw new IllegalArgumentException("Invalid orientation. It should be either HORIZONTAL or VERTICAL");
        } else {
            this.mOrientation = orientation;
        }
    }

    public void setDrawable(@NonNull Drawable drawable) {
        if (drawable == null) {
            throw new IllegalArgumentException("Drawable cannot be null.");
        } else {
            this.mDivider = drawable;
        }
    }

    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() != null && this.mDivider != null) {
            if (this.mOrientation == 1) {
                this.drawVertical(c, parent);
            } else {
                this.drawHorizontal(c, parent);
            }

        }
    }

    private void drawVertical(Canvas canvas, RecyclerView parent) {
        canvas.save();
        int left;
        int right;
        if (parent.getClipToPadding()) {
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
            canvas.clipRect(left, parent.getPaddingTop(), right, parent.getHeight() - parent.getPaddingBottom());
        } else {
            left = 0;
            right = parent.getWidth();
        }

        int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; ++i) {
            View child = parent.getChildAt(i);
            parent.getDecoratedBoundsWithMargins(child, this.mBounds);
            int bottom = this.mBounds.bottom + Math.round(child.getTranslationY());
            int top = bottom - this.mDivider.getIntrinsicHeight();
            this.mDivider.setBounds(left, top, right, bottom);
            this.mDivider.draw(canvas);
        }

        canvas.restore();
    }

    /**
     * todo.. 研究这个
     *
     * @param canvas
     * @param parent
     */
    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        canvas.save();
        int top;
        int bottom;
        if (parent.getClipToPadding()) {
            top = parent.getPaddingTop();
            bottom = parent.getHeight() - parent.getPaddingBottom();
            canvas.clipRect(parent.getPaddingLeft(), top, parent.getWidth() - parent.getPaddingRight(), bottom);
        } else {
            top = 0;
            bottom = parent.getHeight();
        }

        int childCount = parent.getChildCount();

        //得到列数
        int spanCount = 0;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        }

        for (int i = 0; i < childCount; ++i) {
            View child = parent.getChildAt(i);
            parent.getLayoutManager().getDecoratedBoundsWithMargins(child, this.mBounds);

            int intrinsicWidth = this.mDivider.getIntrinsicWidth();
            int i4 = parent.getWidth() - spanCount * intrinsicWidth;
            int perWidth = i4 / spanCount;

            int i3 = parent.getWidth() - (spanCount - 1) * intrinsicWidth;
            int w = i3 / spanCount;

            Log.i(TAG, i+"drawHorizontal单个view宽度: "+child.getWidth());
            Log.i(TAG, i+"drawHorizontal: "+perWidth);
            Log.i(TAG, i+"drawHorizontal3: "+w);
            Log.i(TAG, i+"drawHorizontal3: "+intrinsicWidth);




//            int right = this.mBounds.right + Math.round(child.getTranslationX());
//            int left = right - this.mDivider.getIntrinsicWidth();
//            this.mDivider.setBounds(left, top, right, bottom);
//            this.mDivider.draw(canvas);
        }

        canvas.restore();
    }

    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //得到列数
        int spanCount = 0;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        }

//        int childCount = parent.getAdapter().getItemCount();
        int childPosition = parent.getChildPosition(view);
        childPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();

        if (this.mDivider == null) {
            outRect.set(0, 0, 0, 0);
        } else {
            if (this.mOrientation == 1) {
                outRect.set(0, 0, 0, this.mDivider.getIntrinsicHeight());
            } else {
//                outRect.set(0, 0, this.mDivider.getIntrinsicWidth(), 0);


                int intrinsicWidth = this.mDivider.getIntrinsicWidth();
                int i = parent.getWidth() - spanCount * intrinsicWidth;
                int perWidth = i / spanCount;

                int i3 = parent.getWidth() - (spanCount - 1) * intrinsicWidth;
                int w = i3 / spanCount;
                Log.i(TAG, "getItemOffsets: " + childPosition);
                Log.i(TAG, "getItemOffsets: " + spanCount);
                if ((childPosition + 1) % spanCount == 0)
                    outRect.set(intrinsicWidth/3*2, 0, 0, 0);
                else if ((childPosition + 1) % spanCount == 1)
                    outRect.set(0, 0, intrinsicWidth/3*2, 0);
                else if ((childPosition + 1) % spanCount == 2)
                    outRect.set(intrinsicWidth/3, 0, intrinsicWidth/3, 0);
                else
                    outRect.set(intrinsicWidth/3, 0, intrinsicWidth/3, 0);
            }

        }
    }

}
