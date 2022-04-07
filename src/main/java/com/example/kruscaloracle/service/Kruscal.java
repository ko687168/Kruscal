package com.example.kruscaloracle.service;

import com.example.kruscaloracle.pojo.Edata;

public class Kruscal {
    private final String[] vertexs;//顶点数组
    private final int[][] matrix;//邻接矩阵存储图
    private int edgeNum;//边的个数

    //克鲁斯卡尔主算法
    public Edata[] krus(){
        //表示最后结果数组的索引
        int index = 0;
        //用于保存已有最小生成树的每个顶点在当前最小生成树中的终点
        int[] ends = new  int[edgeNum];
        //创建结果数组，保存最后的最小生成树
        Edata[] results = new Edata[edgeNum];

        //获取图中所有的边的集合
        Edata[] edges = getEdges();
        //按照边的权值大小进行排序
        sortEdges(edges);
        //遍历edges数组，将边添加到最小生成树中，判断准备加入的边是否构成回路
        //如果没有，加入results 否则 不加入
        for(int i=0;i<edgeNum;i++){
            //获取第i条边的第一个顶点
            int p1 = getPosition(edges[i].start);
            //获取第i条边的第2个顶点
            int p2 = getPosition(edges[i].end);

            //获取p1这个顶点在已有的最小生成树中的终点
            int m = getEnd(ends,p1);
            //获取p2这个顶点在已有的最小生成树中的终点
            int n = getEnd(ends,p2);
            //是否构成回路
            if(m!=n){//没有构成回路
                ends[m]=n;//设置m在已有最下生成树中的终点
                results[index++]=edges[i];
            }
        }
        //打印结果
        System.out.println("最小生成树为");
        for(int i = 0; i < index; i++) {
            System.out.println(results[i]);
        }
        return results;
    }


    //构造器
    public Kruscal(String[] vertexs, int[][] matrix) {
        //获得顶点个数
        int vlen = vertexs.length;

        //初始化顶点  复制拷贝的方式
        this.vertexs = new String[vlen];
        System.arraycopy(vertexs, 0, this.vertexs, 0, vertexs.length);

        //初始化边   用顶点的个数创建邻接矩阵 拷贝matrix
        this.matrix = new int[vlen][vlen];
        //
        for (int i = 0; i < vlen; i++) {
            System.arraycopy(matrix[i], 0, this.matrix[i], 0, vlen);
        }

        //统计边
        for (int i = 0; i < vlen; i++) {
            for (int j = i + 1; j < vlen; j++) {
                /*如果matrix[i][j]不等于INF，说明i和j两个顶点之间存在边，边数加一*/
                if (this.matrix[i][j] != 0) {
                    edgeNum++;
                }
            }
        }
    }

    //打印邻接矩阵
    public void print(int[][] matrix,String[] vertexs) {
        System.out.println("邻接矩阵为:");
        for (int i = 0; i < vertexs.length; i++) {
            for (int j = 0; j < vertexs.length; j++) {
                /*输出12位整型，不够12位向右对齐*/
                System.out.printf("%12d\t", matrix[i][j]);
            }
            System.out.println();
        }
    }


    //对边进行冒泡排序
    private void sortEdges(Edata[] edges) {
        for (int i = 0; i < edges.length - 1; i++) {
            for (int j = 0; j < edges.length - 1 - i; j++) {


                if (edges[j].weight > edges[j + 1].weight) {

                    Edata tmp = edges[j];
                    edges[j] = edges[j + 1];
                    edges[j + 1] = tmp;
                }
            }
        }

    }

    /**ch为顶点的值，如'A'，'B'，如果传入顶点，找到该顶点在数组中的位置则返回位置
     * 如果找不到，返回-1
     * */
    private  int getPosition(String ch){
        for (int i = 0; i < vertexs.length; i++) {
            if (vertexs[i].equals(ch)) {//找到了
                return i;
            }
        }
        return -1;//找不到
    }


    /**获取图中边，放到EData[] 数组中，后面我们需要遍历该数组 通过matrix 邻接矩阵来获取
     * 形如['A','B',12]
     * **/
    private Edata[] getEdges() {

        int index = 0;
        Edata[] edges = new Edata[edgeNum];
        //通过遍历邻接矩阵拿到相邻两点之间的权值 只遍历一半就行，无向图遍历上三角。
        for (int i = 0; i < vertexs.length; i++) {
            //j=i+1为了排除自己和自己连线出现null
            for (int j = i + 1; j < vertexs.length; j++) {
                if (matrix[i][j] != 0) {
                    edges[index++] = new Edata(vertexs[i], vertexs[j], matrix[i][j]);
                }

            }
        }

        return edges;


    }

    /**
     * 获取下标为i的顶点的终点，用于判断两个顶点的终点是否相同
     * ends数组记录了各个顶点对应的终点是哪个
     * i表示传入顶点对应的下标
     * 返回的是下标为i的这个顶点对应的终点的下标
     * */
    private int getEnd(int[] ends,int i){
        //如果传过来ends[i]=0;返回i 说明终点是自身
        while(ends[i]!=0){
            i=ends[i];
        }
        return i;
    }




}
