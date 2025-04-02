package it.unibs.fp.planetarium;

import java.util.ArrayList;

/**
 * Classe che rappresenta un sistema stellare, composto da una stella e i corpi celesti che la orbitano.
 * Permette di aggiungere, rimuovere e verificare collisioni tra i corpi celesti.
 *
 * @author Orizio Leonardo, Brumana Alberto, Loda Samuel
 * @version 1.0
 */

public class StarSystem {
    public static final String MINUS_SIGN = " - ";
    public static final String NEXT = ">";
    public static final String MSG_STAR_SYSTEM_ALREADY_HAS_A_STAR = "This star system already has a star";
    public static final String MSG_CANNOT_ADD_A_MOON_TO_A_STAR = "Cannot add a moon to a star";
    public static final String MSG_CANNOT_ADD_PLANET_TO_PLANET = "Cannot add a planet to a planet";
    public static final String MSG_ERROR_A_BODY_NULL = "One of the bodies is null";
    private String name;
    private final Star star;

    /**
     * Costruttore della classe StarSystem.
     *
     * @param name Il nome del sistema stellare.
     * @param star La stella centrale del sistema.
     */
    public StarSystem(String name, Star star) {
        this.name = name;
        this.star = star;
    }

    /**
     * Restituisce il nome del sistema stellare.
     *
     * @return Il nome del sistema stellare.
     */
    public String getName() {
        return name;
    }

    /**
     * Restituisce la stella centrale del sistema stellare.
     *
     * @return La stella centrale del sistema stellare.
     */
    public Star getStar() {
        return star;
    }

    /**
     * Imposta il nome del sistema stellare.
     *
     * @param name Il nuovo nome del sistema stellare.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Restituisce la massa totale del sistema stellare.
     *
     * @return La massa totale del sistema stellare.
     */
    public double getMass() {
        return star.getTotalMass();
    }

    /**
     * Restituisce la posizione del sistema stellare.
     *
     * @return La posizione del sistema stellare.
     */
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

    /**
     * Restituisce una rappresentazione in forma di stringa del sistema stellare.
     *
     * @return Una stringa che rappresenta il sistema stellare.
     */
    public String toString() {
        return name + MINUS_SIGN + star.toString();
    }

    /**
     * Restituisce una rappresentazione in forma di stringa del sistema stellare con i corpi celesti.
     *
     * @return Una stringa che rappresenta il sistema stellare con i corpi celesti.
     */
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

    /**
     * Restituisce una rappresentazione in forma di stringa del percorso verso un corpo celeste.
     *
     * @param search Il nome del corpo celeste da cercare.
     * @return Una stringa che rappresenta il percorso verso il corpo celeste.
     */
    public String getPathString(String search) {
        ArrayList<CelestialBody> path = getPath(search);
        return concatenatePath(path);
    }

    /**
     * Restituisce una rappresentazione in forma di stringa del percorso tra due corpi celesti.
     *
     * @param fromName Il nome del corpo celeste di partenza.
     * @param toName   Il nome del corpo celeste di arrivo.
     * @return Una stringa che rappresenta il percorso tra i due corpi celesti.
     */
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

    /**
     * Restituisce una rappresentazione in forma di stringa del percorso tra due corpi celesti.
     *
     * @param fromName Il nome del corpo celeste di partenza.
     * @param toName   Il nome del corpo celeste di arrivo.
     * @return Una stringa che rappresenta il percorso tra i due corpi celesti.
     */
    public String getPathBetweenToString(String fromName, String toName) {
        ArrayList<CelestialBody> path = getPathBetween(fromName, toName);
        return concatenatePath(path);
    }

    /**
     * Restituisce una rappresentazione in forma di stringa del percorso tra due corpi celesti.
     *
     * @param path La lista dei corpi celesti che compongono il percorso.
     * @return Una stringa che rappresenta il percorso tra i due corpi celesti.
     */
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

    /**
     * Verifica se due corpi celesti collidono.
     *
     * @param c1 Il nome del primo corpo celeste.
     * @param c2 Il nome del secondo corpo celeste.
     * @return true se i corpi celesti collidono, false altrimenti.
     */
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
            Planet planet1 = getMoonPlanet((Moon) body1);
            Planet planet2 = getMoonPlanet((Moon) body2);


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

    /**
     * Aggiunge un corpo celeste al sistema stellare.
     *
     * @param body Il corpo celeste da aggiungere.
     * @param connectedTo Il nome del corpo celeste a cui è connesso.
     * @return true se il corpo celeste è stato aggiunto con successo, false altrimenti.
     */
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

    /**
     * Restituisce un corpo celeste in base al nome fornito.
     *
     * @param name Il nome del corpo celeste da cercare.
     * @return Il corpo celeste corrispondente al nome fornito, o null se non trovato.
     */
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

    /**
     * Restituisce il corpo celeste connesso a un altro corpo celeste.
     *
     * @param body Il corpo celeste di cui trovare il corpo connesso.
     * @return Il corpo celeste connesso, o null se non trovato.
     */
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

    /**
     * Restituisce una lista di tutti i corpi celesti nel sistema stellare.
     *
     * @return Un ArrayList contenente tutti i corpi celesti nel sistema stellare.
     */
    public ArrayList<CelestialBody> getAllCorps() {
        ArrayList<CelestialBody> corps = new ArrayList<>();
        corps.add(star);
        for (int i = 0; i < star.getPlanets().size(); i++) {
            corps.add(star.getPlanets().get(i));
            corps.addAll(star.getPlanets().get(i).getMoons());
        }
        return corps;
    }

    /**
     * Restituisce la distanza tra due corpi celesti.
     *
     * @param name1 Il nome del primo corpo celeste.
     * @param name2 Il nome del secondo corpo celeste.
     * @return La distanza tra i due corpi celesti.
     */
    public double getDistanceBetween(String name1, String name2) {
        ArrayList<CelestialBody> path = getPathBetween(name1, name2);
        double distance = 0;

        for (int i = 0; i < path.size() - 2; i++) {
            distance += path.get(i).getPosition().distance(path.get(i + 1).getPosition());
        }

        return distance;
    }

    /**
     * Restituisce il pianeta a cui la luna appartiene.
     *
     * @param moon La luna di cui trovare il pianeta.
     */

    public Planet getMoonPlanet(Moon moon) {
        for (Planet p : star.getPlanets()) {
            if (p.getMoons().contains(moon)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Rimuove un corpo celeste dal sistema stellare.
     *
     * @param body Il corpo celeste da rimuovere.
     */
    public void removeCelestialBody(CelestialBody body) {
        if (body instanceof Planet) {
            star.removePlanet((Planet) body);
        } else if (body instanceof Moon) {
            Planet planet = getMoonPlanet((Moon) body);
            if (planet != null) {
                planet.removeMoon((Moon) body);
            }
        }
    }

}
