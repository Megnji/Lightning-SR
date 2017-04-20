package bean;

import java.util.List;

public class Graph {
    public List<Edge> edges;
    public List<Vertex> vertices;
    
    public Graph(List<Edge> e,List<Vertex> v){
        edges = e;
        vertices = v;
    }
}
