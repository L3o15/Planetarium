package it.unibs.fp.planetarium;
import java.util.ArrayList;
import it.kibo.fp.lib.InputData;

public class PlanetariumMain {
    public static void main(String[] args) {
        ArrayList<StarSystem> starSystems = SystemBuilder.build("input.txt");
        Menu menu = new Menu();

        System.out.println("Planetarium app!");

        int choice;

        do {
            menu.display();

            choice = menu.getChoice();

            switch (choice) {
                case 1:
                    printAllStarSystems(starSystems);
                    break;
                case 2:
                    printStarOfStarSystem(starSystems);
                    break;
                case 3:
                    printPlanetsOfStar(starSystems);
                    break;
                case 4:
                    printMoonsOfPlanet(starSystems);
                    break;
                case 5:
                    printCelestialBodiesStarSystem(starSystems);
                    break;
                case 6:
                    printCenterOfMassStarSystem(starSystems);
                    break;
                case 7:
                    printPathToCelestialBody(starSystems);
                    break;
                case 8:
                    break;
                case 9:
                    break;
                case 10:
                    break;
                case 11:
                    break;
                case 12:
                    break;
                default:
                    break;
            }
        } while (choice != 0);
    }

    private static void printPathToCelestialBody(ArrayList<StarSystem> starSystems) {
        String search = InputData.readString("Enter search: ", true);

        for (StarSystem starSystem : starSystems) {
            System.out.println(starSystem.getPath(search));
        }
    }

    private static void printCenterOfMassStarSystem(ArrayList<StarSystem> starSystems) {
        for (int i = 0; i < starSystems.size(); i++) {
            System.out.println(i + ". " + starSystems.get(i).getStar().toString());
        }

        int starChosen = InputData.readIntegerBetween("Choose a star", 0, starSystems.size() - 1);

        StarSystem starSystem = starSystems.get(starChosen);
        System.out.println(starSystem.getCenterOfMass().toString());
    }

    private static void printCelestialBodiesStarSystem(ArrayList<StarSystem> starSystems) {
        for (StarSystem starSystem : starSystems) {
            System.out.println(starSystem.getStar().toString());
            for (Planet planet : starSystem.getStar().getPlanets()) {
                System.out.println(planet.toString());
                for (Moon moon : planet.getMoons()) {
                    System.out.println(moon.toString());
                }
            }
        }
    }

    private static void printMoonsOfPlanet(ArrayList<StarSystem> starSystems) {
        for (int i = 0; i < starSystems.size(); i++) {
            System.out.println(i + ". " + starSystems.get(i).getStar().toString());
        }

        int starChosen = InputData.readIntegerBetween("Choose a star", 0, starSystems.size() - 1);
        Star star = starSystems.get(starChosen).getStar();

        for (int i = 0; i < star.getPlanets().size(); i++) {
            System.out.println(i + ". " + star.getPlanets().get(i).toString());
        }

        int planetChosen = InputData.readIntegerBetween("Choose a planet", 0, star.getPlanets().size() - 1);
        Planet planet = star.getPlanets().get(planetChosen);

        for (Moon moon : planet.getMoons()) {
            System.out.println(moon.toString());
        }
    }

    private static void printPlanetsOfStar(ArrayList<StarSystem> starSystems) {
        for (int i = 0; i < starSystems.size(); i++) {
            Star star = starSystems.get(i).getStar();
            System.out.println(i + ". " + star.toString());
        }

        int starChosen = InputData.readIntegerBetween("Choose a star", 0, starSystems.size() - 1);
        Star star = starSystems.get(starChosen).getStar();

        for (Planet planet : star.getPlanets()) {
            System.out.println(planet.toString());
        }
    }

    private static void printStarOfStarSystem(ArrayList<StarSystem> starSystems) {
        for (int i = 0; i < starSystems.size(); i++) {
            StarSystem starSystem = starSystems.get(i);
            System.out.println(i + ". " + starSystem.getStar().toString());
        }
        int starSystemChosen = InputData.readIntegerBetween("Choose a star system", 0, starSystems.size() - 1);
        StarSystem starSystem = starSystems.get(starSystemChosen);
        System.out.println(starSystem.getStar().toString());
    }

    private static void printAllStarSystems(ArrayList<StarSystem> starSystems) {
        for (StarSystem starSystem : starSystems) {
            System.out.println(starSystem.toString());
        }
    }
}