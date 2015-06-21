import sun.font.FontRunIterator;

import java.io.IOException;

/**
 * Created by wjdbr on 15/6/21.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello World");
        Graph g = new Graph();
        g.createGraph("./files/Graph");
        g.print();
        for (int i = 0; i < g.V(); i++) {
            System.out.print(i + ":");
            g.dfs(i);
            System.out.println();
            g.bfs(i);
            System.out.println();
        }

    }
}
