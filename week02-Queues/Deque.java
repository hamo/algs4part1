public class Deque<Item> implements Iterable<Item> {
    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    private Node first;
    private Node last;
    private int total;

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        total = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return total == 0;
    }

    // return the number of items on the deque
    public int size() {
        return total;
    }

    // insert the item at the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.prev = null;
        first.next = oldfirst;
        if (oldfirst != null) {
            oldfirst.prev = first;
        }
        total++;

        if (last == null) {
            last = first;
        }
    }
    // insert the item at the end
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        Node n = new Node();
        n.item = item;
        n.next = null;
        total++;

        if (last == null) {
            last = n;
            first = last;
            last.prev = null;
        } else {
            Node oldlast = last;
            oldlast.next = n;
            oldlast.next.prev = oldlast;
            last = oldlast.next;
        }
    }

    // delete and return the item at the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        
        Node oldfirst = first;
        first = oldfirst.next;
        total--;
        
        if (first == null) {
            last = first;
        } else {
            first.prev = null;
        }
        return oldfirst.item;
    }

    // delete and return the item at the end
    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }

        Node oldlast = last;
        last = last.prev;
        total--;
        
        if (last == null) {
            first = null;
        } else {
            last.next = null;
        }
        return oldlast.item;
    }

    private class DequeIterator implements java.util.Iterator<Item> {
        private Node current = first;
        public boolean hasNext() {
            return current != null;
        }
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            Item i = current.item;
            current = current.next;
            return i;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // return an iterator over items in order from front to end
    public java.util.Iterator<Item> iterator() {
        return new DequeIterator();
    }
    // unit testing
    public static void main(String[] args) {
        Deque<String> d = new Deque<String>();
        d.addFirst("hello\n");
        StdOut.println(d.size());
        d.addFirst("hello\n");
        StdOut.println(d.size());

        d.removeFirst();
        StdOut.println(d.size());
        d.removeFirst();
        StdOut.println(d.size());


    }

}
