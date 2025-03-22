package it.unibs.fp.planetarium;

import java.io.FileNotFoundException;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class PlanetariumMain {
    public static void main(String[] args) {
        ArrayList<StarSystem> starSystems = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File("input.txt"));
            String line;
            ArrayList<String> lines = new ArrayList<>();

            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                lines.add(line);
            }

            scanner.close();

            HashMap<CelestialBody, String> corps = new HashMap<>();

            for (int i = 0; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(" ");
                int dimensions = Integer.parseInt(lines.get(0).split(" ")[1]);

                if (i != 0) {
                    switch (parts[0]) {
                        case "star": {
                            Position position = new Position(dimensions);

                            for (int j = 0; j < dimensions; j++) {
                                position.setCoordinate(j , Double.parseDouble(parts[j + 3]));
                            }

                            Star star = new Star(parts[1], Double.parseDouble(parts[2]), position);
                            corps.put(star,parts[3 + dimensions]);
                        }
                        case "planet": {
                            Position position = new Position(dimensions);

                            for (int j = 0; j < dimensions; j++) {
                                position.setCoordinate(j, Double.parseDouble(parts[j + 3]));
                            }

                            Planet planet = new Planet(parts[1], Double.parseDouble(parts[2]), position);
                            corps.put(planet, parts[3 + dimensions]);
                        }
                        case "moon": {
                            Position position = new Position(dimensions);

                            for (int j = 0; j < dimensions; j++) {
                                position.setCoordinate(j, Double.parseDouble(parts[j + 3]));
                            }

                            Moon moon = new Moon(parts[1], Double.parseDouble(parts[2]), position);
                            corps.put(moon, parts[3 + dimensions]);
                        }
                    }
                }
            }

            for (var corp : corps.keySet()) {
                if (corp instanceof Star) {
                    System.out.println("star");
                    StarSystem starSystem = new StarSystem(corps.get(corp), (Star) corp);
                    starSystems.add(starSystem);
                }
            }

            for (var corp : corps.keySet()) {
                if (corp instanceof Planet) {
                    System.out.println("planet");
                    var value = corps.get(corp);

                    for (StarSystem system : starSystems) {
                        if (system.getName().equals(value)) {
                            system.getStar().addPlanet((Planet) corp);
                        }
                    }
                }
            }

            for (var corp : corps.keySet()) {
                if (corp instanceof Moon) {
                    System.out.println("moon");
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

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        for (StarSystem system : starSystems) {
            System.out.println(system.toString());
            System.out.println("Mass: " + system.getMass());
            System.out.println("Center of mass: " + system.getCenterOfMass().toString());
        }

        System.out.println("Planetarium app!");
    }
}