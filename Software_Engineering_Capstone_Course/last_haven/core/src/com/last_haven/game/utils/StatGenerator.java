package com.last_haven.game.utils;

import java.util.Random;

/**
 * Generates stats using a Gaussian distribution with given Mean and Standard Deviation.
 */
public class StatGenerator {
    /**
     * The random class provided by Java.
     */
    static Random rand = new Random();

    /**
     * Generates a number on a normal curve, then converts the number to an integer.
     * @param mean The desired mean of the Gaussian distribution.
     * @param stddev The desired standard deviation of the Gaussian distribution.
     * @return Returns the output of the gaussian distribution as an int. This is used as the stats for villagers.
     */
    public static int generate(int mean, int stddev) {
        return (int)rand.nextGaussian(mean, stddev);
    }

}
