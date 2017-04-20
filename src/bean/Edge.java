package bean;

public class Edge {
    double weight;
    Vertex source;
    Vertex des;
    public Edge(Vertex source, Vertex des, double weight){
        this.source = source;
        this.des  = des;
        this.weight = weight;
    }
}
