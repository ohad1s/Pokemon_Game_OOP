package graph;


import graph.*;
import json.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.*;

public class DirectedWeightedGraphAlgorithmsClass {
    private DirectedWeightedClass graph;
    private DirectedWeightedClass invertedGraph;
    private Hashtable<Integer, Double> mapDist;
    private Hashtable<Integer, Integer> mapPrev;
    private static final int VISITED = 1;
    private static final int NOT_VISITED = 0;
    public static final double INFINITY = Double.POSITIVE_INFINITY;

    /**
     * this method is the constructor of DirectedWeightedGraphAlgorithm
     */
    public DirectedWeightedGraphAlgorithmsClass() {
        this.graph = new DirectedWeightedClass();
        this.invertedGraph = new DirectedWeightedClass();
        this.mapDist = new Hashtable<>();
        this.mapPrev = new Hashtable<>();
    }

    /**
     * this method initiate the graph on which the algorithms are executed to be the given graph.
     *
     * @param g
     */

    public void init(DirectedWeightedClass g) {
        this.graph = g;
        this.invertedGraph = invertGraph();
    }

    /**
     * this method returns the graph on which the algorithms are executed.
     *
     * @return
     */

    public DirectedWeightedClass getGraph() {
        return this.graph;
    }

    /**
     * this method creates a deep copy of the graph.
     *
     * @return
     */
    public DirectedWeightedClass copy() {
        DirectedWeightedClass duplicatedGraph = new DirectedWeightedClass();
        Iterator<NodeDataClass> verticesIterator = this.graph.nodeIter();
        while (verticesIterator.hasNext()) {
            NodeDataClass currentVertex = verticesIterator.next();
            double x = currentVertex.getLocation().x();
            double y = currentVertex.getLocation().y();
            double z = currentVertex.getLocation().z();
            NodeDataClass toAdd = new NodeDataClass(currentVertex.getKey(), x, y, z);
            duplicatedGraph.addNode(toAdd);
        }
        Iterator<EdgeDataClass> edgesIterator = this.graph.edgeIter();
        while (edgesIterator.hasNext()) {
            EdgeDataClass currentEdge = edgesIterator.next();
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

        Iterator<NodeDataClass> graphNodeIter = graph.nodeIter();
        NodeDataClass graphFirstNode = graphNodeIter.next();

        return isConnected(graph, graphFirstNode) && isConnected(invertedGraph, graphFirstNode);
    }

    /**
     * this method returns a boolean value whether the given graph is strongly connected or not.
     * @param g
     * @param nodeFirst
     * @return
     */
    private boolean isConnected(DirectedWeightedClass g, NodeDataClass nodeFirst) {
        setAllTags(g, NOT_VISITED);
        BFS(nodeFirst, g);
        Iterator<NodeDataClass> nodesIter = g.nodeIter();
        while (nodesIter.hasNext()) {
            NodeDataClass currentNode = nodesIter.next();
            if (currentNode.getTag() == NOT_VISITED) {
                return false;
            }
        }
        return true;
    }

    /**
     * this method is a BFS algorithm.
     * @param node
     * @param g
     */
    private void BFS(NodeDataClass node, DirectedWeightedClass g) {
        NodeDataClass firstNode = node;
        Queue<NodeDataClass> queue = new LinkedList<>();
        firstNode.setTag(VISITED);
        queue.add(firstNode);
        while (!queue.isEmpty()) {
            firstNode = queue.poll();
            Iterator<EdgeDataClass> currentNodeEdgesIter = g.edgeIter(firstNode.getKey());
            while (currentNodeEdgesIter.hasNext()) {
                EdgeDataClass currentEdge = currentNodeEdgesIter.next();
                int destNodeId = currentEdge.getDest();
                NodeDataClass destNode = g.getNode(destNodeId);
                if (destNode.getTag() == NOT_VISITED) {
                    destNode.setTag(VISITED);
                    queue.add(destNode);
                }
            }
        }
    }

    private void setAllTags(DirectedWeightedClass graph, int value) {
        Iterator<NodeDataClass> nodesIter = graph.nodeIter();
        while (nodesIter.hasNext()) {
            NodeDataClass currentNode = nodesIter.next();
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

    public List<NodeDataClass> shortestPath(int src, int dest) {
        if (graph.getNode(src) == null || graph.getNode(dest) == null) {
            return null;
        }
        List<NodeDataClass> path = new ArrayList<>();
        if (graph.getNode(src) != null && src == dest) {
            NodeDataClass toAdd = graph.getNode(src);
            path.add(toAdd);
            return path;
        }
        calculateShortestPath(src);
        if (!mapPrev.containsKey(dest)) {
            return null;
        }
        NodeDataClass destVertex = graph.getNode(dest);
        path.add(destVertex);
        int prevVertex = mapPrev.get(dest);
        while (prevVertex != -1) {
            NodeDataClass toAdd = graph.getNode(prevVertex);
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

    public NodeDataClass center() {
        Iterator<NodeDataClass> graphVerticesIter = graph.nodeIter();
        int keyofCenter = -1;
        double minMaxDist = INFINITY;
        while (graphVerticesIter.hasNext()) {
            NodeDataClass currentVertex = graphVerticesIter.next();
            calculateShortestPath(currentVertex.getKey());
            double maxValue = findMaxValue();
            if (maxValue < minMaxDist) {
                minMaxDist = maxValue;
                keyofCenter = currentVertex.getKey();
            }

        }
        if (keyofCenter == -1) {
            NodeDataClass first = getGraph().nodeIter().next();
            return first;
        }
        NodeDataClass centerVertex = this.graph.getNode(keyofCenter);
        return centerVertex;
    }

    /**
     * this method returns the shortest path that goes through all the given vertices.
     * @param cities - list of vertices
     * @return
     */

    public List<NodeDataClass> tsp(List<NodeDataClass> cities) {
        if (cities.size() == 1) {
            return cities;
        }
        List<NodeDataClass> toReturn = new ArrayList<>();
        HashSet<Integer> unvisited = new HashSet<>();
        initiateSetForTSP(cities, unvisited);
        return tsp(unvisited, cities.get(0));
    }

    /**
     * this method is a greedy algorithm to find the shortest path between al cities.
     * @param unvisited
     * @param first
     * @return
     */
    public List<NodeDataClass> tsp(HashSet<Integer> unvisited, NodeDataClass first){
        List<NodeDataClass> toReturn = new ArrayList<>();
        NodeDataClass currentNode = first;
        while (unvisited.size() > 0){
            unvisited.remove(currentNode.getKey());
            int nextNodeId = findIdOfMinDistForTSP(unvisited, currentNode.getKey());
            List<NodeDataClass> pathFromCurrentToNext = shortestPath(currentNode.getKey(), nextNodeId);
            for (NodeDataClass node : pathFromCurrentToNext){
                int pathNodeID = node.getKey();
                if(unvisited.contains(pathNodeID)){
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
            FileReader FR = new FileReader(file);
            BufferedReader BR = new BufferedReader(FR);
            GraphJson graphFromJson = json.fromJson(BR, GraphJson.class);
            DirectedWeightedClass graph = deserializeGraph(graphFromJson);
            init(graph);
            return true;

        } catch (FileNotFoundException e) {
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
        NodeDataClass source = graph.getNode(src);
        mapDist.clear();
        mapPrev.clear();
        initiateMapDist();
        mapPrev.put(src, -1);
        Queue<NodeDataClass> queue = new LinkedList<>();
        mapDist.put(src, 0.0);
        queue.add(source);
        while (!queue.isEmpty()) {
            NodeDataClass currentNode = queue.poll();
            Iterator<EdgeDataClass> currentNodeNeighbors = graph.edgeIter(currentNode.getKey());
            while (currentNodeNeighbors.hasNext()) {
                EdgeDataClass currentEdge = currentNodeNeighbors.next();
                NodeDataClass destNode = graph.getNode(currentEdge.getDest());
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
        Iterator<NodeDataClass> nodeIter = this.graph.nodeIter();
        Iterator<EdgeDataClass> edgeIter = this.graph.edgeIter();
        while (nodeIter.hasNext()) {
            NodeDataClass currentVertex = nodeIter.next();
            serializedGraph.addNode(currentVertex);
        }
        while (edgeIter.hasNext()) {
            EdgeDataClass currentEdge = edgeIter.next();
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
    private DirectedWeightedClass deserializeGraph(GraphJson fromJson) {
        DirectedWeightedClass loadedGraph = new DirectedWeightedClass();
        for (NodeForJson node : fromJson.Nodes) {
            String[] location = node.pos.split(",");
            double x = Double.parseDouble(location[0]);
            double y = Double.parseDouble(location[1]);
            double z = Double.parseDouble(location[2]);
            int id = node.id;
            NodeDataClass toAdd = new NodeDataClass(id, x, y, z);
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
        Iterator<NodeDataClass> nodeIter = graph.nodeIter();
        while (nodeIter.hasNext()) {
            NodeDataClass currentVertex = nodeIter.next();
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
    public void initiateSetForTSP(List<NodeDataClass> nodeList, HashSet<Integer> unvisited) {
        for (NodeDataClass node : nodeList) {
            int idOfCurrentVertex = node.getKey();
            unvisited.add(idOfCurrentVertex);
        }
    }

    /**
     * this method returns an inverted graph of the class' graph.
     *
     * @return
     */
    public DirectedWeightedClass invertGraph() {
        DirectedWeightedClass inverted = new DirectedWeightedClass();
        Iterator<NodeDataClass> nodesIter = getGraph().nodeIter();
        Iterator<EdgeDataClass> edgesIter = getGraph().edgeIter();
        while (nodesIter.hasNext()) {
            NodeDataClass currentNode = nodesIter.next();
            inverted.addNode(new NodeDataClass(currentNode));
        }
        while (edgesIter.hasNext()) {
            EdgeDataClass currentEdge = edgesIter.next();
            inverted.connect(currentEdge.getDest(), currentEdge.getSrc(), currentEdge.getWeight());
        }
        return inverted;
    }


}




