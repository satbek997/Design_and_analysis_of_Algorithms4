package common;

import java.util.*;

public class Graph {
    public final int n;
    public final java.util.List<java.util.List<Edge>> adj;
    public Graph(int n){
        this.n=n; this.adj=new ArrayList<>(n);
        for(int i=0;i<n;i++) adj.add(new ArrayList<>());
    }
    public void addEdge(int u,int v,double w){ adj.get(u).add(new Edge(u,v,w)); }
    public int edgeCount(){ int m=0; for(var l:adj) m+=l.size(); return m; }
}
