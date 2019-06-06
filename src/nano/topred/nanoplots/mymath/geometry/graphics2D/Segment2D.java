package nano.topred.nanoplots.mymath.geometry.graphics2D;

import nano.topred.nanoplots.mymath.geometry.graphics3D.Point3D;
import nano.topred.nanoplots.Position;

import java.util.ArrayList;
import java.util.UUID;

import static nano.topred.nanoplots.mymath.geometry.graphics2D.Point2D.determinant;

public class Segment2D {
    private Point2D p1;
    private Point2D p2;
    private ArrayList<Point2D> points;
    private double slope;
    private double yIntercept;
    private double length;

    public Segment2D(Point2D p1, Point2D p2)
    {
        this.p1 = p1;
        this.p2 = p2;
        this.slope = calcSlope();
        this.yIntercept = calcYIntercept();
        this.length = calcLenght();
        if (length<100)
            this.points = bresenham(this);
        else
            this.points = null;

    }

    public double calcLenght()
    {
        double deltaX = this.p2.getX()-this.p1.getX();
        double deltaY = this.p2.getY()-this.p1.getY();
        return(Math.sqrt(deltaX*deltaX+deltaY*deltaY));
    }

    public double calcSlope()
    {
        double deltaX = this.p2.getX()-this.p1.getX();
        double deltaY = this.p2.getY()-this.p1.getY();
        return deltaY/deltaX;
    }

    public double calcYIntercept()
    {
        double Y = this.p1.getY();
        double X = this.p1.getX();
        return Y - this.slope*X;
    }

    /*
    public static int orientation(Point2D p, Point2D q, Point2D r) {
        double val = (q.getY() - p.getY()) * (r.getX() - q.getX())
                - (q.getX() - p.getX()) * (r.getY() - q.getY());

        if (val == 0.0)
            return 0;
        return (val > 0) ? 1 : 2;
    }

    private static boolean onSegment(Point2D p, Point2D q, Point2D r)
    {
        if (q.getX() <= Math.max(p.getX(), r.getX()) && q.getX() >= Math.min(p.getX(), r.getX()) &&
                q.getY() <= Math.max(p.getY(), r.getY()) && q.getY() >= Math.min(p.getY(), r.getY()))
            return true;

        return false;
    }

    public static boolean intersect(Segment2D s1, Segment2D s2) {
        Point2D p1 = s1.getP1();
        Point2D p2 = s1.getP2();
        Point2D q1 = s2.getP1();
        Point2D q2 = s2.getP2();
        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        if (o1 != o2 && o3 != o4)
            return true;

        // Special Cases
        // p1, q1 and p2 are colinear and p2 lies on segment p1q1
        if (o1 == 0 && onSegment(p1, p2, q1)) return true;

        // p1, q1 and q2 are colinear and q2 lies on segment p1q1
        if (o2 == 0 && onSegment(p1, q2, q1)) return true;

        // p2, q2 and p1 are colinear and p1 lies on segment p2q2
        if (o3 == 0 && onSegment(p2, p1, q2)) return true;

        // p2, q2 and q1 are colinear and q1 lies on segment p2q2
        if (o4 == 0 && onSegment(p2, q1, q2)) return true;

        return false; // Doesn't fall in any of the above cases

    }
    */






    /*
    public static int orientation(Point2D p, Point2D q, Point2D r) {
        double val = (q.getY() - p.getY()) * (r.getX() - q.getX())
                - (q.getX() - p.getX()) * (r.getY() - q.getY());

        if (val == 0.0)
            return 0; // colinear
        return (val > 0) ? 1 : 2; // clock or counterclock wise
    }

    public static boolean intersect(Segment2D s1, Segment2D s2) {
        Point2D p1 = s1.getP1();
        Point2D q1 = s2.getP1();
        Point2D p2 = s1.getP2();
        Point2D q2 = s2.getP2();



        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        if (o1 != o2 && o3 != o4)
            return true;

        return false;
    }
    */


    public static boolean doBoundingBoxesIntersect(Segment2D a, Segment2D b) {
        Rectangle2D ra = new Rectangle2D(a);
        Rectangle2D rb = new Rectangle2D(b);
        return rb.isInside(ra);
    }

    public static final double EPSILON = 0.000001;

    public static boolean isPointOnLine(Segment2D a, Point2D b) {
        // Move the image, so that a.first is on (0|0)
        Segment2D aTmp = new Segment2D(new Point2D(0, 0), new Point2D(
                a.p2.getX() - a.p1.getX(), a.p2.getY() - a.p1.getY()));
        Point2D bTmp = new Point2D(b.getX() - a.p1.getX(), b.getY() - a.p1.getY());
        double r = determinant(aTmp.p2, bTmp);
        return Math.abs(r) < EPSILON;
    }

    public static boolean isPointRightOfLine(Segment2D a, Point2D b) {
        // Move the image, so that a.first is on (0|0)
        Segment2D aTmp = new Segment2D(new Point2D(0, 0), new Point2D(
                a.p2.getX() - a.p1.getX(), a.p2.getY() - a.p1.getY()));
        Point2D bTmp = new Point2D(b.getX() - a.p1.getX(), b.getY() - a.p1.getY());
        return determinant(aTmp.p2, bTmp) < 0;
    }




    public static boolean lineSegmentTouchesOrCrossesLine(Segment2D a,
                                                   Segment2D b) {
        return isPointOnLine(a, b.p1)
                || isPointOnLine(a, b.p2)
                || (isPointRightOfLine(a, b.p1) ^
                isPointRightOfLine(a, b.p2));
    }

    public static boolean intersect(Segment2D a, Segment2D b) {
        return doBoundingBoxesIntersect(a, b)
                && lineSegmentTouchesOrCrossesLine(a, b)
                && lineSegmentTouchesOrCrossesLine(b, a);
    }


    public static Segment2D switcharoo(Segment2D a)
    {
        return new Segment2D(a.p2, a.p1);
    }

    public String toString()
    {
        return "p1:"+this.p1.toString()+"  p2:"+this.p2.toString();
    }

    public ArrayList<Position> toPositions(double y, UUID worldID)
    {

        ArrayList<Position> positions = new ArrayList<>();
        if (this.length>100000)
        {
            System.out.println("Segment to big to draw");
            return positions;
        }
        if(this.points==null)
        {
            this.points = this.bresenham(this);
        }


        for (Point2D p2D: this.points)
            positions.add(new Position(new Point3D(p2D.getX(), y, p2D.getY()),worldID));
        return positions;
    }

    public static ArrayList<Point2D> bresenham(Segment2D segment2D)
    {
        Point2D pos0 = segment2D.p1;
        Point2D pos1 = segment2D.p2;
        return Point2D.bresenham(pos0,pos1);
    }

    public Line2D getLine()
    {
        return new Line2D(this.slope,this.yIntercept);
    }

    public Point2D getP1() {
        return p1;
    }

    public void setP1(Point2D p1) {
        this.p1 = p1;
    }

    public Point2D getP2() {
        return p2;
    }

    public void setP2(Point2D p2) {
        this.p2 = p2;
    }

    public double getSlope()
    {
        return this.slope;
    }

    public void setSlope(double s)
    {
        this.slope = s;
    }

    public double getyIntercept() {
        return yIntercept;
    }

    public void setyIntercept(double yIntercept) {
        this.yIntercept = yIntercept;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public ArrayList<Point2D> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<Point2D> points) {
        this.points = points;
    }
}
