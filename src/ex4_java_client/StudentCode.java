package ex4_java_client; /**
 * @authors: Ohad Shirazi and Itamar Asulin
 * A trivial example for starting the server and running all needed commands
 */

import GUI.graphWindow;
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
        String infoStr = client.getInfo();
        int sum_agents = 0;
        try {
            JSONObject infoJ = new JSONObject(infoStr);
            JSONObject infoJsonArr = infoJ.getJSONObject("GameServer");
            sum_agents = infoJsonArr.getInt("agents");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < sum_agents; i++) {
            client.addAgent("{\"id\":0}"); //we need to think which node to start with any agant
        }
        String agentsStr = client.getAgents();
        Game myGame = new Game();
        myGame.initPokemons(pokemonsStr);
        myGame.initAgents(agentsStr);
        myGame.initInfo(infoStr);
        int ttl = Integer.parseInt(client.timeToEnd());
        myGame.ttl = ttl / 1000;
        String isRunningStr = client.isRunning();
        System.out.println(isRunningStr);
        client.start();
        graphWindow gWnd = new graphWindow(graphForGame, myGame);
        while (client.isRunning().equals("true")) {
//            System.out.println(client.getAgents());
//            System.out.println(client.getPokemons());
//            System.out.println(client.timeToEnd());
//            System.out.println(client.getInfo());
//            Scanner keyboard = new Scanner(System.in);
//            System.out.println("enter the next dest: ");
//            int next = keyboard.nextInt();
            for (int j = 0; j < sum_agents; j++) {
                client.chooseNextEdge("{\"agent_id\":" + j + ", \"next_node_id\": " + myGame.agents.get(j).getSrc()+1 + "}");
            }
            pokemonsStr = client.getPokemons();
            agentsStr = client.getAgents();
            infoStr = client.getInfo();
            myGame.UpdateAgents(agentsStr);
            myGame.UpdatePokemons(pokemonsStr);
            myGame.UpdateInfo(infoStr);
            gWnd.repaint();
            client.move();

        }
    }

}
