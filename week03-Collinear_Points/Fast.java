public class Fast {
    public static void main(String[] args) {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Point[] save = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            save[i] = p;
            p.draw();
        }

        for (int i = 0; i < N; i++) {
            Point[] ps = new Point[N];
            ps[0] = save[i];
            int y = 1;
            for (int x = 0; x < N; x++) {
                if (x == i)
                    continue;
                ps[y++] = save[x];
            }

            java.util.Arrays.sort(ps, 1, N, ps[0].SLOPE_ORDER);

            int lo, hi = 0;
            for (int j = 1; j < N - 2; j++) {
                if (j < hi) {
                    continue;
                }
                if (ps[0].slopeTo(ps[j]) == ps[0].slopeTo(ps[j+2])) {
                    lo = j;
                    for (hi = lo+3; hi < N; hi++) {
                        if (ps[0].slopeTo(ps[lo]) == ps[0].slopeTo(ps[hi])) {
                            continue;
                        } else {
                            break;
                        }
                    }
                } else {
                    continue;
                }

                Point[] tmp = new Point[hi - lo];
                for (int a = lo, b = 0; a < hi; a++, b++) {
                        tmp[b] = ps[a];
                }

                java.util.Arrays.sort(tmp);
                if (ps[0].compareTo(tmp[0]) < 0) {
                    StdOut.printf("%s", ps[0]);
                    for (Point p : tmp) {
                        StdOut.printf(" -> %s", p);
                    }
                    StdOut.println();
                    ps[0].drawTo(tmp[hi - lo - 1]);
                }
            }
        }
    }
}
