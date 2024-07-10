package com.walking.lesson39_queue1.task3.structure;

import com.walking.lesson39_queue1.task3.exception.ElementNotFoundException;

import java.util.*;

public class LinkedList<E> implements Iterable<E> {
    private Node<E> first;
    private Node<E> last;
    private int size;
    private int modificationCounter;

    public LinkedList() {
    }

    public LinkedList(E element) {
        this.first = new Node<>(element);
        this.last = first;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public E getFirst() {
        if (isEmpty()) {
            throw new ElementNotFoundException();
        }

        return first.value;
    }

    public E getLast() {
        if (isEmpty()) {
            throw new ElementNotFoundException();
        }

        return last.value;
    }

    public void addFirst(E element) {
        Node<E> oldFirst = first;

        first = new Node<E>(element, oldFirst, null);

        if (first.isLast()) {
            last = first;
        } else {
            oldFirst.previous = first;
        }

        size++;
        modificationCounter++;
    }

    public void addLast(E element) {
        Node<E> oldLast = last;

        last = new Node<E>(element, null, oldLast);

        if (last.isFirst()) {
            first = last;
        } else {
            oldLast.next = last;
        }

        size++;
        modificationCounter++;
    }

    public E removeFirst() {
        if (isEmpty()) {
            throw new ElementNotFoundException();
        }

        E result = getFirst();
        deleteNode(first);
        modificationCounter++;

        return result;
    }

    public E removeLast() {
        if (isEmpty()) {
            throw new ElementNotFoundException();
        }

        E result = getLast();
        deleteNode(last);
        modificationCounter++;

        return result;
    }

    public void reverse() {
        if (size < 2) {
            return;
        }

        Node<E> current = first;

        while (current != null) {
            current.swapNextAndPrevious();
            current = current.previous;
        }

        Node<E> temp = first;
        first = last;
        last = temp;

        modificationCounter++;
    }

    public void deleteNode(Node<E> current) {
        if (current.isFirst()) {
            first = first.next;
        } else {
            current.previous.next = current.next;
        }

        if (current.isLast()) {
            last = last.previous;
        } else {
            current.next.previous = current.previous;
        }

        size--;
        modificationCounter++;
    }

    public Iterator<E> iterator() {
        return new LinkedListIterator<E>(this);
    }

    @Override
    public String toString() {
        StringJoiner result = new StringJoiner("] <> [", "first-> [", "] <-last (size=" + size + ")");
        Node<E> current = first;

        while (current != null) {
            result.add(current.value.toString());
            current = current.next;
        }

        return result.toString();
    }

    private static class Node<E> {
        private final E value;
        private Node<E> next;
        private Node<E> previous;

        public Node(E value) {
            this.value = value;
        }

        public Node(E value, Node<E> next, Node<E> previous) {
            this.value = value;
            this.next = next;
            this.previous = previous;
        }

        private boolean isFirst() {
            return previous == null;
        }

        private boolean isLast() {
            return next == null;
        }

        private void swapNextAndPrevious() {
            Node<E> temp = next;
            next = previous;
            previous = temp;
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
            current = linkedList.first;
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

            linkedList.deleteNode(previous);
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