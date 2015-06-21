
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by wjdbr on 15/6/21.
 */


public class Graph implements GraphInterface{
    private int V;
    private int E;
    private ArrayList<ArrayList<Integer>> adj;
    private GraphType type;
    private Integer[][] weight;
    public Graph(GraphType t) {
        this.type = t;
    }
    public Graph() {
        this.type = GraphType.undirected;
    }
    @Override
    public void initialize(int v,int e) {
        this.V = v;
        this.E = e;
        this.weight = new Integer[this.V()][this.V()];
        this.adj = new ArrayList<>(v);
        for (int i = 0; i < v; i++) {
            ArrayList<Integer> line = new ArrayList<>();
            adj.add(line);
        }
    }

    @Override
    public void createGraph(String fileName) throws IOException {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            int v = Integer.parseInt(br.readLine());
            int e = Integer.parseInt(br.readLine());
            this.initialize(v, e);
            String line;
            while ((line = br.readLine()) != null) {
                String[] lines = line.split(" ");
                int v1 = Integer.parseInt(lines[0]);
                int v2 = Integer.parseInt(lines[1]);
                int w = Integer.parseInt(lines[2]);
                this.adj.get(v1).add(v2);
                this.weight[v1][v2] = w;
                if (type == GraphType.undirected) {
                    this.adj.get(v2).add(v1);
                    this.weight[v2][v1] = w;
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void print() {
        System.out.println("V:" + this.V);
        System.out.println("E:" + this.E);
        for (int i = 0; i < this.V; i++) {
            System.out.print(i + ":"+ " ");
            for (int j = 0; j < this.adj.get(i).size(); j++) {
                System.out.print(this.adj.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }

    @Override
    public int V() {
        return this.V;
    }

    @Override
    public int E() {
        return this.E;
    }

    @Override
    public int degree(int v) {
        return adj.get(v).size();
    }

    @Override
    public int maxDegree() {
        int max = 0;
        for (int i = 0; i < this.V(); i++) {
            if (max < this.adj.get(i).size()) {
                max = this.adj.get(i).size();
            }
        }
        return max;
    }

    @Override
    public boolean isAdjacent(int v1, int v2) {
        ArrayList<Integer> bag = adj.get(v1);
        return bag.contains(v2);
    }

    @Override
    public void dfs(int v) {
        boolean[] visited = new boolean[this.V()];
        dfsMethod(v,visited);
    }
    public void dfsMethod(int v,boolean[] mark) {
        mark[v] = true;
        adj.get(v).stream().filter(i -> !mark[i]).forEach(i -> {
            System.out.print(i + " ");
            dfsMethod(i, mark);
        });
    }

    @Override
    public void bfs(int v) {
        boolean[] visited = new boolean[this.V()];
        Queue<Integer> queue = new PriorityQueue<>();
        queue.add(v);
        visited[v] = true;
        while (!queue.isEmpty()) {
            int d = queue.remove();
            adj.get(d).stream().filter(integer -> !visited[integer]).forEach(integer -> {
                visited[integer] = true;
                System.out.print(integer + " ");
                queue.add(integer);
            });
        }
    }

    @Override
    public void shortestPath(int s, int t) {
        if (this.type != GraphType.directed) {
            return;
        }
        Set<Integer> s1 = new HashSet<>();
        Set<Integer> s2 = new HashSet<>();
        int[] dist = new int[this.V()];
        s1.add(s);
        for (int i = 0; i < this.V(); i++) {
            if (i != s) {
                s2.add(i);
                if (this.isAdjacent(s,i)) {
                    dist[i] = weight[s][i];
                } else {
                    dist[i] = 9999;
                }
            } else {
                dist[i] = 0;
            }
        }
        while (s1 != s2) {
            Integer min = 9999;
            int k = -1;
            for (int i = 0; i < dist.length; i++) {
                if (dist[i] != 9999 && dist[i] != 0) {
                    if (min > dist[i]) {
                        min = dist[i];
                        k = i;
                    }
                }
            }
            s2.remove(k);
            s1.add(k);
            for (int i = 0; i < dist.length; i++) {
                if (this.isAdjacent(k,i)) {
                    if (dist[i] > weight[s][k] + weight[k][i]) {
                        dist[i] = weight[s][k] + weight[k][i];
                    }
                }
            }
        }
    }
}
