package au.edu.curtin.prac2;

import android.content.Intent;
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

    public void addItem(Item item)
    {
        this.items.add(item);
    }

    public void generateItems()
    {
        for(int i = 0; i < 3; i++)
        {
            int randomNum = (int)(Math.random() * 9 + 1);
            boolean special = false;
            if(randomNum == 1)
            {
                this.items.add(new Equipment("junk", 2, 40));
                this.items.add(new Food("Melon", 25, 40));
            }
            else if(randomNum == 6)
            {
                this.items.add(new Equipment("knife", 20, 2));
                this.items.add(new Food("peanut", 5, 40));
            }
            else if(randomNum == 3)
            {
                this.items.add(new Equipment("Hood", 20, 1));
            }
            else if(randomNum == 5)
            {
                this.items.add(new Equipment("Bucket Helmet", 80, 10));
            }
            else if(randomNum == 4 && isTown())
            {
                this.items.add(new Food("Beer", 7, -1.0));
                this.items.add(new Food("Fried Chicken", 5, 10));
            }
            else if(randomNum == 8 && isTown())
            {
                this.items.add(new Equipment("Steel chestplate", 7, 50.0));
            }
        }
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
