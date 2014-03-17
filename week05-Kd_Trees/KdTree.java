public class KdTree {
    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        public Node(Point2D point, RectHV rec) {
            p = point;
            rect = rec;
            lb = null;
            rt = null;
        }
    }
    
    private Node root;
    private int size;

    // construct an empty set of points
    public KdTree() {
        root = null;
        size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        return size;
    }                              

    private Node insert(Node n, Point2D p, boolean x, RectHV rect) {
        if (n == null) {
            size++;
            return new Node(p, rect);
        }
        if (n.p.compareTo(p) == 0)
            return n;

        double cmp = 0;
        RectHV rec = null;
        if (x)
            cmp = p.x() - n.p.x();
        else 
            cmp = p.y() - n.p.y();

        if (cmp < 0) {
            if (n.lb == null) {
                if (x)
                    rec = new RectHV(n.rect.xmin(), n.rect.ymin(), n.p.x(), n.rect.ymax());
                else
                    rec = new RectHV(n.rect.xmin(), n.rect.ymin(), n.rect.xmax(), n.p.y());
            }
            n.lb = insert(n.lb, p, !x, rec);
            
        } else {
            if (n.rt == null) {
                if (x) 
                    rec = new RectHV(n.p.x(), n.rect.ymin(), n.rect.xmax(), n.rect.ymax());
                else
                    rec = new RectHV(n.rect.xmin(), n.p.y(), n.rect.xmax(), n.rect.ymax());
            }
            n.rt = insert(n.rt, p, !x, rec);
        }

        return n;
    }

    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
        root = insert(root, p, true, new RectHV(0.0, 0.0, 1.0, 1.0));
    }

    private boolean contains(Node n, Point2D p, boolean x) {
        if (n == null) return false;
        if (n.p.compareTo(p) == 0) return true;

        double cmp = 0;
        if (x)
            cmp = p.x() - n.p.x();
        else 
            cmp = p.y() - n.p.y();

        if (cmp < 0)
            return contains(n.lb, p, !x);
        else
            return contains(n.rt, p, !x);
    }


    // does the set contain the point p?
    public boolean contains(Point2D p) {
        return contains(root, p, true);
    }

    private void draw(Node n, boolean x) {
        if (n == null)
            return;

        n.p.draw();
        if (x) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(n.p.x(), n.rect.ymin(), n.p.x(), n.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(n.rect.xmin(), n.p.y(), n.rect.xmax(), n.p.y());
        }

        draw(n.lb, !x);
        draw(n.rt, !x);
    }
            
    // draw all of the points to standard draw
    public void draw() {
        draw(root, true);
    }

    private void range(SET<Point2D> re, RectHV rect, Node n) {
        if (n == null)
            return;
        
        if (rect.contains(n.p))
            re.add(n.p);

        if (n.lb != null && rect.intersects(n.lb.rect))
            range(re, rect, n.lb);

        if (n.rt != null && rect.intersects(n.rt.rect))
            range(re, rect, n.rt);
    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        SET<Point2D> re = new SET<Point2D>();
        range(re, rect, root);
        return re;
    }

    private void nearest(Point2D[] re, double[] min, Node n, Point2D p, boolean x) {
        if (n == null)
            return;

        if (n.rect.distanceSquaredTo(p) > min[0])
            return;

        double dis = n.p.distanceSquaredTo(p);
        if (dis < min[0]) {
            min[0] = dis;
            re[0] = n.p;
        }

        boolean lb = false;

        if (x) {
            if (p.x() < n.p.x())
                lb = true;
        } else {
            if (p.y() < n.p.y())
                lb = true;
        }
        
        if (lb) {
            nearest(re, min, n.lb, p, !x);
            nearest(re, min, n.rt, p, !x);
        } else {
            nearest(re, min, n.rt, p, !x);
            nearest(re, min, n.lb, p, !x);
        }
    }
        
    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
        if (isEmpty())
            return null;

        double[] min = new double[1];
        min[0] = Double.MAX_VALUE;
        Point2D[] re = new Point2D[1];
        re[0] = null;

        nearest(re, min, root, p, true);

        return re[0];
    }

    public static void main(String[] args) {
        KdTree kd = new KdTree();
        In in = new In(args[0]);

        for (int n = 0; n < 10; n++) {
            double x = in.readDouble();
            double y = in.readDouble();
            kd.insert(new Point2D(x, y));
        }

        kd.draw();
    }
}
