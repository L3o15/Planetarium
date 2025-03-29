package it.unibs.fp.planetarium;

import java.util.ArrayList;

public class Position {
    private final ArrayList<Double> coordinates;
    private final int dimensions;

    public Position(int dimensions) {
        this.dimensions = dimensions;
        this.coordinates = new ArrayList<>();
    }

    public int getDimensions() {
        return dimensions;
    }

    public double getCoordinate(int index) {
        return coordinates.get(index);
    }

    public void setCoordinate(int index, double value) {
        coordinates.add(index, value);
    }

    public double distance(Position other) {
        double sum = 0;

        for (int i = 0; i < dimensions; i++) {
            sum += Math.pow(coordinates.get(i) - other.getCoordinate(i), 2);
        }
        return Math.sqrt(sum);
    }

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Position position = (Position) obj;
        return dimensions == position.dimensions && coordinates.equals(position.coordinates);
    }
}
