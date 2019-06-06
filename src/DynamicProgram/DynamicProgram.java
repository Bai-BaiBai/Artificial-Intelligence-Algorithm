package DynamicProgram;

import java.util.ArrayList;

/**
 * 动态规划解决0-1背包问题
 * 有三个商品：①价5重4 ②价6重5 ③价3重2，背包最大承重为9
 * 决策变量：X1,X2,X3 可取值{0,1}
 * 约束条件：4X1+5X2+2X3 <= 9
 * 目标函数：argMax(5X1+6X2+3X3)
 */
public class DynamicProgram {

    private int room;//背包最大容量
    private int[][] commodities;//商品价值、重量矩阵，[0]为价值，[1]为重量
    private int quantity;//商品数量

    private int[][] matrix;//动态规划矩阵

    public DynamicProgram(int[][] commodities, int room) {
        this.commodities = commodities;
        this.quantity = commodities[0].length;
        this.room = room;
        //要多开辟一个空间存放0的情况
        this.matrix = new int[room+1][quantity+1];//[0][0]代表背包容量为0可选物品为0件的情况 对应的背包最大价值
    }

    //解决问题接口
    public void solve(){

        //matrix初始化时，默认matrix[i][0]的一列全为0，也就是可选商品数量为0件时，不管背包容量多大，最大价值都为0

        //讨论拿不拿第index个物品
        //这里的index是在物品在矩阵中的下标，第一个物品的index就是1，0号代表没有物品
        //所以从commodities数组中取值时，要减去1的偏移量
        for (int index = 1; index < quantity+1; index++) {
            //该物品的重量和价值
            int weight = commodities[1][index-1];//有1的偏移量，Matrix第1列代表第一个物品，而第一个物品的下标是0
            int value = commodities[0][index-1];

            for (int j = 0; j < room+1; j++) {
                //如果背包容量大于商品index号重量
                if (j >= commodities[1][index-1]){
                    //取最大值(不拿当前index号物品， 拿当前index号物品)          当前物品的价值+容量减去占用重量时的最大价值
                    int max = Math.max(matrix[j][index - 1], value+matrix[j - weight][index - 1]);
                    matrix[j][index] = max;
                }
                //不大于的话matrix[j][index]存index-1号物品的情况，也就是不拿当前物品的最大值
                else {
                    matrix[j][index] = matrix[j][index-1];
                }
            }
        }
    }

    //根据生成的矩阵，找出最优解
    public void findMaxValue(){
        System.out.println("最大能装物品的价值为：" + matrix[room][quantity]);
        ArrayList<Integer> result = new ArrayList<>();
        int rom = room;//根据rom作为行标
        for (int i = quantity; i > 0 ; i--) {
            //如果前i个物品的最优值不等于前i-1个物品的最优值，那么说明第i个物品是需要选择的
            if (matrix[rom][i] != matrix[rom][i-1]){
                result.add(i);
                rom -= commodities[1][i];
            }
            //如果相等，则说明第i个物品不被选择，
            // i--, 看前面i个物品的情况
        }

        //输出选择的物品index号
        System.out.print("选择的物品为：");
        for (int index : result){
            System.out.print(index + "号 ");
        }
        System.out.println("");
    }

    //测试函数，输出动态规划矩阵
    public void outPutMatrix(){
        System.out.println("物品：0 1 2 3");
        for (int i = 0; i < matrix.length; i++) {
            System.out.print(i + "    ");
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
