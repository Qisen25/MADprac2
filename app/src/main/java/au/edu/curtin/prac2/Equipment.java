package au.edu.curtin.prac2;

public class Equipment extends Item {
    private double mass;

    public Equipment(String desc, int value, double mass) {
        super(desc, value);
        this.mass = mass;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }
}
