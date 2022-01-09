package Tests;

import ex4_java_client.Client;
import graph.DiGraph;
import graph.DiGraphAlgo;
import graph.Game;
import graph.Vertex;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    DiGraphAlgo di_al = new DiGraphAlgo();
    Game myGame = new Game(di_al);


    @Test
    void updatePokemons() {
        myGame.UpdatePokemons("{\"Pokemons\":[{\"Pokemon\":{\"value\":5.0,\"type\":-1,\"pos\":\"35.197656770719604,32.10191878639921,0.0\"}},{\"Pokemon\":{\"value\":8.0,\"type\":-1,\"pos\":\"35.199963710098416,32.105723673136964,0.0\"}},{\"Pokemon\":{\"value\":13.0,\"type\":-1,\"pos\":\"35.195224052340706,32.10575624080796,0.0\"}}]}");
        assertEquals(myGame.pokemons.size(),3);

    }


    @Test
    void updateAgents() {
        myGame.UpdateAgents("{\"Agents\":[{\"Agent\":{\"id\":0,\"value\":0.0,\"src\":26,\"dest\":-1,\"speed\":1.0,\"pos\":\"35.20260156093624,32.10476360672269,0.0\"}}]}");
        assertEquals(myGame.agents.size(),1);
    }


    @Test
    void updateInfo() {
        myGame.UpdateInfo("{\"GameServer\":{\"pokemons\":3,\"is_logged_in\":false,\"moves\":48,\"grade\":10,\"game_level\":8,\"max_user_level\":-1,\"id\":0,\"graph\":\"data/A2\",\"agents\":1}}");
        assertEquals(myGame.moves,48);
        assertEquals(myGame.score,10);

    }

    @Test
    void updateTime() {
        myGame.UpdateTime("60000");
        assertEquals(myGame.ttl,60);
    }

}