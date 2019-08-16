package au.edu.curtin.prac2;

import java.util.ArrayList;
import java.util.List;

public class Area {
    private boolean town;
    private List<Item> items;

    public Area(boolean town) {
        this.town = town;
        this.items = new ArrayList<>();
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
}
