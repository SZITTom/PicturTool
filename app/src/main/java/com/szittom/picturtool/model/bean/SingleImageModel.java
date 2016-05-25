package com.szittom.picturtool.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * @author: zzp
 * @since: 2015-06-16
 */
public class SingleImageModel implements Parcelable {

    public String path;
    public boolean isPicked;
    public long date;
    public long id;

    public SingleImageModel(String path, boolean isPicked, long date, long id) {
        this.path = path;
        this.isPicked = isPicked;
        this.date = date;
        this.id = id;
    }

    public SingleImageModel() {

    }

    public boolean isThisImage(String path) {
        return this.path.equalsIgnoreCase(path);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.path);
        dest.writeByte(this.isPicked ? (byte) 1 : (byte) 0);
        dest.writeLong(this.date);
        dest.writeLong(this.id);
    }

    protected SingleImageModel(Parcel in) {
        this.path = in.readString();
        this.isPicked = in.readByte() != 0;
        this.date = in.readLong();
        this.id = in.readLong();
    }

    public static final Creator<SingleImageModel> CREATOR = new Creator<SingleImageModel>() {
        @Override
        public SingleImageModel createFromParcel(Parcel source) {
            return new SingleImageModel(source);
        }

        @Override
        public SingleImageModel[] newArray(int size) {
            return new SingleImageModel[size];
        }
    };
}
