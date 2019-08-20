package au.edu.curtin.prac2;

import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Player
{

    private static Player play = null;

    public static Player getInstance()
    {
        if (play == null)
        {
            play = new Player();
        }

        return play;
    }


    private int rowLoc;
    private int colLoc;
    private int cash;
    private double health;
    private double equipMass;
    private List<Equipment> equips;

    private Player()
    {
        this.health = 100;
        this.rowLoc = 3;
        this.colLoc = 3;
        this.cash = 500;
        this.equips = new ArrayList<>();
        equipMass = 0.0;
    }

    public int getRowLoc()
    {
        return rowLoc;
    }

    public void setRowLoc(int rowLoc)
    {
        this.rowLoc = rowLoc;
        this.health = Math.max(0.0, this.health - 5.0 - (this.equipMass / 2.0));
    }

    public int getColLoc()
    {
        return colLoc;
    }

    public void setColLoc(int colLoc)
    {
        this.colLoc = colLoc;
        this.health = Math.max(0.0, this.health - 5.0 - (this.equipMass / 2.0));
    }

    public int getCash()
    {
        return cash;
    }

    public void setCash(int cash)
    {
        this.cash = cash;
    }

    public double getHealth()
    {
        return health;
    }

    public void setHealth(double heal)
    {
        if ((this.health + heal) < 100)
        {
            this.health += heal;
        }
        else
        {
            this.health = 100;
        }
    }

    public double getEquipMass()
    {
        return equipMass;
    }

    public void setEquipMass(double equipMass)
    {
        this.equipMass = equipMass;
    }

    public List<Equipment> getEquips()
    {
        return equips;
    }

    public void setEquips(List<Equipment> equips)
    {
        this.equips = equips;
    }

    public void reset()
    {
        this.health = 100;
        this.rowLoc = 3;
        this.colLoc = 3;
        this.cash = 500;
        this.equips = new ArrayList<>();
        equipMass = 0.0;
        this.hardcodeItems();
    }

    public void hardcodeItems()
    {
        this.addItem(new Equipment("stick", 5, 5));
        this.addItem(new Equipment("book", 50, 2));
    }

    public void addItem(Equipment item)
    {
        this.equips.add(item);
        this.equipMass += item.getMass();
    }

    public void removeItem(Equipment item)
    {
        this.equips.remove(item);
        this.equipMass -= item.getMass();
    }
}
