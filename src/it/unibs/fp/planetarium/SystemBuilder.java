package it.unibs.fp.planetarium;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Classe che rappresenta un costruttore di sistemi stellari.
 * Contiene metodi per costruire un sistema stellare a partire da un file di input.
 *
 * @author Orizio Leonardo, Brumana Alberto, Loda Samuel
 * @version 1.0
 */

public class SystemBuilder {

    /**
     * Costruisce un sistema stellare a partire da un file di input.
     *
     * @param url Il percorso del file di input.
     * @return Un ArrayList contenente i sistemi stellari costruiti.
     */
    public static ArrayList<StarSystem> build(String url) {
        ArrayList<StarSystem> starSystems = new ArrayList<>();

        try {
            ArrayList<String> lines = getLinesFromFile(url);
            HashMap<CelestialBody, String> corps = new HashMap<>();

            populateCorps(lines, corps);

            connectStars(corps, starSystems);
            connectPlanets(corps, starSystems);
            connectMoons(corps, starSystems);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return starSystems;
    }


    /**
     * Popola la mappa dei corpi celesti a partire dalle righe del file di input.
     *
     * @param lines Le righe del file di input.
     * @param corps La mappa dei corpi celesti.
     */
    private static void populateCorps(ArrayList<String> lines, HashMap<CelestialBody, String> corps) {
        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(" ");
            int dimensions = Integer.parseInt(lines.get(0).split(" ")[1]);

            if (i != 0) {
                switch (parts[0]) {
                    case "star": {
                        createStar(dimensions, parts, corps);
                        break;
                    }
                    case "planet": {
                        createPlanet(dimensions, parts, corps);
                        break;
                    }
                    case "moon": {
                        createMoon(dimensions, parts, corps);
                        break;
                    }
                }
            }
        }
    }

    /**
     * Collega le lune ai pianeti a cui appartengono.
     *
     * @param corps La mappa dei corpi celesti.
     * @param starSystems La lista dei sistemi stellari.
     */
    private static void connectMoons(HashMap<CelestialBody, String> corps, ArrayList<StarSystem> starSystems) {
        for (var corp : corps.keySet()) {
            if (corp instanceof Moon) {
                var value = corps.get(corp);
                for (StarSystem system : starSystems) {
                    Star star = system.getStar();
                    ArrayList<Planet> planets = star.getPlanets();
                    for (Planet planet : planets) {
                        if (planet.getName().equals(value)) {
                            planet.addMoon((Moon) corp);
                        }
                    }
                }
            }
        }
    }

    /**
     * Collega i pianeti alle stelle a cui appartengono.
     *
     * @param corps La mappa dei corpi celesti.
     * @param starSystems La lista dei sistemi stellari.
     */
    private static void connectPlanets(HashMap<CelestialBody, String> corps, ArrayList<StarSystem> starSystems) {
        for (var corp : corps.keySet()) {
            if (corp instanceof Planet) {
                var value = corps.get(corp);
                for (StarSystem system : starSystems) {
                    var star = system.getStar();
                    if (star.getName().equals(value)) {
                        star.addPlanet((Planet) corp);
                    }
                }
            }
        }
    }

    /**
     * Collega le stelle ai sistemi stellari a cui appartengono.
     *
     * @param corps La mappa dei corpi celesti.
     * @param starSystems La lista dei sistemi stellari.
     */
    private static void connectStars(HashMap<CelestialBody, String> corps, ArrayList<StarSystem> starSystems) {
        for (var corp : corps.keySet()) {
            if (corp instanceof Star) {
                StarSystem starSystem = new StarSystem(corps.get(corp), (Star) corp);
                starSystems.add(starSystem);
            }
        }
    }

    /**
     * Crea una luna e la aggiunge alla mappa dei corpi celesti.
     *
     * @param dimensions Le dimensioni del sistema stellare.
     * @param parts Le parti della stringa che rappresenta la luna.
     * @param corps La mappa dei corpi celesti.
     */
    private static void createMoon(int dimensions, String[] parts, HashMap<CelestialBody, String> corps) {
        Position position = getPosition(dimensions, parts);

        Moon moon = new Moon(parts[1], Double.parseDouble(parts[2]), position);
        corps.put(moon, parts[3 + dimensions]);
    }

    /**
     * Crea un pianeta e lo aggiunge alla mappa dei corpi celesti.
     *
     * @param dimensions Le dimensioni del sistema stellare.
     * @param parts Le parti della stringa che rappresenta il pianeta.
     * @param corps La mappa dei corpi celesti.
     */
    private static void createPlanet(int dimensions, String[] parts, HashMap<CelestialBody, String> corps) {
        Position position = getPosition(dimensions, parts);

        Planet planet = new Planet(parts[1], Double.parseDouble(parts[2]), position);
        corps.put(planet, parts[3 + dimensions]);
    }

    /**
     * Crea una stella e la aggiunge alla mappa dei corpi celesti.
     *
     * @param dimensions Le dimensioni del sistema stellare.
     * @param parts Le parti della stringa che rappresenta la stella.
     * @param corps La mappa dei corpi celesti.
     */
    private static void createStar(int dimensions, String[] parts, HashMap<CelestialBody, String> corps) {
        Position position = getPosition(dimensions, parts);

        Star star = new Star(parts[1], Double.parseDouble(parts[2]), position);
        corps.put(star, parts[3 + dimensions]);
    }

    /**
     * Restituisce la posizione a partire dalle dimensioni e dalle parti della stringa.
     *
     * @param dimensions Le dimensioni del sistema stellare.
     * @param parts Le parti della stringa che rappresenta il corpo celeste.
     * @return La posizione del corpo celeste.
     */
    private static Position getPosition(int dimensions, String[] parts) {
        Position position = new Position(dimensions);

        for (int j = 0; j < dimensions; j++) {
            position.setCoordinate(j, Double.parseDouble(parts[j + 3]));
        }
        return position;
    }

    /**
     * Restituisce le righe del file di input come un ArrayList di stringhe.
     *
     * @param url Il percorso del file di input.
     * @return Un ArrayList contenente le righe del file.
     * @throws FileNotFoundException se il file non viene trovato.
     */
    private static ArrayList<String> getLinesFromFile(String url) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(url));
        String line;
        ArrayList<String> lines = new ArrayList<>();

        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            lines.add(line);
        }

        scanner.close();
        return lines;
    }
}
