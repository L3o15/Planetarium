package it.unibs.fp.planetarium;

import java.util.Vector;

public class Planet extends CelestialBody{
    private final Vector<Moon> moons;
    public Planet(String name, double mass, Position position) {
        super(name, mass, position);
        this.moons = new Vector<>();
    }

    public void addMoon(Moon moon) {
        moons.add(moon);
    }

    public Vector<Moon> getMoons() {
        return moons;
    }

    public Moon getMoon(int index) {
        return moons.get(index);
    }

    public boolean removeMoon(int index) {
        return moons.remove(index) != null;
    }

    public boolean removeMoon(Moon moon) {
        return moons.remove(moon);
    }

    @Override
    public String toString() {
        return null;
    }

    public double getTotalMass() {
        double totalMass = super.getMass();
        for (int i = 0; i < moons.size(); i++) {
            System.out.println(moons.get(i));
            totalMass += moons.get(i).getMass();
        }
        return totalMass;
    }
}
