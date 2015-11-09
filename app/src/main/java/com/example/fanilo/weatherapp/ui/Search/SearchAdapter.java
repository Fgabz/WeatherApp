package com.example.fanilo.weatherapp.ui.Search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fanilo.weatherapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by fanilo on 9/16/15.
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>{

    private SearchAdapter.OnClickListner onClickListener;
    private List<String> cityList = new ArrayList<>();
    private LayoutInflater layoutInflater;

    public SearchAdapter(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
    }

    public void setOnClickListener(SearchAdapter.OnClickListner onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                layoutInflater.inflate(R.layout.city_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.cityItem.setText(cityList.get(position));
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    public void remove(int i) {
        cityList.remove(i);
        notifyDataSetChanged();
    }

    public void clear() {
        cityList.clear();
        notifyDataSetChanged();
    }

    public void addData(List<String> data) {
        cityList.clear();
        if (data != null) {
            cityList.addAll(data);
        }
        notifyDataSetChanged();
    }

    public interface OnClickListner {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(cityClickListener);
        }

        @Bind(R.id.city_item) TextView cityItem;

        final View.OnClickListener cityClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v != null) {
                    onClickListener.onItemClick(v, getLayoutPosition());
                }
            }
        };
    }
}
