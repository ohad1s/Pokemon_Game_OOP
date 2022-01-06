package json;


import graph.Edge;
import graph.Vertex;

import java.util.ArrayList;
import java.util.List;

public class GraphJson {
    public List<EdgeForJson> Edges;
    public List<NodeForJson> Nodes;

    public GraphJson() {
        this.Edges = new ArrayList<>();
        this.Nodes = new ArrayList<>();
    }

    public void addNode(Vertex toAdd) {
        double x = toAdd.getLocation().x();
        double y = toAdd.getLocation().y();
        double z = toAdd.getLocation().z();
        String pos = "" + x + "," + y + "," + z;
        NodeForJson jsonNode = new NodeForJson(pos, toAdd.getKey());
        Nodes.add(jsonNode);
    }

    public void addEdge(Edge toAdd){
        int src = toAdd.getSrc();
        double w = toAdd.getWeight();
        int dest = toAdd.getDest();
        EdgeForJson jsonEdge = new EdgeForJson(src, w, dest);
        Edges.add(jsonEdge);
    }
}
