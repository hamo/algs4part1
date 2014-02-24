public class RandomizedQueue<Item> implements Iterable<Item> {

    private class ResizingArrayStack<Item> {
        private Item[] a;         // array of items
        private int N;            // number of elements on stack

        /**
         * Initializes an empty stack.
         */
        public ResizingArrayStack() {
            a = (Item[]) new Object[2];
        }

        /**
         * Is this stack empty?
         * @return true if this stack is empty; false otherwise
         */
        public boolean isEmpty() {
            return N == 0;
        }

        /**
         * Returns the number of items in the stack.
         * @return the number of items in the stack
         */
        public int size() {
            return N;
        }


        // resize the underlying array holding the elements
        private void resize(int capacity) {
            assert capacity >= N;
            Item[] temp = (Item[]) new Object[capacity];
            for (int i = 0; i < N; i++) {
                temp[i] = a[i];
            }
            a = temp;
        }

        /**
         * Adds the item to this stack.
         * @param item the item to add
         */
        public void push(Item item) {
            // double size of array if necessary
            if (N == a.length) resize(2*a.length);
            // add item
            a[N++] = item;
        }

        /**
         * Removes and returns the item most recently added to this stack.
         * @return the item most recently added
         * @throws java.util.NoSuchElementException if this stack is empty
         */
        public Item pop() {
            if (isEmpty()) throw new java.util.NoSuchElementException("Stack underflow");
            Item item = a[N-1];
            a[N-1] = null;                              // to avoid loitering
            N--;
            // shrink size of array if necessary
            if (N > 0 && N == a.length/4) resize(a.length/2);
            return item;
        }


        /**
         * Returns (but does not remove) the item most recently added to this stack.
         * @return the item most recently added to this stack
         * @throws java.util.NoSuchElementException if this stack is empty
         */
        public Item peek() {
            if (isEmpty()) throw new java.util.NoSuchElementException("Stack underflow");
            return a[N-1];
        }

        public void moveOneToEnd(int n) {
            if (n < 0 || n >= N) throw new java.util.NoSuchElementException();
            Item temp = a[n];
            a[n] = a[N-1];
            a[N-1] = temp;
        }

        public Item[] shuffle() {
            if (N == 0) return null;
            Item[] r = (Item[]) new Object[N];
            for (int i = 0; i < N; i++) {
                r[i] = a[i];
            }
            StdRandom.shuffle(r, 0, N-1);
            return r;
        }
    }

    private ResizingArrayStack<Item> stack;

    // construct an empty randomized queue
    public RandomizedQueue() {
        stack = new ResizingArrayStack<Item>();
    }

    // is the queue empty?
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    // return the number of items on the queue
    public int size() {
        return stack.size();
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException();
        stack.push(item);
    }

    // delete and return a random item
    public Item dequeue() {
        if (stack.isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        int rand = StdRandom.uniform(stack.size());
        stack.moveOneToEnd(rand);
        return stack.pop();
    }

    // return (but do not delete) a random item
    public Item sample() {
        if (stack.isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        int rand = StdRandom.uniform(stack.size());
        stack.moveOneToEnd(rand);
        return stack.peek();
    }
        
    // return an independent iterator over items in random order
    public java.util.Iterator<Item> iterator() {
        return new RandomizedIterator(stack.shuffle());
    }

    private class RandomizedIterator implements java.util.Iterator<Item> {
        private int i;
        private Item[] a;

        public RandomizedIterator() {
            throw new UnsupportedOperationException();
        }

        public RandomizedIterator(Item[] r) {
            a = r;
            if (r != null) {
                i = r.length;
            } else {
                i = 0;
            }
        }

        public boolean hasNext() {
            return i > 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            return a[--i];
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        for (int i = 0; i < 10; i++) {
            rq.enqueue(i);
        }

        java.util.Iterator<Integer> i1 = rq.iterator();
        java.util.Iterator<Integer> i2 = rq.iterator();

        for (int i = i1.next(); i1.hasNext(); i = i1.next()) {
            StdOut.println(i);
        }
        StdOut.println("--------");

        for (int i = i2.next(); i2.hasNext(); i = i2.next()) {
            StdOut.println(i);
        }
            



    }
}
