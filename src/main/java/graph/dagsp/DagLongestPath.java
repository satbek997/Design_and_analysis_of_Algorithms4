package graph.dagsp;

import common.Edge;
import common.Graph;
import common.Metrics;
import java.util.*;

public class DagLongestPath {
    public static class Result {
        public double[] best;
        public int[] parent;
        public int end;
        public Metrics metrics = new Metrics();
    }

    public static Result criticalPath(Graph dag, java.util.List<Integer> topo){
        long t0=System.nanoTime();
        int n=dag.n;
        double[] best=new double[n]; int[] parent=new int[n];
        Arrays.fill(parent,-1);

        for(int u:topo){
            for(Edge e:dag.adj.get(u)){
                double cand=best[u]+e.w;
                if(cand>best[e.v]){ best[e.v]=cand; parent[e.v]=u; }
            }
        }
        int end=0;
        for(int i=1;i<n;i++) if(best[i]>best[end]) end=i;

        Result r=new Result();
        r.best=best; r.parent=parent; r.end=end;
        for(int u:topo) r.metrics.relaxations+=dag.adj.get(u).size();
        r.metrics.timeMs=(System.nanoTime()-t0)/1_000_000.0;
        return r;
    }

    public static java.util.List<Integer> rebuildPath(int end,int[] parent){
        java.util.List<Integer> path=new ArrayList<>();
        for(int v=end; v!=-1; v=parent[v]) path.add(v);
        java.util.Collections.reverse(path);
        return path;
    }
}
