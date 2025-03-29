package it.unibs.fp.planetarium;

import java.util.ArrayList;

public class StarSystem {
    public static final String MINUS_SIGN = " - ";
    public static final String NEXT = ">";
    public static final String MSG_STAR_SYSTEM_ALREADY_HAS_A_STAR = "This star system already has a star";
    public static final String MSG_CANNOT_ADD_A_MOON_TO_A_STAR = "Cannot add a moon to a star";
    public static final String MSG_CANNOT_ADD_PLANET_TO_PLANET = "Cannot add a planet to a planet";
    public static final String MSG_ERROR_A_BODY_NULL = "One of the bodies is null";
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

    public ArrayList<CelestialBody> getPath(String search) {
        ArrayList<CelestialBody> path = new ArrayList<>();

        if (star.getName().equals(search)) {
            path.add(star);
            return path;
        }

        for (int i = 0; i < star.getPlanets().size(); i++) {
            if (star.getPlanets().get(i).getName().equals(search)) {
                path.add(star);
                path.add(star.getPlanets().get(i));
                return path;
            }
        }

        for (int j = 0; j < star.getPlanets().size(); j++){
            for (int k = 0; k < star.getPlanets().get(j).getMoons().size(); k++) {
                if (star.getPlanets().get(j).getMoons().get(k).getName().equals(search)) {
                    path.add(star);
                    path.add(star.getPlanets().get(j));
                    path.add(star.getPlanets().get(j).getMoons().get(k));
                    return path;
                }
            }
        }
        return path;
    }

    public String getPathString(String search) {
        ArrayList<CelestialBody> path = getPath(search);
        return concatenatePath(path);
    }

    public ArrayList<CelestialBody> getPathBetween(String fromName, String toName) {
        ArrayList<CelestialBody> path = new ArrayList<>();
        ArrayList<CelestialBody> pathToFrom = getPath(fromName);
        ArrayList<CelestialBody> pathToTo = getPath(toName);

        for (int i = pathToFrom.size() - 1; i >= 0; i--) {
            path.add(pathToFrom.get(i));
        }

        int idx = -1;

        for (int j = 0; j < pathToTo.size(); j++) {
            if (path.contains(pathToTo.get(j))) {
                idx = j;
                path.remove(pathToTo.get(j));
            }
        }

        for (int k = idx; k < pathToTo.size(); k++) {
            path.add(pathToTo.get(k));
        }

        return path;
    }

    public String getPathBetweenToString(String fromName, String toName) {
        ArrayList<CelestialBody> path = getPathBetween(fromName, toName);
        return concatenatePath(path);
    }

    private static String concatenatePath(ArrayList<CelestialBody> path) {
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < path.size(); i++) {
            ret.append(path.get(i).getName());
            if (i != path.size() - 1) {
                ret.append(NEXT);
            }
        }
        return ret.toString();
    }

    public boolean checkCollision(String c1, String c2) {
        CelestialBody body1 = getCorp(c1);
        CelestialBody body2 = getCorp(c2);

        if (body1 == null || body2 == null) {
            throw new RuntimeException(MSG_ERROR_A_BODY_NULL);
        }

        if (body1.equals(body2)){
            return false;
        }

        if (body1 instanceof Planet && body2 instanceof Planet) {
            double radiusP1 = body1.getPosition().distance(star.getPosition());
            double radiusP2 = body2.getPosition().distance(star.getPosition());

            return radiusP1 == radiusP2;
        }

        if (body1 instanceof Moon && body2 instanceof Moon) {
            Planet planet1 = null;
            Planet planet2 = null;

            for (var planet : star.getPlanets()) {
                if (planet.getMoons().contains(body1)) {
                    planet1 = planet;
                } else if (planet.getMoons().contains(body2)) {
                    planet2 = planet;
                }
            }

            if (planet1 != null && planet2 != null) {

                if (planet1.equals(planet2)) {
                    return body1.getPosition().distance(planet1.getPosition()) == body2.getPosition().distance(planet2.getPosition());
                }

                double radiusM1 = body1.getPosition().distance(planet1.getPosition());
                double radiusM2 = body2.getPosition().distance(planet2.getPosition());
                double radiusP1 = planet1.getPosition().distance(star.getPosition());
                double radiusP2 = planet2.getPosition().distance(star.getPosition());

                return Math.abs(radiusP1 - radiusP2) < (radiusM1 + radiusM2);

            }

        }

        if ((body1 instanceof Moon && body2 instanceof Star) || (body2 instanceof Moon && body1 instanceof Star)) {
            if (body1 instanceof Moon) {
                CelestialBody cb1 = body1;
                body1 = body2;
                body2 = cb1;
            }

            Planet planet = getMoonPlanet((Moon) body2);

            if (planet != null){
                return planet.getPosition().distance(body2.getPosition()) == planet.getPosition().distance(body1.getPosition());
            } else {
                throw new RuntimeException(MSG_ERROR_A_BODY_NULL);
            }

        }

        if ((body1 instanceof Star && body2 instanceof Planet) || (body2 instanceof Star && body1 instanceof Planet)) {
            if (body1 instanceof Star) {
                CelestialBody cb1 = body1;
                body1 = body2;
                body2 = cb1;
            }

            return body1.getPosition().equals(body2.getPosition());
        }

        if ((body1 instanceof Planet && body2 instanceof Moon) || (body2 instanceof Planet && body1 instanceof Moon)) {
            if (body1 instanceof Planet) {
                CelestialBody cb1 = body1;
                body1 = body2;
                body2 = cb1;
            }

            // body1 is a moon, body2 is a planet

            Planet planetMoon = getMoonPlanet((Moon) body1);

            if (planetMoon == null) {
                throw new RuntimeException(MSG_ERROR_A_BODY_NULL);
            }

            double radiusM = body1.getPosition().distance(planetMoon.getPosition());

            double radiusBody2 = body2.getPosition().distance(star.getPosition());

            double radiusPlanet = planetMoon.getPosition().distance(star.getPosition());
            return Math.abs(radiusBody2 - radiusPlanet) <= radiusM;
        }

        return false;
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

    public ArrayList<CelestialBody> getAllCorps() {
        ArrayList<CelestialBody> corps = new ArrayList<>();
        corps.add(star);
        for (int i = 0; i < star.getPlanets().size(); i++) {
            corps.add(star.getPlanets().get(i));
            corps.addAll(star.getPlanets().get(i).getMoons());
        }
        return corps;
    }

    public double getDistanceBetween(String name1, String name2) {
        ArrayList<CelestialBody> path = getPathBetween(name1, name2);
        double distance = 0;

        for (int i = 0; i < path.size() - 2; i++) {
            distance += path.get(i).getPosition().distance(path.get(i + 1).getPosition());
        }

        return distance;
    }

    private Planet getMoonPlanet(Moon moon) {
        for (Planet p : star.getPlanets()) {
            if (p.getMoons().contains(moon)) {
                return p;
            }
        }

        return null;
    }

}
