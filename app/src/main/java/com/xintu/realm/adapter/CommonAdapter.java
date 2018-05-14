package com.xintu.realm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用适配器
 * Created by Administrator on 2016/3/31.
 */
public abstract class CommonAdapter < T > extends RecyclerView.Adapter< ViewHolder > {

    //    上下文
    private Context mContext;


    private LayoutInflater mInflater;

    private List< T > mDatas = new ArrayList<>();

    private int mItemLayoutId;

    private int mPosition;


    public CommonAdapter ( Context mContext, List< T > mDatas, int itemLayoutId ) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        this.mItemLayoutId = itemLayoutId;
        this.mInflater = LayoutInflater.from( mContext );
    }

    //    实例化一个ViewHolder对象 ,并传入我们的item布局
    @Override
    public ViewHolder onCreateViewHolder ( ViewGroup viewGroup, int position ) {
        this.mPosition = position;
        View view = mInflater.inflate( mItemLayoutId, viewGroup, false );
        return new ViewHolder( view );
    }

    //    绑定viewholder的item里面控件的值，也可以对item设置点击事件，因为有postion参数
    @Override
    public void onBindViewHolder ( final ViewHolder mViewHolder, final int position ) {
        this.mPosition = position;
        T t = mDatas.get( position );
        convert( mViewHolder, t );
        // 设置回调，看需求，设置点击和长按点击事件,左滑事件暂时未设置
        if ( mOnItemClickListener != null ) {
            mViewHolder.itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick ( View v ) {
                            int pos = mViewHolder.getLayoutPosition();
                            mOnItemClickListener.onItemClickListener( mViewHolder.itemView, pos );
                        }
                    } );
        }
        if ( mOnItemLongClickListener != null ) {
            mViewHolder.itemView.setOnLongClickListener(
                    new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick ( View v ) {
                            int pos = mViewHolder.getLayoutPosition();
                            mOnItemLongClickListener.onItemLongClickListener( mViewHolder.itemView, pos );
                            return false;
                        }
                    } );
        }

        if ( mOnItemSingleAndLongClickListener != null ) {
            mViewHolder.itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick ( View v ) {
                            int pos = mViewHolder.getLayoutPosition();
                            mOnItemSingleAndLongClickListener.onItemClickListener( mViewHolder.itemView, pos );
                        }
                    } );
            mViewHolder.itemView.setOnLongClickListener(
                    new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick ( View v ) {
                            int pos = mViewHolder.getLayoutPosition();
                            mOnItemSingleAndLongClickListener.onItemLongClickListener( mViewHolder.itemView, pos );
                            return false;
                        }
                    } );
        }

    }

    /**
     * 添加一项新数据到position为1的位置上
     *
     * @param t
     */
    public void addItem ( T t ) {
        addAssignItem( t, 1 );
    }

    /**
     * 指定位置上添加一项
     *
     * @param t
     * @param position
     */
    public void addAssignItem ( T t, int position ) {
        mDatas.add( position, t );
        notifyItemInserted( position );
    }

    /**
     * 刷新一项数据
     *
     * @param position
     */
    public void changeItem ( int position ) {
        notifyItemChanged( position );
    }


    /**
     * 删除某一项
     *
     * @param position
     */
    public void removeItem ( int position ) {
        mDatas.remove( position );
        notifyItemRemoved( position );
    }

    /**
     * 执行次数
     *
     * @return
     */
    @Override
    public int getItemCount () {
        return mDatas.size();
    }

    /**
     * 获取某项数据
     *
     * @param position
     * @return
     */
    public T getItem ( int position ) {
        return mDatas.get( position );
    }


    /**
     * 子类的布局跟值对应
     *
     * @param helper
     * @param item
     */
    public abstract void convert ( ViewHolder helper, T item );


    /**
     * single together
     */
    public interface OnItemClickListener {

        void onItemClickListener ( View view, int position );
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickLitener ( OnItemClickListener mOnItemClickListener ) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    /**
     * long together
     */
    public interface OnItemLongClickListener {

        void onItemLongClickListener ( View view, int position );
    }

    private OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemLongClickLitener ( OnItemLongClickListener mOnItemLongClickListener ) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }

    /**
     * single and long together
     */
    public interface OnItemSingleAndLongClickListener {

        void onItemClickListener ( View view, int position );

        void onItemLongClickListener ( View view, int position );
    }

    private OnItemSingleAndLongClickListener mOnItemSingleAndLongClickListener;

    public void setOnItemSingleAndLongClickLitener ( OnItemSingleAndLongClickListener mOnItemSingleAndLongClickListener ) {
        this.mOnItemSingleAndLongClickListener = mOnItemSingleAndLongClickListener;
    }

}
