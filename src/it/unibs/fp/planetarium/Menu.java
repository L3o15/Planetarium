package it.unibs.fp.planetarium;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Classe che rappresenta un menu di opzioni per la gestione di un planetario.
 * Contiene varie opzioni per visualizzare, aggiungere e rimuovere corpi celesti.
 *
 * @author Orizio Leonardo, Brumana Alberto, Loda Samuel
 * @version 1.0
 */
public class Menu {
    public static final String MSG_PRINT_STAR_SYSTEM = "Print all star systems";
    public static final String MSG_STAR_OF_A_STAR_SYSTEM = "Print the star of a star system";
    public static final String MSG_PRINT_ALL_PLANETS_OF_A_STAR = "Print all planets of a star";
    public static final String MSG_PRINT_ALL_MOONS_OF_A_PLANET = "Print all moons of a planet";
    public static final String MSG_PRINT_ALL_CELESTIAL_BODIES_IN_A_STAR_SYSTEM = "Print all celestial bodies in a star system";
    public static final String MSG_PRINT_CENTER_OF_MASS_OF_A_STAR_SYSTEM = "Print center of mass of a star system";
    public static final String MSG_PRINT_PATH_TO_A_CELESTIAL_BODY = "Print path to a celestial body";
    public static final String MSG_PRINT_PATH_BETWEEN_TWO_CELESTIAL_BODIES = "Print path between two celestial bodies";
    public static final String MSG_PRINT_DISTANCE_BETWEEN_TWO_CELESTIAL_BODIES = "Print distance between two celestial bodies";
    public static final String MSG_EXIT = "Exit";
    public static final String MSG_REMOVE_A_CELESTIAL_BODY = "Remove a celestial body";
    public static final String MSG_ADD_A_CELESTIAL_BODY = "Add a celestial body";
    public static final String MSG_PRINT_POSSIBLE_COLLISIONS = "Print possible collisions between two celestial bodies";
    public static final String MSG_ADD_NEW_STAR_SYSTEM = "Create a new star system";
    public static final String MSG_SELECT_AN_OPTION = "Select an option:\n";
    public static final String DP = ": ";
    public static final String MSG_SAVE_ON_FILE = "Save on file";
    public static final String MSG_GET_CELESTIAL_BODY_BY_NAME = "Get celestial body by name";
    public static final String MSG_GET_MOON_PLANET_DEPENDENCY = "Get moon planet dependency";

    private HashMap<Integer, String> menu = new HashMap<>();

    /**
     * Costruttore della classe Menu. Inizializza le opzioni del menu.
     */
    public Menu() {
        menu.put(1, MSG_PRINT_STAR_SYSTEM);
        menu.put(2, MSG_STAR_OF_A_STAR_SYSTEM);
        menu.put(3, MSG_PRINT_ALL_PLANETS_OF_A_STAR);
        menu.put(4, MSG_PRINT_ALL_MOONS_OF_A_PLANET);
        menu.put(5, MSG_PRINT_ALL_CELESTIAL_BODIES_IN_A_STAR_SYSTEM);
        menu.put(6, MSG_PRINT_CENTER_OF_MASS_OF_A_STAR_SYSTEM);
        menu.put(7, MSG_PRINT_PATH_TO_A_CELESTIAL_BODY);
        menu.put(8, MSG_PRINT_PATH_BETWEEN_TWO_CELESTIAL_BODIES);
        menu.put(9, MSG_PRINT_DISTANCE_BETWEEN_TWO_CELESTIAL_BODIES);
        menu.put(10, MSG_PRINT_POSSIBLE_COLLISIONS);
        menu.put(11, MSG_ADD_A_CELESTIAL_BODY);
        menu.put(12, MSG_REMOVE_A_CELESTIAL_BODY);
        menu.put(13, MSG_SAVE_ON_FILE);
        menu.put(14, MSG_ADD_NEW_STAR_SYSTEM);
        menu.put(15, MSG_GET_CELESTIAL_BODY_BY_NAME);
        menu.put(16, MSG_GET_MOON_PLANET_DEPENDENCY);
        menu.put(0, MSG_EXIT);
    }

    /**
     * Restituisce il menu delle opzioni.
     *
     * @return Un HashMap contenente le opzioni del menu.
     */
    public HashMap<Integer, String> getMenu() {
        return menu;
    }

    /**
     * Imposta un nuovo menu di opzioni.
     *
     * @param menu Un HashMap contenente le nuove opzioni del menu.
     */
    public void setMenu(HashMap<Integer, String> menu) {
        this.menu = menu;
    }

    /**
     * Mostra all'utente le opzioni disponibili e legge la scelta dell'utente.
     *
     * @return L'opzione scelta dall'utente.
     */
    public int getChoice() {
        Scanner in = new Scanner(System.in);
        System.out.println(MSG_SELECT_AN_OPTION);
        return in.nextInt();
    }

    /**
     * Visualizza tutte le opzioni disponibili nel menu.
     */
    public void display() {
        menu.forEach((k, v) -> System.out.println(k + DP + v));
    }
}
