package com.example.kruscaloracle.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Edata {
    public int id;//
    public String start;//边的起点
    public String end;//边的终点
    public int weight;//边的权值

    public  Edata (String start,String end,int weight){
        this.start=start;
        this.end=end;
        this.weight=weight;
    }

    //重写toString, 便于输出边信息
    @Override
    public String toString() {
        return "<" + start + ", " + end + ">" +"  权值："+ weight ;
    }
}
