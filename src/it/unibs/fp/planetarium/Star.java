package it.unibs.fp.planetarium;

import java.util.Vector;

public class Star extends CelestialBody{

    private final Vector<Planet> planets;
    public Star(String name, double mass, Position position) {
        super(name, mass, position);
        this.planets = new Vector<>();
    }

    public void addPlanet(Planet planet) {
        planets.add(planet);
    }

    public Vector<Planet> getPlanets() {
        return planets;
    }

    public boolean removePlanet(int index) {
        return planets.remove(index) != null;
    }

    public boolean removePlanet(Planet planet) {
        return planets.remove(planet);
    }

    public double getTotalMass() {
        double totalMass = super.getMass();
        for (int i = 0; i < planets.size(); i++) {
            totalMass += planets.get(i).getTotalMass();
        }
        return totalMass;
    }

    @Override
    public String toString() {
        return super.getName() + " (" + super.getMass() + " kg) at " + super.getPosition().toString();
    }
}
