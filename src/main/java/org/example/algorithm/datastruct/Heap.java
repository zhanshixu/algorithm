package org.example.algorithm.datastruct;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author zhanshixu
 * @since 2024-05-08
 */
public class Heap<T> {

    private final Comparator<T> comparator;

    private List<T> keys = new ArrayList<>();

    public Heap() {
        comparator = null;
    }

    public Heap(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public Heap(List<T> keys) {
        comparator = null;
        this.keys.addAll(keys);
        buildMaxHeap();
    }

    public Heap(List<T> keys, Comparator<T> comparator) {
        this.comparator = comparator;
        this.keys.addAll(keys);
        buildMaxHeap();
    }

    public T top() {
        return keys.get(0);
    }

    public T extract() {
        if (keys.isEmpty())
            return null;
        T max = keys.get(0);
        T last = keys.remove(keys.size() - 1);
        if (!keys.isEmpty()) {
            keys.set(0, last);
        }
        maxHeapify(0);
        return max;
    }

    public void insert(T key) {
        if (key == null)
            throw new NullPointerException();
        if (keys.isEmpty()) {
            keys.add(key);
        } else {
            T last = keys.get(keys.size() - 1);
            keys.add(compare(last, key) < 0 ? last : key);
        }
        increaseKey(keys.size() - 1, key);
    }

    private static int parent(int index) {
        return (index - 1) >> 1;
    }

    private static int left(int index) {
        return (index << 1) + 1;
    }

    private static int right(int index) {
        return (index + 1) << 1;
    }

    private void maxHeapify(int index) {
        int left = left(index);
        int right = right(index);
        int largest = index;
        if (left < keys.size() && compare(keys.get(left), keys.get(largest)) > 0)
            largest = left;
        if (right < keys.size() && compare(keys.get(right), keys.get(largest)) > 0)
            largest = right;
        if (largest != index) {
            swap(largest, index);
            maxHeapify(largest);
        }
    }

    private void buildMaxHeap() {
        int n = (keys.size() - 1) >> 1;
        for (int i = n; i >= 0; i--) {
            maxHeapify(i);
        }
    }

    private void increaseKey(int index, T key) {
        if (key == null || compare(key, keys.get(index)) < 0)
            throw new RuntimeException("New key is smaller than current key.");
        keys.set(index, key);
        while (index > 0 && compare(keys.get(parent(index)), keys.get(index)) < 0) {
            swap(index, parent(index));
            index = parent(index);
        }
    }

    @SuppressWarnings("unchecked")
    private int compare(T key1, T key2) {
        return comparator != null ? comparator.compare(key1, key2) :
                ((Comparable<T>)key1).compareTo(key2);
    }

    private void swap(int index1, int index2) {
        T temp = keys.get(index1);
        keys.set(index1, keys.get(index2));
        keys.set(index2, temp);
    }
}
