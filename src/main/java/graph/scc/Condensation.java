package graph.scc;

import common.Edge;
import common.Graph;

public class Condensation {
    public static Graph build(Graph g, TarjanSCC.Result scc){
        Graph dag = new Graph(scc.compCount);
        boolean[][] seen = new boolean[scc.compCount][scc.compCount];
        for(int u=0;u<g.n;u++){
            int cu=scc.compId[u];
            for(Edge e:g.adj.get(u)){
                int cv=scc.compId[e.v];
                if(cu!=cv && !seen[cu][cv]){
                    dag.addEdge(cu,cv,e.w);
                    seen[cu][cv]=true;
                }
            }
        }
        return dag;
    }
}
