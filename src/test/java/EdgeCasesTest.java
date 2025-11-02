import common.Graph;
import graph.scc.TarjanSCC;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EdgeCasesTest {
    @Test
    void singleNode(){
        Graph g=new Graph(1);
        var r=new TarjanSCC(g).run();
        assertEquals(1,r.compCount);
        assertEquals(1,r.comps.get(0).size());
    }
}
