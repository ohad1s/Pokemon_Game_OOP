package json;

public class AgentForJson {
    public int id;
    public double value;
    public int src;
    public int dest;
    public double speed;
    public String pos;

    public AgentForJson(int id, double value, int src, int dest, double speed, String pos) {
        this.id = id;
        this.value = value;
        this.src = src;
        this.dest = dest;
        this.speed = speed;
        this.pos = pos;
    }
}
