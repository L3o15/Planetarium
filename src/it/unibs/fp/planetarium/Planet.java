package it.unibs.fp.planetarium;

import java.util.ArrayList;

/**
 * Classe che rappresenta un pianeta.
 * Contiene metodi per aggiungere e rimuovere lune, ottenere informazioni sulle lune e sul pianeta stesso.
 *
 * @author Orizio Leonardo, Brumana Alberto, Loda Samuel
 * @version 1.0
 */
public class Planet extends CelestialBody {
    public static final String OPEN_BRACKET = " (";
    public static final String MSG_KG_AT = " kg) at ";
    private final ArrayList<Moon> moons;

    /**
     * Costruttore della classe Planet.
     *
     * @param name Il nome del pianeta.
     * @param mass La massa del pianeta.
     * @param position La posizione del pianeta.
     */
    public Planet(String name, double mass, Position position) {
        super(name, mass, position);
        this.moons = new ArrayList<>();
    }

    /**
     * Aggiunge una luna al pianeta.
     *
     * @param moon La luna da aggiungere.
     * @throws IllegalArgumentException se il nome della luna è già in uso.
     */
    public void addMoon(Moon moon) {
        boolean validName = true;
        for (Moon m : moons) {
            if (m.getName().equals(moon.getName())) {
                validName = false;
                break;
            }
        }

        if (!validName) {
            throw new IllegalArgumentException("Name already in use");
        }

        moons.add(moon);
    }

    /**
     * Restituisce la lista delle lune del pianeta.
     *
     * @return Un ArrayList contenente le lune del pianeta.
     */
    public ArrayList<Moon> getMoons() {
        return moons;
    }

    /**
     * Restituisce una luna specifica in base all'indice fornito.
     *
     * @param index L'indice della luna da restituire.
     * @return La luna corrispondente all'indice fornito.
     */
    public Moon getMoon(int index) {
        return moons.get(index);
    }

    /**
     * Rimuove una luna dal pianeta in base all'indice fornito.
     *
     * @param index L'indice della luna da rimuovere.
     * @return true se la luna è stata rimossa con successo, false altrimenti.
     */
    public boolean removeMoon(int index) {
        return moons.remove(index) != null;
    }

    /**
     * Rimuove una luna specifica dal pianeta.
     *
     * @param moon La luna da rimuovere.
     * @return true se la luna è stata rimossa con successo, false altrimenti.
     */
    public boolean removeMoon(Moon moon) {
        return moons.remove(moon);
    }

    /**
     * Restituisce una rappresentazione testuale del pianeta.
     *
     * @return Una stringa contenente il nome, la massa e la posizione del pianeta.
     */
    @Override
    public String toString() {
        return super.getName() + OPEN_BRACKET + super.getMass() + MSG_KG_AT + super.getPosition().toString();
    }

    /**
     * Calcola la massa totale del pianeta, includendo le sue lune.
     *
     * @return La massa totale del pianeta e delle sue lune.
     */
    public double getTotalMass() {
        double totalMass = super.getMass();
        for (Moon moon : moons) {
            totalMass += moon.getMass();
        }
        return totalMass;
    }
}
