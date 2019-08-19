package au.edu.curtin.prac2;

import android.os.Parcel;
import android.os.Parcelable;

public class Equipment extends Item implements Parcelable {
    private double mass;

    public Equipment(String desc, int value, double mass) {
        super(desc, value);
        this.mass = mass;
    }

    public Equipment(Parcel in){
        super(in);
        this.mass = in.readDouble();
    }

    public final static Creator<Equipment> CREATOR = new Creator<Equipment>() {
        @Override
        public Equipment createFromParcel(Parcel parcel) {
            return new Equipment(parcel);
        }

        @Override
        public Equipment[] newArray(int i) {
            return new Equipment[0];
        }
    };

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    @Override
    public int describeContents() {
        return super.describeContents();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeDouble(mass);
    }
}
