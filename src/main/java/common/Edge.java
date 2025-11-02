package common;

public class Edge {
    public final int u, v;
    public final double w;
    public Edge(int u, int v, double w){ this.u=u; this.v=v; this.w=w; }
    @Override public String toString(){ return u + "->" + v + "(" + w + ")"; }
}
