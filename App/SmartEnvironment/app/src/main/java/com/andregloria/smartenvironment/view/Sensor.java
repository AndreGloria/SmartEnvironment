package com.andregloria.smartenvironment.view;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ANDRE on 27/03/16.
 */
public class Sensor implements Parcelable {

    protected long id;
    protected String sensorName, sensorId, value;
    protected Bitmap imageCover;


    public Sensor(long id, String sensorName, String sensorId, String value) {
        this.id = id;
        this.sensorName = sensorName;
        this.sensorId = sensorId;
        this.value = value;
    }


    protected Sensor(Parcel in) {
        id = in.readLong();
        sensorName = in.readString();
        sensorId = in.readString();
        value = in.readString();
        imageCover = in.readParcelable(Bitmap.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(sensorName);
        dest.writeString(sensorId);
        dest.writeString(value);
        dest.writeParcelable(imageCover, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Sensor> CREATOR = new Creator<Sensor>() {
        @Override
        public Sensor createFromParcel(Parcel in) {
            return new Sensor(in);
        }

        @Override
        public Sensor[] newArray(int size) {
            return new Sensor[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Bitmap getImageCover() {
        return imageCover;
    }

    public void setImageCover(Bitmap imageCover) {
        this.imageCover = imageCover;
    }
}
