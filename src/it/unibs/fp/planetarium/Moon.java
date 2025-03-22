package it.unibs.fp.planetarium;

public class Moon extends CelestialBody{
    public Moon(String name, double mass, Position position) {
        super(name, mass, position);
    }

    @Override
    public String toString() {
        return super.getName() + " (" + super.getMass() + " kg) at " + super.getPosition().toString();
    }
}
