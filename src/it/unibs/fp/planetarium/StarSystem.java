package it.unibs.fp.planetarium;

public class StarSystem {
    public static final String MINUS_SIGN = " - ";
    public static final String NEXT = ">";
    public static final String MSG_CORP_NOT_FOUND = "Corp not found";
    public static final String MSG_STAR_SYSTEM_ALREADY_HAS_A_STAR = "This star system already has a star";
    public static final String MSG_CANNOT_ADD_A_MOON_TO_A_STAR = "Cannot add a moon to a star";
    public static final String MSG_CANNOT_ADD_PLANET_TO_PLANET = "Cannot add a planet to a planet";
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
        int dimensions = star.getPosition().getDimensions();
        Position ret = new Position(dimensions);

        for (int i = 0; i < dimensions; i++) {
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
        return name + MINUS_SIGN + star.toString();
    }

    public String getPath(String search) {
        if (star.getName().equals(search)) {
            return name + NEXT + star.getName();
        }

        for (int i = 0; i < star.getPlanets().size(); i++) {
            if (star.getPlanets().get(i).getName().equals(search)) {
                return name + NEXT + star.getName() + NEXT + star.getPlanets().get(i).getName();
            }
        }

        for (int j = 0; j < star.getPlanets().size(); j++){
            for (int k = 0; k < star.getPlanets().get(j).getMoons().size(); k++) {
                if (star.getPlanets().get(j).getMoons().get(k).getName().equals(search)) {
                    return name + NEXT + star.getName() + NEXT + star.getPlanets().get(j).getName() + NEXT + star.getPlanets().get(j).getMoons().get(k).getName();
                }
            }
        }
        return MSG_CORP_NOT_FOUND;
    }

    public boolean addCorp(CelestialBody body, String connectedTo) {
        if (connectedTo.equals(name)){
            throw new RuntimeException(MSG_STAR_SYSTEM_ALREADY_HAS_A_STAR);
        }
        if (connectedTo.equals(star.getName())) {
            if (body instanceof Planet) {
                star.addPlanet((Planet) body);
                return true;
            }
            if (body instanceof Moon) {
                throw new RuntimeException(MSG_CANNOT_ADD_A_MOON_TO_A_STAR);
            }
        }
        for (int i = 0; i < star.getPlanets().size(); i++) {
            if (connectedTo.equals(star.getPlanets().get(i).getName())) {
                if (body instanceof Moon) {
                    star.getPlanets().get(i).addMoon((Moon) body);
                    return true;
                }
                if (body instanceof Planet) {
                    throw new RuntimeException(MSG_CANNOT_ADD_PLANET_TO_PLANET);
                }
            }
        }
        return false;
    }

    public CelestialBody getCorp(String name) {
        if (star.getName().equals(name)) {
            return star;
        } else {
            for (int i = 0; i < star.getPlanets().size(); i++) {
                if (star.getPlanets().get(i).getName().equals(name)) {
                    return star.getPlanets().get(i);
                }
                for (int j = 0; j < star.getPlanets().get(i).getMoons().size(); j++) {
                    if (star.getPlanets().get(i).getMoons().get(j).getName().equals(name)) {
                        return star.getPlanets().get(i).getMoons().get(j);
                    }
                }
            }
        }
        return null;
    }

    public CelestialBody getConnectedBody(CelestialBody body){
        if (body instanceof Star){
            return null;
        } else {
            for (int i = 0; i < star.getPlanets().size(); i++){
                if (star.getPlanets().get(i).equals(body)){
                    return star;
                }
                if (body instanceof Moon){
                    for (int j = 0; j < star.getPlanets().get(i).getMoons().size(); j++){
                        if (star.getPlanets().get(i).getMoons().get(j).equals(body)){
                            return star.getPlanets().get(i);
                        }
                    }
                }

            }
        }
        return null;
    }
}
