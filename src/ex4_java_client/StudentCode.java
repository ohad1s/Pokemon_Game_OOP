package ex4_java_client; /**
 * @authors: Ohad Shirazi and Itamar Asulin
 * A trivial example for starting the server and running all needed commands
 */

import GUI.FinishWnd;
import GUI.graphWindow;
import graph.Agent;
import graph.DiGraphAlgo;
import graph.Game;
import graph.Pokemon;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class StudentCode {
    public static void main(String[] args)  {
        Client client = new Client();

        try {
            client.startConnection("127.0.0.1", 6666);

        } catch (IOException e) {
            e.printStackTrace();
        }
        String graphStr = client.getGraph();
        DiGraphAlgo graphForGame = new DiGraphAlgo();
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

        Game myGame = new Game(graphForGame);
        myGame.UpdatePokemons(pokemonsStr);
        for (int i = 0; i < sum_agents && i < myGame.pokemons.size(); i++) {
            Pokemon currPokemon = myGame.pokemons.get(i);
            int pokEdgeSrc = currPokemon.getEdge().getSrc();
            client.addAgent("{\"id\":" + pokEdgeSrc + "}"); //we need to think which node to start with any agant
        }
        String agentsStr = client.getAgents();
        myGame.UpdateAgents(agentsStr);
        myGame.UpdateInfo(infoStr);
        myGame.initAgentMaps();
        int startTIme = Integer.parseInt(client.timeToEnd());
        myGame.ttl = startTIme / 1000;
        String isRunningStr = client.isRunning();
        System.out.println(isRunningStr);
        client.start();
        startTIme = Integer.parseInt(client.timeToEnd()) / 1000;
        System.out.println("" + startTIme);
        graphWindow gWnd = new graphWindow(graphForGame, myGame);
        while (client.isRunning().equals("true")) {
            for (Agent agent : myGame.agents) {
                int agentId = agent.getId();
                if (agent.getDest() != -1) {
                    continue;
                }
                int nextDest = myGame.agentNextDest(agent);
                client.chooseNextEdge("{\"agent_id\":" + agentId + ", \"next_node_id\": " + nextDest + "}");
            }
            pokemonsStr = client.getPokemons();
            agentsStr = client.getAgents();
            infoStr = client.getInfo();
            myGame.UpdateAgents(agentsStr);
            myGame.UpdatePokemons(pokemonsStr);
            myGame.UpdateInfo(infoStr);
            myGame.UpdateTime(client.timeToEnd());
            gWnd.repaint();
            client.move();
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(client.getInfo());
        }
        try {
            client.stopConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        gWnd.setVisible(false);
        gWnd.dispose();
        FinishWnd finish=new FinishWnd(myGame);
        finish.setVisible(true);

    }


}

