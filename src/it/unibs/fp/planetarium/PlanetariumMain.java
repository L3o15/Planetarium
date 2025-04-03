package it.unibs.fp.planetarium;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import it.kibo.fp.lib.InputData;

/**
 * Classe principale del programma Planetarium.
 * Gestisce l'interazione con l'utente e le operazioni sui sistemi stellari, stelle, pianeti e lune.
 *
 * @author Orizio Leonardo, Brumana Alberto, Loda Samuel
 * @version 1.0
 */

public class PlanetariumMain {

    public static final String FILE = "input.txt";
    public static final String MSG_PLANETARIUM_APP = "Planetarium app!";
    public static final String MSG_CHOOSE_A_STAR = "Choose a star: ";
    public static final String MSG_DOT = ". ";
    public static final String MSG_CHOOSE_A_PLANET = "Choose a planet: ";
    public static final String MSG_CHOOSE_A_STAR_SYSTEM = "Choose a star system_ ";
    public static final String MSG_CHOOSE_A_CELESTIAL_CORP = "Choose a celestial corp: ";
    public static final String MSG_COLLIDES_WITH = " collides with ";
    public static final String MSG_NOT_COLLIDE_WITH = " doesn't collide with ";
    public static final String MSG_ENTER_THE_NAME_OF_THE_PLANET = "Enter the name of the planet: ";
    public static final String MSG_ENTER_THE_NAME_OF_THE_STAR = "Enter the name of the star: ";
    public static final String MSG_REMOVED_FROM = " removed from ";
    public static final String MSG_ERROR_ADD_MOON_TO_STAR = "You can't add a  celestial body to a moon";
    public static final String MSG_ENTER_THE_NAME_OF_STAR_SYSTEM = "Enter the name of the star system: ";
    public static final String MSG_ENTER_THE_MASS = "Enter the mass: ";
    public static final String MSG_ENTER_THE_COORDINATE = "Enter the coordinate ";
    public static final String DP = ": ";
    public static final String STAR = "star";
    public static final String LINE_BREAK = "\n";
    public static final String SPACE = " ";
    public static final String OPEN_BRACKET = "(";
    public static final String CLOSE_BRACKET = ")";
    public static final String COMMA = ",";
    public static final String PLANET = "planet";
    public static final String MOON = "moon";
    public static final int MIN_DIMENSIONS = 2;
    public static final int PRINT_STAR_SYSTEMS = 1;
    public static final int PRINT_STAR = 2;
    public static final int PRINT_PLANETS = 3;
    public static final int PRINT_MOONS = 4;
    public static final int PRINT_CELESTIAL_BODIES = 5;
    public static final int PRINT_CENTER_OF_MASS = 6;
    public static final int PRINT_PATH_TO = 7;
    public static final int PRINT_PATH_BETWEEN = 8;
    public static final int PRINT_DISTANCE_IN_PATH = 9;
    public static final int PRINT_POSSIBLE_COLLISIONS = 10;
    public static final int ADD_CELESTIAL_BODY = 11;
    public static final int REMOVE_CELESTIAL_BODY = 12;
    public static final int SAVE_ON_FILE = 13;
    public static final int ADD_STAR_SYSTEM = 14;
    public static final String MSG_GET_NAME = "Enter the name of the celestial body: ";
    public static final String MSG_ERROR_CELESTIAL_BODY_NOT_FOUND = "Celestial body not found.";
    public static final int GET_CORP_BY_NAME = 15;
    public static final String MSG_IS_A_MOON_OF = " is a moon of ";
    public static final String MSG_IS_NOT_A_MOON = " is not a moon";
    public static final int GET_MOON_DIPENDENCY = 16;
    public static final String MES_ERROR_POSITION_ALREADY_IN_USE = "There's already a corp at the selected position";

    /**
     * Metodo main del programma.
     * Inizializza il sistema stellare e gestisce il menu di interazione con l'utente.
     */
    public static void main(String[] args) {
        greet();

        ArrayList<StarSystem> starSystems = getStarSystems();

        Menu menu = new Menu();

        handleMenu(menu, starSystems);
    }

    /**
     * Ottiene i sistemi stellari da un file o crea un nuovo sistema stellare se il file è vuoto.
     *
     * @return La lista dei sistemi stellari.
     */
    private static ArrayList<StarSystem> getStarSystems() {
        ArrayList<StarSystem> starSystems = SystemBuilder.build(FILE);

        if (starSystems.isEmpty()) {
            addStar(starSystems);
        }
        return starSystems;
    }

    /**
     * Gestisce il menu di interazione con l'utente.
     *
     * @param menu Il menu da visualizzare.
     * @param starSystems La lista dei sistemi stellari.
     */
    private static void handleMenu(Menu menu, ArrayList<StarSystem> starSystems) {
        int choice;

        do {
            menu.display();

            choice = menu.getChoice();

            switch (choice) {
                case PRINT_STAR_SYSTEMS:
                    printAllStarSystems(starSystems);
                    break;
                case PRINT_STAR:
                    printStarOfStarSystem(starSystems);
                    break;
                case PRINT_PLANETS:
                    printPlanetsOfStar(starSystems);
                    break;
                case PRINT_MOONS:
                    printMoonsOfPlanet(starSystems);
                    break;
                case PRINT_CELESTIAL_BODIES:
                    printCelestialBodiesStarSystem(starSystems);
                    break;
                case PRINT_CENTER_OF_MASS:
                    printCenterOfMassStarSystem(starSystems);
                    break;
                case PRINT_PATH_TO:
                    printPathToCelestialBody(starSystems);
                    break;
                case PRINT_PATH_BETWEEN:
                    printPathBetweenCelestialBody(starSystems);
                    break;
                case PRINT_DISTANCE_IN_PATH:
                    printDistanceInPath(starSystems);
                    break;
                case PRINT_POSSIBLE_COLLISIONS:
                    printPossibleCollisions(starSystems);
                    break;
                case ADD_CELESTIAL_BODY:
                    addCelestialBody(starSystems);
                    break;
                case REMOVE_CELESTIAL_BODY:
                    removeCelestialBody(starSystems);
                    break;
                case SAVE_ON_FILE:
                    try {
                        saveOnFile(starSystems);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case ADD_STAR_SYSTEM:
                    addStar(starSystems);
                    break;
                case GET_CORP_BY_NAME:
                    getCorpByName(starSystems);
                    break;
                case GET_MOON_DIPENDENCY:
                    getMoonPlanetDependency(starSystems);
                    break;
                default:
                    break;
            }
        } while (choice != 0);
    }

    /**
     * Ottiene la dipendenza tra una luna e un pianeta.
     *
     * @param starSystems La lista dei sistemi stellari.
     */

    private static void getMoonPlanetDependency(ArrayList<StarSystem> starSystems) {
        StarSystem starSystem = getStarSystem(starSystems);
        String name = InputData.readString(MSG_GET_NAME, true);
        CelestialBody celestialBody = starSystem.getCorp(name);
        if (celestialBody == null) {
            System.out.println(MSG_ERROR_CELESTIAL_BODY_NOT_FOUND);
        } else if (celestialBody instanceof Moon) {
            System.out.println(celestialBody.getName() + MSG_IS_A_MOON_OF + (starSystem.getMoonPlanet((Moon) celestialBody)));
        } else {
            System.out.println(celestialBody.getName() + MSG_IS_NOT_A_MOON);
        }
    }

    /**
     * Ottiene un corpo celeste per nome.
     *
     * @param starSystems La lista dei sistemi stellari.
     */
    private static void getCorpByName(ArrayList<StarSystem> starSystems) {
        StarSystem starSystem = getStarSystem(starSystems);
        String name = InputData.readString(MSG_GET_NAME, true);
        CelestialBody celestialBody = starSystem.getCorp(name);
        if (celestialBody == null) {
            System.out.println(MSG_ERROR_CELESTIAL_BODY_NOT_FOUND);
        } else {
            System.out.println(celestialBody);
        }
    }

    /**
     * Rimuove un corpo celeste da un sistema stellare.
     *
     * @param starSystems La lista dei sistemi stellari.
     */
    private static void removeCelestialBody(ArrayList<StarSystem> starSystems){
        StarSystem starSystem = getStarSystem(starSystems);
        CelestialBody celestialBody = getCelestialBody(starSystem);
        if (celestialBody instanceof Star) {
            for (StarSystem s : starSystems) {
                if (s.getStar().getName().equals(celestialBody.getName())) {
                    System.out.println(celestialBody.getName() + MSG_REMOVED_FROM + s.getName());
                    starSystems.remove(s);
                    break;
                }
            }

            if (starSystems.isEmpty()) {
                addStar(starSystems);
            }
        } else {
            System.out.println(celestialBody.getName() + MSG_REMOVED_FROM + starSystem.getName());
            starSystem.removeCelestialBody(celestialBody);
        }
    }

    /**
     * Aggiunge un corpo celeste a un sistema stellare.
     *
     * @param starSystems La lista dei sistemi stellari.
     */
    private static void addCelestialBody(ArrayList<StarSystem> starSystems) {
        StarSystem starSystem = getStarSystem(starSystems);
        CelestialBody celestialBody = getCelestialBody(starSystem);

        try {
            if (celestialBody instanceof Star) {
                addPlanet(celestialBody, starSystem);
            } else if (celestialBody instanceof Planet) {
                addMoon(celestialBody, starSystem);
            } else if (celestialBody instanceof Moon) {
                System.out.println(MSG_ERROR_ADD_MOON_TO_STAR);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Aggiunge un sistema stellare alla lista dei sistemi stellari.
     *
     * @param starSystems La lista dei sistemi stellari.
     */
    private static void addStar(ArrayList<StarSystem> starSystems) throws RuntimeException {
        String starSystemName = InputData.readString(MSG_ENTER_THE_NAME_OF_STAR_SYSTEM, true);
        String starName = InputData.readString(MSG_ENTER_THE_NAME_OF_THE_STAR, true);
        double mass = getValidMass();
        int dimensions = InputData.readIntegerWithMinimum("Enter the number of dimensions: ", MIN_DIMENSIONS);
        Position position = getValidPosition(dimensions);

        for (StarSystem s : starSystems) {
            if (s.getName().equals(starSystemName)) {
                s.getAllCorps().forEach(c -> {
                    if (c.getPosition().equals(position)) {
                        throw new RuntimeException("There's already a corp at the selected position");
                    }
                });
            }
        }

        Star star = new Star(starName, mass, position);
        StarSystem starSystem = new StarSystem(starSystemName, star);
        starSystems.add(starSystem);
    }

    /**
     * Aggiunge una luna a un pianeta.
     *
     * @param celestialBody Il corpo celeste a cui aggiungere la luna.
     * @param starSystem Il sistema stellare in cui si trova il corpo celeste.
     */
    private static void addMoon(CelestialBody celestialBody, StarSystem starSystem) throws RuntimeException {
        Planet planet = (Planet) celestialBody;
        String planetName = getValidName(starSystem);
        double mass = getValidMass();
        Position position = getValidPosition(starSystem.getStar().getPosition().getDimensions());

        for (CelestialBody cb : starSystem.getAllCorps() ) {
            if (cb.getPosition().equals(position)) {
                throw new RuntimeException(MES_ERROR_POSITION_ALREADY_IN_USE);
            }
        }

        Moon moon = new Moon(planetName, mass, position);
        planet.addMoon(moon);
    }

    /**
     * Aggiunge un pianeta a una stella.
     *
     * @param celestialBody Il corpo celeste a cui aggiungere il pianeta.
     * @param starSystem Il sistema stellare in cui si trova il corpo celeste.
     */
    private static void addPlanet(CelestialBody celestialBody, StarSystem starSystem) throws RuntimeException {
        Star star = (Star) celestialBody;
        String planetName = getValidName(starSystem);
        double mass = getValidMass();
        Position position = getValidPosition(star.getPosition().getDimensions());

        for (CelestialBody cb : starSystem.getAllCorps() ) {
            if (cb.getPosition().equals(position)) {
                throw new RuntimeException(MES_ERROR_POSITION_ALREADY_IN_USE);
            }
        }

        Planet planet = new Planet(planetName, mass, position);
        star.addPlanet(planet);
    }

    /**
     * Ottiene un nome valido per un corpo celeste.
     *
     * @param starSystem Il sistema stellare in cui si trova il corpo celeste.
     * @return Il nome valido per il corpo celeste.
     */
    private static String getValidName(StarSystem starSystem) {
        String planetName;
        do {
            planetName = InputData.readString(MSG_ENTER_THE_NAME_OF_THE_PLANET, true);
        } while (starSystem.getCorp(planetName) != null);
        return planetName;
    }

    /**
     * Ottiene una massa valida per un corpo celeste.
     *
     * @return La massa valida per il corpo celeste.
     */
    private static double getValidMass() {
        double mass;
        do {
            mass = InputData.readDouble(MSG_ENTER_THE_MASS);
        } while (mass <= 0);
        return mass;
    }

    /**
     * Ottiene una posizione valida per un corpo celeste.
     *
     * @param dimensions Le dimensioni della posizione.
     * @return La posizione valida per il corpo celeste.
     */
    private static Position getValidPosition(int dimensions) {
        Position position = new Position(dimensions);
        for (int i = 0; i < dimensions; i++) {
            double coordinate = InputData.readDouble(MSG_ENTER_THE_COORDINATE + (i + 1) + DP);
            position.setCoordinate(i, coordinate);
        }
        return position;
    }

    /**
     * Stampa le possibili collisioni tra due corpi celesti.
     *
     * @param starSystems La lista dei sistemi stellari.
     */
    private static void printPossibleCollisions(ArrayList<StarSystem> starSystems) {
        StarSystem starSystem = getStarSystem(starSystems);
        CelestialBody celestialBody1 = getCelestialBody(starSystem);
        CelestialBody celestialBody2 = getCelestialBody(starSystem);

        System.out.println(celestialBody1.getName() + (starSystem.checkCollision(celestialBody1.getName(), celestialBody2.getName()) ? MSG_COLLIDES_WITH : MSG_NOT_COLLIDE_WITH) + celestialBody2.getName());
    }

    /**
     * Stampa la distanza tra due corpi celesti in un percorso.
     *
     * @param starSystems La lista dei sistemi stellari.
     */
    private static void printDistanceInPath(ArrayList<StarSystem> starSystems) {
        StarSystem starSystem = getStarSystem(starSystems);
        CelestialBody from = getCelestialBody(starSystem);
        CelestialBody to = getCelestialBody(starSystem);
        System.out.println(starSystem.getDistanceBetween(from.toString(), to.toString()));
    }

    /**
     * Stampa il percorso tra due corpi celesti.
     *
     * @param starSystems La lista dei sistemi stellari.
     */
    private static void printPathBetweenCelestialBody(ArrayList<StarSystem> starSystems) {
        StarSystem starSystem = getStarSystem(starSystems);

        CelestialBody celestialBodyFrom = getCelestialBody(starSystem);

        CelestialBody celestialBodyTo = getCelestialBody(starSystem);

        System.out.println(starSystem.getPathBetweenToString(celestialBodyFrom.getName(), celestialBodyTo.getName()));
    }

    /**
     * Ottiene un sistema stellare dalla lista dei sistemi stellari.
     *
     * @param starSystems La lista dei sistemi stellari.
     * @return Il sistema stellare selezionato.
     */
    private static StarSystem getStarSystem(ArrayList<StarSystem> starSystems) {
        for (int i = 0; i < starSystems.size(); i++) {
            System.out.println(i + MSG_DOT + starSystems.get(i).toString());
        }

        int choose = InputData.readIntegerBetween(MSG_CHOOSE_A_STAR_SYSTEM, 0, starSystems.size() - 1);

         return starSystems.get(choose);
    }

    /**
     * Ottiene un corpo celeste da un sistema stellare.
     *
     * @param starSystem Il sistema stellare da cui ottenere il corpo celeste.
     * @return Il corpo celeste selezionato.
     */
    private static CelestialBody getCelestialBody(StarSystem starSystem) {
        int choose;
        for (int i = 0; i < starSystem.getAllCorps().size(); i++) {
            System.out.println(i + MSG_DOT + starSystem.getAllCorps().get(i).getName());
        }

        choose = InputData.readIntegerBetween(MSG_CHOOSE_A_CELESTIAL_CORP, 0, starSystem.getAllCorps().size() - 1);

        return starSystem.getAllCorps().get(choose);
    }

    /**
     * Stampa un messaggio di benvenuto.
     */
    private static void greet(){
        System.out.println(MSG_PLANETARIUM_APP);
    }

    /**
     * Stampa il percorso verso un corpo celeste.
     *
     * @param starSystems La lista dei sistemi stellari.
     */
    private static void printPathToCelestialBody(ArrayList<StarSystem> starSystems) {
        StarSystem starSystem = getStarSystem(starSystems);
        CelestialBody celestialBody = getCelestialBody(starSystem);

        System.out.println(starSystem.getPathString(celestialBody.getName()));
    }

    /**
     * Stampa il centro di massa di un sistema stellare.
     *
     * @param starSystems La lista dei sistemi stellari.
     */
    private static void printCenterOfMassStarSystem(ArrayList<StarSystem> starSystems) {
        StarSystem starSystem = getStarSystem(starSystems);
        System.out.println(starSystem.getCenterOfMass().toString());
    }

    /**
     * Stampa i corpi celesti di un sistema stellare.
     *
     * @param starSystems La lista dei sistemi stellari.
     */
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

    /**
     * Stampa le lune di un pianeta.
     *
     * @param starSystems La lista dei sistemi stellari.
     */
    private static void printMoonsOfPlanet(ArrayList<StarSystem> starSystems) {
        Star star = getStar(starSystems);

        Planet planet = getPlanet(star);

        for (Moon moon : planet.getMoons()) {
            System.out.println(moon.toString());
        }
    }

    /**
     * Ottiene un pianeta da una stella.
     *
     * @param star La stella da cui ottenere il pianeta.
     * @return Il pianeta selezionato.
     */
    private static Planet getPlanet(Star star) {
        for (int i = 0; i < star.getPlanets().size(); i++) {
            System.out.println(i + MSG_DOT + star.getPlanets().get(i).toString());
        }

        int planetChosen = InputData.readIntegerBetween(MSG_CHOOSE_A_PLANET, 0, star.getPlanets().size() - 1);
        
        return star.getPlanets().get(planetChosen);
    }

    /**
     * Ottiene una stella da un sistema stellare.
     *
     * @param starSystems La lista dei sistemi stellari.
     * @return La stella selezionata.
     */
    private static Star getStar(ArrayList<StarSystem> starSystems) {
        for (int i = 0; i < starSystems.size(); i++) {
            System.out.println(i + MSG_DOT + starSystems.get(i).getStar().toString());
        }

        int starChosen = InputData.readIntegerBetween(MSG_CHOOSE_A_STAR, 0, starSystems.size() - 1);
        return starSystems.get(starChosen).getStar();
    }

    /**
     * Stampa i pianeti di una stella.
     *
     * @param starSystems La lista dei sistemi stellari.
     */
    private static void printPlanetsOfStar(ArrayList<StarSystem> starSystems) {
        Star star = getStar(starSystems);

        for (Planet planet : star.getPlanets()) {
            System.out.println(planet.toString());
        }
    }

    /**
     * Stampa le stelle di un sistema stellare.
     *
     * @param starSystems La lista dei sistemi stellari.
     */
    private static void printStarOfStarSystem(ArrayList<StarSystem> starSystems) {
        StarSystem starSystem = getStarSystem(starSystems);
        System.out.println(starSystem.getStar().toString());
    }

    /**
     * Stampa tutti i sistemi stellari.
     *
     * @param starSystems La lista dei sistemi stellari.
     */
    private static void printAllStarSystems(ArrayList<StarSystem> starSystems) {
        for (StarSystem starSystem : starSystems) {
            System.out.println(starSystem.toString());
        }
    }

    /**
     * Salva i sistemi stellari su un file.
     *
     * @param starSystems La lista dei sistemi stellari.
     * @throws IOException Se si verifica un errore durante la scrittura del file.
     */
    private static void saveOnFile(ArrayList<StarSystem> starSystems) throws IOException {
        StringBuilder sb = new StringBuilder();

        for (StarSystem ss : starSystems) {
            sb.append(ss.getName()).append(" ").append(ss.getStar().getPosition().getDimensions()).append(LINE_BREAK);
            sb.append(STAR).append(SPACE);
            sb.append(ss.getStar().getName()).append(SPACE);
            sb.append(ss.getStar().getMass()).append(SPACE);
            sb.append(ss.getStar().getPosition().toString().replace(OPEN_BRACKET, "").replace(CLOSE_BRACKET, "").replace(COMMA, "")).append(SPACE);
            sb.append(ss.getName()).append(LINE_BREAK);

            for (Planet planet : ss.getStar().getPlanets()) {
                buildString(sb, PLANET, planet.getName(), planet.getMass(), planet.getPosition());
                sb.append(ss.getStar().getName()).append(LINE_BREAK);

                for (Moon moon : planet.getMoons()) {
                    buildString(sb, MOON, moon.getName(), moon.getMass(), moon.getPosition());
                    sb.append(planet.getName()).append(LINE_BREAK);
                }
            }
        }

        Files.write(new File(FILE).toPath(), sb.toString().getBytes());
    }

    /**
     * Costruisce una stringa per rappresentare un corpo celeste.
     *
     * @param sb Il StringBuilder in cui costruire la stringa.
     * @param type Il tipo di corpo celeste (stella, pianeta, luna).
     * @param name Il nome del corpo celeste.
     * @param mass La massa del corpo celeste.
     * @param position La posizione del corpo celeste.
     */
    private static void buildString(StringBuilder sb, String type, String name, double mass, Position position) {
        sb.append(type).append(SPACE);
        sb.append(name).append(SPACE);
        sb.append(mass).append(SPACE);
        sb.append(position.toString().replace(OPEN_BRACKET, "").replace(CLOSE_BRACKET, "").replace(COMMA, "")).append(SPACE);
    }
}