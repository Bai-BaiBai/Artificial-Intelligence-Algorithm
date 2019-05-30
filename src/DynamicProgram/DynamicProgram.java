package DynamicProgram;

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

    public void solve(){

        //matrix初始化时，默认matrix[i][0]的一列全为0，也就是可选商品数量为0件时，不管背包容量多大，最大价值都为0

        //讨论拿不拿第index个物品
        for (int index = 1; index <= quantity+1; index++) {
            //该物品的重量和价值
            int weight = commodities[1][index];
            int value = commodities[0][index];

            for (int j = 0; j <= room+1; j++) {
                //如果背包容量大于商品index号重量
                if (j >= commodities[1][index]){
                    //取最大值(不拿当前index号物品， 拿当前index号物品)          当前物品的价值+容量减去占用重量时的最大价值
                    int max = Math.max(matrix[j][index - 1], matrix[j - weight][index - 1]+value);
                    matrix[j][index] = max;
                }
                //不大于的话matrix[j][index]存index-1号物品的情况，也就是不拿当前物品的最大值
                else {
                    matrix[j][index] = matrix[j][index-1];
                }
            }
        }
    }
}
