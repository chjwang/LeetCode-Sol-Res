package com.freetymekiyan.algorithms.Other;

import java.util.Arrays;

/**
Implement your own List/Stack/Map
 */
public class ListStackMap {

}

class MyList<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private E[] elements;
    private int size = 0;

    public MyList() {
//        elements = new E[DEFAULT_CAPACITY];
    }

    public void add(E e) {
        if (size == elements.length)
            ensureCapacity();
        elements[size++] = e;
    }

    public void ensureCapacity() {
        int newSize = elements.length*2;
        elements = Arrays.copyOf(elements,newSize);
    }

    public E get(int i) {
        if (i < 0 || i >= size)
            throw new IndexOutOfBoundsException("Index : " + i + " Size : " + size);
        return elements[i];
    }
}

class MyStack<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private E[] elements;
    private int size = 0;

    public MyStack() {
//        elements = new E[DEFAULT_CAPACITY];
    }

    public void push(E e) {
        if (size == elements.length)
            ensureCapacity();
        elements[size++] = e;
    }

    public void ensureCapacity() {
        int newSize = elements.length*2;
        elements = Arrays.copyOf(elements,newSize);
    }

    public E peek() {
        if (size == 0)
            return null;
        else
            return elements[size-1];
    }

    public E pop() {
        if (size == 0)
            return null;
        E top = elements[size-1];
        elements[--size] = null;
        return top;
    }
}


class MyEntry<K,V> {
    private final K key;
    private V value;

    public MyEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}

class MyMap<K,V> { // not hashing implementation
    private static final int DEFAULT_CAPACITY = 16;
    private MyEntry<K,V>[] entries;
    private int size = 0;

    public MyMap() {
        entries = new MyEntry[DEFAULT_CAPACITY];
    }

    public void put(K key, V value) {
        for (int i = 0; i < entries.length; i++) {
            if (entries[i].getKey().equals(key)) {
                entries[i].setValue(value);
                return;
            }
        }
        if (size == entries.length)
            ensureCapacity();
        entries[size++] = new MyEntry<K,V>(key,value);
    }

    public void ensureCapacity() {
        int newSize = entries.length*2;
        entries = Arrays.copyOf(entries,newSize);
    }

    public V get(K key) {
        for (int i = 0; i < entries.length; i++) {
            if (entries[i].getKey().equals(key)) {
                return entries[i].getValue();
            }
        }
        return null;
    }

}