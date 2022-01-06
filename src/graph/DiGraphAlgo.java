package graph;


import json.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.*;

public class DiGraphAlgo {
    private DiGraph graph;
    private DiGraph invertedGraph;
    private Hashtable<Integer, Double> mapDist;
    private Hashtable<Integer, Integer> mapPrev;
    private static final int VISITED = 1;
    private static final int NOT_VISITED = 0;
    public static final double INFINITY = Double.POSITIVE_INFINITY;

    /**
     * this method is the constructor of DirectedWeightedGraphAlgorithm
     */
    public DiGraphAlgo() {
        this.graph = new DiGraph();
        this.invertedGraph = new DiGraph();
        this.mapDist = new Hashtable<>();
        this.mapPrev = new Hashtable<>();
    }

    /**
     * this method initiate the graph on which the algorithms are executed to be the given graph.
     *
     * @param g
     */

    public void init(DiGraph g) {
        this.graph = g;
        this.invertedGraph = invertGraph();
    }

    /**
     * this method returns the graph on which the algorithms are executed.
     *
     * @return
     */

    public DiGraph getGraph() {
        return this.graph;
    }

    /**
     * this method creates a deep copy of the graph.
     *
     * @return
     */
    public DiGraph copy() {
        DiGraph duplicatedGraph = new DiGraph();
        Iterator<Vertex> verticesIterator = this.graph.nodeIter();
        while (verticesIterator.hasNext()) {
            Vertex currentVertex = verticesIterator.next();
            double x = currentVertex.getLocation().x();
            double y = currentVertex.getLocation().y();
            double z = currentVertex.getLocation().z();
            Vertex toAdd = new Vertex(currentVertex.getKey(), x, y, z);
            duplicatedGraph.addNode(toAdd);
        }
        Iterator<Edge> edgesIterator = this.graph.edgeIter();
        while (edgesIterator.hasNext()) {
            Edge currentEdge = edgesIterator.next();
            int src = currentEdge.getSrc();
            int dest = currentEdge.getDest();
            double weight = currentEdge.getWeight();
            duplicatedGraph.connect(src, dest, weight);
        }

        return duplicatedGraph;
    }

    /**
     * this method returns a boolean value whether the graph is connected or not.
     *
     * @return
     */

    public boolean isConnected() {

        Iterator<Vertex> graphNodeIter = graph.nodeIter();
        Vertex graphFirstNode = graphNodeIter.next();

        return isConnected(graph, graphFirstNode) && isConnected(invertedGraph, graphFirstNode);
    }

    /**
     * this method returns a boolean value whether the given graph is strongly connected or not.
     *
     * @param g
     * @param nodeFirst
     * @return
     */
    private boolean isConnected(DiGraph g, Vertex nodeFirst) {
        setAllTags(g, NOT_VISITED);
        BFS(nodeFirst, g);
        Iterator<Vertex> nodesIter = g.nodeIter();
        while (nodesIter.hasNext()) {
            Vertex currentNode = nodesIter.next();
            if (currentNode.getTag() == NOT_VISITED) {
                return false;
            }
        }
        return true;
    }

    /**
     * this method is a BFS algorithm.
     *
     * @param node
     * @param g
     */
    private void BFS(Vertex node, DiGraph g) {
        Vertex firstNode = node;
        Queue<Vertex> queue = new LinkedList<>();
        firstNode.setTag(VISITED);
        queue.add(firstNode);
        while (!queue.isEmpty()) {
            firstNode = queue.poll();
            Iterator<Edge> currentNodeEdgesIter = g.edgeIter(firstNode.getKey());
            while (currentNodeEdgesIter.hasNext()) {
                Edge currentEdge = currentNodeEdgesIter.next();
                int destNodeId = currentEdge.getDest();
                Vertex destNode = g.getNode(destNodeId);
                if (destNode.getTag() == NOT_VISITED) {
                    destNode.setTag(VISITED);
                    queue.add(destNode);
                }
            }
        }
    }

    private void setAllTags(DiGraph graph, int value) {
        Iterator<Vertex> nodesIter = graph.nodeIter();
        while (nodesIter.hasNext()) {
            Vertex currentNode = nodesIter.next();
            currentNode.setTag(value);
        }
    }

    /**
     * this method calculates the shortest path between the two given vertices, using Dijkstra's algorithm.
     * in case at least one of the nodes does not exist in the graph, the method will return -1.
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */

    public double shortestPathDist(int src, int dest) {
        if (graph.getNode(src) == null || graph.getNode(dest) == null) {
            return -1.0;
        }
        if (graph.getNode(src) != null && src == dest) {
            return 0;
        }
        calculateShortestPath(src);
        double dist = mapDist.get(dest);
        return (dist == INFINITY) ? -1 : dist;
    }

    /**
     * this method returns a list of the shortest path between the two given vertices, using Dijkstra's algorithm.
     * in case at least one of the nodes does not exist in the graph, the method will return null.
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */

    public List<Vertex> shortestPath(int src, int dest) {
        if (graph.getNode(src) == null || graph.getNode(dest) == null) {
            return null;
        }
        List<Vertex> path = new ArrayList<>();
        if (graph.getNode(src) != null && src == dest) {
            Vertex toAdd = graph.getNode(src);
            path.add(toAdd);
            return path;
        }
        calculateShortestPath(src);
        if (!mapPrev.containsKey(dest)) {
            return null;
        }
        Vertex destVertex = graph.getNode(dest);
        path.add(destVertex);
        int prevVertex = mapPrev.get(dest);
        while (prevVertex != -1) {
            Vertex toAdd = graph.getNode(prevVertex);
            path.add(toAdd);
            prevVertex = mapPrev.get(prevVertex);
        }
        Collections.reverse(path);
        return path;
    }

    /**
     * this method return the vertex for which the distance from the farthest vertex from it is minimal.
     *
     * @return
     */

    public Vertex center() {
        Iterator<Vertex> graphVerticesIter = graph.nodeIter();
        int keyofCenter = -1;
        double minMaxDist = INFINITY;
        while (graphVerticesIter.hasNext()) {
            Vertex currentVertex = graphVerticesIter.next();
            calculateShortestPath(currentVertex.getKey());
            double maxValue = findMaxValue();
            if (maxValue < minMaxDist) {
                minMaxDist = maxValue;
                keyofCenter = currentVertex.getKey();
            }

        }
        if (keyofCenter == -1) {
            Vertex first = getGraph().nodeIter().next();
            return first;
        }
        Vertex centerVertex = this.graph.getNode(keyofCenter);
        return centerVertex;
    }

    /**
     * this method returns the shortest path that goes through all the given vertices.
     *
     * @param cities - list of vertices
     * @return
     */

    public List<Vertex> tsp(List<Vertex> cities) {
        if (cities.size() == 1) {
            return cities;
        }
        List<Vertex> toReturn = new ArrayList<>();
        HashSet<Integer> unvisited = new HashSet<>();
        initiateSetForTSP(cities, unvisited);
        return tsp(unvisited, cities.get(0));
    }

    /**
     * this method is a greedy algorithm to find the shortest path between al cities.
     *
     * @param unvisited
     * @param first
     * @return
     */
    public List<Vertex> tsp(HashSet<Integer> unvisited, Vertex first) {
        List<Vertex> toReturn = new ArrayList<>();
        Vertex currentNode = first;
        while (unvisited.size() > 0) {
            unvisited.remove(currentNode.getKey());
            int nextNodeId = findIdOfMinDistForTSP(unvisited, currentNode.getKey());
            List<Vertex> pathFromCurrentToNext = shortestPath(currentNode.getKey(), nextNodeId);
            for (Vertex node : pathFromCurrentToNext) {
                int pathNodeID = node.getKey();
                if (unvisited.contains(pathNodeID)) {
                    unvisited.remove(pathNodeID);
                }
            }
            for (int i = 1; i < pathFromCurrentToNext.size(); i++) {
                toReturn.add(pathFromCurrentToNext.get(i));
            }
            currentNode = graph.getNode(nextNodeId);
        }
        return toReturn;
    }

    /**
     * this method saves the graph into the given json file.
     *
     * @param file - the file name (may include a relative path).
     * @return
     */

    public boolean save(String file) {
        GraphJson serializedGraph = serializeGraph();
        GsonBuilder json = new GsonBuilder();
        String toWrite = json.setPrettyPrinting().create().toJson(serializedGraph);
        try {
            FileWriter myFile = new FileWriter(file);
            myFile.write(toWrite);
            myFile.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }


    /**
     * this method initates the graph from a given json file.
     *
     * @param file - file name of JSON file
     * @return
     */
    public boolean load(String file) {
        try {
            Gson json = new Gson();
//            FileReader FR = new FileReader(file);
//            BufferedReader BR = new BufferedReader(FR);
            GraphJson graphFromJson = json.fromJson(file, GraphJson.class);
            DiGraph graph = deserializeGraph(graphFromJson);
            init(graph);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


    /**
     * this method calculates the shortest path from the given vertex to each of the graph's vertices.
     *
     * @param src
     */
    public void calculateShortestPath(int src) {
        Vertex source = graph.getNode(src);
        mapDist.clear();
        mapPrev.clear();
        initiateMapDist();
        mapPrev.put(src, -1);
        Queue<Vertex> queue = new LinkedList<>();
        mapDist.put(src, 0.0);
        queue.add(source);
        while (!queue.isEmpty()) {
            Vertex currentNode = queue.poll();
            Iterator<Edge> currentNodeNeighbors = graph.edgeIter(currentNode.getKey());
            while (currentNodeNeighbors.hasNext()) {
                Edge currentEdge = currentNodeNeighbors.next();
                Vertex destNode = graph.getNode(currentEdge.getDest());
                double weight = currentEdge.getWeight();
                double totalWeight = mapDist.get(currentNode.getKey()) + weight;
                if (totalWeight < mapDist.get(destNode.getKey())) {
                    mapDist.put(destNode.getKey(), totalWeight);
                    mapPrev.put(destNode.getKey(), currentNode.getKey());
                    queue.add(destNode);
                }

            }
        }
    }

    /**
     * this method returns the max value of mapDist.
     *
     * @return
     */
    private double findMaxValue() {
        double maxDist = 0;
        Iterator<Double> distIter = mapDist.values().iterator();
        while (distIter.hasNext()) {
            double currentDist = distIter.next();
            if (currentDist > maxDist) {
                maxDist = currentDist;
            }
        }
        return maxDist;
    }

    /**
     * this method returns the index of the vertex with the min dist from src for TSP.
     *
     * @param unvisited
     * @return
     */
    public int findIdOfMinDistForTSP(HashSet<Integer> unvisited, int currentNodeID) {
        Iterator<Integer> unvisitedIter = unvisited.iterator();
        calculateShortestPath(currentNodeID);
        int idOfMinValue = -1;
        double minDist = INFINITY;
        while (unvisitedIter.hasNext()) {
            int currentVertexId = unvisitedIter.next();
            double distToCurrentVertex = mapDist.get(currentVertexId);
            if (distToCurrentVertex < minDist && currentVertexId != currentNodeID) {
                minDist = distToCurrentVertex;
                idOfMinValue = currentVertexId;
            }
        }
        if (idOfMinValue == -1) {
            Iterator<Integer> newIter = unvisited.iterator();
            int idToReturn = newIter.next();
            return idToReturn;
        }
        return idOfMinValue;
    }

    /**
     * this method serialize the graph into a json file.
     *
     * @return
     */
    private GraphJson serializeGraph() {
        GraphJson serializedGraph = new GraphJson();
        Iterator<Vertex> nodeIter = this.graph.nodeIter();
        Iterator<Edge> edgeIter = this.graph.edgeIter();
        while (nodeIter.hasNext()) {
            Vertex currentVertex = nodeIter.next();
            serializedGraph.addNode(currentVertex);
        }
        while (edgeIter.hasNext()) {
            Edge currentEdge = edgeIter.next();
            serializedGraph.addEdge(currentEdge);
        }
        return serializedGraph;
    }

    /**
     * this method deserialize the graph from a json file.
     *
     * @param fromJson
     * @return
     */
    private DiGraph deserializeGraph(GraphJson fromJson) {
        DiGraph loadedGraph = new DiGraph();
        for (NodeForJson node : fromJson.Nodes) {
            String[] location = node.pos.split(",");
            double x = Double.parseDouble(location[0]);
            double y = Double.parseDouble(location[1]);
            double z = Double.parseDouble(location[2]);
            int id = node.id;
            Vertex toAdd = new Vertex(id, x, y, z);
            loadedGraph.addNode(toAdd);
        }
        for (EdgeForJson edge : fromJson.Edges) {
            int src = edge.src;
            int dest = edge.dest;
            double weight = edge.w;
            loadedGraph.connect(src, dest, weight);
        }
        return loadedGraph;
    }

    /**
     * this method sets mapDist values to be infinity.
     *
     * @return
     */
    void initiateMapDist() {
        Iterator<Vertex> nodeIter = graph.nodeIter();
        while (nodeIter.hasNext()) {
            Vertex currentVertex = nodeIter.next();
            int vertexId = currentVertex.getKey();
            mapDist.put(vertexId, INFINITY);
        }
    }

    /**
     * this method initiates the set for TSP algorithm.
     *
     * @param nodeList
     * @param unvisited
     */
    public void initiateSetForTSP(List<Vertex> nodeList, HashSet<Integer> unvisited) {
        for (Vertex node : nodeList) {
            int idOfCurrentVertex = node.getKey();
            unvisited.add(idOfCurrentVertex);
        }
    }

    /**
     * this method returns an inverted graph of the class' graph.
     *
     * @return
     */
    public DiGraph invertGraph() {
        DiGraph inverted = new DiGraph();
        Iterator<Vertex> nodesIter = getGraph().nodeIter();
        Iterator<Edge> edgesIter = getGraph().edgeIter();
        while (nodesIter.hasNext()) {
            Vertex currentNode = nodesIter.next();
            inverted.addNode(new Vertex(currentNode));
        }
        while (edgesIter.hasNext()) {
            Edge currentEdge = edgesIter.next();
            inverted.connect(currentEdge.getDest(), currentEdge.getSrc(), currentEdge.getWeight());
        }
        return inverted;
    }

    public void str() {
        Iterator <Vertex> iter = graph.nodeIter();
        while (iter.hasNext()) {
            Vertex n = (Vertex) iter.next();
            System.out.println(n.getKey() + "  " + n.getLocation().x() + "  " + n.getLocation().y());

        }
    }

    public Hashtable<Integer, Double> distToVertices(int agentVertexId){
        calculateShortestPath(agentVertexId);
        Hashtable<Integer, Double> mapToReturn = new Hashtable<>();
        Iterator<Integer> keyIter = this.mapDist.keySet().iterator();
        while (keyIter.hasNext()){
            int currentKey = keyIter.next();
            double currentDist = mapDist.get(currentKey);
            mapToReturn.put(currentKey, currentDist);
        }
        return mapToReturn;
    }


}




