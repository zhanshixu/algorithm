package org.example.algorithm.datastruct;

import java.util.Comparator;
import java.util.function.Consumer;

/**
 * @author zhanshixu
 * @since 2024-05-08
 */
public class BinarySearchTree<T> {
    /**
     * 比较器
     */
    private final Comparator<T> comparator;

    /**
     * 根节点
     */
    private Node<T> root;

    /**
     * 无参构造器
     */
    public BinarySearchTree() {
        this(null);
    }

    /**
     * 有参构造器
     * @param comparator 比较器
     */
    public BinarySearchTree(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    /**
     * 中序遍历
     * @param consumer 回调函数
     */
    public void traverse(Consumer<T> consumer) {
        traverse(root, consumer);
    }

    /**
     * 搜索
     * @param key key
     * @return node
     */
    public Node<T> search(T key) {
        if (key == null)
            return null;
        Node<T> p = root;
        int result;
        while (p != null && (result = compare(key, p.key)) != 0)
            p = result < 0 ? p.left : p.right;
        return p;
    }

    /**
     * 插入
     * @param key key
     * @return node
     */
    public Node<T> insert(T key) {
        if (key == null)
            throw new NullPointerException();
        Node<T> p1 = root,
                p2 = root;
        while (p2 != null) {
            p1 = p2;
            p2 = compare(key, p2.key) < 0 ? p2.left : p2.right;
        }
        Node<T> node = new Node<>();
        node.key = key;
        if (p1 == null) {
            root = node;
        } else {
            node.parent = p1;
            if (compare(key, p1.key) < 0)
                p1.left = node;
            else
                p1.right = node;
        }
        return node;
    }

    /**
     * 删除指定节点
     * @param node node
     */
    public void delete(Node<T> node) {
        if (node == null)
            return;
        if (node.left == null) {
            transplant(node, node.right);
        } else if (node.right == null) {
            transplant(node, node.left);
        } else {
            Node<T> replacement = node.right.minimum();
            if (replacement.parent != node) {
                transplant(replacement, replacement.right);
                replacement.right = node.right;
                replacement.right.parent = replacement;
            }
            transplant(node, replacement);
            replacement.left = node.left;
            replacement.left.parent = replacement;
        }
    }

    /**
     * 递归中序遍历
     * @param node node
     * @param consumer 回调函数
     */
    private void traverse(Node<T> node, Consumer<T> consumer) {
        if (node != null) {
            traverse(node.left, consumer);
            consumer.accept(node.key);
            traverse(node.right, consumer);
        }
    }

    /**
     * 移植
     * @param target 目标节点
     * @param source 源节点
     */
    private void transplant(Node<T> target, Node<T> source) {
        if (target.parent == null)
            root = source;
        else if (target == target.parent.left)
            target.parent.left = source;
        else
            target.parent.right = source;
        if (source != null)
            source.parent = target.parent;
    }

    /**
     * 默认使用比较器进行比较, 若比较器为空则将key强制转换为Comparable类型进行比较
     *
     * @param key1 key1
     * @param key2 key2
     *
     * @return 比较结果
     */
    @SuppressWarnings("unchecked")
    private int compare(T key1, T key2) {
        return comparator != null ? comparator.compare(key1, key2) :
                ((Comparable<T>)key1).compareTo(key2);
    }

    public static class Node<T> {
        /**
         * 父节点、左子节点、右子节点
         */
        private Node<T> parent, left, right;

        /**
         * key
         */
        private T key;

        /**
         * 获取key
         * @return key
         */
        public T getKey() {
            return key;
        }

        /**
         * 最小值
         * @return node
         */
        public Node<T> minimum() {
            Node<T> p1 = this,
                    p2 = left;
            while (p2 != null) {
                p1 = p2;
                p2 = p2.left;
            }
            return p1;
        }

        /**
         * 最大值
         * @return node
         */
        public Node<T> maximum() {
            Node<T> p1 = this,
                    p2 = right;
            while (p2 != null) {
                p1 = p2;
                p2 = p2.right;
            }
            return p1;
        }

        /**
         * 后继
         * @return node
         */
        public Node<T> successor() {
            if (right != null)
                return right.minimum();

            Node<T> p1 = this,
                    p2 = parent;
            while (p2 != null && p2.right == p1) {
                p1 = p2;
                p2 = p2.parent;
            }
            return p2;
        }

        /**
         * 前驱
         * @return node
         */
        public Node<T> predecessor() {
            if (left != null)
                return left.maximum();

            Node<T> p1 = this,
                    p2 = parent;
            while (p2 != null && p2.left == p1) {
                p1 = p2;
                p2 = p2.parent;
            }
            return p2;
        }
    }
}
