package com.example.a10850.recyclerviewdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/***
 * 创建时间：2019/6/3 
 * 创建人：
 * 功能描述：
 */
class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    //数据源
    private List<String> mList;
    private Context mContext;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }


    public MyAdapter(Context context, List<String> list) {
        this.mList = list;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        viewHolder.mTextView.setText(mList.get(i));

//        ViewGroup.LayoutParams layoutParams = viewHolder.mTextView.getLayoutParams();
//        layoutParams.height = new Random().nextInt(200) + 200;
//        viewHolder.mTextView.setLayoutParams(layoutParams);

        if (mOnItemClickListener != null) {
            viewHolder.mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = viewHolder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(viewHolder.mTextView, pos);
                }
            });

            viewHolder.mTextView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = viewHolder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(viewHolder.mTextView, pos);
                    return false;
                }
            });

        }


    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;
        FrameLayout mFrameLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.text_view);
            mFrameLayout = itemView.findViewById(R.id.fl_view);
        }
    }


    public void addData(int position) {
        mList.add(position, "Insert One");
        notifyItemInserted(position);
    }

    public void removeData(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }
}
