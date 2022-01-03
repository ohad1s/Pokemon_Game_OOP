package graph;

public class Agent {
    private int id;
    private double value;
    private int src;
    private int dest;
    private double speed;
    private GeoLocationClass pos;

    public Agent(int id, double value, int src, int dest, double speed, double x, double y) {
        this.id = id;
        this.value = value;
        this.src = src;
        this.dest = dest;
        this.speed = speed;
        this.pos = new GeoLocationClass(x, y, 0);
    }

    public int getId() {
        return id;
    }

    public double getValue() {
        return value;
    }

    public int getSrc() {
        return src;
    }

    public int getDest() {
        return dest;
    }

    public double getSpeed() {
        return speed;
    }

    public GeoLocationClass getPos() {
        return pos;
    }

    public double x() {
        return this.pos.x();
    }

    public double y() {
        return this.pos.y();
    }
}
