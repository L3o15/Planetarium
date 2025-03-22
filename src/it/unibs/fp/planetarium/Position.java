package it.unibs.fp.planetarium;

public class Position {
    private final double[] coordinates;
    private final int dimensions;

    public Position(int dimensions) {
        this.dimensions = dimensions;
        this.coordinates = new double[dimensions];
    }

    public int getDimensions() {
        return dimensions;
    }

    public double getCoordinate(int index) {
        return coordinates[index];
    }

    public void setCoordinate(int index, double value) {
        coordinates[index] = value;
    }

    public double distance(Position other) {
        double sum = 0;
        for (int i = 0; i < dimensions; i++) {
            sum += Math.pow(coordinates[i] - other.getCoordinate(i), 2);
        }
        return Math.sqrt(sum);
    }

    public String toString() {
        StringBuilder result = new StringBuilder("(");
        for (int i = 0; i < dimensions; i++) {
            result.append(coordinates[i]);
            if (i < dimensions - 1) {
                result.append(", ");
            }
        }
        result.append(")");
        return result.toString();
    }
}
