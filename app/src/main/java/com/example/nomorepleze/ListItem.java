package com.example.nomorepleze;

import android.os.Parcel;
import android.os.Parcelable;

public class ListItem implements Parcelable {
    private int mImageResource;
    private String mText1;
    private String mText2;

    public ListItem(int imageResource, String text1, String text2)
    {
        mImageResource = imageResource;
        mText1 = text1;
        mText2 = text2;
    }

    protected ListItem(Parcel in) {
        mImageResource = in.readInt();
        mText1 = in.readString();
        mText2 = in.readString();
    }

    public static final Creator<ListItem> CREATOR = new Creator<ListItem>() {
        @Override
        public ListItem createFromParcel(Parcel in) {
            return new ListItem(in);
        }

        @Override
        public ListItem[] newArray(int size) {
            return new ListItem[size];
        }
    };


    public int getmImageResource(){ return mImageResource; }

    public String getmText1(){
        return mText1;
    }

    public String getmText2(){
        return mText2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mImageResource);
        parcel.writeString(mText1);
        parcel.writeString(mText2);
    }
}
