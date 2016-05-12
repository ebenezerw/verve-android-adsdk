package com.vervewireless.mastersampleapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class SamplesAdapter extends BaseAdapter {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    public List<SampleItem> listOfItems;

    public SamplesAdapter(List<SampleItem> listOfItems) {
        this.listOfItems = listOfItems;
    }

    @Override
    public int getCount() {
        return listOfItems.size();
    }

    @Override
    public SampleItem getItem(int position) {
        return listOfItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return listOfItems.get(position).getTarget() == null ? TYPE_HEADER : TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SampleItem item = listOfItems.get(position);
        boolean isHeader = item.getTarget() == null;

        View view = convertView;
        ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());

            view = inflater.inflate(isHeader ? R.layout.header_item : R.layout.sample_item, null, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.setSampleName(item.getName());

        view.setEnabled(!isHeader);

        return view;
    }

    private class ViewHolder {

        private TextView sampleName;

        public ViewHolder(View v) {
            sampleName = (TextView) v.findViewById(R.id.sampleName);
        }

        public void setSampleName(String name) {
            sampleName.setText(name);
        }
    }
}
