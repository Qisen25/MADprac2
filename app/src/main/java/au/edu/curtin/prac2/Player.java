package au.edu.curtin.prac2;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private static Player play = null;

    public static Player getInstance()
    {
        if(play == null)
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
        this.cash = 0;
        this.equips = new ArrayList<>();
    }

    public int getRowLoc() {
        return rowLoc;
    }

    public void setRowLoc(int rowLoc) {
        this.rowLoc = rowLoc;
        this.health = Math.max(0.0, this.health - 5.0 - (this.equipMass/2.0));
    }

    public int getColLoc() {
        return colLoc;
    }

    public void setColLoc(int colLoc) {
        this.colLoc = colLoc;
        this.health = Math.max(0.0, this.health - 5.0 - (this.equipMass/2.0));
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        if(this.health < 100)
            this.health = health;

        if(this.health > 100)
            this.health = 100;
    }

    public double getEquipMass() {
        return equipMass;
    }

    public void setEquipMass(double equipMass) {
        this.equipMass = equipMass;
    }

    public List<Equipment> getEquips() {
        return equips;
    }

    public void setEquips(List<Equipment> equips) {
        this.equips = equips;
    }

    public void reset()
    {
        this.health = 100;
        this.equips = new ArrayList<>();
        this.equipMass = 0;
        this.cash = 0;
        this.colLoc = 0;
        this.rowLoc = 0;
    }

    public void hardcodeItems()
    {
        this.equips.add(new Equipment("stick", 5, 5));
    }
}
