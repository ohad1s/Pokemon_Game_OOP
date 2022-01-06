package graph;

import com.google.gson.Gson;
import json.PokemonForJson;

public class Pokemon {
    private double value;
    private int type;
    private Point3D pos;
    Edge edge;


    /**
     * this method is the vertex constructor.
     * @param value
     * @param type
     *  @param x
     * @param y
     *
     */
    public Pokemon(double value, int type, double x, double y, Edge pokemonEdge) {
        this.value = value;
        this.type = type;
        this.pos = new Point3D(x,y, 0);
        this.edge = pokemonEdge;
    }

    public Pokemon(){
        this.value = 0;
        this.type = 0;
        this.pos = null;
        this.edge = null;
    }

    public void setEdge(Edge edge) {
        this.edge = edge;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setPos(Point3D pos) {
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

    public Edge getEdge() {
        return edge;
    }

    public Point3D getPos() {
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
            this.pos=new Point3D(Double.parseDouble(arr[0]),Double.parseDouble(arr[1]),0);
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
