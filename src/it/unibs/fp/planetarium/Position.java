package it.unibs.fp.planetarium;

import java.util.ArrayList;

/**
 * Classe che rappresenta una posizione nello spazio n-dimensionale.
 * Contiene metodi per gestire le coordinate e calcolare distanze tra posizioni.
 *
 * @author Orizio Leonardo, Brumana Alberto, Loda Samuel
 * @version 1.0
 */
public class Position {
    private final ArrayList<Double> coordinates;
    private final int dimensions;

    /**
     * Costruttore della classe Position.
     *
     * @param dimensions Il numero di dimensioni della posizione.
     */
    public Position(int dimensions) {
        this.dimensions = dimensions;
        this.coordinates = new ArrayList<>();
    }

    /**
     * Restituisce il numero di dimensioni della posizione.
     *
     * @return Il numero di dimensioni.
     */
    public int getDimensions() {
        return dimensions;
    }

    /**
     * Restituisce la coordinata in una data dimensione.
     *
     * @param index L'indice della coordinata da ottenere.
     * @return Il valore della coordinata richiesta.
     */
    public double getCoordinate(int index) {
        return coordinates.get(index);
    }

    /**
     * Imposta una coordinata in una data dimensione.
     *
     * @param index L'indice della coordinata da impostare.
     * @param value Il valore della coordinata.
     */
    public void setCoordinate(int index, double value) {
        coordinates.add(index, value);
    }

    /**
     * Calcola la distanza euclidea tra questa posizione e un'altra.
     *
     * @param other L'altra posizione da confrontare.
     * @return La distanza euclidea tra le due posizioni.
     */
    public double distance(Position other) {
        double sum = 0;
        for (int i = 0; i < dimensions; i++) {
            sum += Math.pow(coordinates.get(i) - other.getCoordinate(i), 2);
        }
        return Math.sqrt(sum);
    }

    /**
     * Restituisce una rappresentazione testuale della posizione.
     *
     * @return Una stringa rappresentante le coordinate della posizione.
     */
    public String toString() {
        StringBuilder result = new StringBuilder("(");
        for (int i = 0; i < dimensions; i++) {
            result.append(coordinates.get(i));
            if (i < dimensions - 1) {
                result.append(", ");
            }
        }
        result.append(")");
        return result.toString();
    }

    /**
     * Confronta questa posizione con un altro oggetto per verificare l'uguaglianza.
     *
     * @param obj L'oggetto da confrontare.
     * @return true se le posizioni sono uguali, false altrimenti.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Position position = (Position) obj;
        return dimensions == position.dimensions && coordinates.equals(position.coordinates);
    }
}