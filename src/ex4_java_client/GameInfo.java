package ex4_java_client;

import graph.DiGraphAlgo;

public class GameInfo {
    public int pokemons;
    public boolean is_logged_in;
    public int moves;
    public int grade;
    public int game_level;
    public int max_user_level;
    public int id;
    public DiGraphAlgo graph;
    public int agents;

    public GameInfo(int pokemons, String is_logged_in, int moves, int grade, int game_level, int max_user_level, int id, String graph, int agents) {
        this.pokemons = pokemons;
        this.is_logged_in = Boolean.parseBoolean(is_logged_in);
        this.moves = moves;
        this.grade = grade;
        this.game_level = game_level;
        this.max_user_level = max_user_level;
        this.id = id;
        this.graph = new DiGraphAlgo();
        this.graph.load(graph);
        this.agents = agents;
    }
}
