
import com.sun.codemodel.internal.JForEach;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by wjdbr on 15/6/21.
 */

class Node {
    Integer vertex;
    float weight;

    public Node(Integer v,float w) {
        this.vertex = v;
        this.weight = w;
    }
}

public class AdjGraph implements Graph{
    private int V;
    private int E;
    private ArrayList<ArrayList<Node>> adj;
    private Graph.GraphType type;

    public AdjGraph(Graph.GraphType t) {
        this.type = t;
    }
    public AdjGraph() {
        this.type = Graph.GraphType.undirected;
    }

    @Override
    public void initialize(int v,int e) {
        this.V = v;
        this.E = e;
        this.adj = new ArrayList<>(v);
        for (int i = 0; i < v; i++) {
            ArrayList<Node> line = new ArrayList<>();
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
                this.adj.get(v1).add(new Node(v2,w));
                if (type == GraphType.undirected) {
                    this.adj.get(v2).add(new Node(v1,w));
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
            for (int j = 0; j < this.adj.get(i).size(); j++) {
                System.out.println(i + " " + this.adj.get(i).get(j).vertex + " " + this.adj.get(i).get(j).weight);
            }
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
    public float weight(int v1, int v2) {
        for (Node node : adj.get(v1)) {
            if (node.vertex == v2) {
                return node.weight;
            }
        }
        return INFINITY;
    }

    @Override
    public boolean isAdjacent(int v1, int v2) {
        ArrayList<Node> nodeList = adj.get(v1);
        for (Node node : nodeList) {
            if (node.vertex == v2) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void dfs(int v) {
        boolean[] visited = new boolean[this.V()];
        dfsMethod(v,visited);
    }
    public void dfsMethod(int v,boolean[] mark) {
        mark[v] = true;
        for (Node node : adj.get(v)) {
            if (!mark[node.vertex]) {
                System.out.print(node.vertex + " ");
                dfsMethod(node.vertex, mark);
            }
        }
    }

    @Override
    public void bfs(int v) {
        boolean[] visited = new boolean[this.V()];
        Queue<Integer> queue = new PriorityQueue<>();
        queue.add(v);
        visited[v] = true;
        while (!queue.isEmpty()) {
            int d = queue.remove();
            for (Node node : adj.get(d)) {
                if (!visited[node.vertex]) {
                    visited[node.vertex] = true;
                    System.out.print(node.vertex + " ");
                    queue.add(node.vertex);
                }
            }
        }
    }

    @Override
    public float shortestPath(int s, int t) {
        if (this.type != GraphType.directed) {
            return -1;
        }
        Set<Integer> s2 = new HashSet<>();
        ArrayList<Integer> path = new ArrayList<>();
        path.add(s);
        float[] dist = new float[this.V()];
        for (int i = 0; i < this.V(); i++) {
            if (i != s) {
                s2.add(i);
                if (this.isAdjacent(s,i)) {
                    dist[i] = weight(s,i);
                } else {
                    dist[i] = INFINITY;
                }
            } else {
                dist[i] = 0;
            }
        }
        while (s2.size() > 0) {
            float min = INFINITY;
            int k = -1;
            for (int i = 0; i < dist.length; i++) {
                if (dist[i] != INFINITY && s2.contains(i)) {
                    if (min > dist[i]) {
                        min = dist[i];
                        k = i;
                    }
                }
            }
            for (int i = 0; i < dist.length; i++) {
                if (this.isAdjacent(s,k) && this.isAdjacent(k,i)) {
                    if (dist[i] > weight(s,k) + weight(k,i)) {
                        dist[i] = weight(s,k) + weight(k,i);
                        if (i == t) {
                            path.add(k);
                        }
                    }
                }
            }
            s2.remove(k);
        }
        path.add(t);
        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i) + " ");
        }
        return dist[t];
    }

}
