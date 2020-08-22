package com.example.a10850.recyclerviewdemo;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;
    private MyAdapter mAdapter;
    private List<String> list;
    private LinearLayoutManager mLayout;

    //滑动的标志
    private int TAG_CHECK_SCROLL_UP = -1;
    private int TAG_CHECK_SCROLL_DOWN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        rv = findViewById(R.id.rv);
        //设置adapter
        mAdapter = new MyAdapter(this, list);
        rv.setAdapter(mAdapter);
        //设置适配器
        mLayout = new LinearLayoutManager(this);
        //布局有3种 LinearLayoutManager,GridLayoutManager,StaggeredGridLayoutManager
        rv.setLayoutManager(mLayout);

//        GridLayoutManager layoutManager = new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
//        rv.setLayoutManager(layoutManager);

//        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
//        rv.setLayoutManager(layoutManager);

        //设置Item增加、移除动画
        rv.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
        //1、直接通过decor.setDrawable()来设置2、通过AppTheme添加<item name="android:listDivider">@drawable/divider</item>，两者取其一即可
        DividerItemDecoration decor = new DividerItemDecoration(
                this, DividerItemDecoration.VERTICAL);
//        decor.setDrawable(getResources().getDrawable(R.drawable.divider));
//        rv.addItemDecoration(decor);

        mAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this, ((TextView) view).getText() + " click", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(MainActivity.this, position + " Long click", Toast.LENGTH_SHORT).show();
                mAdapter.removeData(position);
                mAdapter.notifyDataSetChanged();
            }
        });

//        DividerGridItemDecoration decoration=new DividerGridItemDecoration(this);
        DividerTestDecoration decoration = new DividerTestDecoration(this, 0);
//        rv.addItemDecoration(decoration);

        //了解滑动机制
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.i("scroll", "-----------onScrollStateChanged-----------");
                Log.i("scroll", "newState: " + newState);

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.i("scroll", "-----------onScrolled-----------");
                Log.i("scroll", "dx: " + dx);
                Log.i("scroll", "dy: " + dy);
                Log.i("scroll", "CHECK_SCROLL_UP: " + recyclerView.canScrollVertically(TAG_CHECK_SCROLL_UP));
                Log.i("scroll", "CHECK_SCROLL_DOWN: " + recyclerView.canScrollVertically(TAG_CHECK_SCROLL_DOWN));
            }
        });
    }

    private void initData() {
        list = new ArrayList<>();
        for (int i = 0; i <= 120; i++) {
            list.add("Item " + i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                mAdapter.addData(1);
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.delete:
                mAdapter.removeData(1);
                mAdapter.notifyDataSetChanged();
                break;

            case R.id.rvmenu1:
                //从上往下（14-100），100停在最后一个完全可见位置。从下往上（120-100），100停在第一个
                mLayout.scrollToPosition(100);//同rv.scrollToPosition();
                break;
            case R.id.rvmenu2:
                //从上往下（14-100），100停在第一个完全可见位置。从下往上（120-100），100同样效果
                mLayout.scrollToPositionWithOffset(100, -50);
                break;
            case R.id.rvmenu3:
                //从上往下（14-100），100停在最后一个完全可见位置。从下往上（120-100），100停在第一个
                //右滑动效果
                RecyclerView.State state = new RecyclerView.State();
                mLayout.smoothScrollToPosition(rv, state, 100);
                break;
            case R.id.rvmenu4:
                MoveToPosition(mLayout, 100);
                break;
            case R.id.rvmenu5:
                //不论从上往下（14-100），100停在第一个位置。从下往上（120-100），100同样的效果
                LinearSmoothScroller s = new TopSmoothScroller(this);
                s.setTargetPosition(100);
                mLayout.startSmoothScroll(s);
                break;
        }
        return true;
    }

    /**
     * RecyclerView 移动到当前位置，
     *
     * @param manager 设置RecyclerView对应的manager
     * @param n       要跳转的位置
     */
    public static void MoveToPosition(LinearLayoutManager manager, int n) {
        manager.scrollToPositionWithOffset(n, 0);
        manager.setStackFromEnd(true);
    }
}
