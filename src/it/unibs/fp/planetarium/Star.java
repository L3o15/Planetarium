package it.unibs.fp.planetarium;

import java.util.ArrayList;

public class Star extends CelestialBody {
    private final ArrayList<Planet> planets;

    public Star(String name, double mass, Position position) {
        super(name, mass, position);
        this.planets = new ArrayList<>();
    }

    public void addPlanet(Planet planet) {
        planets.add(planet);
    }

    public ArrayList<Planet> getPlanets() {
        return planets;
    }

    public Planet getPlanet(int index) {
        return planets.get(index);
    }

    public boolean removePlanet(int index) {
        return planets.remove(index) != null;
    }

    public boolean removePlanet(Planet planet) {
        return planets.remove(planet);
    }

    public double getTotalMass() {
        double totalMass = super.getMass();
        System.out.println(planets.size());
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
