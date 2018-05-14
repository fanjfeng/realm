package com.xintu.realm.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xintu.realm.R;


public class ViewHolder extends RecyclerView.ViewHolder {

    public View itemView;

    private SparseArray< View > mViews;

    public ViewHolder ( View itemView ) {
        //   这里传入的就是我们item的布局，注意这个类的实例化地方
        super( itemView );
        this.itemView = itemView;
        this.mViews = new SparseArray< View >();
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText ( int viewId, String text ) {
        TextView view = getView( viewId );
        view.setText( text );
        return this;
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId
     * @return
     */
    @SuppressWarnings ( "unchecked" )
    public < T extends View > T getView ( int viewId ) {
        View view = mViews.get( viewId );
        if ( view == null ) {
            view = itemView.findViewById( viewId );
            mViews.put( viewId, view );
        }
        return (T) view;
    }

    /**
     * 隐藏控件(如ListView中TextView)，不保留空间所占有的空间
     *
     * @param viewId
     * @return
     */
    public ViewHolder setInvisible ( int viewId ) {
        View view = getView( viewId );
        view.setVisibility( View.GONE );
        return this;
    }

    /**
     * 为View设置Tag
     *
     * @param viewId
     * @param tag
     * @return
     */
    public ViewHolder setTag ( int viewId, Object tag ) {
        View view = getView( viewId );
        view.setTag( tag );
        return this;
    }

    /**
     * 为View设置Tag
     *
     * @param viewId
     * @param key
     * @param tag
     * @return
     */
    public ViewHolder setTag ( int viewId, int key, Object tag ) {
        View view = getView( viewId );
        view.setTag( key, tag );
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public ViewHolder setImageResource ( int viewId, int drawableId ) {
        ImageView view = getView( viewId );
        view.setImageResource( drawableId );
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param bm
     * @return
     */
    public ViewHolder setImageBitmap ( int viewId, Bitmap bm ) {
        ImageView view = getView( viewId );
        view.setImageBitmap( bm );
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param defaultDrwId
     * @return
     */
    public ViewHolder setImageByUrl ( int viewId, int defaultDrwId ) {
        return setImageByUrl( viewId, R.mipmap.ic_launcher );
    }
}
