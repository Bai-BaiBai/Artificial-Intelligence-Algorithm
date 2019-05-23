package AntGroup;

/**
 * 城市相关信息
 */
public class City {

    public static final int CITYSIZE = 10;//城市数
    public static final double INITIAL_PHEROMONE = 0.25;//信息素的初始值

    public int[][] distance;//距离向量矩阵
    public double[][] pheromone;//信息素矩阵

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
}
