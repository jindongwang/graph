import java.io.IOException;

/**
 * Created by wjdbr on 15/6/21.
 */
class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Graph implementation in Java");

        //region graph in adjacency list
        Graph g = new AdjGraph(Graph.GraphType.directed);
        g.createGraph("./files/Graph");
        g.print();
        for (int i = 0; i < g.V(); i++) {
            System.out.print(i + ":");
            g.dfs(i);
            System.out.println();
            g.bfs(i);
            System.out.println();
        }
        for (int i = 0; i < 5; i++) {
            System.out.println(g.shortestPath(0, i));
        }
        //endregion

        //region graph in adjacency matrix
        Graph g2 = new MatrixGraph(Graph.GraphType.directed);
        g2.createGraph("./files/Graph");
        g2.print();
        for (int i = 0; i < g2.V(); i++) {
            System.out.print(i + ":");
            g2.dfs(i);
            System.out.println();
            g2.bfs(i);
            System.out.println();
        }
        for (int i = 0; i < 5; i++) {
            System.out.println(g2.shortestPath(0, i));
        }
        //endregion

    }
}
