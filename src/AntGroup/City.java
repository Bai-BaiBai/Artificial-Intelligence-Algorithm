package AntGroup;

/**
 * 城市相关信息
 * 根据测试用例的城市数量，需要设置 CITYSIZE
 * 根据测试用例的城市间距离，需要合理的设置信息素放大倍数 PHEROMINE_EXPAND_CONSTANT
 */
public class City {

    public static final int CITYSIZE = 10;//城市数
    public static final double PHEROMINE_EXPAND_CONSTANT = 0.15;//信息素更新的放大倍数
    public static final double INITIAL_PHEROMONE = 0.25;//信息素的初始值

    public int[][] distance;//距离向量矩阵
    public volatile double[][] pheromone;//信息素矩阵

    public City(int[][] position){
        initDistance(position);
        initPheromone();
    }

    //初始化城市之间距离，传入城市的坐标二维数组
    private void initDistance(int[][] pos){
        distance = new int[CITYSIZE][CITYSIZE];
        for (int i = 0; i < CITYSIZE; i++) {
            distance[i][i] = Integer.MAX_VALUE;//将本地的距离设为无穷大
            for (int j = i+1; j < CITYSIZE; j++) {
                distance[j][i] = distance[i][j] = Math.abs(pos[0][i] - pos[0][j]) + Math.abs(pos[1][i] - pos[1][j]);
            }
        }
    }

    //初始化城市之间的信息素矩阵
    private void initPheromone(){
        pheromone = new double[CITYSIZE][CITYSIZE];
        for (int i = 0; i < CITYSIZE; i++) {
            pheromone[i][i] = 0;//将本地的信息素设置为0
            for (int j = i+1; j < CITYSIZE; j++) {
                pheromone[i][j] = pheromone[j][i] = INITIAL_PHEROMONE;
            }
        }
    }

    //更新信息素矩阵:每走一步更新一步
    public void updatePheromone(int currentCity, int nextCity){
        double d = pheromone[currentCity][nextCity];
        pheromone[currentCity][nextCity] = pheromone[nextCity][currentCity] =
                d + PHEROMINE_EXPAND_CONSTANT/distance[currentCity][nextCity];
    }

    //控制台输出距离矩阵
    public void outPutDistance(){
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < CITYSIZE; i++) {
            for (int j = 0; j < CITYSIZE; j++) {
                res.append(distance[i][j] + " ");
            }
            res.append('\n');
        }
        System.out.println(res.toString());
    }

    //控制台输出信息素矩阵
    public void outPutPheromone(){
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < CITYSIZE; i++) {
            for (int j = 0; j < CITYSIZE; j++) {
                res.append(pheromone[i][j] + " ");
            }
            res.append('\n');
        }
        System.out.println(res.toString());
    }
}
