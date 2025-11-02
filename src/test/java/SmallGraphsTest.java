import common.Graph;
import graph.scc.TarjanSCC;
import graph.scc.Condensation;
import graph.topo.KahnTopo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SmallGraphsTest {
    @Test
    void sccTriangle(){
        Graph g=new Graph(3);
        g.addEdge(0,1,1); g.addEdge(1,2,1); g.addEdge(2,0,1);
        var r=new TarjanSCC(g).run();
        assertEquals(1,r.compCount);
        assertEquals(3,r.comps.get(0).size());
        var dag=Condensation.build(g,r);
        assertEquals(1,dag.n); assertEquals(0,dag.edgeCount());
        var topo=KahnTopo.topo(dag);
        assertEquals(1, topo.order.size());
    }
}
