package it.unibs.fp.planetarium;

import java.util.HashMap;
import java.util.Scanner;

public class Menu {
    private HashMap<Integer, String> menu = new HashMap<>();

    public Menu() {
        menu.put((Integer) 1, "Print all star systems");
        menu.put((Integer) 2, "Print the star of a star system");
        menu.put((Integer) 3, "Print all planets of a star");
        menu.put((Integer) 4, "Print all moons of a planet");
        menu.put((Integer) 5, "Print all celestial bodies in a star system");
        menu.put((Integer) 6, "Print center of mass of a star system");
        menu.put((Integer) 7, "Print path to a celestial body");
        menu.put((Integer) 8, "Print path between two celestial bodies");
        menu.put((Integer) 9, "Print distance between two celestial bodies");
        menu.put((Integer) 10, "Print possible collisions between two celestial bodies");
        menu.put((Integer) 11, "Add a celestial body");
        menu.put((Integer) 12, "Remove a celestial body");
        menu.put((Integer) 0, "Remove a celestial body");
    }

    public HashMap<Integer, String> getMenu() {
        return menu;
    }

    public void setMenu(HashMap<Integer, String> menu) {
        this.menu = menu;
    }

    public int getChoice() {
        Scanner in = new Scanner(System.in);

        System.out.println("Select an option:\n");

        return in.nextInt();
    }

    public void display() {
        menu.forEach((k, v) -> System.out.println(k + ": " + v + "\n"));
    }
}
