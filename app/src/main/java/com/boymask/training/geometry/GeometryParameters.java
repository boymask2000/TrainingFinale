package com.boymask.training.geometry;

import java.io.Serializable;

class GeometryParameters implements Serializable {

    private int delay = 1;
    private int numFigures = 1;
    private boolean withWords = false;

    public int getNumFigures() {
        return numFigures;
    }

    public void setNumFigures(int numFigures) {
        this.numFigures = numFigures;
    }


    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }


    public boolean isWithWords() {
        return withWords;
    }

    public void setWithWords(boolean withWords) {
        this.withWords = withWords;
    }

}
