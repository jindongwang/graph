# graph
用Java实现一个图，用了常用的邻接矩阵和邻接表两种方式来实现。
##创建图的文件格式
		顶点数
		边数
		顶点1 顶点2 权值
		……
##创建图
		Graph g = new AdjGraph(Graph.GraphType.directed);
        g.createGraph("./files/Graph");
  
  or
  
        Graph g = new MatrixGraph(Graph.GraphType.directed);
        g.createGraph("./files/Graph");
##打印图
		g.print();

##遍历
		g.dfs();
		g.bfs();
##最短路径
		float length = g.shortestPath(0,3);
##图的接口
	interface Graph {
    	float INFINITY = 9999;

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


        
