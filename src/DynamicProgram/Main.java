package DynamicProgram;

/**
 * 动态规划 0-1背包测试用例
 */
public class Main {

    public static void main(String[] args) {
        int[][] commodities = {{5, 6, 3},
                                {4, 5, 2}};

        DynamicProgram dp = new DynamicProgram(commodities, 9);
        dp.solve();
        dp.findMaxValue();
        dp.outPutMatrix();
    }
}
