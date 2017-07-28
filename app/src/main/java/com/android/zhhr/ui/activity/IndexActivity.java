package com.android.zhhr.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.zhhr.R;
import com.android.zhhr.data.commons.Constants;
import com.android.zhhr.presenter.IndexPresenter;
import com.android.zhhr.ui.adapter.DetailAdapter;
import com.android.zhhr.ui.adapter.IndexAdapter;
import com.android.zhhr.ui.view.IBaseView;
import com.android.zhhr.ui.view.IIndexView;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by 皓然 on 2017/7/28.
 */

public class IndexActivity extends BaseActivity<IndexPresenter> implements IIndexView {
    private List<String> comic_chapter_titles;
    private DetailAdapter mAdapter;
    @Bind(R.id.rv_index)
    RecyclerView mRecycleView;
    @Bind(R.id.iv_order)
    ImageView mOrder;
    @Override
    protected void initPresenter() {
        mPresenter = new IndexPresenter(this,this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_index;
    }

    @Override
    protected void initView() {
        mAdapter = new DetailAdapter(this,R.layout.item_chapter);
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.updateWithClear(getIntent().getStringArrayListExtra(Constants.COMIC_CHAPER_TITLE));
    }

    @Override
    public void ShowToast(String t) {
        showToast(t);
    }

    @OnClick({R.id.iv_order })
    public void OrderList(ImageView Order) {
        mAdapter.setOrder(!mAdapter.isOrder());
        if(!mAdapter.isOrder()){
            mOrder.setImageResource(R.mipmap.daoxu);
        }else{
            mOrder.setImageResource(R.mipmap.zhengxu);
        }
    }

    @OnClick(R.id.iv_back)
    public void finish(View view){
        this.finish();
    }

}
