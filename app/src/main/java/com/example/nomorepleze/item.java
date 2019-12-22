package com.example.nomorepleze;

import android.os.Parcel;
import android.os.Parcelable;

public class item implements Parcelable {
    public String mImageURI;
    public String Name;
    public double Price;

    public item() {}
    public item(String imageURI,String name, double price)
    {
        mImageURI = imageURI;
        Name = name;
        Price =price;
    }

    protected item(Parcel in) {
        mImageURI = in.readString();
        Name = in.readString();
        Price = in.readDouble();
    }

    public static final Creator<item> CREATOR = new Creator<item>() {
        @Override
        public item createFromParcel(Parcel in) {
            return new item(in);
        }

        @Override
        public item[] newArray(int size) {
            return new item[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mImageURI);
        parcel.writeString(Name);
        parcel.writeDouble(Price);
    }
}
