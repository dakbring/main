package com.demo.dakbring.maindemo;


import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.demo.dakbring.maindemo.data.ClientDevice;
import com.demo.dakbring.maindemo.widget.ColorChangeableIcon;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainDeviceAdapter extends RecyclerView.Adapter<MainDeviceAdapter.DeviceViewHolder> {

    private List<ClientDevice> mList;

    private WeakReference<DeviceListListener> mItemWeakReference;

    private WeakReference<Activity> mActivityWeakReference;

    public MainDeviceAdapter(Activity activity, DeviceListListener listener) {
        mItemWeakReference = new WeakReference<>(listener);
        mActivityWeakReference = new WeakReference<>(activity);
    }

    public void setData(List<ClientDevice> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public void appendData(List<ClientDevice> list) {
        int start = mList.size() - 1;
        int end = start + mList.size();
        mList.addAll(list);
        notifyItemRangeChanged(start, end);
    }

    public void removeDataAtPosition(int position){
        mList.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public DeviceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_device, parent, false);
        final DeviceViewHolder holder = new DeviceViewHolder(mActivityWeakReference, view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemWeakReference != null) {
                    mItemWeakReference.get().onItemClick(holder.mPosition);
                }
            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mItemWeakReference != null) {
                    mItemWeakReference.get().onItemLongClick(holder.mPosition);
                }
                return true;
            }
        });

        holder.vId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemWeakReference != null) {
                    mItemWeakReference.get().onIdClick(String.valueOf(holder.mData.getId()));
                }
            }
        });

        holder.vName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemWeakReference != null) {
                    mItemWeakReference.get().onNameClick(holder.mData.getName());
                }
            }
        });

        holder.vRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemWeakReference != null) {
                    mItemWeakReference.get().onItemDeleteClick(holder.mPosition);
                }
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(DeviceViewHolder holder, int position) {
        ClientDevice data = mList.get(position);

        if (data != null) {
            holder.renderData(data, position);
        }
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    static class DeviceViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.device_id)
        TextView vId;

        @BindView(R.id.device_name)
        TextView vName;

        @BindView(R.id.device_remove)
        ColorChangeableIcon vRemove;

        @BindView(R.id.device_container)
        SwipeLayout vContainer;

        private ClientDevice mData;

        private int mPosition;

        private WeakReference<Activity> mActivityWeakReference;

        DeviceViewHolder(WeakReference<Activity> activityWeakReference, View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mActivityWeakReference = activityWeakReference;

            vContainer.setLeftSwipeEnabled(false);
            vContainer.addDrag(SwipeLayout.DragEdge.Left, vRemove);
            vContainer.setRightSwipeEnabled(true);
        }

        void renderData(ClientDevice data, int position) {
            if (data != null) {
                mData = data;
                mPosition = position;

                vId.setText(String.valueOf(data.getId()));
                vName.setText(data.getName());
            }
        }
    }

    public interface DeviceListListener {

        void onIdClick(String id);

        void onNameClick(String name);

        void onItemClick(int position);

        void onItemDeleteClick(int position);

        void onItemLongClick(int position);
    }
}
