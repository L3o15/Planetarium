package it.unibs.fp.planetarium;

/**
 * Classe astratta che rappresenta un corpo celeste.
 * Ogni corpo celeste ha un nome, una massa e una posizione nello spazio.
 *
 * @author Orizio Leonardo, Brumana Alberto, Loda Samuel
 * @version 1.0
 */
public abstract class CelestialBody {
    private final String name;
    private final double mass;
    private final Position position;

    /**
     * Costruttore della classe CelestialBody.
     *
     * @param name Il nome del corpo celeste.
     * @param mass La massa del corpo celeste.
     * @param position La posizione del corpo celeste.
     */
    public CelestialBody(String name, double mass, Position position) {
        this.name = name;
        this.mass = mass;
        this.position = position;
    }

    /**
     * Restituisce il nome del corpo celeste.
     *
     * @return Il nome del corpo celeste.
     */
    public String getName() {
        return name;
    }

    /**
     * Restituisce la massa del corpo celeste.
     *
     * @return La massa del corpo celeste.
     */
    public double getMass() {
        return mass;
    }

    /**
     * Restituisce la posizione del corpo celeste.
     *
     * @return La posizione del corpo celeste.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Restituisce una rappresentazione testuale del corpo celeste.
     *
     * @return Una stringa che rappresenta il corpo celeste.
     */
    @Override
    public abstract String toString();
}
