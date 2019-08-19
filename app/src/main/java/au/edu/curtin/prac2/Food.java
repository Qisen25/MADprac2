package au.edu.curtin.prac2;

import android.os.Parcel;
import android.os.Parcelable;

public class Food extends Item implements Parcelable {
    private double health;

    public Food(String desc, int value, double health) {
        super(desc, value);
        this.health = health;
    }

    public Food(Parcel in){
        super(in);
        this.health = in.readDouble();
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel parcel) {
            return new Food(parcel);
        }

        @Override
        public Food[] newArray(int i) {
            return new Food[0];
        }
    };

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public void consume()
    {
        Player p = Player.getInstance();
        p.setHealth(this.health);
    }

    @Override
    public int describeContents() {
        return super.describeContents();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeDouble(health);
    }
}
