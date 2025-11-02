package common;

public class Metrics {
    public long dfsVisits=0, dfsEdges=0;   
    public long queuePush=0, queuePop=0;   
    public long relaxations=0;             
    public double timeMs=0.0;

    public String toTableRow(String name,int n,int m){
        return "%-10s n=%d m=%d | dfsV=%d dfsE=%d | push=%d pop=%d | relax=%d | t=%.3f ms"
                .formatted(name,n,m,dfsVisits,dfsEdges,queuePush,queuePop,relaxations,timeMs);
    }
}
