import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by wjdbr on 15/6/22.
 */
public class MatrixGraph implements Graph{
    private int V;
    private int E;
    private Graph.GraphType type;
    private float[][] mat;
    public MatrixGraph(Graph.GraphType t) {
        this.type = t;
    }
    public MatrixGraph() {
        this.type = Graph.GraphType.undirected;
    }

    @Override
    public void initialize(int v, int e) {
        this.V = v;
        this.E = e;
        this.mat = new float[v][v];
        for (int i = 0; i < v; i++) {
            for (int j = 0; j < v; j++) {
                this.mat[i][j] = INFINITY;
            }
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
                this.mat[v1][v2] = w;
                if (type == GraphType.undirected) {
                    this.mat[v2][v1] = w;
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
        for (int i = 0; i < this.V(); i++) {
            for (int j = 0; j < this.V(); j++) {
                if (mat[i][j] != INFINITY) {
                    System.out.println(i + " " + j + " " + this.mat[i][j]);
                }
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
        int count = 0;
        for (int i = 0; i < this.V(); i++) {
            if (this.mat[v][i] != INFINITY) {
                count++;
            }
        }
        return count;
    }

    @Override
    public int maxDegree() {
        int max = 0;
        for (int i = 0; i < this.V(); i++) {
            int degree = this.degree(i);
            if (max < degree) {
                max = degree;
            }
        }
        return max;
    }

    @Override
    public float weight(int v1, int v2) {
        return mat[v1][v2];
    }

    @Override
    public boolean isAdjacent(int v1, int v2) {
        return weight(v1, v2) != INFINITY;
    }

    @Override
    public void dfs(int v) {
        boolean[] mark = new boolean[this.V()];
        this.dfsMethod(v,mark);
    }

    private void dfsMethod(int v, boolean[] mark) {
        mark[v] = true;
        for (int i = 0; i < this.V(); i++) {
            if (isAdjacent(v,i) && !mark[i]) {
                mark[i] = true;
                dfsMethod(i,mark);
                System.out.print(i + " ");
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
            for (int i = 0;i < this.V();i++) {
                if (this.isAdjacent(v,i) && !visited[i]) {
                    visited[i] = true;
                    System.out.print(i + " ");
                    queue.add(i);
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

        return dist[t];
    }
}
