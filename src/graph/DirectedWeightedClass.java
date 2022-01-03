package graph;
import java.util.*;
import graph.*;
import json.*;

public class DirectedWeightedClass  {

    private Hashtable<Integer, NodeDataClass> vertices;
    private Hashtable<String, EdgeDataClass> edges;
    private Hashtable<Integer, HashSet<EdgeDataClass>> edgesFromSpecificVertex;
    private int MC;

    /**
     * this method is the constructor that initiates the DirectedWeightedGraph.
     */
    public DirectedWeightedClass() {
        this.vertices = new Hashtable<>();
        this.edges = new Hashtable<>();
        this.edgesFromSpecificVertex = new Hashtable<Integer, HashSet<EdgeDataClass>>();
        this.MC = 0;
    }

    /**
     * this method returns a vertex by given key. returns null in case this vertex does not exist in the graph.
     *
     * @param key - the node_id
     * @return
     */
    public NodeDataClass getNode(int key) {
        if (vertices.containsKey(key)) {
            NodeDataClass toReturn = vertices.get(key);
            return toReturn;
        } else {
            return null;
        }
    }

    /**
     * this method returns an edge by given source vertex and destination vertex.
     * returns null in case this edge does not exist in the graph.
     *
     * @param src
     * @param dest
     * @return
     */
    public EdgeDataClass getEdge(int src, int dest) {
        String key = "" + src + "," + dest;
        if (edges.containsKey(key)) {
            EdgeDataClass toReturn = edges.get(key);
            return toReturn;
        } else {
            return null;
        }
    }

    /**
     * this method returns the graph's vertexes HashTable.
     *
     * @return
     */
    public Hashtable<Integer, NodeDataClass> getVertices() throws RuntimeException {
        return vertices;
    }

    /**
     * this method returns the graph's edges HashTable.
     *
     * @return
     */
    public Hashtable<String, EdgeDataClass> getEdges() {
        return edges;
    }

    /**
     * this method returns the graph's HashTable of the HashSets of edges leaving all nodes.
     *
     * @return
     */
    public Hashtable<Integer, HashSet<EdgeDataClass>> getEdgesFromSpecificVertex() {
        return edgesFromSpecificVertex;
    }

    /**
     * this method receives a vertex, and adds it to the graph in case it does not exist already.
     *
     * @param n
     */

    public void addNode(NodeDataClass n) {
        int idKey = n.getKey();
        if (!vertices.containsKey(idKey)) {
            vertices.put(idKey, n);
            HashSet<EdgeDataClass> toAdd = new HashSet<>();
            edgesFromSpecificVertex.put(idKey, toAdd);
            MC++;
        }

    }

    /**
     * this method connects two vertexes by given source vertex and destination vertex.
     *
     * @param src    - the source vertex of the edge.
     * @param dest   - the destination vertex of the edge.
     * @param weight
     */

    public void connect(int src, int dest, double weight) {
        if(!vertices.containsKey(src) || !vertices.containsKey(dest)){
            return;
        }
        String key = "" + src + "," + dest;
        if (!edges.containsKey(key)) {
            EdgeDataClass toConnect = new EdgeDataClass(src, dest, weight);
            edges.put(key, toConnect);
            HashSet<EdgeDataClass> srcNodeEdges = edgesFromSpecificVertex.get(src);
            srcNodeEdges.add(toConnect);
            MC++;
        }
    }

    /**
     * this method returns an iterator over the graph's vertexes.
     *
     * @return
     */

    public Iterator<NodeDataClass> nodeIter() throws ConcurrentModificationException{
        return this.vertices.values().iterator();
    }

    /**
     * this method returns an iterator over the graph's edges.
     *
     * @return
     */

    public Iterator<EdgeDataClass> edgeIter() {
        return edges.values().iterator();
    }

    /**
     * this method returns an iterator over edges leaving a specific vertex.
     * if the vertex does not exist in the graph, the method will return null.
     *
     * @param node_id
     * @return
     */

    public Iterator<EdgeDataClass> edgeIter(int node_id) {
        if (vertices.containsKey(node_id)) {
            HashSet<EdgeDataClass> toIterate = edgesFromSpecificVertex.get(node_id);
            return toIterate.iterator();
        } else {
            return null;
        }
    }

    /**
     * this method removes than returns a vertex from the graph by given key. the method removes all edges connected to
     * this vertex, leaving from it or arriving to it.
     * in case the vertex does not exist in the graph the method will return null.
     *
     * @param key
     * @return
     */

    public NodeDataClass removeNode(int key) {
        if (vertices.containsKey(key)) {
            NodeDataClass toRemove = vertices.get(key);
            Iterator<EdgeDataClass> edgeDataIterator = edgeIter();
            ArrayList<String> keysToRemove = new ArrayList<>();
            while (edgeDataIterator.hasNext()) {
                EdgeDataClass edgeToCheck = edgeDataIterator.next();
                int currentSrc = edgeToCheck.getSrc();
                int currentDest = edgeToCheck.getDest();
                if (currentSrc == key || currentDest == key) {
                    String keyToRemove = "" + currentSrc + "," + currentDest;
                    keysToRemove.add(keyToRemove);
                }
            }
            for (String keyToRemove : keysToRemove) {
                EdgeDataClass edgeToRemove = edges.remove(keyToRemove);
                if (edgeToRemove.getDest() == key) {
                    int edgeToRemoveSrc = edgeToRemove.getSrc();
                    HashSet<EdgeDataClass> destVertexEdges = edgesFromSpecificVertex.get(edgeToRemoveSrc);
                    destVertexEdges.remove(edgeToRemove);

                }
            }
            edgesFromSpecificVertex.remove(key);
            vertices.remove(key);
            MC++;
            return toRemove;
        } else {
            return null;
        }
    }

    /**
     * this method removes an edge from the graph by given source vertex and destination vertex.
     * the method removes the edge from edges HashTable, and also removing the edge from the HashSet
     * of the source vertex.
     * in case the edge does not exist in the graph, the function will return null;
     *
     * @param src
     * @param dest
     * @return
     */
    public EdgeDataClass removeEdge(int src, int dest) {
        String key = "" + src + "," + dest;
        if (edges.containsKey(key)) {
            EdgeDataClass toRemove = edges.remove(key);
            HashSet<EdgeDataClass> srcEdges = edgesFromSpecificVertex.get(src);
            srcEdges.remove(toRemove);
            MC++;
            return toRemove;
        } else {
            return null;
        }
    }


    /**
     * this method returns the number of vertexes in the graph.
     *
     * @return
     */

    public int nodeSize() {
        return vertices.size();
    }

    /**
     * this method returns the number of edges in the graph.
     *
     * @return
     */

    public int edgeSize() {
        return edges.size();
    }

    /**
     * this method returns the number of changes done to the graph.
     *
     * @return
     */
    public int getMC() {
        return this.MC;
    }

}
