package it.unibs.fp.planetarium;

/**
 * Classe che rappresenta una luna.
 * Contiene le informazioni relative al nome, alla massa e alla posizione della luna.
 *
 * @author Orizio Leonardo, Brumana Alberto, Loda Samuel
 * @version 1.0
 */

public class Moon extends CelestialBody {

    /**
     * Costruttore della classe Moon. Inizializza le variabili nome, massa e posizione.
     */
    public Moon(String name, double mass, Position position) {
        super(name, mass, position);
    }

    /**
     * Restituisce una rappresentazione testuale della luna.
     *
     * @return Una stringa che rappresenta la luna.
     */
    @Override
    public String toString() {
        return super.getName() + " (" + super.getMass() + " kg) at " + super.getPosition().toString();
    }
}
