package au.edu.curtin.prac2;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Area implements Parcelable{
    private boolean town;
    private List<Item> items;

    public static final Creator<Area> CREATOR = new Creator<Area>() {
        @Override
        public Area createFromParcel(Parcel parcel) {
            return new Area(parcel);
        }

        @Override
        public Area[] newArray(int i) {

            return new Area[i];
        }
    };

    public Area(boolean town) {
        this.town = town;
        this.items = new ArrayList<>();
    }

    public Area(Parcel in){
        this.town = in.readInt() == 1;
        this.items = in.readArrayList(Item.class.getClassLoader());
    }

    public boolean isTown() {
        return town;
    }

    public void setTown(boolean town) {
        this.town = town;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void generateItems()
    {
        this.items.add(new Equipment("junk", 500, 40));
        this.items.add(new Food("Melon", 25, 40));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(town ? 1 : 0);
        parcel.writeList(items);
    }
}
