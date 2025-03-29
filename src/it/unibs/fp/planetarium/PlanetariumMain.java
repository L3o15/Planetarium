package it.unibs.fp.planetarium;
import java.util.ArrayList;
import it.kibo.fp.lib.InputData;

/*
Planetarium 2
star StarMain 30 0 0 Planetarium
planet Planet1 5 0 -3 StarMain
moon Moon1 1 0 -6 Planet1
planet Planet2 7 0 5 StarMain
moon Moon3 2 2 3 Planet2
moon Moon4 1 4 4 Planet2

*/

public class PlanetariumMain {

    public static final String FILE = "input.txt";
    public static final String MSG_PLANETARIUM_APP = "Planetarium app!";
    public static final String MSG_CHOOSE_A_STAR = "Choose a star: ";
    public static final String MSG_DOT = ". ";
    public static final String MSG_CHOOSE_A_PLANET = "Choose a planet: ";
    public static final String MSG_CHOOSE_A_STAR_SYSTEM = "Choose a star system_ ";
    public static final String MSG_CHOOSE_A_CELESTIAL_CORP = "Choose a celestial corp: ";
    public static final String MSG_COLLIDES_WITH = "collides with";
    public static final String MSG_NOT_COLLIDE_WITH = "doesn't collide with";
    public static final String MSG_ENTER_THE_NAME_OF_THE_PLANET = "Enter the name of the planet: ";

    public static void main(String[] args) {
        ArrayList<StarSystem> starSystems = SystemBuilder.build(FILE);
        if (starSystems.isEmpty()) {
            addStar(starSystems);
        }

        greet();

        Menu menu = new Menu();

        handleMenu(menu, starSystems);
    }

    private static void handleMenu(Menu menu, ArrayList<StarSystem> starSystems) {
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
                    printPathBetweenCelestialBody(starSystems);
                    break;
                case 9:
                    printDistanceInPath(starSystems);
                    break;
                case 10:
                    break;
                case 11:
                    break;
                case 12:
                    break;
                case 13:
//                    try {
//                        saveOnFile(starSystems);
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
                    break;
                default:
                    break;
            }
        } while (choice != 0);
    }

    private static void printDistanceInPath(ArrayList<StarSystem> starSystems) {
        StarSystem starSystem = getStarSystem(starSystems);
        CelestialBody from = getCelestialBody(starSystem);
        CelestialBody to = getCelestialBody(starSystem);
        System.out.println(starSystem.getDistanceBetween(from.toString(), to.toString()));
    }

    private static void printPathBetweenCelestialBody(ArrayList<StarSystem> starSystems) {
        StarSystem starSystem = getStarSystem(starSystems);

        CelestialBody celestialBodyFrom = getCelestialBody(starSystem);

        CelestialBody celestialBodyTo = getCelestialBody(starSystem);

        System.out.println(starSystem.getPathBetweenToString(celestialBodyFrom.getName(), celestialBodyTo.getName()));
    }

    private static StarSystem getStarSystem(ArrayList<StarSystem> starSystems) {
        for (int i = 0; i < starSystems.size(); i++) {
            System.out.println(i + MSG_DOT + starSystems.get(i).toString());
        }

        int choose = InputData.readIntegerBetween(MSG_CHOOSE_A_STAR_SYSTEM, 0, starSystems.size() - 1);

         return starSystems.get(choose);
    }

    private static CelestialBody getCelestialBody(StarSystem starSystem) {
        int choose;
        for (int i = 0; i < starSystem.getAllCorps().size(); i++) {
            System.out.println(i + MSG_DOT + starSystem.getAllCorps().get(i).getName());
        }

        choose = InputData.readIntegerBetween(MSG_CHOOSE_A_CELESTIAL_CORP, 0, starSystem.getAllCorps().size() - 1);

        return starSystem.getAllCorps().get(choose);
    }

    private static void greet(){
        System.out.println(MSG_PLANETARIUM_APP);
    }

    private static void printPathToCelestialBody(ArrayList<StarSystem> starSystems) {
        StarSystem starSystem = getStarSystem(starSystems);
        CelestialBody celestialBody = getCelestialBody(starSystem);

        System.out.println(starSystem.getPathString(celestialBody.getName()));
    }

    private static void printCenterOfMassStarSystem(ArrayList<StarSystem> starSystems) {
        StarSystem starSystem = getStarSystem(starSystems);
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
        Star star = getStar(starSystems);

        Planet planet = getPlanet(star);

        for (Moon moon : planet.getMoons()) {
            System.out.println(moon.toString());
        }
    }

    private static Planet getPlanet(Star star) {
        for (int i = 0; i < star.getPlanets().size(); i++) {
            System.out.println(i + MSG_DOT + star.getPlanets().get(i).toString());
        }

        int planetChosen = InputData.readIntegerBetween(MSG_CHOOSE_A_PLANET, 0, star.getPlanets().size() - 1);
        
        return star.getPlanets().get(planetChosen);
    }

    private static Star getStar(ArrayList<StarSystem> starSystems) {
        for (int i = 0; i < starSystems.size(); i++) {
            System.out.println(i + MSG_DOT + starSystems.get(i).getStar().toString());
        }

        int starChosen = InputData.readIntegerBetween(MSG_CHOOSE_A_STAR, 0, starSystems.size() - 1);
        return starSystems.get(starChosen).getStar();
    }

    private static void printPlanetsOfStar(ArrayList<StarSystem> starSystems) {
        Star star = getStar(starSystems);

        for (Planet planet : star.getPlanets()) {
            System.out.println(planet.toString());
        }
    }

    private static void printStarOfStarSystem(ArrayList<StarSystem> starSystems) {
        StarSystem starSystem = getStarSystem(starSystems);
        System.out.println(starSystem.getStar().toString());
    }

    private static void printAllStarSystems(ArrayList<StarSystem> starSystems) {
        for (StarSystem starSystem : starSystems) {
            System.out.println(starSystem.toString());
        }
    }
}