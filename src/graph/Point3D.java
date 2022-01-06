package graph;

public class Point3D {
    public static final double EPS = 0.0000000001;
    private double x;
    private double y;
    private double z;

    /**
     * this method is the constructor of GeoLocationClass.
     *
     * @param x
     * @param y
     * @param z
     */
    public Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * this is a copy constructor for another GeoLocation.
     *
     * @param other
     */
    public Point3D(Point3D other) {
        this.x = other.x();
        this.y = other.y();
        this.z = other.z();
    }

    /**
     * this method return the GeoLocation's x position.
     *
     * @return this.x
     */

    public double x() {
        return this.x;
    }

    /**
     * this method return the GeoLocation's y position.
     *
     * @return this.y
     */

    public double y() {
        return this.y;
    }

    /**
     * this method return the GeoLocation's z position.
     *
     * @return this.z
     */

    public double z() {
        return this.z;
    }

    /**
     * this method calculates the distance from another 3D point using the formula:
     * d = ((x2 - x1)^2 + (y2 - y1)^2 + (z2 - z1)^2)^1/2
     *
     * @param g
     * @return
     */

    public double distance(Point3D g) {
        double otherX = g.x();
        double otherY = g.y();
        double xDist = Math.abs(this.x - otherX);
        double yDist = Math.abs(this.y - otherY);

        double dist = Math.pow((Math.pow(xDist, 2) + Math.pow(yDist, 2)), 0.5);
        return dist;
    }

    public boolean isOnEdge(Point3D p1, Point3D p2) {
        double distP1ToP2 = p1.distance(p2);
        double distThisToP1 = this.distance(p1);
        double distThisToP2 = this.distance(p2);
        double totalDist = distThisToP1 + distThisToP2;
        return Math.abs(distP1ToP2 - totalDist) < EPS;
    }

    @Override
    public String toString() {
        return
                x + ", " + y;
    }


}
