package com.example.kruscaloracle.controller;

import com.example.kruscaloracle.dao.EdataMapper;
import com.example.kruscaloracle.pojo.Edata;
import com.example.kruscaloracle.service.Dijkstra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

@RestController
public class DijkstraController {

    @Autowired
    EdataMapper edataMapper;

    @GetMapping("/Dijkstra")
    public void Dijkstra() {
        int Edgenums = 0;
        int vernums = 0;
        int index = 0;
        //从数据库中查到邻接矩阵
        List<Edata> Edatas = edataMapper.listEdata();
        //根据边的数量拿到顶点的个数
        if (!Edatas.isEmpty()) {
            while (Edgenums < Edatas.size()) {
                Edgenums++;
            }
            vernums = (int) Math.sqrt(Edgenums);
            //定义数组中包含的顶点
            String[] vertexs = new String[vernums];
            //初始化顶点数组
            //Arrays.fill(vertexs, " ");
            //初始化hashset用于去重
            HashSet<String> set = new HashSet<>();

            for (Edata edata : Edatas) {
                set.add(edata.getStart());
            }
            //遍历装到数组中

            Iterator it = set.iterator();
            while (it.hasNext()) {
                vertexs[index] = String.valueOf(it.next());
                index++;
            }

            //dijkstra算法的邻接矩阵
            int[][] matrix = new int[vernums][vernums];
            //使数据库映射成邻接矩阵
            for (Edata edata : Edatas) {
                int row=0;
                int column=0;

                while(!edata.getStart().equals(vertexs[row])){
                    row++;
                }
                while(!edata.getEnd().equals(vertexs[column])){
                    column++;
                }

                matrix[row][column]=edata.getWeight();
            }
            Dijkstra.Graph  graph =  new Dijkstra.Graph(vertexs,matrix);
            graph.dsj(2);
            graph.showDSJ();

        }
    }
}
