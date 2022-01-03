package graph;

public class GeoLocationClass {
    private double x;
    private double y;
    private double z;

    /**
     * this method is the constructor of GeoLocationClass.
     * @param x
     * @param y
     * @param z
     */
    public GeoLocationClass(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * this is a copy constructor for another GeoLocation.
     * @param other
     */
    public GeoLocationClass(GeoLocationClass other){
        this.x = other.x();
        this.y = other.y();
        this.z = other.z();
    }

    /**
     * this method return the GeoLocation's x position.
     * @return this.x
     */

    public double x() {
        return this.x;
    }
    /**
     * this method return the GeoLocation's y position.
     * @return this.y
     */

    public double y() {
        return this.y;
    }
    /**
     * this method return the GeoLocation's z position.
     * @return this.z
     */

    public double z() {
        return this.z;
    }

    /**
     * this method calculates the distance from another 3D point using the formula:
     * d = ((x2 - x1)^2 + (y2 - y1)^2 + (z2 - z1)^2)^1/2
     * @param g
     * @return
     */

    public double distance(GeoLocationClass g) {
        double otherX = g.x();
        double otherY = g.y();
        double otherZ = g.z();
        double xDist = Math.abs(this.x - otherX);
        double yDist = Math.abs(this.y - otherY);
        double zDist = Math.abs(this.z - otherZ);

        double dist = Math.pow((Math.pow(xDist,2) + Math.pow(yDist, 2) + Math.pow(zDist, 2)), 0.5);
        return dist;
    }

    @Override
    public String toString() {
        return
                x + ", " + y;
    }
}
