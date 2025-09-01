package org.example;

import java.util.concurrent.RecursiveAction;

public class ForkBlur extends RecursiveAction {
    private final int[] source;
    private final int start;
    private final int length;
    private final int[] destination;
    private static final int BLUR_WIDTH = 15;
    private static final int THRESHOLD = 100000;

    public ForkBlur(int[] source, int start, int length, int[] destination) {
        this.source = source;
        this.start = start;
        this.length = length;
        this.destination = destination;
    }

    @Override
    protected void compute() {
        if (length < THRESHOLD) {
            computeDirectly();
        } else {
            int split = length / 2;
            invokeAll(
                    new ForkBlur(source, start, split, destination),
                    new ForkBlur(source, start + split, length - split, destination)
            );
        }
    }

    private void computeDirectly() {
        int sidePixels = (BLUR_WIDTH - 1) / 2;
        for (int index = start; index < start + length; index++) {
            float rt = 0, gt = 0, bt = 0;
            for (int mi = -sidePixels; mi <= sidePixels; mi++) {
                int mindex = Math.min(Math.max(mi + index, 0), source.length - 1);
                int pixel = source[mindex];
                rt += ((pixel & 0x00ff0000) >> 16) / (float) BLUR_WIDTH;
                gt += ((pixel & 0x0000ff00) >> 8) / (float) BLUR_WIDTH;
                bt += (pixel & 0x000000ff) / (float) BLUR_WIDTH;
            }
            int dpixel = (0xff000000) |
                    (((int) rt) << 16) |
                    (((int) gt) << 8) |
                    ((int) bt);
            destination[index] = dpixel;
        }
    }
}
