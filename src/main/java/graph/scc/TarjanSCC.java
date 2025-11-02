package graph.scc;

import common.Graph;
import common.Metrics;
import java.util.*;

public class TarjanSCC {
    public static class Result {
        public int compCount;
        public int[] compId;
        public java.util.List<java.util.List<Integer>> comps;
        public Metrics metrics = new Metrics();
    }

    private final Graph g;
    private int time=0, compCnt=0;
    private int[] disc, low, compId;
    private boolean[] onStack;
    private Deque<Integer> st = new ArrayDeque<>();
    private Metrics met;

    public TarjanSCC(Graph g){ this.g=g; }

    public Result run(){
        long t0=System.nanoTime();
        int n=g.n;
        disc=new int[n]; Arrays.fill(disc,-1);
        low =new int[n];
        compId=new int[n]; Arrays.fill(compId,-1);
        onStack=new boolean[n];
        met=new Metrics();

        for(int u=0;u<n;u++) if(disc[u]==-1) dfs(u);

        var comps = new ArrayList<java.util.List<Integer>>(compCnt);
        for(int i=0;i<compCnt;i++) comps.add(new ArrayList<>());
        for(int v=0;v<n;v++) comps.get(compId[v]).add(v);

        Result r=new Result();
        r.compCount=compCnt; r.compId=compId; r.comps=comps; r.metrics=met;
        r.metrics.timeMs=(System.nanoTime()-t0)/1_000_000.0;
        return r;
    }

    private void dfs(int u){
        met.dfsVisits++;
        disc[u]=low[u]=time++;
        st.push(u); onStack[u]=true;
        for(var e:g.adj.get(u)){
            met.dfsEdges++;
            int v=e.v;
            if(disc[v]==-1){ dfs(v); low[u]=Math.min(low[u],low[v]); }
            else if(onStack[v]) low[u]=Math.min(low[u],disc[v]);
        }
        if(low[u]==disc[u]){
            while(true){
                int v=st.pop(); onStack[v]=false; compId[v]=compCnt;
                if(v==u) break;
            }
            compCnt++;
        }
    }
}
