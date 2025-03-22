package it.unibs.fp.planetarium;

public class StarSystem {
    private String name;
    private final Star star;

    public StarSystem(String name, Star star) {
        this.name = name;
        this.star = star;
    }

    public String getName() {
        return name;
    }

    public Star getStar() {
        return star;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMass() {
        return star.getTotalMass();
    }

    public Position getCenterOfMass() {
        int dimentions = star.getPosition().getDimensions();
        Position ret = new Position(dimentions);
        for (int i = 0; i < dimentions; i++) {
            double sommaPesata = 0;
            sommaPesata += star.getPosition().getCoordinate(i) * star.getMass();
            for (int j = 0; j < star.getPlanets().size(); j++) {
                Planet planet = star.getPlanets().get(j);
                sommaPesata += planet.getPosition().getCoordinate(i) * planet.getMass();
                for (int k = 0; k < planet.getMoons().size(); k++) {
                    Moon moon = planet.getMoons().get(k);
                    sommaPesata += moon.getPosition().getCoordinate(i) * moon.getMass();
                }
            }
            ret.setCoordinate(i, sommaPesata/star.getTotalMass());
        }

        return ret;
    }

    public String toString() {
        return name + " - " + star.toString();
    }
}
