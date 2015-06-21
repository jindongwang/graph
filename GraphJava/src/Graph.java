
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by wjdbr on 15/6/21.
 */
public class Graph implements GraphInterface{
    private int V;
    private int E;
    private ArrayList<ArrayList<Integer>> adj;

    @Override
    public void createGraph(int v,int e) {
        this.V = v;
        this.E = e;
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
            this.createGraph(v,e);
            String line;
            while ((line = br.readLine()) != null) {
                String[] lines = line.split(" ");
                int v1 = Integer.parseInt(lines[0]);
                int v2 = Integer.parseInt(lines[1]);
                this.adj.get(v1).add(v2);
                this.adj.get(v2).add(v1);
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

}
