import java.io.IOException;

/**
 * Created by wjdbr on 15/6/21.
 */

public interface Graph {
    public static final float INFINITY = 9999;

    enum GraphType {
        directed,
        undirected
    }
    void initialize(int v,int e);
    void createGraph(String fileName) throws IOException;
    void print();
    int V();
    int E();
    int degree(int v);
    int maxDegree();
    float weight(int v1,int v2);
    boolean isAdjacent(int v1,int v2);
    void dfs(int v);
    void bfs(int v);
    float shortestPath(int s,int t);
}
