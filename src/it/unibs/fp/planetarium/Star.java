package it.unibs.fp.planetarium;

import java.util.ArrayList;

/**
 * Classe che rappresenta una stella.
 * Contiene metodi per gestire i pianeti in orbita e calcolare la massa totale del sistema.
 *
 * @author Orizio Leonardo, Brumana Alberto, Loda Samuel
 * @version 1.0
 */
public class Star extends CelestialBody {
    public static final String MSG_NAME_ALREADY_IN_USE = "Name already in use";
    public static final String OPEN_BRACKET = " (";
    public static final String MSG_KG_AT = " kg) at ";
    private final ArrayList<Planet> planets;

    /**
     * Costruttore della classe Star.
     *
     * @param name Il nome della stella.
     * @param mass La massa della stella.
     * @param position La posizione della stella.
     */
    public Star(String name, double mass, Position position) {
        super(name, mass, position);
        this.planets = new ArrayList<>();
    }

    /**
     * Aggiunge un pianeta alla stella.
     *
     * @param planet Il pianeta da aggiungere.
     * @throws IllegalArgumentException se il nome del pianeta o di una sua luna è già in uso.
     */
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

    /**
     * Restituisce la lista dei pianeti in orbita attorno alla stella.
     *
     * @return Un ArrayList contenente i pianeti della stella.
     */
    public ArrayList<Planet> getPlanets() {
        return planets;
    }

    /**
     * Restituisce un pianeta specifico in base all'indice fornito.
     *
     * @param index L'indice del pianeta da restituire.
     * @return Il pianeta corrispondente all'indice fornito.
     */
    public Planet getPlanet(int index) {
        return planets.get(index);
    }

    /**
     * Rimuove un pianeta dalla stella in base all'indice fornito.
     *
     * @param index L'indice del pianeta da rimuovere.
     * @return true se il pianeta è stato rimosso con successo, false altrimenti.
     */
    public boolean removePlanet(int index) {
        return planets.remove(index) != null;
    }

    /**
     * Rimuove un pianeta specifico dalla stella.
     *
     * @param planet Il pianeta da rimuovere.
     * @return true se il pianeta è stato rimosso con successo, false altrimenti.
     */
    public boolean removePlanet(Planet planet) {
        return planets.remove(planet);
    }

    /**
     * Calcola la massa totale del sistema, includendo la stella e tutti i pianeti e le loro lune.
     *
     * @return La massa totale del sistema.
     */
    public double getTotalMass() {
        double totalMass = super.getMass();

        for (int i = 0; i < planets.size(); i++) {
            totalMass += planets.get(i).getTotalMass();
        }

        return totalMass;
    }

    /**
     * Restituisce una rappresentazione testuale della stella.
     *
     * @return Una stringa contenente il nome, la massa e la posizione della stella.
     */
    @Override
    public String toString() {
        return super.getName() + OPEN_BRACKET + super.getMass() + MSG_KG_AT + super.getPosition().toString();
    }
}
