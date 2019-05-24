package AntGroup;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 蚁群算法解决TSP问题
 * 存在问题：路径收敛效果不好
 * City类中的信息素更新操作只是将走过的距离取倒数*信息素扩大倍数+到原来的信息素值上，
 * 如果城市间距离太近，需要将信息素扩大倍数设置为<1；如果距离太远需要将信息素扩大倍数设置得大一点
 */
public class AntCityAlgorithm {

    public void travel(Ant ant){
        //同一个蚂蚁每次旅行完输出一次路径
        int minDistance = Integer.MAX_VALUE;
        int[] minPath = new int[City.CITYSIZE];
        for (int i = 0; i < 1; i++) {
            ant.travel();
            int[] res = ant.getPath();
//            for (int j : res) {
//                System.out.print(j + " ");
//            }
            int distance = ant.getPathDistance();
//            System.out.print(" 此路径距离为：" + distance);
            if (distance < minDistance) {
                minDistance = distance;
                minPath = res;
            }
//            System.out.println();
            ant.initPath();
            ant.getInitVisitedFlag();
        }
        System.out.print("最短路径为：");
        for (int j : minPath) {
            System.out.print(j + " ");
        }
        System.out.println(" 行走距离为：" + minDistance);

    }

    public static void main(String[] args) {

        //城市的位置
        int[][] position = {{10, 23, 5, 11, 15, 22, 7, 12, 13, 20},
                            {10, 4, 6, 13, 7, 12, 22, 15, 11, 20}};

        City cities = new City(position);
        AntCityAlgorithm algorithm = new AntCityAlgorithm();


        //每个城市一个蚂蚁，分别走1,000,000次，提交线程池中执行
        ExecutorService pool = Executors.newCachedThreadPool();
        for (int i = 0; i < City.CITYSIZE; i++) {
            final int j = i;
            pool.submit(() -> algorithm.travel(new Ant(cities, j)));
        }
        pool.shutdown();



//        cities.outPutPheromone();
    }
}
