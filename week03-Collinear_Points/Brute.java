public class Brute {
    public static void main(String[] args) {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Point[] ps = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            ps[i] = p;
            p.draw();
        }

        java.util.Arrays.sort(ps);

        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                for (int k = j + 1; k < N; k++) {
                    for (int l = k + 1; l < N; l++) {
                        if (ps[i].slopeTo(ps[j]) == ps[i].slopeTo(ps[k]) && ps[i].slopeTo(ps[k]) == ps[i].slopeTo(ps[l])) {
                            StdOut.printf("%s -> %s -> %s -> %s\n", ps[i].toString(), ps[j].toString(), ps[k].toString(), ps[l].toString());
                            ps[i].drawTo(ps[l]);
                        }
                    }
                }
            }
        }

        StdDraw.show(0);
    }
}
