package com.boymask.training.math;

import java.util.Random;

import static java.lang.Thread.sleep;

class PairGenerator {
    private final int maxLength;
    private Random r = new Random();
    private int v1;
    private int v2;

    public PairGenerator(int maxLengh) {
        this.maxLength = maxLengh;
    }

    public void generate() {
        while (true) {
            v1 = rand(9)+1;
            v2 = rand(9)+1;

            if (v1 * v2 < maxLength * 10) break;
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    private int rand(int max) {
        int val = r.nextInt(max);

        return val;
    }

    public int getV1() {
        return v1;
    }

    public int getV2() {
        return v2;
    }
}
