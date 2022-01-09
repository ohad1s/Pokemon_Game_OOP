package graph;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

public class Game {
    public Hashtable<Integer, Edge> agentDestEdge;
    public Hashtable<Integer, List<Vertex>> agentPath;
    public ArrayList<Pokemon> pokemons;
    public ArrayList<Agent> agents;
    public DiGraphAlgo graph;
    public int ttl;
    public int score;
    public int moves;

    public Game(DiGraphAlgo graph) {
        this.graph = graph;
        this.pokemons = new ArrayList<>();
        this.agents = new ArrayList<>();
        this.ttl = 0;
        this.moves = 0;
        this.score = 0;
        this.agentDestEdge = new Hashtable<>();
        this.agentPath = new Hashtable<>();

    }

    public boolean initPokemons(String pokemonsStr) {
        try {
            JSONObject pokJ = new JSONObject(pokemonsStr);
            JSONArray pokemonsJsonArr = pokJ.getJSONArray("Pokemons");
            loadPokemonsFromJsonArray(pokemonsJsonArr);
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean UpdatePokemons(String pokemonsStr) {
        try {
            JSONObject pokJ = new JSONObject(pokemonsStr);
            JSONArray pokemonsJsonArr = pokJ.getJSONArray("Pokemons");
            this.pokemons.clear();
            loadPokemonsFromJsonArray(pokemonsJsonArr);
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
            loadAgentsFromJsonArray(agentsJsonArr);

            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean UpdateAgents(String agentsStr) {
        try {
            JSONObject agJ = new JSONObject(agentsStr);
            JSONArray agentsJsonArr = agJ.getJSONArray("Agents");
            this.agents.clear();
            loadAgentsFromJsonArray(agentsJsonArr);

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

    public boolean UpdateInfo(String infoStr) {
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

    public void UpdateTime(String time) {
        this.ttl=(Integer.parseInt(time))/1000;
    }


    private void loadPokemonsFromJsonArray(JSONArray pokemonsJsonArr) throws JSONException {
        for (int i = 0; i < pokemonsJsonArr.length(); i++) {
            JSONObject pok = pokemonsJsonArr.getJSONObject(i);
            JSONObject p = pok.getJSONObject("Pokemon");
            Pokemon my_pok = new Pokemon();
            my_pok.setType((int) p.get("type"));
//            BigDecimal temp = (BigDecimal) p.get("value");
            my_pok.setValue((double) p.get("value"));
            String my_pos = (String) p.get("pos");
            String[] arr = my_pos.split(",");
            Point3D pokPos = new Point3D(Double.parseDouble(arr[0]), Double.parseDouble(arr[1]), 0);
            my_pok.setPos(pokPos);
            Edge pokEdge = this.findEdge(pokPos, my_pok.getType());
            my_pok.setEdge(pokEdge);
            this.pokemons.add(my_pok);
        }
    }

    private void loadAgentsFromJsonArray(JSONArray agentsJsonArr) throws JSONException {
        for (int i = 0; i < agentsJsonArr.length(); i++) {
            JSONObject aga = agentsJsonArr.getJSONObject(i);
            JSONObject a = aga.getJSONObject("Agent");
            Agent my_ag = new Agent();
            my_ag.setId((int) a.get("id"));
//            BigDecimal temp = (BigDecimal) a.get("value");
            my_ag.setValue((double) a.get("value"));
            my_ag.setSrc((int) a.get("src"));
            my_ag.setDest((int) a.get("dest"));
//            temp = (BigDecimal) a.get("speed");
            my_ag.setSpeed((double) a.get("speed"));
            String my_pos = (String) a.get("pos");
            String[] arr = my_pos.split(",");
            my_ag.setPos(new Point3D(Double.parseDouble(arr[0]), Double.parseDouble(arr[1]), 0));
            this.agents.add(my_ag);
        }
    }

    private Edge findEdge(Point3D pokemonPos, int pokType) {
        Iterator<Edge> edgeIterator = graph.getGraph().edgeIter();
        while (edgeIterator.hasNext()) {
            Edge currentEdge = edgeIterator.next();
            Vertex edgeSrc = graph.getGraph().getNode(currentEdge.getSrc());
            Vertex edgeDest = graph.getGraph().getNode(currentEdge.getDest());
            Point3D srcPos = edgeSrc.getLocation();
            Point3D destPos = edgeDest.getLocation();
            boolean flagForEdge = pokType > 0 && edgeSrc.getKey() < edgeDest.getKey() || pokType < 0 && edgeSrc.getKey() > edgeDest.getKey();
            if (pokemonPos.isOnEdge(srcPos, destPos) && flagForEdge) {
                return currentEdge;
            }
        }
        return null;
    }

    public void print_game() {
        for (Pokemon p : this.pokemons) {
            System.out.println(p);
        }
        for (Agent a : this.agents) {
            System.out.println(a);
        }
    }

    public void initAgentMaps() {
        agentDestEdge.clear();
        agentPath.clear();
        for (Agent agent : agents) {
            int agentId = agent.getId();
            Edge toInit = new Edge(-1, -1, -1);
            agentDestEdge.put(agentId, toInit);
            agentPath.put(agentId, new ArrayList<>());
        }
    }

    public void findNextPath(Agent agent) {
        int agentId = agent.getId();
        int agentSrcVertexId = agent.getSrc();
        Hashtable<Integer, Double> distToAllVertices = graph.distToVertices(agentSrcVertexId);
        double maxValuePokemon = 0;
        double minDIst = Double.MAX_VALUE;
        Edge destEdge = null;
        for (Pokemon pokemon : pokemons) {
            Edge currEdge = pokemon.getEdge();
            if (agents.size() == 1 || !isDest(currEdge)) {
                double distToEdgeSrc = distToAllVertices.get(currEdge.getSrc());
                double edgeWeight = currEdge.getWeight();
                double totalWeight = distToEdgeSrc + edgeWeight;
                if (totalWeight < minDIst) {
                    minDIst = totalWeight;
                    destEdge = currEdge;
                }
            }
        }
        if (destEdge == null) {
            return;
        }
        this.agentDestEdge.put(agentId, destEdge);
        int edgeSrcVertex = destEdge.getSrc();
        List<Vertex> pathToDestEdge = graph.shortestPath(agentSrcVertexId, edgeSrcVertex);
        this.agentPath.put(agentId, pathToDestEdge);
    }


    public int agentNextDest(Agent agent) {
        int agentId = agent.getId();
        int agentSrcVertex = agent.getSrc();
        int agentDestVertex = agent.getDest();
        int nextVertexId = -1;
        if (agentDestVertex != -1) {
            return -1;
        }
        Edge destEdge = this.agentDestEdge.get(agentId);
        List<Vertex> agentCurrPath = this.agentPath.get(agentId);
        int edgeSrcVertex = destEdge.getSrc();
        int edgeDestVertex = destEdge.getDest();
        if (destEdge.getSrc() == -1 || agentCurrPath.size() == 0 || agentSrcVertex == edgeDestVertex) {
            findNextPath(agent);
        }
        destEdge = this.agentDestEdge.get(agentId);
        agentCurrPath = this.agentPath.get(agentId);
        edgeSrcVertex = destEdge.getSrc();
        edgeDestVertex = destEdge.getDest();

        if (agentCurrPath.size() == 1) {
            Edge nullEdge = new Edge(-1, -1, -1);
            this.agentDestEdge.put(agentId, nullEdge);
            return destEdge.getDest();
        }
        if (agentSrcVertex == edgeSrcVertex) {
            Edge nullEdge = new Edge(-1, -1, -1);
            this.agentDestEdge.put(agentId, nullEdge);
            return edgeDestVertex;
        }


        for (int i = 0; i < agentCurrPath.size(); i++) {
            Vertex currVertex = agentCurrPath.get(i);
            int vertexId = currVertex.getKey();
            if (vertexId == agentSrcVertex) {
                nextVertexId = agentCurrPath.get(i + 1).getKey();
                break;
            }
        }
        return nextVertexId;
    }

    private boolean isDest(Edge edge) {
        for (Edge e : this.agentDestEdge.values()) {
            if (e == edge) {
                return true;
            }
        }
        return false;
    }
}
