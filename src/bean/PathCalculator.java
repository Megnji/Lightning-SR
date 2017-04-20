package bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.*;

public class PathCalculator {
    
    private static Map<Vertex,List<Edge>> edges = new HashMap<>();
    public static List<Path> calcualte(Graph g, Vertex source, Vertex sink){
        Map<Vertex,Double> dist = new HashMap<>();
        Map<Vertex,Boolean> visited = new HashMap<>();
        Map<Vertex,List<Vertex>> previous = new HashMap<>();
        Map<Vertex,List<Vertex>> neighbours = new HashMap<>();
        
        
        for (Vertex v: g.vertices){
            dist.put(v, Double.POSITIVE_INFINITY);
            visited.put(v, false);
            previous.put(v, new LinkedList<Vertex>());
            neighbours.put(v, new LinkedList<Vertex>());
            edges.put(v, new LinkedList<Edge>());
        }
        
        for (Edge e : g.edges){
            neighbours.get(e.source).add(e.des);
            neighbours.get(e.des).add(e.source);
            edges.get(e.source).add(e);
            edges.get(e.des).add(e);
        }
        
        List<Path> result = new ArrayList<>();
        dist.put(source, 0.0);
        List<Vertex> q = new LinkedList<>(); 
        q.add(source);
        
        while (!q.isEmpty()){
            Vertex u = null;
            Double d = Double.POSITIVE_INFINITY;
            for (Vertex v:q){
                if (visited.get(v) == false && dist.get(v)<d){
                    d = dist.get(v);
                    u  = v;
                }
            }
            q.remove(u);
            visited.put(u, true);
            for (Vertex v: neighbours.get(u)){
                double alt = dist.get(u) + distanceBetween(u,v);
                if (alt < dist.get(v)){
                    dist.put(v, alt);
                    previous.get(v).clear();
                    previous.get(v).add(u);
                }else if (alt == dist.get(v)){
                    previous.get(v).add(u);
                }
                
                if (!visited.get(v) && !q.contains(v)){
                    q.add(v);
                }
            }
        }
        
        while (!previous.get(sink).isEmpty()){
            Path p = new Path();
            Vertex temp = sink;
            Vertex parent = temp;
            while (!previous.get(temp).isEmpty()){
                p.vertices.push(temp.name);
                if (previous.get(temp).size() == 1){
                    previous.get(parent).remove(temp);
                }
                parent = temp;
                temp = previous.get(temp).get(0);
            }
            p.vertices.push(source.name);
            result.add(p);
        }
        return result;
        
    }
    
    private static double distanceBetween(Vertex u, Vertex v){
        for (Edge e: edges.get(u)){
            if ((e.des.equals(u) && e.source.equals(v)) || (e.des.equals(v) && e.source.equals(u))){
                return e.weight;
            }
        }
        return Double.POSITIVE_INFINITY;
    }
    
    public static void main(String[] args){
        Vertex va = new Vertex("a");
        Vertex vb = new Vertex("b");
        Vertex vc = new Vertex("c");
        Vertex vd = new Vertex("d");
        Vertex ve = new Vertex("e");
        Vertex vf = new Vertex("f");
        Vertex vz = new Vertex("z");
        List<Vertex> vs = new ArrayList<>();
        vs.add(va);
        vs.add(vb);
        vs.add(vc);
        vs.add(vd);
        vs.add(ve);
        vs.add(vf);
        vs.add(vz);
        Edge eab = new Edge(va,vb,1);
        Edge eac = new Edge(va,vc,2);
        Edge ead = new Edge(va,vd,3);
        Edge ebf = new Edge(vb,vf,3);
        Edge ecf = new Edge(vc,vf,2);
        Edge edf = new Edge(vd,vf,1);
        Edge efz = new Edge(vf,vz,1);
        Edge eae = new Edge(va,ve,3);
        Edge eef = new Edge(ve,vf,3);
        List<Edge> es = new ArrayList<>();
        es.add(eab);
        es.add(eac);
        es.add(ead);
        es.add(ebf);
        es.add(ecf);
        es.add(edf);
        es.add(efz);
        es.add(eae);
        es.add(eef);
        String s = "";
        Graph g = new Graph(es,vs);
        List<Path> ps = calcualte(g,va,vz);
        for (Path p : ps){
            p.print();
            System.out.println();
        }
    }
}
