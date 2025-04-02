package it.unibs.fp.planetarium;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class SystemBuilder {
    
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

    private static void connectStars(HashMap<CelestialBody, String> corps, ArrayList<StarSystem> starSystems) {
        for (var corp : corps.keySet()) {
            if (corp instanceof Star) {
                StarSystem starSystem = new StarSystem(corps.get(corp), (Star) corp);
                starSystems.add(starSystem);
            }
        }
    }

    private static void createMoon(int dimensions, String[] parts, HashMap<CelestialBody, String> corps) {
        Position position = getPosition(dimensions, parts);

        Moon moon = new Moon(parts[1], Double.parseDouble(parts[2]), position);
        corps.put(moon, parts[3 + dimensions]);
    }

    private static void createPlanet(int dimensions, String[] parts, HashMap<CelestialBody, String> corps) {
        Position position = getPosition(dimensions, parts);

        Planet planet = new Planet(parts[1], Double.parseDouble(parts[2]), position);
        corps.put(planet, parts[3 + dimensions]);
    }

    private static void createStar(int dimensions, String[] parts, HashMap<CelestialBody, String> corps) {
        Position position = getPosition(dimensions, parts);

        Star star = new Star(parts[1], Double.parseDouble(parts[2]), position);
        corps.put(star, parts[3 + dimensions]);
    }

    private static Position getPosition(int dimensions, String[] parts) {
        Position position = new Position(dimensions);

        for (int j = 0; j < dimensions; j++) {
            position.setCoordinate(j, Double.parseDouble(parts[j + 3]));
        }
        return position;
    }

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
