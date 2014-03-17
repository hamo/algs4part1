public class PointSET {
    private SET<Point2D> set;

    // construct an empty set of points
    public PointSET() {
        set = new SET<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return set.isEmpty();
    }

    // number of points in the set
    public int size() {
        return set.size();
    }                              

    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
        set.add(p);
        return;
    }

    // does the set contain the point p?
    public boolean contains(Point2D p) {
        return set.contains(p);
    }

    // draw all of the points to standard draw
    public void draw() {
        for (Point2D p : set) {
            p.draw();
        }
    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        SET<Point2D> re = new SET<Point2D>();
        for (Point2D p : set) {
            if (rect.contains(p)) {
                re.add(p);
            }
        }
        return re;
    }

    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
        if (set.isEmpty()) {
            return null;
        }

        double minD = Double.MAX_VALUE;
        Point2D re = null;
        for (Point2D sp : set) {
            double dis = sp.distanceTo(p);
            if (dis < minD) {
                minD = dis;
                re = sp;
            }
        }
        return re;
    }
}
