package com.andregloria.smartenvironment.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.andregloria.smartenvironment.R;

import java.util.List;

/**
 * Created by ANDRE on 02/09/16.
 */
public class SliderAdapterGrid extends ArrayAdapter {

    protected List<Sensor> sensorList;
    private LayoutInflater mInflater;
    private Context context;

    public SliderAdapterGrid(Context context, List<Sensor> movieList) {
        super(context,0, movieList);
        mInflater = LayoutInflater.from(context);
        this.context=context;
        this.sensorList =movieList;
        System.out.println(sensorList.size());
    }

    @Override
    public int getCount() {
        return sensorList.size();
    }

    @Override
    public Sensor getItem(int position) {
        return sensorList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null || convertView.getTag() == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.fragment_grid_sensor_item, parent, false);

            holder.name = (TextView) convertView.findViewById(R.id.etSliderID);
            holder.value = (TextView) convertView.findViewById(R.id.etSliderValue);
            holder.cover = (ImageView) convertView.findViewById(R.id.iv_slider_img);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Sensor sensor = sensorList.get(position);
        holder.name.setText(sensor.getSensorName());
        holder.value.setText(sensor.getValue());

        if(sensor.getImageCover()==null) {
            //new DownloadImageTask(holder.cover,sensor).execute(sensor.getCover());
        }
        else {
            //holder.cover.setImageBitmap(sensor.getImageCover());
        }
        return convertView;
    }

    private static class ViewHolder {
        ImageView cover;
        TextView name;
        TextView value;
    }

    public List<Sensor> getSensorList() {
        return sensorList;
    }
}
