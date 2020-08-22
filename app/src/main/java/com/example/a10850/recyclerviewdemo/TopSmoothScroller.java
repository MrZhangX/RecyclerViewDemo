package com.example.a10850.recyclerviewdemo;

import android.content.Context;

import androidx.recyclerview.widget.LinearSmoothScroller;

/***
 * 创建时间：2020/2/7 20:20
 * 创建人：10850
 * 功能描述：https://yq.aliyun.com/articles/638016
 * https://blog.csdn.net/weimingjue/article/details/82805361
 * https://blog.csdn.net/bzlj2912009596/article/details/87873738
 *
 *  public static final int SNAP_TO_START = -1;
 *  public static final int SNAP_TO_END = 1;
 *  public static final int SNAP_TO_ANY = 0;
 *  SNAP_TO_START使子视图的左侧或顶部与父视图的左侧或顶部对齐。
 *  SNAP_TO_END使子视图的右侧或底部与父视图的右侧面或底部对齐。
 *  SNAP_TO_ANY根据子视图的当前位置与父布局的关系，决定子视图是否从头到尾跟随。
 *  比如，如果子视图实际位于RecyclerView的左侧，SNAP_TO_ANY和SNAP_TO_START是没有
 *  差别的。
 *  默认值就是SNAP_TO_ANY。
 */
public class TopSmoothScroller extends LinearSmoothScroller {

    public TopSmoothScroller(Context context) {
        super(context);
    }

    @Override
    protected int getHorizontalSnapPreference() {
        return SNAP_TO_START;//具体见源码注释
    }

    @Override
    protected int getVerticalSnapPreference() {
        return SNAP_TO_START;//具体见源码注释
    }

}
