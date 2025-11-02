package app;

import common.Graph;
import graph.scc.Condensation;
import graph.scc.TarjanSCC;
import graph.topo.KahnTopo;
import graph.dagsp.DagShortestPaths;
import graph.dagsp.DagLongestPath;
import io.GraphIO;

import java.util.*;

public class App {
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Usage:\n  scc <path.json>\n  dagsp <path.json> <sourceCompId>");
            return;
        }
        String mode=args[0]; String path=args[1];

        GraphIO.Loaded L = GraphIO.loadJson(path);
        Graph g = L.g;

        TarjanSCC scc = new TarjanSCC(g);
        var sccRes = scc.run();

        if (mode.equalsIgnoreCase("scc")) {
            System.out.println("SCC count = " + sccRes.compCount);
            for (int cid=0; cid<sccRes.compCount; cid++) {
                System.out.println("Component "+cid+" (size="+sccRes.comps.get(cid).size()+"): "+sccRes.comps.get(cid));
            }
            System.out.println(sccRes.metrics.toTableRow("SCC", g.n, g.edgeCount()));

            Graph dag = Condensation.build(g, sccRes);
            System.out.println("Condensation DAG: n="+dag.n+" m="+dag.edgeCount());

            var topo = KahnTopo.topo(dag);
            System.out.println("Topological order of components: " + topo.order);
            System.out.println(topo.metrics.toTableRow("Topo", dag.n, dag.edgeCount()));

            var expanded = new ArrayList<Integer>();
            for (int cid : topo.order) expanded.addAll(sccRes.comps.get(cid));
            System.out.println("Derived order of tasks after compression: " + expanded);
            return;
        }

        if (mode.equalsIgnoreCase("dagsp")) {
            if (args.length < 3) { System.out.println("Provide <sourceCompId> for DAG-SP."); return; }
            Graph dag = Condensation.build(g, sccRes);
            var topo = KahnTopo.topo(dag);

            int s = Integer.parseInt(args[2]);
            var sp = DagShortestPaths.singleSource(dag, topo.order, s);
            System.out.println(sp.metrics.toTableRow("DAG-SP", dag.n, dag.edgeCount()));
            System.out.println("Distances from component "+s+": "+Arrays.toString(sp.dist));

            int t = dag.n - 1;
            System.out.println("Shortest path "+s+"->"+t+" (by comps): "+DagShortestPaths.rebuildPath(t, sp.parent));

            var lp = DagLongestPath.criticalPath(dag, topo.order);
            System.out.println(lp.metrics.toTableRow("DAG-LONG", dag.n, dag.edgeCount()));
            System.out.println("Critical path (by comps): "+DagLongestPath.rebuildPath(lp.end, lp.parent)+" | length="+lp.best[lp.end]);
            return;
        }

        System.out.println("Unknown mode: "+mode);
    }
}
