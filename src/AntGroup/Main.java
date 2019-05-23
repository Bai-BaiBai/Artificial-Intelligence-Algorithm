package AntGroup;

/**
 * 蚁群算法解决TSP问题
 */
public class Main {

    public static void main(String[] args) {

        //城市的位置
        int[][] position = {{1,4,6,8,10,12,14,16,18,20},
                            {1,4,6,8,10,12,14,16,18,20}};

        City cities = new City(position);
        Ant ant = new Ant(cities);

        //输出城市之间距离
        for (int i = 0; i < cities.CITYSIZE; i++) {
            for (int j = 0; j < cities.CITYSIZE; j++) {
                System.out.print(cities.distance[i][j] + " ");
            }
            System.out.println();
        }

        //同一个蚂蚁爬100次，每次旅行完输出一次路径
        for (int i = 0; i < 100; i++) {
            ant.travel(0);
            int[] res = ant.getPath();
            for (int j : res) {
                System.out.print(j + " ");
            }
            System.out.println();
            ant.initPath();
            ant.getInitVisitedFlag();
        }

    }
}
