package graph;

import com.google.gson.Gson;
import json.GraphJson;
import json.PokemonForJson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Game {
    public ArrayList<Pokemon> pokemons;
    public ArrayList<Agent> agents;
    public int ttl;
    public int score;
    public int moves;

    public Game() {
        this.pokemons = new ArrayList<>();
        this.agents = new ArrayList<>();
        this.ttl = 0;
        this.moves = 0;
        this.score = 0;
    }

    public boolean initPokemons(String pokemonsStr) {
        try {
            JSONObject pokJ = new JSONObject(pokemonsStr);
            JSONArray pokemonsJsonArr = pokJ.getJSONArray("Pokemons");
            for (int i = 0; i < pokemonsJsonArr.length(); i++) {
                JSONObject pok = pokemonsJsonArr.getJSONObject(i);
                JSONObject p = pok.getJSONObject("Pokemon");
                Pokemon my_pok = new Pokemon(0, 0, 0, 0);
                my_pok.setType((int) p.get("type"));
                my_pok.setValue((double) p.get("value"));
                String my_pos = (String) p.get("pos");
                String[] arr = my_pos.split(",");
                my_pok.setPos(new GeoLocationClass(Double.parseDouble(arr[0]), Double.parseDouble(arr[1]), 0));
                this.pokemons.add(my_pok);
            }
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean initAgents(String agentsStr) {
        try {
            JSONObject agJ = new JSONObject(agentsStr);
            JSONArray agentsJsonArr = agJ.getJSONArray("Agents");
            for (int i = 0; i < agentsJsonArr.length(); i++) {
                JSONObject aga = agentsJsonArr.getJSONObject(i);
                JSONObject a = aga.getJSONObject("Agent");
                Agent my_ag = new Agent(0, 0, 0, 0, 0, 0, 0);
                my_ag.setId((int) a.get("id"));
                my_ag.setValue((double) a.get("value"));
                my_ag.setSrc((int) a.get("src"));
                my_ag.setDest((int) a.get("dest"));
                my_ag.setSpeed((double) a.get("speed"));
                String my_pos = (String) a.get("pos");
                String[] arr = my_pos.split(",");
                my_ag.setPos(new GeoLocationClass(Double.parseDouble(arr[0]), Double.parseDouble(arr[1]), 0));
                this.agents.add(my_ag);
            }
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean initInfo(String infoStr) {
        try {
            JSONObject infoJ = new JSONObject(infoStr);
            JSONObject infoJsonArr = infoJ.getJSONObject("GameServer");
            this.score = infoJsonArr.getInt("grade");
            this.moves = infoJsonArr.getInt("moves");
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean UpdatePokemons(String pokemons) {
        try {
            Gson json = new Gson();
//            FileReader FR = new FileReader(file);
//            BufferedReader BR = new BufferedReader(FR);
            Game PokemonsFromJson = json.fromJson(pokemons, Game.class);
            this.pokemons = PokemonsFromJson.pokemons;
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean UpdateAgents(String agents) {
        try {
            Gson json = new Gson();
//            FileReader FR = new FileReader(file);
//            BufferedReader BR = new BufferedReader(FR);
            Game AgentsFromJson = json.fromJson(agents, Game.class);
            this.agents = AgentsFromJson.agents;
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void print_game() {
        for (Pokemon p : this.pokemons) {
            System.out.println(p);
        }
        for (Agent a : this.agents) {
            System.out.println(a);
        }
    }
}
