package graph;


public class EdgeDataClass {
    private int src;
    private int dest;
    private double weight;
    private int tag;
    private String info;

    /**
     * this method is the EdgeData constructor.
     * @param src
     * @param dest
     * @param weight
     */
    public EdgeDataClass(int src, int dest, double weight){
        this.src = src;
        this.dest = dest;
        this.weight = weight;
        this.tag = 0;
        this.info = "src: " + src + ", dest: " + dest + ", weight: " + weight + ", tag: " +tag;
    }

    /**
     * this method returns the id of the edge's src vertex.
     * @return this.src
     */

    public int getSrc() {
        return this.src;
    }

    /**
     * this method returns the id of the edge's src vertex.
     * @return this.dest
     */

    public int getDest() {
        return this.dest;
    }

    /**
     * this method returns the edge's weight.
     * @return this.weight
     */

    public double getWeight() {
        return this.weight;
    }

    /**
     * this method returns the edge's info string.
     * @return this.info
     */

    public String getInfo() {
        return this.info;
    }

    /**
     * this method sets the edge's info string.
     * @param s - the new info String
     */

    public void setInfo(String s) {
        this.info = s;
    }

    /**
     * this method returns the edge's tag.
     * @return this.tag
     */

    public int getTag() {
        return this.tag;
    }

    /**
     * this method sets the edge's tag
     * @param t - the new value of the tag
     */

    public void setTag(int t) {
        this.tag = t;

    }
}
