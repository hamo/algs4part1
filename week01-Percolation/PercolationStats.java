public class PercolationStats {
    private double[] result;
    
    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        result = new double[T];
        for (int i = 0; i < T; i++) {
            Percolation p = new Percolation(N);
            int times = 0;
            while (true) {
                int ri = StdRandom.uniform(N) + 1;
                int rj = StdRandom.uniform(N) + 1;
                if (p.isOpen(ri, rj)) {
                    continue;
                }
                p.open(ri, rj);
                times++;
                if (p.percolates()) {
                    break;
                }
            }
            result[i] = (double) times/(N * N);
        }
    }
    // sample mean of percolation threshold
    public double mean() {
        double sum = 0;
        for (int i = 0; i < result.length; i++) {
            sum += result[i];
        }
        return sum/result.length;
    }
    // sample standard deviation of percolation threshold
    public double stddev() {
        double m = mean();
        double plus = 0;
        for (int i = 0; i < result.length; i++) {
            plus += (result[i] - m) * (result[i] - m);
        }
        return Math.sqrt(plus / (result.length - 1));
    }
    // returns lower bound of the 95% confidence interval
    public double confidenceLo() {
        double m = mean();
        double s = stddev();
        return m - 1.96*s/Math.sqrt(result.length);
    }

    // returns upper bound of the 95% confidence interval
    public double confidenceHi() {
        double m = mean();
        double s = stddev();
        return m + 1.96*s/Math.sqrt(result.length);
    }
    
    // test client, described below
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        
        PercolationStats p = new PercolationStats(n, t);

        StdOut.printf("%20s = %f\n", "mean", p.mean());
        StdOut.printf("%20s = %f\n", "stddev", p.stddev());
        StdOut.printf("%20s = %f, %f\n", "95% confidence interval",
                      p.confidenceLo(), p.confidenceHi());
    }
}
