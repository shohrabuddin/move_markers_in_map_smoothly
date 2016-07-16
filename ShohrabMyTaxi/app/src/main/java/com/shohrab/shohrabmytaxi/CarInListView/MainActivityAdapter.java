package com.shohrab.shohrabmytaxi.CarInListView;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.shohrab.shohrabmytaxi.Car2GoApi.ApiResponse;
import com.shohrab.shohrabmytaxi.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by shohrab on 5/24/2016.
 */
public class MainActivityAdapter extends ArrayAdapter<ApiResponse.PlaceMarks> {

    private LayoutInflater mInflater;
    private Context mContext;

    public MainActivityAdapter(Context context, List<ApiResponse.PlaceMarks> carList) {
        super(context, 0, carList);
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.fragment_main_list_view_layout, parent, false);
            holder = new ListViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ListViewHolder) convertView.getTag();
        }

        ApiResponse.PlaceMarks repoResponse = getItem(position);

        holder.tVCarName.setText("Name: "+repoResponse.getCarName());
        holder.tVEngineType.setText("Engine Type: "+repoResponse.getEngineType());
        holder.tVAddress.setText("Addr: "+repoResponse.getAddress());


        return convertView;
    }

    static class ListViewHolder {

        @Bind(R.id.fragmentMainListViewLayout_carName)
        TextView tVCarName;

        @Bind(R.id.fragmentMainListViewLayout_engineType)
        TextView tVEngineType;

        @Bind(R.id.fragmentMainListViewLayout_address)
        TextView tVAddress;


        public ListViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
