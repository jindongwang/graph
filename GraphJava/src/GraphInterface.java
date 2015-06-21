import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by wjdbr on 15/6/21.
 */
public interface GraphInterface {
    void createGraph(int v,int e);
    void createGraph(String fileName) throws IOException;
    void print();
    int V();
    int E();
    int degree(int v);
    int maxDegree();
    boolean isAdjacent(int v1,int v2);
    void dfs(int v);
    void bfs(int v);
}
