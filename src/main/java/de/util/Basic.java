package de.util;


import de.threads.ScaleTester;


public class Basic implements ScaleTester {
    int nCols, nRows;
    private float lookupValues[][];

    public void init(int nRows, int nCols, int nThreads) {
        this.nCols = nCols;
        this.nRows = nRows;
        lookupValues = new float[nRows][];
        for (int j = 0; j < nRows; j++) {
            lookupValues[j] = new float[nCols];
        }
    }

    public float[][] doCalc() {
        for (int i = 0; i < nCols; i++) {
            lookupValues[0][i] = 0;
        }
        for (int i = 0; i < nCols; i++) {
            for (int j = 1; j < nRows; j++) {
                float sinValue =
                        (float) Math.sin((i % 360) * Math.PI / 180.0);
                lookupValues[j][i] = sinValue * (float) i / 180.0f;
                lookupValues[j][i] +=
                        lookupValues[j - 1][i] * (float) j / 180.0f;
            }
        }
        return lookupValues;
    }
}
