package org.example.algorithm.util;

import java.util.List;

/**
 * @author zhanshixu
 * @since 2024-05-08
 */
public class SortUtil {
    /**
     * 插入排序
     * @param keys 要排序的数组
     */
    public static <T extends Comparable<T>> void insertSort(List<T> keys) {
        for (int i = 1; i < keys.size(); i++) {
            for (int j = (i - 1); j >= 0; j--) {
                if (keys.get(j).compareTo(keys.get(j + 1)) < 0) {
                    break;
                }
                swap(keys, j, j + 1);
            }
        }
    }

    /**
     * 堆排序
     * @param keys 要排序的数组
     */
    public static <T extends Comparable<T>> void heapSort(List<T> keys) {
        buildMaxHeap(keys);
        int size = keys.size();
        while (size > 1) {
            swap(keys, 0, size - 1);
            maxHeapify(keys, --size, 0);
        }
    }

    /**
     * 交换数组中的两个元素
     * @param keys 数组
     * @param x 元素1下标
     * @param y 元素2下标
     */
    private static <T> void swap(List<T> keys, int x, int y) {
        T temp = keys.get(x);
        keys.set(x, keys.get(y));
        keys.set(y, temp);
    }

    /**
     * 最大堆化
     * @param keys 数组
     * @param size 堆化长度
     * @param index 下标
     */
    private static <T extends Comparable<T>> void maxHeapify(List<T> keys, int size, int index) {
        int left = left(index);
        int right = right(index);
        int largest = index;
        if (left < size && keys.get(left).compareTo(keys.get(largest)) > 0)
            largest = left;
        if (right < size && keys.get(right).compareTo(keys.get(largest)) > 0)
            largest = right;
        if (largest != index) {
            swap(keys, largest, index);
            maxHeapify(keys, size, largest);
        }
    }

    /**
     * 构建最大堆
     * @param keys 数组
     */
    private static <T extends Comparable<T>> void buildMaxHeap(List<T> keys) {
        int n = (keys.size() - 1) >> 1;
        for (int i = n; i >= 0; i--) {
            maxHeapify(keys, keys.size(), i);
        }
    }

    /**
     * 堆左子节点下标
     * @param index 当前节点下标
     */
    private static int left(int index) {
        return (index << 1) + 1;
    }

    /**
     * 堆右子节点下标
     * @param index 当前节点下标
     */
    private static int right(int index) {
        return (index + 1) << 1;
    }
}
