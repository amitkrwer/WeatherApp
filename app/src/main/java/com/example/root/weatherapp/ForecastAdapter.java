package com.example.root.weatherapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastAdapterViewHolder> {

    private ForecastAdapterOnClickHandler mClickHandler;
    private String[] mWeatherData;

    public ForecastAdapter() {


    }

    public ForecastAdapter(ForecastAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public ForecastAdapter.ForecastAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.forecast_list_item;

        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new ForecastAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastAdapter.ForecastAdapterViewHolder forecastAdapterViewHolder, int position) {
        String weatherForThisDay = mWeatherData[position];
        forecastAdapterViewHolder.mWeatherTextView.setText(weatherForThisDay);
    }

    @Override
    public int getItemCount() {
        if (null == mWeatherData) return 0;
        return mWeatherData.length;
    }

    public void setWeatherData(String[] weatherData) {
        mWeatherData = weatherData;
        notifyDataSetChanged();
    }

    public interface ForecastAdapterOnClickHandler {
        void onClick(String weatherForDay);
    }

    public class ForecastAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView mWeatherTextView;

        public ForecastAdapterViewHolder(View itemView) {
            super(itemView);
            mWeatherTextView = itemView.findViewById(R.id.tv_weather_data);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            String weatherForDay = mWeatherData[adapterPosition];
            mClickHandler.onClick(weatherForDay);
        }
    }


}
