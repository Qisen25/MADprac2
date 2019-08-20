package au.edu.curtin.prac2;

import android.os.Parcel;
import android.os.Parcelable;

public abstract class Item implements Parcelable{
    private String desc;
    private int value;

    public Item(String desc, int value) {
        this.desc = desc;
        this.value = value;
    }

    public Item(Parcel in){
        this.desc = in.readString();
        this.value = in.readInt();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(desc);
        parcel.writeInt(value);
    }

    public abstract String displayStat();
}
