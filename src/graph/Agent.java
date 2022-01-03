package graph;

import com.google.gson.Gson;
import json.AgentForJson;
import json.PokemonForJson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

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

    public boolean LoadFromJson(String file) {
        try {
            Gson json = new Gson();
            FileReader FR = new FileReader(file);
            BufferedReader BR = new BufferedReader(FR);
            AgentForJson AgentFromJson = json.fromJson(BR, AgentForJson.class);
            this.dest = AgentFromJson.dest;
            this.id = AgentFromJson.id;
            this.speed = AgentFromJson.speed;
            this.src = AgentFromJson.src;
            this.value = AgentFromJson.value;
            String[] arr = AgentFromJson.pos.split(",");
            this.pos = new GeoLocationClass(Double.parseDouble(arr[0]), Double.parseDouble(arr[1]), 0);
            return true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String toString(){
        return "  ID:   "+this.getId()+"src:  "+this.getSrc()+"  dest:   "+this.getDest()+"  Value:   "+this.getValue()+"  Speed:   "+this.getSpeed()+"   pos:   "+this.pos;
    }
}
