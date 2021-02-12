package com.boymask.training.colors;

import java.io.Serializable;

class ColorParameters implements Serializable {

    private int delay = 1;
    private int numColors = 1;
    private boolean withWords = false;

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getNumColors() {
        return numColors;
    }

    public void setNumColors(int numColors) {
        this.numColors = numColors;
    }

    public boolean isWithWords() {
        return withWords;
    }

    public void setWithWords(boolean withWords) {
        this.withWords = withWords;
    }

}
