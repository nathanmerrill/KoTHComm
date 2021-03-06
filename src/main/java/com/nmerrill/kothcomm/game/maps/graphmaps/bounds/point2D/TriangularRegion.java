package com.nmerrill.kothcomm.game.maps.graphmaps.bounds.point2D;

import com.nmerrill.kothcomm.game.maps.graphmaps.bounds.Region;
import com.nmerrill.kothcomm.game.maps.Point2D;

public final class TriangularRegion implements Region<Point2D> {
    private final double a;
    private final int s1, s2, s3, t1, t2, t3;

    private final Point2D startingPoint;

    public TriangularRegion(Point2D p0, Point2D p1, Point2D p2){
        if (p0.equals(p1) || p0.equals(p2) || p1.equals(p2)){
            throw new IllegalArgumentException("Points cannot be on the same location");
        }
        double area = .5*(
                - p1.getY() * p2.getX()
                + p0.getY() * (p2.getX() - p1.getX())
                + p0.getX() * (p1.getY() - p2.getY())
                + p1.getX() * p2.getY());
        a = 1/(2*area);
        s1 = p0.getY()*p2.getX() - p0.getX()*p2.getY();
        s2 = p2.getY() - p0.getY();
        s3 = p0.getX() - p2.getX();
        t1 = p0.getX()*p1.getY() - p0.getY()*p1.getX();
        t2 = p0.getY() - p1.getY();
        t3 = p1.getX() - p0.getX();
        startingPoint = p0;
    }

    public TriangularRegion(int size){
        this(new Point2D(0,0), new Point2D(0, size), new Point2D(size, 0));
    }

    @Override
    public Point2D startingPoint() {
        return startingPoint;
    }

    @Override
    public boolean outOfBounds(Point2D p) {
        double s = a * (s1 + s2*p.getX() + s3*p.getY());
        if (s < 0){
            return true;
        }
        double t = a * (t1 + t2*p.getX() + t3*p.getY());
        return t < 0 || 1-s-t < 0;
    }
}
