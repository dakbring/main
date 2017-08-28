package com.demo.dakbring.maindemo.screens;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.demo.dakbring.maindemo.MainDeviceAdapter;
import com.demo.dakbring.maindemo.MainDeviceWrapperAdapter;
import com.demo.dakbring.maindemo.R;
import com.demo.dakbring.maindemo.base.BaseActivity;
import com.demo.dakbring.maindemo.base.IFinishable;
import com.demo.dakbring.maindemo.data.ClientDevice;
import com.demo.dakbring.maindemo.wrapper.OnErrorRetryListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardActivity extends BaseActivity implements MainDeviceAdapter.DeviceListListener, OnErrorRetryListener, IFinishable {

    private static final int PAGE_TO_LOAD = 1;
    private static final int PAGE_ITEM_COUNT = 10;
    private static final int LOAD_MORE_DELAY = 1000;

    @BindView(R.id.main_list)
    RecyclerView vRecyclerView;

    MainDeviceAdapter mAdapter;
    MainDeviceWrapperAdapter mWrapperAdapter;
    LinearLayoutManager mLayoutManager;

    private boolean mIsLoadingMore = false;

    private int mVisibleItemCount = 0;

    private int mTotalItemCount = 0;

    private int mPastVisibleItems = 0;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, DashboardActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);

        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new MainDeviceAdapter(this, this);
        mWrapperAdapter = new MainDeviceWrapperAdapter(this, mAdapter);
        vRecyclerView.setLayoutManager(mLayoutManager);
        vRecyclerView.setAdapter(mWrapperAdapter);

        vRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) { //check for scroll down
                    mVisibleItemCount = mLayoutManager.getChildCount();
                    mTotalItemCount = mLayoutManager.getItemCount();
                    mPastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (!mIsLoadingMore) {
                        if ((mVisibleItemCount + mPastVisibleItems) >= mTotalItemCount) {
                            int pageToLoad = PAGE_TO_LOAD;
                            if (pageToLoad > 0) {
                                mIsLoadingMore = true;
                                loadMoreData(PAGE_ITEM_COUNT);
                                if (mAdapter.getItemCount() > 0) {
                                    mWrapperAdapter.showAppendedLoading();
                                    postToHideAppendedLoadingView();
                                }
                            } else {
                                if (mAdapter.getItemCount() > 0) {
                                    mWrapperAdapter.showAppendedLoading();
                                    postToHideAppendedLoadingView();
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //generate dummy data - load data

        mAdapter.setData(generateListData(PAGE_ITEM_COUNT));
    }

    @Override
    public void onRetry() {

    }

    @Override
    public void onIdClick(String id) {
        Toast.makeText(this, "Device Id: " + id, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNameClick(String name) {
        Toast.makeText(this, "Device name: " + name, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, "Item " + position + " clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemDeleteClick(int position) {
        Toast.makeText(this, "Item " + position + " delete clicked", Toast.LENGTH_SHORT).show();
        mAdapter.removeDataAtPosition(position);
    }

    @Override
    public void onItemLongClick(int position) {
        Toast.makeText(this, "Item " + position + " long clicked", Toast.LENGTH_SHORT).show();
    }

    private List<ClientDevice> generateListData(int listSize) {
        List<ClientDevice> list = new ArrayList<>();
        for (int i = 0; i < listSize; i++) {
            list.add(new ClientDevice(i, "Item" + i));
        }
        return list;
    }

    private void loadMoreData(final int listSize) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<ClientDevice> list = new ArrayList<>();
                for (int i = 0; i < listSize; i++) {
                    int index = mAdapter.getItemCount() + i;
                    list.add(new ClientDevice(index, "Item" + index));
                }
                mAdapter.appendData(list);
                mIsLoadingMore = false;
            }
        }, LOAD_MORE_DELAY);
    }

    private void postToHideAppendedLoadingView() {
        vRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mWrapperAdapter.showItemView();
            }
        }, LOAD_MORE_DELAY);
    }
}

