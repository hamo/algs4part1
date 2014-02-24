public class Subset {
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            rq.enqueue(StdIn.readString());
        }

        for (int i = 0; i < Integer.parseInt(args[0]); i++) {
            StdOut.println(rq.dequeue());
        }
    }
}
