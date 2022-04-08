package com.example.kruscaloracle.service;


import java.util.Arrays;

public class Dijkstra {

    public static class Graph {
        private String[] vertexs;
        private int[][] matrix;
        private VisitedVertex vv;//已经访问的顶点的集合

        public Graph(String[] vertexs, int[][] matrix) {
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
        }

        //迪杰斯特拉
        public void dsj(int index) {
            VisitedVertex vv = new VisitedVertex(vertexs.length, index);
            update(index);//更新index顶点到周围顶点的距离和前驱顶点
            for (int j=1;j< vertexs.length;j++){
                index = vv.updateArr();//选择并返回新的访问顶点
                update(index);
            }
        }
        //显示最后结果
        public void showDSJ(){
            vv.show();
        }

        //更新index下标顶点到周围顶点和前驱顶点
        public void update(int index) {
            int len = 0;
            //根据遍历我们的邻接矩阵的matrix[index]行
            for (int j = 0; j < matrix[index].length; j++) {
                //len是出发顶点到index顶点的距离和index到j顶点的和
                len = vv.getDis(index) + matrix[index][j];
                //如果j顶点没有被访问过，并且len小于出发顶点到j顶点的距离就需要更新
                if (!vv.in(j) && len < vv.getDis(j)) {
                    //更新j顶点的前驱为index顶点
                        vv.updatePre(j,index);
                        //更新出发顶点到j顶点的距离
                        vv.updateDis(j,len);
                }
            }
        }


        private static class VisitedVertex {
            //记录各个顶点是否访问过 1or0
            public int[] already_arr;
            //每个下标对应的值为第一个顶点下标，会动态更新
            public int[] pre_visited;
            //记录出发顶点到其他所有顶点的距离
            public int[] dis;

            //构造器

            /**
             * length表示顶点的个数，index出发顶点对应的下标
             */
            public VisitedVertex(int length, int index) {
                this.already_arr = new int[length];
                this.pre_visited = new int[length];
                this.dis = new int[length];
                //初始化dis数组
                Arrays.fill(dis, 0);
                this.already_arr[index]=1;//设置出发顶点已访问过
            }

            //判断index顶点是否访问过
            public boolean in(int index) {
                return already_arr[index] == 1;
            }

            //更新出发顶点到index顶点的距离
            public void updateDis(int index, int length) {
                dis[index] = length;
            }

            //更新pre这个顶点的前驱顶点为index顶点
            public void updatePre(int pre, int index) {
                pre_visited[pre] = index;
            }

            //返回出发顶点到index顶点的距离
            public int getDis(int index) {
                return dis[index];
            }
            //继续选择并返回新的访问节点
            public  int updateArr(){
                int min =65535,index= 0;
                for (int i= 0;i<already_arr.length;i++){
                    if(already_arr[i]==0 && dis[i]< min){
                        min=dis[i];
                        index = i;
                    }
                }
                //更新index顶点被访问过
                already_arr[index] =1;
                return index;
            }
            public void show(){
                System.out.println("===============");
                for (int i: already_arr){
                    System.out.print(i+" ");
                }
                System.out.println();
                for (int i: pre_visited){
                    System.out.print(i+" ");
                }
                System.out.println();
                for (int i: dis){
                    System.out.print(i+" ");
                }
                System.out.println();
            }
        }


    }
}
