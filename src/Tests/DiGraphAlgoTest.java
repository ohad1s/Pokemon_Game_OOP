package Tests;

import graph.DiGraph;
import graph.DiGraphAlgo;
import graph.Edge;
import graph.Vertex;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DiGraphAlgoTest {
    DiGraphAlgo graphAlgoTest = new DiGraphAlgo();
    static DiGraph g1 = new DiGraph();
    static DiGraph g2 = new DiGraph();
    static DiGraph g3 = new DiGraph();
    static DiGraph g4 = new DiGraph();
    static DiGraph g5 = new DiGraph();
    public static final double EPS = 0.1;

    @BeforeAll
    static void Setup() {
        createGraph(10, g1);
        g1.connect(0, 1, 0.5);
        g1.connect(0, 2, 2.5);
        g1.connect(2, 3, 1.98);
        g1.connect(1, 4, 8.3);
        g1.connect(4, 6, 4.1);
        g1.connect(8, 6, 2.4);
        g1.connect(5, 6, 3.1);
        g1.connect(6, 5, 3.1);
        g1.connect(7, 8, 1.8);
        g1.connect(3, 8, 9.6);
        g1.connect(1, 5, 5.6);

        createGraph(10, g2);
        g2.connect(0, 5, 5);
        g2.connect(0, 4, 20);
        g2.connect(0, 3, 20);
        g2.connect(0, 1, 10);
        g2.connect(0, 7, 15);
        g2.connect(1, 3, 10);
        g2.connect(1, 2, 5);
        g2.connect(2, 3, 5);
        g2.connect(2, 1, 15);
        g2.connect(3, 4, 10);
        g2.connect(4, 5, 5);
        g2.connect(6, 5, 10);
        g2.connect(7, 6, 5);
        g2.connect(7, 0, 5);
        g2.connect(7, 1, 20);
        g2.connect(8, 7, 20);
        g2.connect(8, 1, 15);
        g2.connect(8, 9, 10);
        g2.connect(9, 2, 15);
        g2.connect(9, 1, 5);

        createGraph(4, g3);
        g3.connect(0, 1, 5);
        g3.connect(2, 1, 5);
        g3.connect(0, 2, 5);
        g3.connect(3, 0, 5);
        g3.connect(1, 3, 5);
        g3.connect(3, 1, 5);

        createGraph(4, g4);
        g4.connect(0, 1, 5);
        g4.connect(2, 1, 5);
        g4.connect(2, 0, 5);
        g4.connect(0, 3, 5);
        g4.connect(1, 3, 5);
        g4.connect(3, 1, 5);


        createGraph(3, g5);
        g5.connect(0, 1, 5);
        g5.connect(1, 2, 5);
        g5.connect(2, 0, 5);

    }

    @Test
    void init() {
        DiGraph graphToInit = g1;
        graphAlgoTest.init(graphToInit);
        DiGraph algoGraphIter = graphAlgoTest.getGraph();
        Iterator<Vertex> algoGraphVerticesIter = algoGraphIter.nodeIter();
        while (algoGraphVerticesIter.hasNext()) {
            Vertex algoGraphVertex = algoGraphVerticesIter.next();
            Vertex initGraphVertex = g1.getNode(algoGraphVertex.getKey());
            assertEquals(initGraphVertex, algoGraphVertex);
        }
        Iterator<Vertex> initGraphVerticesIter = g1.nodeIter();
        while (initGraphVerticesIter.hasNext()) {
            Vertex initGraphVertex = initGraphVerticesIter.next();
            Vertex algoGraphVertex = graphAlgoTest.getGraph().getNode(initGraphVertex.getKey());
            assertEquals(initGraphVertex, algoGraphVertex);

        }
    }


    @Test
    void copy() {
        DiGraph graphToInit = g5;
        graphAlgoTest.init(graphToInit);
        DiGraph duplicatedGraph = graphAlgoTest.copy();
        Iterator<Vertex> verticesIterator = graphAlgoTest.getGraph().nodeIter();
        while (verticesIterator.hasNext()) {
            Vertex currentVertex = verticesIterator.next();
            int key = currentVertex.getKey();
            Vertex duplicatedGraphVortex = duplicatedGraph.getNode(key);
            assertEquals(currentVertex.getLocation().x(), duplicatedGraphVortex.getLocation().x());
            assertEquals(currentVertex.getLocation().y(), duplicatedGraphVortex.getLocation().y());
            assertEquals(currentVertex.getLocation().z(), duplicatedGraphVortex.getLocation().z());
            assertNotEquals(currentVertex, duplicatedGraphVortex);
        }
    }

    @Test
    void isConnected() {
        graphAlgoTest.init(g1);
        System.out.println("g1 = " + graphAlgoTest.isConnected());
        assertFalse(graphAlgoTest.isConnected());

        graphAlgoTest.init(g2);
        System.out.println("g2 = " + graphAlgoTest.isConnected());
        assertFalse(graphAlgoTest.isConnected());

        graphAlgoTest.init(g3);
        System.out.println("g3 = " + graphAlgoTest.isConnected());
        assertTrue(graphAlgoTest.isConnected());

        graphAlgoTest.init(g4);
        System.out.println("g4 = " + graphAlgoTest.isConnected());
        assertFalse(graphAlgoTest.isConnected());

        graphAlgoTest.init(g5);
        System.out.println("g5 = " + graphAlgoTest.isConnected());
        assertTrue(graphAlgoTest.isConnected());


    }

    @Test
    void shortestPathDistG1() {
        DiGraph graphToInit = g1;
        graphAlgoTest.init(graphToInit);
        assertEquals(5.6, graphAlgoTest.shortestPathDist(1, 5), EPS);
        assertEquals(0.5, graphAlgoTest.shortestPathDist(0, 1));
        assertEquals(4.48, graphAlgoTest.shortestPathDist(0, 3));
        assertEquals(7.300000000000001, graphAlgoTest.shortestPathDist(7, 5));
        assertEquals(-1.0, graphAlgoTest.shortestPathDist(8, 3));
        assertEquals(0.0, graphAlgoTest.shortestPathDist(2, 2));
        assertEquals(-1.0, graphAlgoTest.shortestPathDist(20, 20));
        assertEquals(-1.0, graphAlgoTest.shortestPathDist(0, 20));


    }

    @Test
    void shortestPathDistG2() {
        DiGraph graphToInit = g2;
        graphAlgoTest.init(graphToInit);
        assertEquals(25.0, graphAlgoTest.shortestPathDist(1, 5));
        assertEquals(10.0, graphAlgoTest.shortestPathDist(0, 1));
        assertEquals(20.0, graphAlgoTest.shortestPathDist(0, 3));
        assertEquals(10.0, graphAlgoTest.shortestPathDist(7, 5));
        assertEquals(25.0, graphAlgoTest.shortestPathDist(8, 3));
        assertEquals(25.0, graphAlgoTest.shortestPathDist(8, 0));
        assertEquals(-1.0, graphAlgoTest.shortestPathDist(9, 0));
        assertEquals(0.0, graphAlgoTest.shortestPathDist(2, 2));
        assertEquals(-1.0, graphAlgoTest.shortestPathDist(20, 20));
        assertEquals(-1.0, graphAlgoTest.shortestPathDist(1, 20));
        assertEquals(5.0, graphAlgoTest.shortestPathDist(2, 3));
        assertEquals(30.0, graphAlgoTest.shortestPathDist(9, 5));

    }

    @Test
    void center() {
        graphAlgoTest.load("./data/G1.json");
//        assertEquals(8, graphAlgoTest.center().getKey());
        graphAlgoTest.load("./data/G2.json");
//        assertEquals(0, graphAlgoTest.center().getKey());
        graphAlgoTest.load("./data/G3.json");
//        assertEquals(40, graphAlgoTest.center().getKey());

    }

//    @Test
//    void tsp() {
//        DiGraphAlgo toTest = new DiGraphAlgo();
//        toTest.load("./data/G1.json");
//        List<Vertex> path = new ArrayList<>();
//        Iterator<Vertex> verticesIter = toTest.getGraph().nodeIter();
//        while (verticesIter.hasNext()){
//            Vertex currentVertex = verticesIter.next();
//            path.add(currentVertex);
//        }
//        List<Vertex> shortestPathAroundTheGraph = toTest.tsp(path);
//        System.out.println(shortestPathAroundTheGraph.toString());
//        System.out.println(shortestPathAroundTheGraph.size());
//        for (Vertex vertex : shortestPathAroundTheGraph){
//            System.out.println(vertex.getInfo());
//        }
//    }
//    @Test
//    void testCase3(){
//        graphAlgoTest.load("./data/G3.json");
//        List<Vertex> listToTest = graphAlgoTest.shortestPath(3, 47);
//        for(Vertex node : listToTest){
//            System.out.println(node.getInfo());
//        }
//    }

    @Test
    void load_save() {
        DiGraphAlgo test2 = new DiGraphAlgo();
        String fileNameLoad = "./data/G1.json";
        String fileNameSave = "./out/G3.json";
//        assertTrue(graphAlgoTest.load(fileNameLoad));
//        assertTrue(graphAlgoTest.save(fileNameSave));
        test2.load(fileNameSave);
        Iterator<Vertex> nodeIter = graphAlgoTest.getGraph().nodeIter();
        Iterator<Edge> edgeIter = graphAlgoTest.getGraph().edgeIter();
        while (nodeIter.hasNext()) {
            Vertex currentNode = nodeIter.next();
            Vertex toCompareNode = test2.getGraph().getNode(currentNode.getKey());
//            assertEquals(currentNode.getLocation().x(), toCompareNode.getLocation().x());
//            assertEquals(currentNode.getLocation().y(), toCompareNode.getLocation().y());
//            assertEquals(currentNode.getLocation().z(), toCompareNode.getLocation().z());
//            assertNotEquals(currentNode, toCompareNode);
        }
        while (edgeIter.hasNext()){
            Edge currentEdge = edgeIter.next();
            Edge toCompare = test2.getGraph().getEdge(currentEdge.getSrc(), currentEdge.getDest());
//            assertNotEquals(toCompare, null);
//            assertNotEquals(currentEdge, toCompare);
        }
//        DirectedWeightedGraphAlgorithms g66=new DirectedWeightedGraphAlgorithmsClass();
//        DirectedWeightedGraphAlgorithms test2 = new DirectedWeightedGraphAlgorithmsClass();
//        test2.load("C:\\Users\\shira\\Desktop\\gToCompare\\1000Nodes.json");
//        assertTrue(test2.save("C:\\Users\\shira\\Desktop\\1000NodesB.json"));
//        assertTrue(test2.load("C:\\Users\\shira\\Desktop\\gToCompare\\G3.json"));
//        assertTrue(test2.load("C:\\Users\\shira\\Desktop\\gToCompare\\1000Nodes.json"));
    }



    static void createGraph(int size, DiGraph g) {
        for (int i = 0; i < size; i++) {
            Vertex toAdd = new Vertex(i, i, i, i);
            g.addNode(toAdd);
        }
    }

    static DiGraphAlgo loadGraph(DiGraphAlgo graph, String file){
        graph.load(file);
        return graph;
    }

}