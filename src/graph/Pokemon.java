package graph;

import com.google.gson.Gson;
import json.PokemonForJson;

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
        this.pos = new GeoLocationClass(x,y, 0);
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setPos(GeoLocationClass pos) {
        this.pos = pos;
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

    public boolean LoadFromJson(String file) {
        try {
            Gson json = new Gson();
//            FileReader FR = new FileReader(file);
//            BufferedReader BR = new BufferedReader(FR);
            PokemonForJson PokemonFromJson = json.fromJson(file, PokemonForJson.class);
            this.type=PokemonFromJson.type;
            this.value= PokemonFromJson.value;
            String[]arr=PokemonFromJson.pos.split(",");
            this.pos=new GeoLocationClass(Double.parseDouble(arr[0]),Double.parseDouble(arr[1]),0);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String toString(){
        return "Type:  "+this.getType()+"  Value:   "+this.getValue()+"   pos:   "+this.pos;
    }
}
