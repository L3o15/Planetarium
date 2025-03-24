package it.unibs.fp.planetarium;

import java.util.ArrayList;

public class PlanetariumMain {
    public static void main(String[] args) {
        ArrayList<StarSystem> starSystems = SystemBuilder.build("input.txt");

        System.out.println("Planetarium app!");

        for (StarSystem starSystem : starSystems) {
            System.out.println(starSystem.getPath("StarMain"));

        }
    }
}