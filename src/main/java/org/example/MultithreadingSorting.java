package org.example;

import java.util.concurrent.RecursiveAction;

public class MultithreadingSorting extends RecursiveAction {
    private int[] array;
    private int left;
    private int right;

    public MultithreadingSorting(int[] array, int left, int right) {
        this.array = array;
        this.left = left;
        this.right = right;
    }

    @Override
    protected void compute() {
        if (right <= left) {
            return;
        }

        int mid = (left + right) / 2;
        MultithreadingSorting leftPart = new MultithreadingSorting(array, left, mid);
        MultithreadingSorting rightPart = new MultithreadingSorting(array, mid + 1, right);
        invokeAll(leftPart, rightPart);
        merge(left, mid, right);
    }

    private void merge(int left, int mid, int right) {
        int[] temp = new int[right - left + 1];

        int leftIndex = left;
        int rightIndex = mid + 1;
        int tempIndex = 0;

        while (leftIndex <= mid && rightIndex <= right) {
            if (array[leftIndex] <= array[rightIndex]) {
                temp[tempIndex++] = array[leftIndex++];
            } else {
                temp[tempIndex++] = array[rightIndex++];
            }
        }

        while (leftIndex <= mid) {
            temp[tempIndex++] = array[leftIndex++];
        }

        while (rightIndex <= right) {
            temp[tempIndex++] = array[rightIndex++];
        }

        System.arraycopy(temp, 0, array, left, temp.length);
    }
}
