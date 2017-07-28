package com.android.zhhr.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.android.zhhr.R;
import com.android.zhhr.data.commons.Constants;
import com.android.zhhr.data.entity.PreloadChapters;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by 皓然 on 2017/7/20.
 */

public class ChapterViewpagerAdapter extends PagerAdapter {
    private List<String> mdatas;
    private Context mContext;
    private OnceClickListener listener;
    private int Direction = Constants.LEFT_TO_RIGHT;

    public OnceClickListener getListener() {
        return listener;
    }

    public void setListener(OnceClickListener listener) {
        this.listener = listener;
    }


    public ChapterViewpagerAdapter(Context context) {
        mdatas = new ArrayList<>();
        this.mContext = context;
    }

    public int getDirection() {
        return Direction;
    }

    public void setDirection(int direction) {
        Direction = direction;
        this.notifyDataSetChanged();
    }

    public void setDatas(PreloadChapters preloadChapters){
        this.mdatas.clear();
        this.mdatas.addAll(preloadChapters.getPrelist());
        this.mdatas.addAll(preloadChapters.getNowlist());
        this.mdatas.addAll(preloadChapters.getNextlist());
        notifyDataSetChanged();
    }

    public void setNextDatas(List<String> mdatas){
        this.mdatas.addAll(mdatas);
        notifyDataSetChanged();
    }

    public void setPreDatas(List<String> mdatas){
        for(int i=0;i<mdatas.size();i++){
            this.mdatas.add(0,mdatas.get(mdatas.size()-1-i));
        }
        Log.d("mdatas",this.mdatas.toString());
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {//必须实现
        return mdatas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {//必须实现
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {//必须实现，实例化
        PhotoView imageView = new PhotoView(mContext);
        if(Direction == Constants.RIGHT_TO_LEFT){
            Glide.with(mContext)
                    .load(mdatas.get(mdatas.size()-position-1))
                    .placeholder(R.mipmap.pic_default_vertical)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }else{
            Glide.with(mContext)
                    .load(mdatas.get(position))
                    .placeholder(R.mipmap.pic_default_vertical)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }
        imageView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                if(listener!=null){
                    listener.onClick(view,x,y);
                }
            }
        });
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {//必须实现，销毁
        ((ViewPager) container).removeView((View) object);
    }

    public interface OnceClickListener{
        void onClick(View view,float x, float y);
    }

}
