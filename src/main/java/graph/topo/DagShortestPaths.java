package graph.dagsp;

import common.Edge;
import common.Graph;
import common.Metrics;
import java.util.*;

public class DagShortestPaths {
    public static class Result {
        public double[] dist;
        public int[] parent;
        public Metrics metrics = new Metrics();
    }

    public static Result singleSource(Graph dag, java.util.List<Integer> topo, int s){
        long t0=System.nanoTime();
        int n=dag.n;
        double INF=1e300;
        double[] dist=new double[n]; int[] parent=new int[n];
        Arrays.fill(dist,INF); Arrays.fill(parent,-1);
        dist[s]=0;

        for(int u:topo){
            if(dist[u]>=INF/2) continue;
            for(Edge e:dag.adj.get(u)){
                double nd=dist[u]+e.w;
                if(nd<dist[e.v]){ dist[e.v]=nd; parent[e.v]=u; }
            }
        }
        Result r=new Result();
        r.dist=dist; r.parent=parent;
        for(int u:topo) r.metrics.relaxations+=dag.adj.get(u).size();
        r.metrics.timeMs=(System.nanoTime()-t0)/1_000_000.0;
        return r;
    }

    public static java.util.List<Integer> rebuildPath(int t,int[] parent){
        java.util.List<Integer> path=new ArrayList<>();
        for(int v=t; v!=-1; v=parent[v]) path.add(v);
        java.util.Collections.reverse(path);
        return path;
    }
}
