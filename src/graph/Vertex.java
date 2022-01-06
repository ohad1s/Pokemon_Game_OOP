package graph;

public class Vertex {
    private int key;
    private Point3D location;
    private double weight;
    private int tag;
    private String info;

    /**
     * this method is the vertex constructor.
     * @param key
     * @param x
     * @param y
     * @param z
     */
    public Vertex(int key, double x, double y, double z ){
        this.key = key;
        this.location = new Point3D(x, y, z);
        this.weight = 0;
        this.tag = 0;
        this.info = "key: " + key + ", location: (" + x + ", " + y + ", " + z + "), weight: " + weight + ", tag: " + tag;

    }

    public Vertex(Vertex other){
        this.key = other.getKey();
        double x = other.getLocation().x();
        double y = other.getLocation().y();
        double z = other.getLocation().z();
        this.location = new Point3D(x,y,z);
        this.weight = 0;
        this.tag = 0;
        this.info = "key: " + key + ", location: (" + x + ", " + y + ", " + z + "), weight: " + weight + ", tag: " + tag;
    }

    /**
     * this method returns the vertex's key.
     * @return key
     */

    public int getKey() {
        return this.key;
    }

    /**
     * this method returns the vertex's GeoLocation.
     * @return GeoLocation
     */
    public Point3D getLocation() {
        return this.location;
    }

    /**
     * this method sets vertex's GeoLocation based ont given GeoLocation.
     * @param p - other GeoLocation
     */

    public void setLocation(Point3D p) {
        this.location = p;
    }

    /**
     * this method returns the vertex's weight.
     * @return this.weight
     */

    public double getWeight() {
        return this.weight;
    }

    /**
     * this method sets the vertex's weight.
     * @return
     */

    public void setWeight(double w) {
        this.weight = w;
    }

    /**
     * this method returns the vertex's info String.
     * @return - this.info
     */

    public String getInfo() {
        return this.info;
    }

    /**
     * this method sets the vertex's info String
     * @param s
     */

    public void setInfo(String s) {
        this.info = s;
    }

    /**
     * this method returns the vertex's tag.
     * @return - this.tag
     */

    public int getTag() {
        return this.tag;
    }

    /**
     * this method sets the vertex's tag.
     * @param t - the new value of the tag
     */

    public void setTag(int t) {
        this.tag = t;
    }

}
