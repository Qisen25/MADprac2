package au.edu.curtin.prac2;

public class Food extends Item {
    private double health;

    public Food(String desc, int value, double health) {
        super(desc, value);
        this.health = health;
    }

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
}
