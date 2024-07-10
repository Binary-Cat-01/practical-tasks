package com.walking.lesson39_queue1.task1.structure;

import com.walking.lesson39_queue1.task1.exception.ElementNotFoundException;

import java.util.*;

public class LinkedList<E> implements Iterable<E> {
    private Node<E> top;
    private int size;
    private int modificationCounter;

    public LinkedList() {
    }

    public LinkedList(E topValue) {
        this.top = new Node<>(topValue);
        size++;
    }

    public E getTop() {
        return top.value;
    }

    public int getSize() {
        return size;
    }

    public boolean add(E element) {
        top = new Node<>(element, top);
        size++;
        modificationCounter++;

        return true;
    }

    public void delete(Object object) {
        if (top == null) {
            throw new ElementNotFoundException();
        }

        if (Objects.equals(object, top.value)) {
            top = top.next;
            size--;
            modificationCounter++;

            return;
        }

        Node<E> current = top;

        while (current.next != null) {
            if (Objects.equals(current.next.value, object)) {
                current.next = current.next.next;
                size--;
                modificationCounter++;

                return;
            }

            current = current.next;
        }
    }

    public void reverse() {
        if (size < 2) {
            return;
        }

        Node<E> nextTop = null;
        Node<E> previousTop = null;

        while (top.next != null) {
            nextTop = top.next;
            top.next = previousTop;
            previousTop = top;
            top = nextTop;
        }

        top.next = previousTop;
        modificationCounter++;
    }

    public void alternativeReverse() {
        if (size < 2) {
            return;
        }

        LinkedList<E> reversed = new LinkedList<>();

        while (top != null) {
            reversed.add(top.value);
            delete(top.value);
        }

        top = reversed.top;
        size = reversed.size;
        modificationCounter++;
    }

    public Iterator<E> iterator() {
        return new LinkedListIterator<>(this);
    }

    @Override
    public String toString() {
        StringJoiner result = new StringJoiner(",", "TOP -> ", " (size = " + size + ")");

        Node<E> current = top;

        while (current != null) {
            result.add(current.value.toString());
            current = current.next;
        }

        return result.toString();
    }

    private static class Node<E> {
        private final E value;
        private Node<E> next;

        public Node(E value) {
            this.value = value;
        }

        public Node(E value, Node<E> next) {
            this.value = value;
            this.next = next;
        }
    }

    private static class LinkedListIterator<E> implements Iterator<E> {
        private final LinkedList<E> linkedList;
        private boolean canBeRemoved = false;
        private int modificationCounter;
        private Node<E> current;
        private Node<E> previous;

        public LinkedListIterator(LinkedList<E> linkedList) {
            this.linkedList = linkedList;
            current = linkedList.top;
            this.modificationCounter = linkedList.modificationCounter;
        }

        @Override
        public boolean hasNext() {
            checkConcurrentModification();

            return current != null;
        }

        @Override
        public E next() {
            checkConcurrentModification();

            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            previous = current;
            current = current.next;
            canBeRemoved = true;

            return previous.value;
        }

        @Override
        public void remove() {
            checkConcurrentModification();

            if (!canBeRemoved) {
                throw new IllegalStateException();
            }

            linkedList.delete(previous.value);
            modificationCounter++;

            canBeRemoved = false;
        }

        private void checkConcurrentModification() {
            if (modificationCounter != linkedList.modificationCounter) {
                throw new ConcurrentModificationException();
            }
        }
    }
}