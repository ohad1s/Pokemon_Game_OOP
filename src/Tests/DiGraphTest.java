package Tests;
import graph.DiGraph;
import graph.Edge;
import graph.Vertex;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class DiGraphTest {

    DiGraph graphTest = generateGraph();



    @Test
    void getNode() {
        Vertex nodeTest1 = graphTest.getNode(0);
        assertEquals(0, nodeTest1.getKey());
        assertEquals(1, nodeTest1.getLocation().x());
        assertEquals(2, nodeTest1.getLocation().y());
        assertEquals(3, nodeTest1.getLocation().z());
        assertEquals(0, nodeTest1.getWeight());
        Vertex nodeTest2 = graphTest.getNode(25);
        assertEquals(null, nodeTest2);


    }

    @Test
    void getEdge() {
        Edge edgeTest1 = graphTest.getEdge(5,6);
        assertEquals(5, edgeTest1.getSrc());
        assertEquals(6, edgeTest1.getDest());
        assertEquals(5, edgeTest1.getWeight());
        Edge edgeTest2 = graphTest.getEdge(0, 3);
        assertEquals(null, edgeTest2);

    }

    @Test
    void addNode() {
        Vertex toAdd = new Vertex(10, 0,5,7);
        graphTest.addNode(toAdd);
        Vertex toCompare = graphTest.getNode(10);
        assertEquals(toAdd, toCompare);
    }

    @Test
    void connect() {
        graphTest.connect(0,3, 0);
        Edge edgeToCheck = graphTest.getEdge(0,3);
        assertEquals(0, edgeToCheck.getSrc());
        assertEquals(3, edgeToCheck.getDest());
        assertEquals(0, edgeToCheck.getWeight());
        HashSet<Edge> edgesOfCurrentSrc = graphTest.getEdgesFromSpecificVertex().get(0);
        assertTrue(edgesOfCurrentSrc.contains(edgeToCheck));

    }

    @Test
    void nodeIter() {
        Iterator <Vertex> nodeIterTest = graphTest.nodeIter();
        while (nodeIterTest.hasNext()) {
            Vertex toCheck = nodeIterTest.next();
            int key = toCheck.getKey();
            Hashtable<Integer, Vertex> graphNodes = graphTest.getVertices();
            Vertex toCompare = graphNodes.get(key);
            assertEquals(toCheck, toCompare);
        }
    }

    @Test
    void edgeIter() {
        Iterator<Edge> edgeIterTest = graphTest.edgeIter();
        while (edgeIterTest.hasNext()){
            Edge toCheck = edgeIterTest.next();
            String key = "" + toCheck.getSrc() +"," + toCheck.getDest();
            Hashtable<String, Edge> graphEdges = graphTest.getEdges();
            Edge toCompare = graphEdges.get(key);
            assertEquals(toCheck, toCompare);
        }

    }

    @Test
    void testEdgeIter() {
        int id = 0;
        Iterator <Edge> iterForSpecificVertexEdges = graphTest.edgeIter(0);
        Edge edge1 = graphTest.getEdge(0,1);
        Edge edge2 = graphTest.getEdge(0,3);
        while (iterForSpecificVertexEdges.hasNext()){
            Edge toCheck = iterForSpecificVertexEdges.next();
            int src = toCheck.getSrc();
            int dest = toCheck.getDest();
            Edge toCompare = graphTest.getEdge(src, dest);
            assertEquals(toCompare, toCheck);
        }
    }

    @Test
    void removeNode() {
        Vertex toCompare = graphTest.getNode(0);
        graphTest.connect(0,2,0);
        graphTest.connect(0,3,0);
        graphTest.connect(0,4,0);
        graphTest.connect(0,5,0);
        graphTest.connect(0,6,0);
        graphTest.connect(0,7,0);
        graphTest.connect(0,8,0);
        graphTest.connect(0,9,0);
        graphTest.connect(2,0,0);

        int numOfEdges = graphTest.edgeSize();
        int updatedEdgesOf2 =0;
        Vertex removedNode = graphTest.removeNode(0);
        Iterator<Edge> edgeIter = graphTest.edgeIter(2);
        while (edgeIter.hasNext()){
            updatedEdgesOf2++;
            edgeIter.next();
        }

//        assertEquals(1, updatedEdgesOf2);
//        assertEquals(toCompare, removedNode);
//        assertEquals(numOfEdges -9, graphTest.edgeSize());

    }

    @Test
    void removeEdge() {
        Edge toCompare = graphTest.getEdge(1,2);
        graphTest.connect(1,3,0);
        graphTest.connect(1,7,0);
        int numOfEdges = graphTest.edgeSize();
        Edge removedEdge = graphTest.removeEdge(1,2);
        int numOfEdgesFrom1 = 0;
        Iterator<Edge> edgeIter = graphTest.edgeIter(1);
        while (edgeIter.hasNext()){
            edgeIter.next();
            numOfEdgesFrom1 ++;
        }
        assertEquals(2, numOfEdgesFrom1);
        assertEquals(toCompare, removedEdge);
        assertEquals(numOfEdges -1 , graphTest.edgeSize());
    }

    @Test
    void nodeSize() {
        int size = 10;
        assertEquals(size, graphTest.nodeSize());
    }

    @Test
    void edgeSize() {
        int size = 9;
        assertEquals(size, graphTest.edgeSize());
    }

    @Test
    void getMC() {
        int currentMC = graphTest.getMC();
        graphTest.removeEdge(0,1);
        assertEquals(currentMC  + 1, graphTest.getMC());
        graphTest.removeNode(0);
        assertEquals(currentMC +2, graphTest.getMC());
        graphTest.connect(3,5, 0);
        assertEquals(currentMC + 3, graphTest.getMC());
        Vertex toAdd = new Vertex(0,1,2,3);
        graphTest.addNode(toAdd);
        assertEquals(currentMC + 4, graphTest.getMC());
        graphTest.addNode(toAdd);
        assertEquals(currentMC + 4, graphTest.getMC());



    }

    public static DiGraph generateGraph() {
        DiGraph graph = new DiGraph();
        for (int i = 0; i < 10; i++) {
            Vertex nodeToAdd = new Vertex(i, i + 1, i + 2, i + 3);
            graph.addNode(nodeToAdd);
        }
        for (int i = 0; i < 9; i++) {
            graph.connect(i, i + 1, i);
        }
        return graph;

    }

}
