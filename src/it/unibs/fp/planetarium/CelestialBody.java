package it.unibs.fp.planetarium;

public abstract class CelestialBody {
    private final String name;
    private final double mass;
    private final Position position;

    public CelestialBody(String name, double mass, Position position) {
        this.name = name;
        this.mass = mass;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public double getMass() {
        return mass;
    }

    public Position getPosition() {
        return position;
    }

    public abstract String toString();
}
