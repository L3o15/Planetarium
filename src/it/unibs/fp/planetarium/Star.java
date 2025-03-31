package it.unibs.fp.planetarium;

import java.util.ArrayList;

public class Star extends CelestialBody {
    public static final String MSG_NAME_ALREADY_IN_USE = "Name already in use";
    public static final String OPEN_BRACKET = " (";
    public static final String MSG_KG_AT = " kg) at ";
    private final ArrayList<Planet> planets;

    public Star(String name, double mass, Position position) {
        super(name, mass, position);
        this.planets = new ArrayList<>();
    }

    public void addPlanet(Planet planet) {
        boolean validName = true;
        for (Planet p : planets) {
            if (p.getName().equals(planet.getName())) {
                validName = false;
                break;
            }
            for (Moon m : p.getMoons()) {
                if (m.getName().equals(planet.getName())) {
                    validName = false;
                    break;
                }
            }
        }

        if (!validName) {
            throw new IllegalArgumentException(MSG_NAME_ALREADY_IN_USE);
        }

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

        for (int i = 0; i < planets.size(); i++) {
            totalMass += planets.get(i).getTotalMass();
        }

        return totalMass;
    }

    @Override
    public String toString() {
        return super.getName() + OPEN_BRACKET + super.getMass() + MSG_KG_AT + super.getPosition().toString();
    }
}
