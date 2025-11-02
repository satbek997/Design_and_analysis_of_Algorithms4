package graph.topo;

import common.Edge;
import common.Graph;
import common.Metrics;

import java.util.*;

public class KahnTopo {
    public static class Result {
        public java.util.List<Integer> order = new ArrayList<>();
        public Metrics metrics = new Metrics();
    }

    public static Result topo(Graph dag){
        long t0=System.nanoTime();
        int n=dag.n;
        int[] indeg=new int[n];
        for(int u=0;u<n;u++) for(Edge e:dag.adj.get(u)) indeg[e.v]++;

        Deque<Integer> q=new ArrayDeque<>();
        Result r=new Result();
        for(int i=0;i<n;i++) if(indeg[i]==0){ q.add(i); r.metrics.queuePush++; }

        while(!q.isEmpty()){
            int u=q.remove(); r.metrics.queuePop++; r.order.add(u);
            for(Edge e:dag.adj.get(u)){
                if(--indeg[e.v]==0){ q.add(e.v); r.metrics.queuePush++; }
            }
        }
        r.metrics.timeMs=(System.nanoTime()-t0)/1_000_000.0;
        return r;
    }
}
