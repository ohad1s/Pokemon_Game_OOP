package ex4_java_client; /**
 * @authors: Ohad Shirazi and Itamar Asulin
 * A trivial example for starting the server and running all needed commands
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import graph.DirectedWeightedGraphAlgorithmsClass;
import graph.Game;
import graph.GeoLocationClass;
import graph.Pokemon;
import json.PokemonForJson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;
import java.util.Scanner;

public class StudentCode {
    public static void main(String[] args) {
        Client client = new Client();
        try {
            client.startConnection("127.0.0.1", 6666);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String graphStr = client.getGraph();
        DirectedWeightedGraphAlgorithmsClass graphForGame = new DirectedWeightedGraphAlgorithmsClass();
        graphForGame.load(graphStr);
        String pokemonsStr = client.getPokemons();
        String agentsStr = client.getAgents();
        Game myGame = new Game();
        myGame.initPokemons(pokemonsStr);
//        myGame.initAgents(agentsStr);
        String infoStr = client.getInfo();
        myGame.initInfo(infoStr);
        int ttl=Integer.parseInt(client.timeToEnd());
        myGame.ttl=ttl/1000;



        client.addAgent("{\"id\":0}");
        String isRunningStr = client.isRunning();
        System.out.println(isRunningStr);

        client.start();

        while (client.isRunning().equals("true")) {
            client.move();
            System.out.println(client.getAgents());
            System.out.println(client.getPokemons());
            System.out.println(client.timeToEnd());
            System.out.println(client.getInfo());

            Scanner keyboard = new Scanner(System.in);
            System.out.println("enter the next dest: ");
            int next = keyboard.nextInt();
            client.chooseNextEdge("{\"agent_id\":0, \"next_node_id\": " + next + "}");

        }
    }

}
