package graph;

import com.google.gson.Gson;
import json.AgentForJson;

public class Agent {
    private int id;
    private double value;
    private int src;
    private int dest;
    private double speed;
    private Point3D pos;

    public Agent(int id, double value, int src, int dest, double speed, double x, double y) {
        this.id = id;
        this.value = value;
        this.src = src;
        this.dest = dest;
        this.speed = speed;
        this.pos = new Point3D(x, y, 0);
    }
    public Agent() {
        this.id = 0;
        this.value = 0;
        this.src = 0;
        this.dest = 0;
        this.speed = 0;
        this.pos = null;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setSrc(int src) {
        this.src = src;
    }

    public void setDest(int dest) {
        this.dest = dest;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setPos(Point3D pos) {
        this.pos = pos;
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

    public Point3D getPos() {
        return pos;
    }

    public double x() {
        return this.pos.x();
    }

    public double y() {
        return this.pos.y();
    }

    public boolean LoadFromJson(String file) {
        try {
            Gson json = new Gson();
            AgentForJson AgentFromJson = json.fromJson(file, AgentForJson.class);
            this.dest = AgentFromJson.dest;
            this.id = AgentFromJson.id;
            this.speed = AgentFromJson.speed;
            this.src = AgentFromJson.src;
            this.value = AgentFromJson.value;
            String[] arr = AgentFromJson.pos.split(",");
            this.pos = new Point3D(Double.parseDouble(arr[0]), Double.parseDouble(arr[1]), 0);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String toString(){
        return "  ID:   "+this.getId()+"src:  "+this.getSrc()+"  dest:   "+this.getDest()+"  Value:   "+this.getValue()+"  Speed:   "+this.getSpeed()+"   pos:   "+this.pos;
    }
}
