package com.last_haven.game.utils;

import java.util.Random;

/**
 * A class for generating a simple noise function.
 * This can be used for terrain and resource generation.
 */
public class Noise {
    private final int width;
    private final int height;

    private final double[][] seed;

    public Noise(int width, int height) {
        this.width = width;
        this.height = height;
        seed = new double[width][height];
        Random rand = new Random();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                seed[x][y] = Math.random();
            }
        }
    }

    /**
     * Given a size x and size y, generates noise for the array.
     * @param x The horizontal length of the noise array.
     * @param y The vertical length of the noise array.
     * @return A random double, based on the noise. //TODO UPDATE
     */
    public double noise(double x, double y) {
        int x0 = (int) Math.floor(x);
        int x1 = (x0 + 1) % width;
        int y0 = (int) Math.floor(y);
        int y1 = (y0 + 1) % height;

        double sx = x - Math.floor(x);
        double sy = y - Math.floor(y);

        double n0 = interpolate(seed[x0][y0], seed[x1][y0], sx);
        double n1 = interpolate(seed[x0][y1], seed[x1][y1], sx);
        return interpolate(n0, n1, sy);
    }

    /**
     * helper function for noise generation.
     * @param start
     * @param end
     * @param weight
     * @return a double for noise.
     */
    private double interpolate(double start, double end, double weight) {
        return start + weight * (end - start);
    }

}
