package graph;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import json.EdgeForJson;
import json.GraphJson;
import json.NodeForJson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Pokemon {
    private double value;
    private int type;
    private GeoLocationClass pos;

    /**
     * this method is the vertex constructor.
     * @param value
     * @param type
     *  @param x
     * @param y
     *
     */
    public Pokemon(double value, int type, double x, double y) {
        this.value = value;
        this.type = type;
        this.pos = new GeoLocationClass(x, y, 0);
    }

    public double getValue() {
        return value;
    }

    public int getType() {
        return type;
    }

    public double x() {
        return this.pos.x();
    }

    public double y() {
        return this.pos.y();
    }

    public GeoLocationClass getPos() {
        return pos;
    }

    public Pokemon LoadFromJson(String file) {
        try {
            Gson json = new Gson();
            FileReader FR = new FileReader(file);
            BufferedReader BR = new BufferedReader(FR);
            Pokemon PokemonFromJson = json.fromJson(BR, Pokemon.class);
            return PokemonFromJson;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String toString(){
        return "Type:  "+this.getType()+"  Value:   "+this.getValue()+"   pos:   "+this.x()+" "+this.y();
    }
}
