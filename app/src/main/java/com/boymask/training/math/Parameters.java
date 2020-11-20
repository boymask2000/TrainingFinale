package com.boymask.training.math;

import java.io.Serializable;

class MathParameters implements Serializable {
    private int delay=1;
    private int numOps=1;
    private int numDigits=1;

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getNumOps() {
        return numOps;
    }

    public void setNumOps(int numOps) {
        this.numOps = numOps;
    }

    public int getNumDigits() {
        return numDigits;
    }

    public void setNumDigits(int numDigits) {
        this.numDigits = numDigits;
    }
}
