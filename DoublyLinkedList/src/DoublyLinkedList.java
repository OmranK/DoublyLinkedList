import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;


// Ask Professor
// Why cant my iterator.next() return Node?

public class DoublyLinkedList<T>
        extends AbstractSequentialList<T>
        implements List<T>, Deque<T>, Cloneable, Serializable {

    private static class Node<T> {
        public T data;
        public Node next;
        public Node previous;

        public Node (T data) {
            this.data = data;
        }
    }

    private Node nil;
    private int size;

    // Constructors
    public DoublyLinkedList() {
        nil = new Node(null);
        nil.previous = nil;
        nil.next = nil;
        size = 0;
    }

    public DoublyLinkedList(Collection<? extends T> collection) {
        this();
        addAll(collection);
    }

    // Clonable Interface Override Requirements

    @Override
    public Object clone() {
        DoublyLinkedList<T> copy = new DoublyLinkedList();
        copy.nil = this.nil;
        copy.size = this.size;
        return copy;
    }

    // List Interface Override Requirements

    @Override
    public boolean add(T t) {
        addLast(t);
        return true;
    }

    @Override
    public void add(int index, T value) {
        Node<T> newNode = new Node(value);
        Node<T> nextNode = getNodeAt(index);
        newNode.next = nextNode;
        newNode.previous = nextNode.previous;
        nextNode.previous.next = newNode;
        nextNode.previous = newNode;
        size++;
    }

    @Override
    public boolean addAll(Collection<? extends T> collection) {
        boolean addedObjects = false;
        for (T value : collection) {
            Node<T> newNode = new Node(value);
            addNodeToTail(newNode);
            addedObjects = true;
        }
        return addedObjects;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> collection) {
        boolean addedObjects = false;
        ListIterator iterator = listIterator(index);
        for (T value : collection) {
            iterator.add(value);
            addedObjects = true;
        }
        return addedObjects;
    }

    @Override
    public void clear() {
        while(size > 0) {
            removeLast();
        }
    }

    @Override
    public boolean contains(Object o) {
        Node<T> searchNode = nil.previous;
        while(searchNode != nil) {
            if (searchNode.data.equals(o)) {
                return true;
            }
            searchNode = searchNode.previous;
        }
        return false;
    }

    @Override
    public T get(int index) {
        Node<T> searchNode = getNodeAt(index);
        return searchNode.data;
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        Node<T> searchNode = nil.next;

        while(searchNode != nil) {
            if (searchNode.data.equals(o)) {
                return index;
            }
            index++;
            searchNode = searchNode.next;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int index = size - 1;
        Node<T> searchNode = nil.previous;

        while(searchNode != nil) {
            if (searchNode.data.equals(o)) {
                return index;
            }
            index--;
            searchNode = searchNode.previous;
        }
        return -1;
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        verifyIndex(index);
        return new LinkedListIterator(index);
    }

    @Override
    public T remove(int index) {
        Node<T> removedNode = getNodeAt(index);
        removedNode.next.previous = removedNode.previous;
        removedNode.previous.next = removedNode.next;
        size--;
        return removedNode.data;
    }

    @Override
    public boolean remove(Object o) {
        int returnIndex = indexOf(o);
        if (returnIndex >= 0) {
            remove(returnIndex);
            return true;
        }
        return false;
    }

    @Override
    public T set(int index, T element) {
        Node<T> searchNode = getNodeAt(index);
        T oldNodeData = searchNode.data;
        searchNode.data = element;
        return oldNodeData;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        int arrayIndex = 0;
        Node<T> searchNode = nil.next;
        while(searchNode != nil) {
            array[arrayIndex++] = searchNode.data;
            searchNode = searchNode.next;
        }
        return array;
    }

    @Override
    public <T> T[] toArray(T[] array) {
        if (size > array.length) {
            array = (T[]) Array.newInstance(array.getClass().getComponentType(), size);
        } else if (size < array.length){
            array[size] = null;
        }
        Node<T> searchNode = nil.next;
        for (int i = 0; i < size; i++) {
            array[i] = searchNode != nil ? searchNode.data : null;
            searchNode = searchNode.next;
        }
        return array;
    }

    // Deque Interface Method Requirements
    @Override
    public void addFirst(T value) {
        Node newNode = new Node(value);
        Node temp = nil.next;
        newNode.next = temp;
        temp.previous = newNode;
        newNode.previous = nil;
        nil.next = newNode;
        size++;
    }

    @Override
    public void addLast(T value) {
        Node newNode = new Node(value);
        newNode.next = nil;
        nil.previous.next = newNode;
        newNode.previous = nil.previous;
        nil.previous = newNode;
        size++;
    }

    @Override
    public Iterator<T> descendingIterator() {
        return new Iterator<T>() {
            private Node<T> next = nil.previous;
            private Node<T> lastReturned;
            private int position = size - 1;

            public boolean hasNext() {
                return next != nil;
            }

            public T next() {
                if (next == nil) {
                    throw new NoSuchElementException("No next element exists");
                }
                position--;
                lastReturned = next;
                next = lastReturned.previous;
                return lastReturned.data;
            }

            public void remove() {
                if (lastReturned == nil) {
                    throw new NoSuchElementException("No element to remove");
                }
                removeNode(lastReturned);
                lastReturned = null;
            }
        };
    }

    @Override
    public T element() {
        return getFirst();
    }

    @Override
    public T getFirst() {
        if ( isEmpty() ) {
            throw new NoSuchElementException("The list is empty");
        }
        return (T) nil.next.data;
    }

    @Override
    public T getLast() {
        if ( isEmpty() ) {
            throw new NoSuchElementException("The list is empty");
        }
        return (T) nil.previous.data;
    }

    @Override
    public boolean offer(T value) {
        return add(value);
    }

    @Override
    public boolean offerFirst(T value) {
        addFirst(value);
        return true;
    }

    @Override
    public boolean offerLast(T value) {
        return add(value);
    }

    @Override
    public T peek() {
        if ( isEmpty() ) {
            return null;
        }
        return getFirst();
    }

    @Override
    public T peekFirst() {
        return peek();
    }

    @Override
    public T peekLast() {
        if ( isEmpty() ) {
            return null;
        }
        return getLast();
    }

    @Override
    public T poll() {
        if ( isEmpty() ) {
            return null;
        }
        return removeFirst();
    }

    @Override
    public T pollFirst() {
        return poll();
    }

    @Override
    public T pollLast() {
        if ( isEmpty() ) {
            return null;
        }
        return removeLast();
    }

    @Override
    public T pop() {
        return removeLast();
    }

    @Override
    public void push(T value) {
        addLast(value);
    }

    @Override
    public T remove() {
        return removeFirst();
    }

    @Override
    public T removeFirst() {
        if( isEmpty() ) {
            throw new NoSuchElementException("The list is empty");
        }
        Node firstNode = nil.next;
        firstNode.next.previous = nil;
        nil.next = firstNode.next;
        firstNode.next = null;
        firstNode.previous = null;
        size--;
        return (T) firstNode.data;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        return remove(o);
    }

    @Override
    public T removeLast() {
        if( isEmpty() ) {
            throw new NoSuchElementException("The list is empty");
        }
        Node lastNode = nil.previous;
        lastNode.previous.next = nil;
        nil.previous = lastNode.previous;
        lastNode.next = null;
        lastNode.previous = null;
        size--;
        return (T) lastNode.data;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        Node<T> searchNode = nil.previous;
        while (searchNode != nil) {
            if (searchNode.data.equals(o)) {
                removeNode(searchNode);
                return true;
            }
            searchNode = searchNode.previous;
        }
        return false;
    }

    // Extra Override Methods
    @Override
    public boolean isEmpty() {
        return size == 0 ? true : false;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if(!(o instanceof DoublyLinkedList)) return false;
        DoublyLinkedList dList = (DoublyLinkedList) o;
        if(size!=dList.size()) return false;

        Iterator iterator = listIterator();
        Iterator dListIterator = dList.listIterator();
        while(iterator.hasNext()) {
            if(!iterator.next().equals(dListIterator.next())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        if ( isEmpty() ) {
            return "";
        }
        Node<T> searchNode = nil.next;
        String output = searchNode.data.toString();

        while(searchNode.next != nil) {
            searchNode = searchNode.next;
            output += (" ==> " + searchNode.data.toString());
        }
        return output;
    }


    // Helper Methods

    private void verifyIndex(int index) {
        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    private boolean closerToFront(int index) {
        verifyIndex(index);
        if (index < size / 2) {
            return true;
        }
        return false;
    }

    private Node getNodeAt(int index) {
        verifyIndex(index);
        Node<T> searchNode = nil;

        if (closerToFront(index)) {
            while (index-- >= 0) {
                searchNode = searchNode.next;
            }
        } else {
            while (++index <= size) {
                searchNode = searchNode.previous;
            }
        }
        return searchNode;
    }

    private void addNodeToTail(Node<T> newNode) {
        newNode.next = nil;
        nil.previous.next = newNode;
        newNode.previous = nil.previous;
        nil.previous = newNode;
        size++;
    }

    private void removeNode(Node<T> node) {
        node.next.previous = node.previous;
        node.previous.next = node.next;
        size--;
    }

    private final class LinkedListIterator<T> implements ListIterator<T> {
        private Node<T> next;
        private Node<T> previous;
        private Node<T> lastReturned;
        private int position;

        LinkedListIterator(int index) {
            next = getNodeAt(index);
            previous = next.previous;
            position = index;
        }

        public int nextIndex() {
            return position;
        }

        public int previousIndex() {
            return position - 1;
        }

        @Override
        public void remove() {
            size--;
            position--;
            next = lastReturned.next;
            previous = lastReturned.previous;
            removeNode( (Node) lastReturned);
            lastReturned = null;
        }

        @Override
        public void set(T value) {
            if (lastReturned == null) {
                throw new NoSuchElementException("No element to set");
            }
            lastReturned.data = value;
        }

        @Override
        public void add(T value) {
            size++;
            position++;
            Node<T> newNode = new Node(value);
            newNode.next = next;
            newNode.previous = previous;
            previous.next = newNode;
            next.previous = newNode;
            previous = newNode;
            lastReturned = null;
        }

        public boolean hasNext() {
            return (next != nil);
        }

        @Override
        public T next() {
            if (next == nil) {
                throw new NoSuchElementException("No next element exists.");
            }
            position++;
            lastReturned = previous = next;
            next = lastReturned.next;
            return lastReturned.data;
        }

        public boolean hasPrevious() {
            return (previous != nil);
        }

        @Override
        public T previous() {
            if (previous == nil) {
                throw new NoSuchElementException("No previous element exists.");
            }
            position--;
            lastReturned = next = previous;
            previous = lastReturned.previous;
            return lastReturned.data;
        }
    }
}
