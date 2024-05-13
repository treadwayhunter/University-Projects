package com.last_haven.game.utils;

import java.util.Random;

/**
 * A class purely for naming villagers. Contains static attributes and methods.
 */
public class Names {
    private static Random rand = new Random();
    private static final String[] names = {
            "Adelaide", "Alexander", "Amelia",
            "Amelie", "Arabella", "Archer", "Atticus",
            "Aurora", "Ava", "Beatrice", "Beckett", "Benjamin",
            "Bianca", "Blaise", "Callum", "Celeste", "Charlotte",
            "Clementine", "Cora", "Daphne", "Darcy", "Declan",
            "Delilah", "Elijah", "Eliza", "Ellis", "Elodie", "Eloise",
            "Emma", "Esther", "Ethan", "Everett", "Felix", "Finn",
            "Fletcher", "Flora", "Freya", "Gemma", "Genevieve", "Gideon",
            "Griffin", "Harper", "Heath", "Hugo", "Hunter", "Imogen", "Iris",
            "Isabella", "Isla", "James", "Jared", "Jasper", "Jude", "Juniper",
            "Joseph", "Knox", "Lachlan", "Landon", "Laura", "Lennox", "Leona",
            "Lily", "Logan", "Lucas", "Lucia", "Mabel", "Magnus", "Margot",
            "Matilda", "Matthew", "Mia", "Michael", "Milo", "Nadia", "Nash",
            "Noah", "Olivia", "Ophelia", "Orion", "Phoebe", "Quentin", "Reid",
            "Ronan", "Rory", "Rowan", "Seraphina", "Sienna", "Silas", "Sophia",
            "Sterling", "Sylvie", "Tessa", "Thane", "Thea", "Theo", "Tobias",
            "Vaughn", "Vivian", "William", "Zachary"
    };

    //TODO: This falls victim to the Birthday Paradox.
    /**
     * Gets a random name from the list of names.
     * @return The randomly generated name.
     */
    public static String getRandomName() {

        return names[rand.nextInt(names.length)];
    }
}
