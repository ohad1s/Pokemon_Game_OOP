package graph;
import java.util.*;

public class DiGraph {

    private Hashtable<Integer, Vertex> vertices;
    private Hashtable<String, Edge> edges;
    private Hashtable<Integer, HashSet<Edge>> edgesFromSpecificVertex;
    private int MC;

    /**
     * this method is the constructor that initiates the DirectedWeightedGraph.
     */
    public DiGraph() {
        this.vertices = new Hashtable<>();
        this.edges = new Hashtable<>();
        this.edgesFromSpecificVertex = new Hashtable<Integer, HashSet<Edge>>();
        this.MC = 0;
    }

    /**
     * this method returns a vertex by given key. returns null in case this vertex does not exist in the graph.
     *
     * @param key - the node_id
     * @return
     */
    public Vertex getNode(int key) {
        if (vertices.containsKey(key)) {
            Vertex toReturn = vertices.get(key);
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
    public Edge getEdge(int src, int dest) {
        String key = "" + src + "," + dest;
        if (edges.containsKey(key)) {
            Edge toReturn = edges.get(key);
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
    public Hashtable<Integer, Vertex> getVertices() throws RuntimeException {
        return vertices;
    }

    /**
     * this method returns the graph's edges HashTable.
     *
     * @return
     */
    public Hashtable<String, Edge> getEdges() {
        return edges;
    }

    /**
     * this method returns the graph's HashTable of the HashSets of edges leaving all nodes.
     *
     * @return
     */
    public Hashtable<Integer, HashSet<Edge>> getEdgesFromSpecificVertex() {
        return edgesFromSpecificVertex;
    }

    /**
     * this method receives a vertex, and adds it to the graph in case it does not exist already.
     *
     * @param n
     */

    public void addNode(Vertex n) {
        int idKey = n.getKey();
        if (!vertices.containsKey(idKey)) {
            vertices.put(idKey, n);
            HashSet<Edge> toAdd = new HashSet<>();
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
            Edge toConnect = new Edge(src, dest, weight);
            edges.put(key, toConnect);
            HashSet<Edge> srcNodeEdges = edgesFromSpecificVertex.get(src);
            srcNodeEdges.add(toConnect);
            MC++;
        }
    }

    /**
     * this method returns an iterator over the graph's vertexes.
     *
     * @return
     */

    public Iterator<Vertex> nodeIter() throws ConcurrentModificationException{
        return this.vertices.values().iterator();
    }

    /**
     * this method returns an iterator over the graph's edges.
     *
     * @return
     */

    public Iterator<Edge> edgeIter() {
        return edges.values().iterator();
    }

    /**
     * this method returns an iterator over edges leaving a specific vertex.
     * if the vertex does not exist in the graph, the method will return null.
     *
     * @param node_id
     * @return
     */

    public Iterator<Edge> edgeIter(int node_id) {
        if (vertices.containsKey(node_id)) {
            HashSet<Edge> toIterate = edgesFromSpecificVertex.get(node_id);
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

    public Vertex removeNode(int key) {
        if (vertices.containsKey(key)) {
            Vertex toRemove = vertices.get(key);
            Iterator<Edge> edgeDataIterator = edgeIter();
            ArrayList<String> keysToRemove = new ArrayList<>();
            while (edgeDataIterator.hasNext()) {
                Edge edgeToCheck = edgeDataIterator.next();
                int currentSrc = edgeToCheck.getSrc();
                int currentDest = edgeToCheck.getDest();
                if (currentSrc == key || currentDest == key) {
                    String keyToRemove = "" + currentSrc + "," + currentDest;
                    keysToRemove.add(keyToRemove);
                }
            }
            for (String keyToRemove : keysToRemove) {
                Edge edgeToRemove = edges.remove(keyToRemove);
                if (edgeToRemove.getDest() == key) {
                    int edgeToRemoveSrc = edgeToRemove.getSrc();
                    HashSet<Edge> destVertexEdges = edgesFromSpecificVertex.get(edgeToRemoveSrc);
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
    public Edge removeEdge(int src, int dest) {
        String key = "" + src + "," + dest;
        if (edges.containsKey(key)) {
            Edge toRemove = edges.remove(key);
            HashSet<Edge> srcEdges = edgesFromSpecificVertex.get(src);
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
